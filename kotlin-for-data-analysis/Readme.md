# Análisis de datos completos en Kotlin

Este es un **script de análisis de datos completo en Kotlin** que demuestra un pipeline integral de procesamiento y visualización de datos. A continuación se explica sus componentes principales:

##  Propósito General

Es una aplicación de análisis de datos que integra múltiples fuentes (archivos locales, bases de datos y APIs) y genera visualizaciones y reportes estadísticos.

##  Estructura del Pipeline

El script ejecuta **7 fases secuenciales**:

### **Fase 1: Carga de Datos Locales**
- Lee archivos CSV (ventas) y JSON (productos)
- Limpia y transforma los datos
- Realiza análisis por región

### **Fase 2: Análisis de Base de Datos**
- Inicializa conexión a BD (probablemente usando Exposed)
- Ejecuta análisis de clientes, regiones top y categorías
- Genera resúmenes de clientes

### **Fase 3: Obtención de APIs**
- Consume datos de criptomonedas
- Obtiene datos de posts (posiblemente de alguna API REST)

### **Fase 4: Monitoreo en Tiempo Real**
- Usa **Kotlin Coroutines** para ejecutar monitoreo asíncrono
- Actualiza precios de criptomonedas cada 30 segundos usando `Flow`
- Genera visualizaciones en tiempo real durante 2 minutos

### **Fase 5: Visualizaciones**
- Crea múltiples tipos de gráficos (barras, histogramas, horizontales)
- Genera un dashboard HTML completo
- Usa la librería **Kandy** (mencionada en el header)

### **Fase 6: Reportes Estadísticos**
- Calcula métricas descriptivas (suma, promedio, máx, mín)
- Resume información de cada dataset

### **Fase 7: Exportación**
- Prepara los resultados para exportar a CSV
- Crea directorio de exportaciones

##  Tecnologías Utilizadas

- **Kotlin DataFrame**: Manipulación de datos estilo pandas
- **Kandy**: Visualizaciones (mencionada en comentarios)
- **Ktor**: Cliente HTTP para APIs
- **Exposed**: ORM para bases de datos
- **Kotlinx Coroutines**: Programación asíncrona

## Características Destacadas

1. **Programación Asíncrona**: Usa `suspend fun` y coroutines para operaciones no bloqueantes
2. **Procesamiento en Background**: El monitoreo corre en paralelo con `launch`
3. **Manejo de Errores**: Try-catch robusto con finally para limpieza
4. **Extensión de String**: Función ingeniosa `String.times(n)` para crear líneas decorativas

##  Salida

El script genera:
- Visualizaciones HTML en `charts/`
- Un dashboard principal en `charts/dashboard_index.html`
- Archivos CSV de exportación en `exports/`
