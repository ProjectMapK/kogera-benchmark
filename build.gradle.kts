import me.champeau.gradle.JMHPluginExtension
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

plugins {
    kotlin("jvm") version "1.8.22"
    id("me.champeau.gradle.jmh") version "0.5.3"
}

group = "org.wrongwrong"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

enum class Mapper {
    Original,
    Kogera,
    OriginalStrictNullCheck,
    KogeraStrictNullCheck;
}

enum class BenchmarkSet {
    // for CI
    MainSer,
    MainDeser,
    ValueClassSer,
    ValueClassSerWithJsonKUnbox,
    ValueClassDeser,
    WrapperSer,
    WrapperDeser,
    StrictNullChecks,
    // for Local
    OnlyMain,
    Full;
}

fun getOptionOrDefault(name: String, default: Boolean): Boolean =
    project.properties[name]?.let { (it as String).toBoolean() } ?: default

val benchmarkSet: BenchmarkSet = (project.properties["benchmarkSet"] as? String)
    ?.let { BenchmarkSet.valueOf(it) }
    ?: BenchmarkSet.OnlyMain
// @see org.wrongwrong.Mapper for find options
val mapper: Mapper = (project.properties["mapper"] as? String)?.let { Mapper.valueOf(it) } ?: Mapper.Kogera
val isKogera = mapper.name.contains("Kogera")

val isSingleShot: Boolean = getOptionOrDefault("isSingleShot", false)
val isCi: Boolean = System.getenv().containsKey("CI") // True when executed in GitHub Actions
val ciFileName: String? = project.properties["fileName"] as String?

val kogeraVersion = "6e4007d091"
val originalVersion = "2.16.1"

dependencies {
    jmhImplementation(kotlin("reflect"))

    jmhImplementation("com.github.ProjectMapK:jackson-module-kogera:$kogeraVersion")
    jmhImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:$originalVersion")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val generatedSrcPath = "$buildDir/generated/kotlin"
kotlin {
    // for PackageVersion
    sourceSets["jmh"].apply {
        kotlin.srcDir(generatedSrcPath)
    }
}

tasks {
    // Task to generate BenchmarkBase file
    val generateBenchmarkBase by registering(Copy::class) {
        val packageStr = "org.wrongwrong"

        from(
            resources.text.fromString(
                """
package $packageStr

import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

/**
 * @see org.wrongwrong.Mapper
 */
@State(Scope.Benchmark)
abstract class BenchmarkBase {
    val mapper = Mapper.${mapper}.value
}

                """.trimIndent()
            )
        ) {
            rename { "BenchmarkBase.kt" }
        }

        into(file("$generatedSrcPath/${packageStr.replace(".", "/")}"))
    }

    compileKotlin {
        dependsOn.add(generateBenchmarkBase)
        kotlinOptions.jvmTarget = "1.8"
    }
}

fun BenchmarkSet.includes(): List<String> = when (this) {
    BenchmarkSet.MainSer -> listOf("org.wrongwrong.main.ser.*")
    BenchmarkSet.MainDeser -> listOf("org.wrongwrong.main.deser.*")
    BenchmarkSet.ValueClassSer -> listOf("org.wrongwrong.extra.ser.value_class.*")
    BenchmarkSet.ValueClassSerWithJsonKUnbox -> listOf("org.wrongwrong.extra.ser.json_k_unbox_value_class.*")
    BenchmarkSet.ValueClassDeser -> listOf("org.wrongwrong.extra.deser.value_class.*")
    BenchmarkSet.WrapperSer -> listOf("org.wrongwrong.extra.ser.wrapper.*")
    BenchmarkSet.WrapperDeser -> listOf("org.wrongwrong.extra.deser.wrapper.*")
    // missing is excluded from the comparison because they are theoretically less affected by StrictNullChecks
    BenchmarkSet.StrictNullChecks ->
        listOf("org.wrongwrong.extra.deser.Collections", "org.wrongwrong.main.deser.*_Ctor.present")
    BenchmarkSet.OnlyMain -> listOf("org.wrongwrong.main.*")
    BenchmarkSet.Full -> listOf("org.wrongwrong.*")
}

fun JMHPluginExtension.setThrptDetails() {
    if (isCi) {
        // For CI, the setting is focused on score stability.
        warmupForks = 2
        warmupBatchSize = 3
        warmupIterations = 3
        warmup = "1500ms"

        fork = 3
        batchSize = 3
        iterations = 3
        timeOnIteration = "1500ms"
    } else {
        // For local, the setting that produces the minimum stable score experienced on the author's PC.
        warmupForks = 2
        warmupBatchSize = 3
        warmupIterations = 3
        warmup = "1s"

        fork = 2
        batchSize = 3
        iterations = 2
        timeOnIteration = "1500ms"
    }
}

jmh {
    val mode: String

    if (isSingleShot) {
        mode = "ss"
        timeUnit = "ms"

        forceGC = true

        // For CI, the setting is focused on score stability.
        if (isCi) {
            fork = 10
        } else {
            fork = 5
        }
    } else {
        mode = "thrpt"
        forceGC = false

        setThrptDetails()
    }
    benchmarkMode = listOf(mode)

    include = benchmarkSet.includes()
    exclude = if (benchmarkSet == BenchmarkSet.Full && !isKogera)
        listOf("org.wrongwrong.extra.deser.value_class.*")
    else
        emptyList()

    failOnError = true
    isIncludeTests = false

    resultFormat = "CSV"

    val name = if (isCi) {
        ciFileName!!
    } else {
        val dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(LocalDateTime.now())
        val targetDependency = if (isKogera) "${mapper}-$kogeraVersion" else "${mapper}-$originalVersion"
        val targetBenchmark = benchmarkSet.name
        listOf(dateTime, targetDependency, targetBenchmark, mode).joinToString(separator = "_")
    }

    val outputDir = if (isCi) "${project.rootDir}/tmp" else "${project.rootDir}/jmh-reports"
    resultsFile = project.file("${outputDir}/${name}.csv")
    humanOutputFile = if (isCi) null else project.file("${outputDir}/${name}.txt")
}
