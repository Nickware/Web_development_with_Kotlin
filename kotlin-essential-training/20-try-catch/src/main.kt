fun main(args: Array<String>) {
    try {
        print("Valor 1: ")
        val valor1 = readLine()
        val d1 = valor1!!.toDouble()

        print("Valor 2: ")
        val valor2 = readLine()
        val d2 = valor2!!.toDouble()

        val suma = d1 + d2
        println("Respuesta: $suma")

    } catch (e: KotlinNullPointerException) {
        println("Valores no nulos")
    } catch (e: NumberFormatException) {
        println("${e.message} este no es un Número")
    }
}