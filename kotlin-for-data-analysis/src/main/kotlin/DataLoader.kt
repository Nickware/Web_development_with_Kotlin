import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readCSV
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double
)

class DataLoader {

    // Cargar datos CSV
    fun loadSalesData(filePath: String): DataFrame<*> {
        return try {
            DataFrame.readCSV(filePath)
        } catch (e: Exception) {
            println("Error loading CSV file: ${e.message}")
            throw e
        }
    }

    // Cargar y transformar datos JSON
    fun loadProductData(filePath: String): DataFrame<Product> {
        return try {
            val jsonString = File(filePath).readText()
            val products = Json.decodeFromString<List<Product>>(jsonString)
            products.toDataFrame()
        } catch (e: Exception) {
            println("Error loading JSON file: ${e.message}")
            throw e
        }
    }

    // Transformar y limpiar datos
    fun cleanAndTransformSalesData(salesData: DataFrame<*>): DataFrame<*> {
        return salesData
            .dropNulls()
            .filter { "amount"<Double>() > 0 }
            .add("total_with_tax") { "amount"<Double>() * 1.19 }
            .groupBy("product_category")
            .aggregate {
                "total_sales" into { "amount"<Double>().sum() }
                "avg_sale" into { "amount"<Double>().mean() }
                "count" into { count() }
            }
    }

    // Análisis por región
    fun analyzeByRegion(salesData: DataFrame<*>): DataFrame<*> {
        return salesData
            .groupBy("region")
            .aggregate {
                "total_sales" into { "amount"<Double>().sum() }
                "avg_sale" into { "amount"<Double>().mean() }
                "transaction_count" into { count() }
                "max_sale" into { "amount"<Double>().max() }
                "min_sale" into { "amount"<Double>().min() }
            }
            .sortBy("total_sales", SortOrder.DESC)
    }

    // Combinar datasets
    fun joinSalesWithProducts(
        sales: DataFrame<*>,
        products: DataFrame<Product>
    ): DataFrame<*> {
        return try {
            sales.join(products, how = JoinType.LEFT) {
                "product_id" match "id"
            }
        } catch (e: Exception) {
            println("Error joining dataframes: ${e.message}")
            sales // Retornar datos originales si falla el join
        }
    }

    // Estadísticas descriptivas
    fun getDataSummary(data: DataFrame<*>): Map<String, Any> {
        return mapOf(
            "rows" to data.rowsCount(),
            "columns" to data.columnsCount(),
            "column_names" to data.columnNames(),
            "numeric_columns" to data.select { colsOf<Number>() }.columnNames()
        )
    }
}