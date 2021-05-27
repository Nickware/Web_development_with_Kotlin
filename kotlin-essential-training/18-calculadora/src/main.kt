import java.lang.NumberFormatException

fun main(args: Array<String>) {
    try {
        print("Valor 1: ")
        val valor1 = readLine()
        val numero1 = valor1!!.toDouble()

        println("Valor 2: ")
        val valor2 = readLine()
        val numero2 = valor2!!.toDouble()

        println("Seleccione el tipo de operación (+ - * /): ")
        val operacion = readLine()

        val resultado: Double? =
            when (operacion) {
                "+" -> sumarValores(numero1, numero2)
                "-" -> restarValores(numero1, numero2)
                "*" -> multiplicarValores(numero1, numero2)
                "/" -> dividirValores(numero1, numero2)
                else -> throw Exception("Operación desconocida")
            }
        println("El resultado de la operacion seleccionada es: $resultado")
    } catch (e: KotlinNullPointerException) {
        println("Valores no nulos")
    } catch (e: NumberFormatException) {
        println("${e.message} este no es un Número")
    }
}

fun sumarValores(numero1: Double, numero2: Double) = numero1 + numero2
fun restarValores(numero1: Double, numero2: Double) = numero1 - numero2
fun multiplicarValores(numero1: Double, numero2: Double) = numero1 * numero2
fun dividirValores(numero1: Double, numero2: Double) = numero1 / numero2