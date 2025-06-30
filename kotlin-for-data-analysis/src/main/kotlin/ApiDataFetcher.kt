import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.toDataFrame
import org.jetbrains.kotlinx.dataframe.api.*  // Para más funciones útiles
import java.time.LocalDateTime

@Serializable
data class CryptoPrice(
    val id: String,
    val symbol: String,
    val name: String,
    val current_price: Double,
    val market_cap: Long? = null,
    val price_change_percentage_24h: Double? = null,
    val total_volume: Long? = null
)

@Serializable
data class JsonPlaceholderPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

class ApiDataFetcher {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    // Obtener datos de criptomonedas en tiempo real
    suspend fun fetchCryptoData(): DataFrame<CryptoPrice> {
        return try {
            println("🪙 Obteniendo datos de criptomonedas...")
            val cryptoData: List<CryptoPrice> = client.get(
                "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1"
            ).body()

            println("✅ Obtenidos ${cryptoData.size} registros de criptomonedas")
            cryptoData.toDataFrame()
        } catch (e: Exception) {
            println("❌ Error obteniendo datos crypto: ${e.message}")
            // Retornar datos dummy si falla la API
            createDummyCryptoData()
        }
    }

    // API alternativa para pruebas (JSONPlaceholder)
    suspend fun fetchPostsData(): DataFrame<*> {
        return try {
            println("📄 Obteniendo posts de JSONPlaceholder...")
            val posts: List<JsonPlaceholderPost> = client.get(
                "https://jsonplaceholder.typicode.com/posts"
            ).body()

            // Convertir a formato más analítico
            val postsAnalysis = posts.take(20).map { post ->
                mapOf(
                    "userId" to post.userId,
                    "postId" to post.id,
                    "title_length" to post.title.length,
                    "body_length" to post.body.length,
                    "word_count" to post.body.split(" ").size,
                    "has_question" to post.title.contains("?"),
                    "timestamp" to LocalDateTime.now().toString()
                )
            }

            println("✅ Procesados ${postsAnalysis.size} posts")
            postsAnalysis.toDataFrame()
        } catch (e: Exception) {
            println("❌ Error obteniendo posts: ${e.message}")
            createDummyPostsData()
        }
    }

    // Monitoreo continuo de datos
    fun monitorCryptoPrices(intervalSeconds: Long = 60): Flow<DataFrame<CryptoPrice>> = flow {
        var attempts = 0
        val maxAttempts = 5

        while (attempts < maxAttempts) {
            try {
                val data = fetchCryptoData()
                emit(data)
                println("📊 Actualización crypto #${attempts + 1} - ${data.rowsCount()} registros")
                delay(intervalSeconds * 1000)
                attempts++
            } catch (e: Exception) {
                println("⚠️  Error en monitoreo: ${e.message}")
                delay(10000) // Esperar 10 segundos antes de reintentar
                attempts++
            }
        }
        println("🔄 Monitoreo completado después de $maxAttempts intentos")
    }

    // Datos dummy para cuando la API no funciona
    private fun createDummyCryptoData(): DataFrame<CryptoPrice> {
        val dummyData = listOf(
            CryptoPrice("bitcoin", "btc", "Bitcoin", 45000.0, 850000000000L, 2.5, 25000000000L),
            CryptoPrice("ethereum", "eth", "Ethereum", 2800.0, 340000000000L, -1.2, 15000000000L),
            CryptoPrice("cardano", "ada", "Cardano", 0.45, 15000000000L, 3.8, 800000000L),
            CryptoPrice("polkadot", "dot", "Polkadot", 7.2, 8500000000L, -0.5, 450000000L),
            CryptoPrice("chainlink", "link", "Chainlink", 14.5, 6800000000L, 1.8, 320000000L)
        )
        println("🎭 Usando datos dummy de criptomonedas")
        return dummyData.toDataFrame()
    }

    private fun createDummyPostsData(): DataFrame<*> {
        val dummyPosts = (1..10).map { i ->
            mapOf(
                "userId" to (i % 3 + 1),
                "postId" to i,
                "title_length" to (20..80).random(),
                "body_length" to (100..500).random(),
                "word_count" to (15..75).random(),
                "has_question" to (i % 4 == 0),
                "timestamp" to LocalDateTime.now().toString()
            )
        }
        println("🎭 Usando datos dummy de posts")
        return dummyPosts.toDataFrame()
    }

    fun close() {
        client.close()
    }
}