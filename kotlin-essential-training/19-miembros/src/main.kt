fun main(args: Array<String>){
    try {
        val mathLib = MathLib()
        while (true){
            val numero = MathLib.getInput("Ingresar un numero ")
            mathLib.agregarValores(numero)
            println("Valor actual: ${mathLib.valorActual}")
        }
    } catch (e: NumberFormatException) {
        println("${e.message} no es un numero")
    } catch (e: Exception){
        println(e.message)
    }
}