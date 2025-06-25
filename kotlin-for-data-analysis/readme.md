# Proyecto de Análisis de Datos con Kotlin

Este proyecto demuestra las capacidades de Kotlin para análisis de datos, incluyendo carga de datos de múltiples fuentes, transformación, visualización y análisis en tiempo real.

## Características principales a implementar

### Funcionalidades a Implementar

- **Carga de datos múltiples formatos**: CSV, JSON y bases de datos relacionales
- **Análisis eficiente de bases de datos**: Usando Exposed ORM con H2
- **APIs en tiempo real**: Conexión a APIs REST con monitoreo continuo usando corrutinas
- **Visualizaciones**: Gráficos interactivos con Kandy
- **Notebooks**: Configuración para Jupyter y Datalore

### Lo que busca este proyecto

- **Análisis en tiempo real** con monitoreo de APIs de criptomonedas
- **Transformaciones avanzadas** de datos con Kotlin DataFrame
- **Visualizaciones interactivas** que se guardan como HTML
- **Arquitectura modular** fácil de expandir
- **Manejo asíncrono** con corrutinas para datos en tiempo real

## Tecnologías Utilizadas

- **Kotlin/JS**: Compilación de Kotlin a JavaScript
- **Chart.js**: Biblioteca para gráficos interactivos
- **HTML5/CSS3**: Interfaz de usuario moderna
- **Gradle**: Sistema de construcción
- **IntelliJ IDEA**: IDE de desarrollo

## Estructura del Proyecto

```
kotlin-data-analysis/
├── build.gradle.kts
├── src/main/kotlin/
│   ├── DataLoader.kt
│   ├── DatabaseAnalyzer.kt
│   ├── ApiDataFetcher.kt
│   ├── DataVisualizer.kt
│   └── Main.kt
├── notebooks/
│   ├── exploratory_analysis.ipynb
│   └── datalore_analysis.kts
├── data/
│   ├── sample_sales.csv
│   └── sample_products.json
└── README.md
```
## Despues de finalizado el proyecto, pasos sugeridos

1. **Configurar el entorno**: Crear el proyecto con la estructura mostrada
2. **Datos de prueba**: Usar los archivos CSV/JSON de ejemplo
3. **Experimentar con notebooks**: Probar tanto Jupyter como Datalore
4. **APIs reales**: Conectar con APIs que te interesen (clima, finanzas, etc.)
5. **Expandir visualizaciones**: Agregar más tipos de gráficos según tus necesidades
