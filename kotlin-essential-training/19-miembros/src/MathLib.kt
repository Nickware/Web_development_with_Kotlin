class MathLib {
    var valorActual = 0.0

    fun agregarValores(valor: Double){
        valorActual += valor
    }

    companion object{
        fun  agregarValores(numero1: Double, numero2: Double) = numero1 + numero2

        fun getInput(prompt: String):Double{
            println(prompt)
            val valor: String? = readLine()
            val numero: Double= valor!!.toDouble()
            return numero

        }
    }
}