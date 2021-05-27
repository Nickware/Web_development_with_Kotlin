import Operacion.*
fun main(args: Array<String>) {
    try {
        val numero1 = MathLib.getInput("Numero 1: ")
        val numero2 = MathLib.getInput("Numero 2: ")

        print("Selección de la operación(${MathLib.operacion})")
        val operacion = readLine()

        val resultado: Double? =
            when (operacion) {
                sumar.operador -> MathLib.sumarValores(numero1, numero2)
                restar.operador -> MathLib.restarValores(numero1, numero2)
                multiplicar.operador -> MathLib.multiplicarValores(numero1, numero2)
                dividir.operador -> MathLib.dividirValores(numero1, numero2)
                else -> throw Exception("Desconocida operacion")
            }
        println("La respuesta es $resultado")
    } catch (e: NumberFormatException) {
        println("${e.message} esto no es un numero")
    } catch (e: Exception) {
        println(e.message)
    }
}