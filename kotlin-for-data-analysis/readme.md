# Análisis de Datos con Kotlin/JS

Un proyecto completo de análisis de datos desarrollado en Kotlin/JS que demuestra las capacidades del lenguaje para crear aplicaciones web interactivas con visualizaciones de datos en tiempo real.

## Características del Proyecto

### Funcionalidades Implementadas

- **Generación de datos sintéticos**: Creación de datos de ventas aleatorios para simulación
- **Análisis estadístico**: Cálculo de métricas como total de ventas, promedios, crecimiento y mejor período
- **Visualizaciones interactivas**: Gráficos de líneas y dona usando Chart.js
- **Tabla de datos**: Presentación detallada de la información con formato responsive
- **Exportación de datos**: Funcionalidad para descargar datos en formato CSV
- **Interfaz moderna**: Diseño responsive con gradientes y animaciones

### Conceptos de Kotlin Demostrados

- **Data Classes**: Representación estructurada de datos de ventas
- **Extension Functions**: Funciones para formatear valores monetarios
- **Higher-Order Functions**: Uso de `map`, `filter`, `reduce` para análisis de datos
- **Enum Classes**: Categorización de productos con valores asociados
- **Object Singleton**: Patrón singleton para el analizador principal
- **Null Safety**: Manejo seguro de valores opcionales
- **Type Safety**: Sistema de tipos estático de Kotlin

## Tecnologías Utilizadas

- **Kotlin/JS**: Compilación de Kotlin a JavaScript
- **Chart.js**: Biblioteca para gráficos interactivos
- **HTML5/CSS3**: Interfaz de usuario moderna
- **Gradle**: Sistema de construcción
- **IntelliJ IDEA**: IDE de desarrollo

## Estructura del Proyecto

```
KotlinDataAnalysis/
├── build.gradle.kts              # Configuración de Gradle
├── src/
│   └── main/
│       ├── kotlin/
│       │   └── Main.kt           # Código principal de Kotlin
│       └── resources/
│           └── index.html        # Interfaz de usuario
├── gradle/
│   └── wrapper/                  # Gradle Wrapper
└── README.md                     # Este archivo
```

## Instalación y Configuración

### Prerrequisitos

- **JDK 8 o superior** instalado
- **IntelliJ IDEA Community Edition** (recomendado)
- **Conexión a internet** para descargar dependencias

### Paso a Paso para Ejecutar

#### 1. Crear el Proyecto

1. Abrir **IntelliJ IDEA Community Edition**
2. Seleccionar **File → New → Project**
3. Elegir **Kotlin Multiplatform** en el panel izquierdo
4. Seleccionar **Browser Application** como template
5. Configurar:
   - **Name**: `KotlinDataAnalysis`
   - **Location**: Ruta deseada para el proyecto
   - **Build system**: Gradle Kotlin DSL
6. Hacer clic en **Create**

#### 2. Configurar build.gradle.kts

Reemplazar el contenido del archivo `build.gradle.kts` con:

```kotlin
plugins {
    kotlin("js") version "1.9.20"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
```

#### 3. Crear los Archivos del Proyecto

1. **Crear `src/main/resources/index.html`** con el HTML proporcionado
2. **Crear `src/main/kotlin/Main.kt`** con el código Kotlin proporcionado

#### 4. Sincronizar el Proyecto

- Hacer clic en el ícono de **Gradle** que aparece en la notificación
- O ir a **File → Sync Project with Gradle Files**

#### 5. Compilar y Ejecutar

Abrir la terminal en IntelliJ (**Alt+F12**) y ejecutar:

```bash
# Para desarrollo con recarga automática
./gradlew browserDevelopmentRun --continuous

# Para ejecutar una sola vez
./gradlew browserDevelopmentRun

# Para compilar únicamente
./gradlew build
```

#### 6. Acceder a la Aplicación

- La aplicación se abrirá automáticamente en `http://localhost:8080`
- Si no se abre automáticamente, navegar manualmente a esa URL

## Uso de la Aplicación

### Funcionalidades Disponibles

