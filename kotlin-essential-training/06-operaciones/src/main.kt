fun main(args: Array<String>) {
    var num1 = 15
    val num2 = 10

    val sum =num1+num2
    println("Suma=$sum")

    val sum2 = num1.plus(num2)
    println("Suma2=$sum2")

    var diff = num1 - num2
    println("Diff=$diff")

    num1 ++
    println("Numero=$num1")

    num1.inc()
    println("num1=$num1")

    var num3 = num1.inc()
    println("Num3=$num3")
    println("El valor de n1 es $num3")

    println("El valor de n1 es ${++num1}")
    println("Num1=$num1")
}