import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

plugins {
    kotlin("jvm") version "1.7.21"
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

val isKogera: Boolean = getOptionOrDefault("isKogera", true)
val isSingleShot: Boolean = getOptionOrDefault("isSingleShot", false)
val isOnlyMain: Boolean = getOptionOrDefault("isOnlyMain", true)

val kogeraVersion = "2.14.2-alpha6"
val originalVersion = "2.14.2"

dependencies {
    jmhImplementation(kotlin("reflect"))

    if (isKogera) {
        jmhImplementation("com.github.ProjectMapK:jackson-module-kogera:$kogeraVersion")
    } else {
        jmhImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:$originalVersion")
    }

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
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
    exclude = if (isKogera) emptyList() else listOf("org.wrongwrong.extra.value_class.*")

    failOnError = true
    isIncludeTests = false

    resultFormat = "CSV"

    val dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(LocalDateTime.now())
    val target = if (isKogera) "kogera-$kogeraVersion" else "orig-$originalVersion"
    val mode = if (isSingleShot) "ss" else "thrpt"
    val name = listOf(dateTime, target, mode).joinToString(separator = "_")

    resultsFile = project.file("${project.rootDir}/jmh-reports/${name}.csv")
    humanOutputFile = project.file("${project.rootDir}/jmh-reports/${name}.txt")
}
