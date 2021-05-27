class MathLib {

    companion object {
        fun agregarValores(numero1: Double, numero2: Double) = numero1 + numero2

        fun getInput(prompt: String): Double {
            print(prompt)
            val valor = readLine()
            val numero = valor!!.toDouble()
            return numero
        }
    }
}