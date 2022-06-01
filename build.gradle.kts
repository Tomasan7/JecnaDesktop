group = "me.tomasan7"
version = "1.5-SNAPSHOT"

plugins {
    /* org.jetbrains.kotlin.jvm Plugin to add support for Kotlin. */
    kotlin("jvm") version "1.5.31"

    /* JavaFX - main GUI library. */
    id("org.openjfx.javafxplugin") version "0.0.10"

    /* ShadowJar - for creating fat jars. */
    id("com.github.johnrengelman.shadow") version "7.1.2"

    /* Apply the application plugin to add support for building a CLI application in Java. */
    application
}

repositories {
    /* Use Maven Central for resolving dependencies. */
    mavenCentral()
}

dependencies {
    /* Configuring JavaFX modules. */
    javafx {
        modules("javafx.controls", "javafx.fxml")
    }

    /* Align versions of all Kotlin components. */
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    /* Use the Kotlin JDK 8 standard library. */
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    /* Use the Kotlin test library. */
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    /* Use the Kotlin JUnit integration. */
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    val ktorVersion = "2.0.2"

    /* Kotlin Coroutines - for asynchronous programming. */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    /* Jetbrains Annotations */
    implementation("org.jetbrains:annotations:23.0.0")
    /* Jsoup - for parsing HTML. */
    implementation("org.jsoup:jsoup:1.14.3")
    /* TornadoFX - JavaFX wrapper for Kotlin. */
    implementation("no.tornado:tornadofx:1.7.20")
    /* MaterialFX - JavaFX addon for Google Material Design. */
    implementation("io.github.palexdev:materialfx:11.13.5")
    /* Ktor - for HTTP client. */
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
}

application {
    /* Define the main class for the application. */
    /* The Kt suffix is needed, or else it will not be found. This is due to some interoperability with Java. */
    mainClass.set("me.tomasan7.jecnadesktop.MainKt")
}

/* Required for Java code to be compiled inside the kotlin folder.  */
sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    shadowJar {
        /* Sets the result shadowed jar's suffix. */
        archiveClassifier.set("complete")
    }
}