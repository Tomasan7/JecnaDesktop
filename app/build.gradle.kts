/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.4.2/userguide/building_java_projects.html
 */

group = "me.tomasan7"
version = "1.5-SNAPSHOT"

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.5.31"

    id("org.openjfx.javafxplugin") version "0.0.10"

    id("com.github.johnrengelman.shadow") version "7.1.2"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {

    javafx {
        modules("javafx.controls", "javafx.fxml")
    }

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    implementation("org.jetbrains:annotations:23.0.0")
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("io.github.palexdev:materialfx:11.13.5")
}

application {
    // Define the main class for the application.
    mainClass.set("me.tomasan7.jecnadesktop.Main")
}

/* Required for Java code to be compiled inside the kotlin folder.  */
configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}