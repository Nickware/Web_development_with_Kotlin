import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.layers.*
import org.jetbrains.kotlinx.kandy.letsplot.settings.Symbol
import org.jetbrains.kotlinx.kandy.util.color.Color
import java.io.File

class DataVisualizer {

    init {
        // Crear directorio de charts si no existe
        File("charts").mkdirs()
    }

    // Gráfico de barras para ventas por región
    fun createSalesBarChart(salesData: DataFrame<*>, outputPath: String = "charts/sales_by_region.html") {
        try {
            println("📊 Creando gráfico de barras - Ventas por región...")

            salesData.plot {
                bars {
                    x("region")
                    y("total_sales")
                    fillColor = Color.BLUE
                    alpha = 0.8
                }
                layout {
                    title = "Ventas Totales por Región"
                    xAxisLabel = "Región"
                    yAxisLabel = "Ventas Totales (USD)"
                    size = 800 to 600
                }
            }.save(outputPath)

            println("✅ Gráfico guardado en: $outputPath")
        } catch (e: Exception) {
            println("❌ Error creando gráfico de barras: ${e.message}")
        }
    }

    // Gráfico de dispersión para criptomonedas
    fun createCryptoPriceChart(cryptoData: DataFrame<*>, outputPath: String = "charts/crypto_analysis.html") {
        try {
            println("📊 Creando gráfico de dispersión - Criptomonedas...")

            cryptoData.plot {
                points {
                    x("market_cap")
                    y("current_price")
                    size("price_change_percentage_24h")
                    color("symbol")
                    symbol = Symbol.CIRCLE
                    alpha = 0.7
                }
                layout {
                    title = "Precio vs Market Cap de Criptomonedas"
                    xAxisLabel = "Market Cap (USD)"
                    yAxisLabel = "Precio Actual (USD)"
                    size = 900 to 700
                }
            }.save(outputPath)

            println("✅ Gráfico crypto guardado en: $outputPath")
        } catch (e: Exception) {
            println("❌ Error creando gráfico crypto: ${e.message}")
        }
    }

    // Gráfico de líneas para tendencias
    fun createTrendChart(data: DataFrame<*>, xCol: String, yCol: String, outputPath: String) {
        try {
            println("📊 Creando gráfico de tendencias...")

            data.plot {
                line {
                    x(xCol)
                    y(yCol)
                    color = Color.RED
                    width = 2.0
                }
                layout {
                    title = "Tendencia: $yCol vs $xCol"
                    xAxisLabel = xCol
                    yAxisLabel = yCol
                    size = 800 to 600
                }
            }.save(outputPath)

            println("✅ Gráfico de tendencias guardado en: $outputPath")
        } catch (e: Exception) {
            println("❌ Error creando gráfico de tendencias: ${e.message}")
        }
    }

    // Histograma para distribuciones
    fun createHistogram(data: DataFrame<*>, column: String, outputPath: String) {
        try {
            println("📊 Creando histograma para columna: $column...")

            data.plot {
                histogram {
                    x(column)
                    bins = 20
                    fillColor = Color.GREEN
                    alpha = 0.7
                    borderColor = Color.BLACK
                }
                layout {
                    title = "Distribución de $column"
                    xAxisLabel = column
                    yAxisLabel = "Frecuencia"
                    size = 800 to 600
                }
            }.save(outputPath)

            println("✅ Histograma guardado en: $outputPath")
        } catch (e: Exception) {
            println("❌ Error creando histograma: ${e.message}")
        }
    }

    // Gráfico de barras horizontales
    fun createHorizontalBarChart(data: DataFrame<*>, categoryCol: String, valueCol: String, outputPath: String) {
        try {
            println("📊 Creando gráfico de barras horizontales...")

            data.plot {
                hBars {
                    y(categoryCol)
                    x(valueCol)
                    fillColor = Color.ORANGE
                    alpha = 0.8
                }
                layout {
                    title = "$valueCol por $categoryCol"
                    xAxisLabel = valueCol
                    yAxisLabel = categoryCol
                    size = 800 to 600
                }
            }.save(outputPath)

            println("✅ Gráfico de barras horizontales guardado en: $outputPath")
        } catch (e: Exception) {
            println("❌ Error creando gráfico horizontal: ${e.message}")
        }
    }

