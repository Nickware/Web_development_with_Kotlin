fun main(args: Array<String>) {
    var valorNulo: String? = null
    println(valorNulo)

    val l: Int? = valorNulo?.length
    println("Longitud es $l")

    if (l == null) {
        println("Longitud es nula")
    }
    val mensaje = if (l != null) {
        "Longitud: $l"
    } else {
        "l es vacio"
    }
    println(mensaje)

    val l2 = l ?: -1
    println("El valor de 12 es $l2")

    try {
        val l3 = l!!
        println("El valor de 13 es $l3")
    } catch (e: KotlinNullPointerException) {
        println("l3 es nulo")
    }

}