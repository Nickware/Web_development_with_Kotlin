plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("org.jetbrains.kotlinx.dataframe") version "0.13.1"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    // Asegúrate de tener estos repositorios
    maven("https://repo1.maven.org/maven2/")
    // Repositorio adicional para funciones estadísticas (opcional)
    maven("https://packages.jetbrains.team/maven/p/kds/kotlin-ds-maven")
}

dependencies {
    // Kotlin DataFrame
    implementation("org.jetbrains.kotlinx:dataframe:0.13.1")

    // Ktor dependencies
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-cio:2.3.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Versión correcta de Kandy
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.8.0")

    // Dependencias adicionales recomendadas
    implementation("org.jetbrains.kotlinx:dataframe:0.14.1")

    // Solo si necesitas funciones estadísticas
    implementation("org.jetbrains.kotlinx:kotlin-statistics-jvm:0.4.0")
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}