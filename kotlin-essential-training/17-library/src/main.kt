import java.lang.NumberFormatException

fun main(args: Array<String>) {
    try {
        val numero1 = MathLib.getInput("Numero 1: ")
        val numero2 = MathLib.getInput("Numero 2: ")

        val resultado: Double = MathLib.agregarValores(numero1, numero2)
        println("Respuesta: $resultado")

    } catch (e: KotlinNullPointerException) {
        println("Valores no nulos")
    } catch (e: NumberFormatException) {
        println("${e.message} este no es un Número")
    }
}