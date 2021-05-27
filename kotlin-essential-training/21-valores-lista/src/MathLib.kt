class MathLib {
    companion object{
        /*const val sumar = "+"
        const val restar = "-"
        const val multiplicar = "*"
        const val dividir = "/"*/
        const val operacion = "+ - * /"

        fun sumarValores(numero1: Double, numero2: Double) = numero1 + numero2
        fun restarValores(numero1: Double, numero2: Double) = numero1 - numero2
        fun multiplicarValores(numero1: Double, numero2: Double) = numero1 * numero2
        fun dividirValores(numero1: Double, numero2: Double) = numero1 / numero2

        fun getInput(prompt: String): Double {
            print(prompt)
            val valor: String? = readLine()
            val numero = valor!!.toBigDecimal()
            return numero.toDouble()
        }
    }
}