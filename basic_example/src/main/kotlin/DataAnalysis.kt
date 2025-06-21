import org.jetbrains.kotlinx.dataframe.api.*

fun main() {
    val df = dataFrameOf(
        "Nombre" to listOf("Ana", "Luis", "Marta"),
        "Edad" to listOf(25, 30, 28)
    )
    println(df)
}