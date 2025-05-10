import me.champeau.jmh.JmhParameters
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

plugins {
    kotlin("jvm") version "2.0.21"
    id("me.champeau.jmh") version "0.7.3"
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
    ValueClass,
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

val kogeraVersion = "2.19.0-beta23"
val originalVersion = "2.19.0"

dependencies {
    jmhImplementation(kotlin("reflect"))

    jmhImplementation("com.github.ProjectMapK:jackson-module-kogera:$kogeraVersion")
    jmhImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:$originalVersion")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val generatedSrcPath = "${layout.buildDirectory.get()}/generated/kotlin"
kotlin {
    // for PackageVersion
    sourceSets["jmh"].apply {
        kotlin.srcDir(generatedSrcPath)
    }
    jvmToolchain(17)
}

tasks {
    // Task to generate BenchmarkBase file
    val generateBenchmarkBase by registering(Copy::class) {
        val packageStr = "org.wrongwrong"

        from(
            resources.text.fromString(
                """
package $packageStr

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
    }
}

fun BenchmarkSet.includes(): List<String> = when (this) {
    BenchmarkSet.MainSer -> listOf("org.wrongwrong.main.ser.*")
    BenchmarkSet.MainDeser -> listOf("org.wrongwrong.main.deser.*")
    BenchmarkSet.ValueClass -> listOf(
        "org.wrongwrong.extra.ser.value_class.*",
        "org.wrongwrong.extra.ser.json_k_unbox_value_class.*",
        "org.wrongwrong.extra.deser.value_class.*"
    )
        .joinToString("|")
        .let { listOf(it) }
    BenchmarkSet.ValueClassSer -> listOf("org.wrongwrong.extra.ser.value_class.*")
    BenchmarkSet.ValueClassSerWithJsonKUnbox -> listOf("org.wrongwrong.extra.ser.json_k_unbox_value_class.*")
    BenchmarkSet.ValueClassDeser -> listOf("org.wrongwrong.extra.deser.value_class.*")
    BenchmarkSet.WrapperSer -> listOf("org.wrongwrong.extra.ser.wrapper.*")
    BenchmarkSet.WrapperDeser -> listOf("org.wrongwrong.extra.deser.wrapper.*")
    // missing is excluded from the comparison because they are theoretically less affected by StrictNullChecks
    // JMH plugin has been updated from 0.5.3, and now it is combined into one regular expression,
    // because it caused an error when multiple expressions were specified.
    BenchmarkSet.StrictNullChecks ->
        listOf("org.wrongwrong.extra.deser.Collections|org.wrongwrong.main.deser.*_Ctor.present")
    BenchmarkSet.OnlyMain -> listOf("org.wrongwrong.main.*")
    BenchmarkSet.Full -> listOf("org.wrongwrong.*")
}

fun JmhParameters.setThrptDetails() {
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
        fork = if (isCi) {
            10
        } else {
            5
        }
    } else {
        mode = "thrpt"
        forceGC = false

        setThrptDetails()
    }
    benchmarkMode = listOf(mode)

    includes = benchmarkSet.includes()
    excludes = if (benchmarkSet == BenchmarkSet.Full && !isKogera)
        listOf("org.wrongwrong.extra.deser.value_class.*")
    else
        emptyList()

    failOnError = true
    includeTests = false

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

    // Output options when jmh task
    if (project.gradle.startParameter.taskNames.contains("jmh")) {
        val props = this::class.java.methods
            .filter {
                it.returnType.run { this == Property::class.java || this == ListProperty::class.java } &&
                        it.parameterCount == 0
            }
            .associate {
                // All property names begin with "get" and are formatted with substring(3).
                it.name.substring(3) to when (val value = it.invoke(this)) {
                    is Property<*> -> value.orNull
                    is ListProperty<*> -> value.orNull
                    else -> throw IllegalStateException("${value::class.java} is not Property")
                }
            }

        logger.log(LogLevel.LIFECYCLE, "> JMH props:\n$props")
    }
}
