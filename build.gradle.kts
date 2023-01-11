import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

plugins {
    kotlin("jvm") version "1.7.20"
    id("me.champeau.gradle.jmh") version "0.5.3"
}

group = "org.wrongwrong"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val isKogera: Boolean = false

dependencies {
    jmhImplementation(kotlin("reflect"))

    if (isKogera) {
        jmhImplementation(files("your local jar here"))
    } else {
        jmhImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")
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
    warmupForks = 2
    warmupBatchSize = 3
    warmupIterations = 3
    warmup = "1s"

    fork = 2
    batchSize = 3
    iterations = 2
    timeOnIteration = "1500ms"

    failOnError = true
    isIncludeTests = false

    resultFormat = "CSV"

    val dateTime = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss").format(LocalDateTime.now())
    val name = if (isKogera) "${dateTime}_kogera" else dateTime

    resultsFile = project.file("${project.buildDir}/reports/jmh/${name}.csv")
    humanOutputFile = project.file("${project.buildDir}/reports/jmh/${name}.txt")
}
