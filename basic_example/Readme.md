# Carpeta `basic_example` en kotlin

## Estructura de archivos

- **.gitignore**: Archivo para excluir archivos/directorios del control de versiones.
- **.idea**: Carpeta de configuración de IntelliJ IDEA (IDE recomendado para Kotlin).
- **build.gradle.kts**: Script de construcción del proyecto usando Gradle con Kotlin DSL.
- **gradle.properties**: Propiedades específicas del proyecto (e.g., estilo de código oficial).
- **gradle**: Carpeta de instalación local de Gradle.
- **gradlew**: Script de ejecución de Gradle para Unix/Linux.
- **gradlew.bat**: Script de ejecución de Gradle para Windows.
- **settings.gradle.kts**: Configuración del nombre y plugins del proyecto.
- **src**: Carpeta principal del código fuente (Kotlin).


## Propósito y configuración

- **Lenguaje y versión:** Proyecto Kotlin con soporte para Java 21 (también compatible con Java 17 LTS).
- **Dependencias principales:**
    - `kotlin("stdlib")`: Biblioteca estándar de Kotlin.
    - `io.ktor:ktor-server-core` y `io.ktor:ktor-server-netty`: Framework Ktor para crear servidores web en Kotlin.
    - `org.jetbrains.kotlinx:dataframe`: Librería para manipulación de datos tabulares (ciencia de datos/ingeniería de datos).
- **Aplicación:** Ejemplo básico de servidor web con Ktor.
- **Configuración de Gradle:** Utiliza Kotlin DSL para la configuración del proyecto.


## Ejemplo de código principal

```kotlin
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun main() {
    embeddedServer(Netty, port = 9590) {
        routing {
            get("/") {
                call.respondText("¡Ktorx funciona! 🌐")
            }
        }
    }.start(wait = true)
}
```

Este código define un servidor web simple que responde con un mensaje de texto cuando se accede a la ruta principal (`/`).

## Resumen breve

La carpeta `basic_example` contiene un proyecto Kotlin básico configurado con Gradle, que implementa un servidor web usando Ktor y cuenta con soporte para manipulación de datos tabulares, ideal para aprendizaje y experimentación con Kotlin en backend y ciencia de datos.

<div style="text-align: center">⁂</div>

[^1]: https://github.com/Nickware/kotlin/tree/main/basic_example