    // Gráfico de área para visualizar volúmenes
    fun createAreaChart(data: DataFrame<*>, xCol: String, yCol: String, outputPath: String) {
        try {
            println("📊 Creando gráfico de área...")

            data.plot {
                area {
                    x(xCol)
                    y(yCol)
                    fillColor = Color.CYAN
                    alpha = 0.6
                    borderColor = Color.BLUE
                }
                layout {
                    title = "Área: $yCol a lo largo de $xCol"
                    xAxisLabel = xCol
                    yAxisLabel = yCol
                    size = 800 to 600
                }
            }.save(outputPath)

            println("✅ Gráfico de área guardado en: $outputPath")
        } catch (e: Exception) {
            println("❌ Error creando gráfico de área: ${e.message}")
        }
    }

    // Crear múltiples visualizaciones de un dataset
    fun createComprehensiveAnalysis(data: DataFrame<*>, prefix: String) {
        println("🎨 Creando análisis visual completo...")

        // Obtener columnas numéricas
        val numericColumns = data.select { colsOf<Number>() }.columnNames()
        val categoricalColumns = data.columnNames() - numericColumns.toSet()

        // Crear histogramas para columnas numéricas
        numericColumns.take(3).forEachIndexed { index, col ->
            createHistogram(data, col, "charts/${prefix}_histogram_${col}.html")
        }

        // Crear gráficos de barras para columnas categóricas vs numéricas
        if (categoricalColumns.isNotEmpty() && numericColumns.isNotEmpty()) {
            val catCol = categoricalColumns.first()
            val numCol = numericColumns.first()

            try {
                val groupedData = data.groupBy(catCol).aggregate {
                    numCol into { (this[numCol] as List<Number>).map { it.toDouble() }.sum() }
                }
                createSalesBarChart(groupedData, "charts/${prefix}_grouped_analysis.html")
            } catch (e: Exception) {
                println("⚠️ No se pudo crear análisis agrupado: ${e.message}")
            }
        }

        println("✅ Análisis visual completo creado con prefijo: $prefix")
    }

    // Función para crear un dashboard simple
    fun createDashboard(
        salesData: DataFrame<*>,
        cryptoData: DataFrame<*>,
        customerData: DataFrame<*>
    ) {
        println("📊 Creando dashboard completo...")

        // Crear visualizaciones principales
        createSalesBarChart(salesData, "charts/dashboard_sales.html")
        createCryptoPriceChart(cryptoData, "charts/dashboard_crypto.html")

        // Análisis de clientes si hay datos
        if (customerData.rowsCount() > 0) {
            createComprehensiveAnalysis(customerData, "customers")
        }

        // Crear archivo índice HTML para el dashboard
        createDashboardIndex()

        println("✅ Dashboard completo creado. Abrir charts/dashboard_index.html")
    }

    private fun createDashboardIndex() {
        val htmlContent = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Dashboard de Análisis de Datos - Kotlin</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
                .container { max-width: 1200px; margin: 0 auto; }
                .header { text-align: center; color: #333; margin-bottom: 30px; }
                .chart-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(400px, 1fr)); gap: 20px; }
                .chart-card { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
                .chart-card h3 { color: #666; margin-top: 0; }
                iframe { width: 100%; height: 400px; border: none; }
                .footer { text-align: center; margin-top: 40px; color: #666; }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>🚀 Dashboard de Análisis de Datos con Kotlin</h1>
                    <p>Análisis completo usando Kotlin DataFrame y Kandy</p>
                </div>
                
                <div class="chart-grid">
                    <div class="chart-card">
                        <h3>📊 Ventas por Región</h3>
                        <iframe src="dashboard_sales.html"></iframe>
                    </div>
                    
                    <div class="chart-card">
                        <h3>🪙 Análisis de Criptomonedas</h3>
                        <iframe src="dashboard_crypto.html"></iframe>
                    </div>
                    
                    <div class="chart-card">
                        <h3>👥 Análisis de Clientes</h3>
                        <iframe src="customers_grouped_analysis.html"></iframe>
                    </div>
                </div>
                
                <div class="footer">
                    <p>Generado con ❤️ usando Kotlin, DataFrame y Kandy</p>
                </div>
            </div>
        </body>
        </html>
        """.trimIndent()

        File("charts/dashboard_index.html").writeText(htmlContent)
    }
}