import kotlinx.coroutines.*
import org.jetbrains.kotlinx.dataframe.DataFrame

class DataAnalysisApp {
    private val dataLoader = DataLoader()
    private val dbAnalyzer = DatabaseAnalyzer()
    private val apiFetcher = ApiDataFetcher()
    private val visualizer = DataVisualizer()

    suspend fun runCompleteAnalysis() {
        println("🚀 Iniciando análisis completo de datos con Kotlin")
        println("=" * 60)

        try {
            // 1. CARGA Y TRANSFORMACIÓN DE DATOS LOCALES
            println("\n📁 FASE 1: Cargando datos CSV y JSON...")
            val salesData = dataLoader.loadSalesData("data/sample_sales.csv")
            val productData = dataLoader.loadProductData("data/sample_products.json")

            println("✅ Datos cargados:")
            println("   - Sales: ${salesData.rowsCount()} filas, ${salesData.columnsCount()} columnas")
            println("   - Products: ${productData.rowsCount()} filas, ${productData.columnsCount()} columnas")

            // Transformar y limpiar datos
            val cleanedSales = dataLoader.cleanAndTransformSalesData(salesData)
            val regionAnalysis = dataLoader.analyzeByRegion(salesData)

            println("✅ Transformaciones completadas")

            // 2. ANÁLISIS DE BASE DE DATOS
            println("\n🗄️ FASE 2: Analizando datos de base de datos...")
            dbAnalyzer.initDatabase()

            val customerAnalysis = dbAnalyzer.analyzeCustomerSales()
            val topRegions = dbAnalyzer.getTopSellingRegions()
            val categoryAnalysis = dbAnalyzer.getCategoryAnalysis()
            val customerSummary = dbAnalyzer.getCustomerSummary()

            println("✅ Análisis de BD completado:")
            println("   - Customer Analysis: ${customerAnalysis.rowsCount()} registros")
            println("   - Top Regions: ${topRegions.rowsCount()} registros")
            println("   - Category Analysis: ${categoryAnalysis.rowsCount()} registros")

            // 3. OBTENCIÓN DE DATOS DE APIs
            println("\n🌐 FASE 3: Obteniendo datos de APIs...")
            val cryptoData = apiFetcher.fetchCryptoData()
            val postsData = apiFetcher.fetchPostsData()

            println("✅ Datos de APIs obtenidos:")
            println("   - Crypto: ${cryptoData.rowsCount()} registros")
            println("   - Posts: ${postsData.rowsCount()} registros")

            // 4. MONITOREO EN TIEMPO REAL (ejecutar en background)
            println("\n⏱️ FASE 4: Iniciando monitoreo en tiempo real...")
            val monitoringJob = launch {
                apiFetcher.monitorCryptoPrices(30).collect { data ->
                    println("📊 Actualización crypto en tiempo real: ${data.rowsCount()} monedas")
                    // Crear visualización actualizada
                    visualizer.createCryptoPriceChart(data, "charts/crypto_realtime.html")
                }
            }

            // 5. CREACIÓN DE VISUALIZACIONES
            println("\n📈 FASE 5: Generando visualizaciones...")

            // Crear visualizaciones principales
            visualizer.createSalesBarChart(regionAnalysis)
            visualizer.createCryptoPriceChart(cryptoData)

            // Crear histogramas
            visualizer.createHistogram(salesData, "amount", "charts/sales_amount_distribution.html")

            // Crear gráfico horizontal de categorías
            visualizer.createHorizontalBarChart(
                categoryAnalysis,
                "category",
                "total_sales",
                "charts/category_sales.html"
            )

            // Crear dashboard completo
            visualizer.createDashboard(regionAnalysis, cryptoData, customerSummary)

            println("✅ Visualizaciones creadas en la carpeta 'charts/'")

            // 6. ANÁLISIS ESTADÍSTICO Y REPORTES
            println("\n📊 FASE 6: Generando reportes estadísticos...")
            generateStatisticalReport(salesData, cryptoData, customerAnalysis, categoryAnalysis)

            // 7. EXPORTAR RESULTADOS
            println("\n💾 FASE 7: Exportando resultados...")
            exportResults(regionAnalysis, cryptoData, customerSummary)

            // Mantener monitoreo por un tiempo limitado
            println("\n⏳ Monitoreo en tiempo real activo por 2 minutos...")
            delay(120000) // 2 minutos

            monitoringJob.cancel()
            println("🔄 Monitoreo finalizado")

        } catch (e: Exception) {
            println("❌ Error durante el análisis: ${e.message}")
            e.printStackTrace()
        } finally {
            apiFetcher.close()
            println("\n🎉 Análisis completo finalizado")
            println("🌐 Abrir charts/dashboard_index.html para ver el dashboard")
        }
    }

    private fun generateStatisticalReport(vararg datasets: DataFrame<*>) {
        println("\n📋 REPORTE ESTADÍSTICO:")
        println("=" * 50)

        datasets.forEachIndexed { index, data ->
            val summary = dataLoader.getDataSummary(data)
            println("Dataset ${index + 1}:")
            println("  - Filas: ${summary["rows"]}")
            println("  - Columnas: ${summary["columns"]}")
            println("  - Columnas numéricas: ${summary["numeric_columns"]}")
            println()
        }

        // Estadísticas específicas si hay datos de ventas
        try {
            val salesData = datasets[0]
            if (salesData.columnNames().contains("amount")) {
                val amounts = salesData["amount"].values().map { it.toString().toDouble() }
                println("📊 Estadísticas de Ventas:")
                println("  - Total: ${amounts.sum()}")
                println("  - Promedio: ${amounts.average()}")
                println("  - Máximo: ${amounts.maxOrNull()}")
                println("  - Mínimo: ${amounts.minOrNull()}")
            }
        } catch (e: Exception) {
            println("⚠️ No se pudieron calcular estadísticas específicas")
        }
    }

    private fun exportResults(vararg datasets: DataFrame<*>) {
        println("💾 Exportando datasets principales...")

        try {
            // Crear directorio de exportación
            java.io.File("exports").mkdirs()

            datasets.forEachIndexed { index, data ->
                val filename = "exports/dataset_${index + 1}_${System.currentTimeMillis()}.csv"
                // Nota: La exportación CSV requiere configuración adicional
                println("  - Dataset ${index + 1}: ${data.rowsCount()} filas preparadas para exportar")
            }

            println("✅ Datos preparados para exportación en carpeta 'exports/'")
        } catch (e: Exception) {
            println("⚠️ Error en exportación: ${e.message}")
        }
    }
}

suspend fun main() {
    println("🌟 KOTLIN DATA ANALYSIS PROJECT")
    println("Desarrollado con Kotlin DataFrame, Kandy, Ktor y Exposed")
    println()

    val app = DataAnalysisApp()
    app.runCompleteAnalysis()
}

// Función auxiliar para repetir strings
operator fun String.times(n: Int): String = repeat(n)