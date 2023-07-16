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

val isSingleShot: Boolean = getOptionOrDefault("isSingleShot", true)
// val isOnlyMain: Boolean = getOptionOrDefault("isOnlyMain", true)

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
    @Param(value = ["Original", "Kogera"])
    private lateinit var _mapper: Mapper
    val mapper get() = _mapper.value
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

    // include = if (isOnlyMain) listOf("org.wrongwrong.main.*") else listOf("org.wrongwrong.*")
    // exclude = if (isKogera) emptyList() else listOf("org.wrongwrong.extra.value_class.*")

    failOnError = true
    isIncludeTests = false

    resultFormat = "CSV"

    val dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(LocalDateTime.now())
    // val targetDependency = if (isKogera) "kogera-$kogeraVersion" else "orig-$originalVersion"
    // val targetBenchmark = if (isOnlyMain) "main" else "full"
    val mode = if (isSingleShot) "ss" else "thrpt"
    // val name = listOf(dateTime, targetDependency, targetBenchmark, mode).joinToString(separator = "_")
    val name = listOf(dateTime, mode).joinToString(separator = "_")

    resultsFile = project.file("${project.rootDir}/jmh-reports/${name}.csv")
    humanOutputFile = project.file("${project.rootDir}/jmh-reports/${name}.txt")
}
