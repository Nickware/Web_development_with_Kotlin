plugins {
    kotlin("jvm") version "1.9.23"  // Versión estable más reciente (soporta Java 21)
    application  // Para ejecutar aplicaciones
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:2.3.5")  // Dependencias de Ktor
    implementation("io.ktor:ktor-server-netty:2.3.5")
    implementation("org.jetbrains.kotlinx:dataframe:0.11.0") // Nuevo
}

// Configuración clave para Java 21:
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "21"  // o "17" si prefieres Java LTS
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))  // JDK 21
}

application {
    mainClass.set("MainKt")
}