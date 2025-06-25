import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.toDataFrame
import java.time.LocalDate

object Sales : IntIdTable() {
    val productId = integer("product_id")
    val customerId = integer("customer_id")
    val amount = double("amount")
    val saleDate = date("sale_date")
    val region = varchar("region", 50)
    val category = varchar("category", 50)
}

object Customers : IntIdTable() {
    val name = varchar("name", 100)
    val email = varchar("email", 100)
    val registrationDate = date("registration_date")
    val city = varchar("city", 50)
}

class DatabaseAnalyzer {

    fun initDatabase() {
        println("🗄️  Inicializando base de datos...")
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

        transaction {
            SchemaUtils.create(Sales, Customers)
            insertSampleData()
        }
        println("✅ Base de datos inicializada con datos de muestra")
    }

    private fun insertSampleData() {
        // Insertar clientes
        val customerIds = mutableListOf<Int>()

        val customerData = listOf(
            Triple("Juan Pérez", "juan@email.com", "Bogotá"),
            Triple("María García", "maria@email.com", "Medellín"),
            Triple("Carlos López", "carlos@email.com", "Cali"),
            Triple("Ana Rodríguez", "ana@email.com", "Barranquilla"),
            Triple("Luis Martínez", "luis@email.com", "Bucaramanga")
        )

        customerData.forEach { (name, email, city) ->
            val id = Customers.insert {
                it[Customers.name] = name
                it[Customers.email] = email
                it[Customers.registrationDate] = LocalDate.now().minusDays((1..365).random().toLong())
                it[Customers.city] = city
            } get Customers.id
            customerIds.add(id.value)
        }

        // Insertar ventas
        val salesData = listOf(
            Quintuple(101, customerIds[0], 150.0, "Norte", "Electronics"),
            Quintuple(102, customerIds[1], 75.5, "Sur", "Clothing"),
            Quintuple(103, customerIds[0], 200.0, "Norte", "Electronics"),
            Quintuple(104, customerIds[2], 45.99, "Centro", "Books"),
            Quintuple(105, customerIds[1], 320.0, "Sur", "Electronics"),
            Quintuple(106, customerIds[3], 25.50, "Norte", "Clothing"),
            Quintuple(107, customerIds[4], 89.99, "Centro", "Books"),
            Quintuple(108, customerIds[0], 445.0, "Norte", "Electronics"),
            Quintuple(109, customerIds[2], 12.99, "Sur", "Books"),
            Quintuple(110, customerIds[3], 167.50, "Centro", "Clothing"),
            Quintuple(111, customerIds[1], 299.99, "Sur", "Electronics"),
            Quintuple(112, customerIds[4], 78.25, "Norte", "Clothing")
        )

        salesData.forEach { (productId, customerId, amount, region, category) ->
            Sales.insert {
                it[Sales.productId] = productId
                it[Sales.customerId] = customerId
                it[Sales.amount] = amount
                it[Sales.saleDate] = LocalDate.now().minusDays((1..30).random().toLong())
                it[Sales.region] = region
                it[Sales.category] = category
            }
        }
    }

    fun analyzeCustomerSales(): DataFrame<*> {
        return transaction {
            val query = Sales
                .join(Customers, JoinType.INNER, Sales.customerId, Customers.id)
                .selectAll()

            val results = query.map { row ->
                mapOf(
                    "customer_name" to row[Customers.name],
                    "customer_city" to row[Customers.city],
                    "total_amount" to row[Sales.amount],
                    "region" to row[Sales.region],
                    "category" to row[Sales.category],
                    "sale_date" to row[Sales.saleDate].toString()
                )
            }

            println("📊 Análisis de ventas por cliente: ${results.size} registros")
            results.toDataFrame()
        }
    }

    fun getTopSellingRegions(): DataFrame<*> {
        return transaction {
            val query = Sales
                .slice(Sales.region, Sales.amount.sum(), Sales.amount.count())
                .selectAll()
                .groupBy(Sales.region)
                .orderBy(Sales.amount.sum(), SortOrder.DESC)

            val results = query.map { row ->
                mapOf(
                    "region" to row[Sales.region],
                    "total_sales" to (row[Sales.amount.sum()] ?: 0.0),
                    "transaction_count" to (row[Sales.amount.count()] ?: 0)
                )
            }

            println("🏆 Top regiones por ventas: ${results.size} registros")
            results.toDataFrame()
        }
    }

    fun getCategoryAnalysis(): DataFrame<*> {
        return transaction {
            val query = Sales
                .slice(
                    Sales.category,
                    Sales.amount.sum(),
                    Sales.amount.avg(),
                    Sales.amount.max(),
                    Sales.amount.min(),
                    Sales.amount.count()
                )
                .selectAll()
                .groupBy(Sales.category)
                .orderBy(Sales.amount.sum(), SortOrder.DESC)

            val results = query.map { row ->
                mapOf(
                    "category" to row[Sales.category],
                    "total_sales" to (row[Sales.amount.sum()] ?: 0.0),
                    "avg_sale" to (row[Sales.amount.avg()] ?: 0.0),
                    "max_sale" to (row[Sales.amount.max()] ?: 0.0),
                    "min_sale" to (row[Sales.amount.min()] ?: 0.0),
                    "transaction_count" to (row[Sales.amount.count()] ?: 0)
                )
            }

            println("📈 Análisis por categoría: ${results.size} registros")
            results.toDataFrame()
        }
    }

    fun getCustomerSummary(): DataFrame<*> {
        return transaction {
            val query = Customers
                .join(Sales, JoinType.LEFT, Customers.id, Sales.customerId)
                .slice(
                    Customers.name,
                    Customers.city,
                    Customers.registrationDate,
                    Sales.amount.sum(),
                    Sales.amount.count()
                )
                .selectAll()
                .groupBy(Customers.id, Customers.name, Customers.city, Customers.registrationDate)
                .orderBy(Sales.amount.sum(), SortOrder.DESC)

            val results = query.map { row ->
                mapOf(
                    "customer_name" to row[Customers.name],
                    "city" to row[Customers.city],
                    "registration_date" to row[Customers.registrationDate].toString(),
                    "total_spent" to (row[Sales.amount.sum()] ?: 0.0),
                    "purchase_count" to (row[Sales.amount.count()] ?: 0)
                )
            }

            println("👥 Resumen de clientes: ${results.size} registros")
            results.toDataFrame()
        }
    }
}

// Clase auxiliar para tuplas de 5 elementos
data class Quintuple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)