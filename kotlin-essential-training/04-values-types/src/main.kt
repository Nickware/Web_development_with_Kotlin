fun main(args: Array<String>) {
    val myInt = 42
    val myLong: Long = myInt.toLong()

    println("El tipo de mylong es ${myLong::class.qualifiedName}")

    val myLong2 = 42.9

    var myInt2 = myLong2.toInt()
    println("El tipo de mylong2 es $myLong2")
    println("El tipo de mylong2 es $myInt2")

    val myInt3 = 568
    val myDouble3 = myInt3.toDouble()
    println("El tipo de mylong2 es $myDouble3")
}