1. **Generar Nuevos Datos**: Botón para crear un nuevo conjunto de datos de ventas aleatorios
2. **Exportar CSV**: Descargar los datos actuales en formato CSV
3. **Visualizaciones Interactivas**:
   - Gráfico de líneas mostrando ventas por mes
   - Gráfico de dona con distribución por categoría
4. **Métricas Calculadas**:
   - Total de ventas del período
   - Promedio mensual
   - Mejor mes en ventas
   - Porcentaje de crecimiento
5. **Tabla Detallada**: Información completa con crecimiento mes a mes

### Interacciones

- **Hover en gráficos**: Ver valores específicos
- **Hover en tarjetas**: Efecto de elevación
- **Responsive**: Adaptación automática a diferentes tamaños de pantalla
- **Exportación**: Un clic para descargar datos

## Detalles Técnicos

### Arquitectura del Código

```kotlin
// Data class para estructura de datos
data class VentaData(
    val mes: String,
    val ventas: Double,
    val categoria: String
)

// Enum para categorías
enum class Categoria(val displayName: String) {
    ELECTRONICOS("Electrónicos"),
    ROPA("Ropa"),
    // ...
}

// Singleton para lógica de negocio
object VentasAnalyzer {
    // Funciones de análisis estadístico
    fun calcularEstadisticas(datos: List<VentaData>): Map<String, Any>
    fun agruparPorCategoria(datos: List<VentaData>): Map<String, Double>
    // ...
}
```

### Características de Kotlin Utilizadas

- **Type Safety**: Compilador verifica tipos en tiempo de compilación
- **Null Safety**: Eliminación de `NullPointerException`
- **Extension Functions**: `formatearVentas()` extiende funcionalidad
- **Higher-Order Functions**: Procesamiento funcional de colecciones
- **Smart Casts**: Casting automático seguro
- **String Templates**: Interpolación de strings con `$`

## Para Producción

Para generar una versión optimizada para producción:

```bash
./gradlew browserProductionWebpack
```

Los archivos optimizados se generarán en `build/distributions/`

## Solución de Problemas

### Problemas Comunes

1. **Error de compilación**: Verificar que JDK esté correctamente instalado
2. **Dependencias no resueltas**: Ejecutar `./gradlew clean build`
3. **Puerto ocupado**: Cambiar puerto en configuración o cerrar otras aplicaciones
4. **Gráficos no aparecen**: Verificar conexión a internet para Chart.js CDN

### Logs Útiles

```bash
# Ver logs detallados
./gradlew browserDevelopmentRun --info

# Limpiar y reconstruir
./gradlew clean build
```

## Contribuciones

### Mejoras Sugeridas

- [ ] Integración con APIs reales de datos
- [ ] Más tipos de visualizaciones (scatter plots, histogramas)
- [ ] Filtros interactivos por fecha y categoría
- [ ] Carga de archivos CSV externos
- [ ] Comparación entre períodos
- [ ] Análisis predictivo básico
- [ ] Temas dark/light mode
- [ ] Internacionalización (i18n)

### Cómo Contribuir

1. Fork el repositorio
2. Crear una rama para la nueva funcionalidad
3. Implementar cambios con tests
4. Hacer commit con mensajes descriptivos
5. Enviar Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## Contacto

Si tienes preguntas o sugerencias sobre este proyecto, no dudes en:

- Crear un **Issue** en GitHub
- Enviar un **Pull Request** con mejoras
- Contactar al equipo de desarrollo

## Estado del Proyecto

> **Nota Importante**: Este repositorio no está exento de cambios o mejoras. El proyecto se encuentra en desarrollo activo y puede recibir actualizaciones regulares que incluyan:
> 
> - Nuevas funcionalidades de análisis de datos
> - Mejoras en la interfaz de usuario
> - Optimizaciones de rendimiento
> - Corrección de bugs
> - Actualizaciones de dependencias
> - Refactorización de código
> 
> Se recomienda revisar regularmente el repositorio para obtener las últimas mejoras y funcionalidades.

---

**¡Desarrollado usando Kotlin/JS!**

*Demostrando el poder de Kotlin para análisis de datos en el navegador*