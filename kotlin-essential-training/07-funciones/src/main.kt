import kotlin.math.*

//import kotlin.math.round

fun main (args:Array<String>){
    val num1 = 15
    val num2 = 10

    val sum = num1.plus(num2)
    println("Sum=$sum")

    val diference = num1.minus(num2)
    println("Diferencia=$diference")

    val product = num1.times(num2)
    println("Producto=$product")

    val quotient = num1.toDouble().div(num2)
    println("División=$quotient")

    val remainder = num1.rem(num2)
    println("Restante=$remainder")

    val negative = -152.4
    val absolute = abs(negative)
    println("Absoluto=$absolute")
    println("Redondeo=${round(absolute)}")

    println("El resultado de Pi es $PI")
}