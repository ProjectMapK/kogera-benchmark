import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

plugins {
    kotlin("jvm") version "1.7.22"
    id("me.champeau.gradle.jmh") version "0.5.3"
}

group = "org.wrongwrong"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

fun getOptionOrDefault(name: String, default: Boolean): Boolean =
    project.properties[name]?.let { (it as String).toBoolean() } ?: default

// @see org.wrongwrong.Mapper for find options
val mapper: String = project.properties["mapper"] as? String ?: "Kogera"
val isKogera = mapper.contains("Kogera")

val isSingleShot: Boolean = getOptionOrDefault("isSingleShot", false)
val isOnlyMain: Boolean = getOptionOrDefault("isOnlyMain", true)
val isCi: Boolean = System.getenv().containsKey("CI") // True when executed in GitHub Actions

val kogeraVersion = "2.15.2-beta0"
val originalVersion = "2.15.2"

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

jmh {
    if (isSingleShot) {
        benchmarkMode = listOf("ss")
        timeUnit = "ms"

        forceGC = true
    } else {
        benchmarkMode = listOf("thrpt")

        warmupForks = 2
        warmupBatchSize = 3
        warmupIterations = 3
        warmup = "1s"

        fork = 2
        batchSize = 3
        iterations = 2
        timeOnIteration = "1500ms"

        forceGC = false
    }

    include = if (isOnlyMain) listOf("org.wrongwrong.main.*") else listOf("org.wrongwrong.*")
    // Benchmarks on value class deserialization are valid only for Kogera.
    exclude = if (isKogera) emptyList() else listOf("org.wrongwrong.extra.deser.value_class.*")

    failOnError = true
    isIncludeTests = false

    resultFormat = "CSV"

    val dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(LocalDateTime.now())
    val targetDependency = if (isKogera) "${mapper}-$kogeraVersion" else "${mapper}-$originalVersion"
    val targetBenchmark = if (isOnlyMain) "main" else "full"
    val mode = if (isSingleShot) "ss" else "thrpt"
    val name = listOf(dateTime, targetDependency, targetBenchmark, mode).joinToString(separator = "_")

    val outputDir = if (isCi) "${project.rootDir}/ci-reports" else "${project.rootDir}/jmh-reports"
    resultsFile = project.file("${outputDir}/${name}.csv")
    humanOutputFile = project.file("${outputDir}/${name}.txt")
}
