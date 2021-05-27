import modelo.itemListado

fun main(args: Array<String>) {
    val mapaColor = mapOf<String, Long>(
        Pair("Rojo", 0xFF0000),
        Pair("Verde", 0x00FF00),
        Pair("Azul", 0x0000FF)
    )
    println(mapaColor)
    println(mapaColor::class.simpleName)

    val mapaEstado = mutableMapOf<String, String>()
    mapaEstado.put("Cun", "Bogota")
    mapaEstado.put("Ant", "Medellín")
    mapaEstado.put("Val", "Calí")
    println(mapaEstado)

    for (estado in mapaEstado.keys){
        println("La capital de $estado es ${mapaEstado.get(estado)}")
    }

    for ((estado, capital) in mapaEstado){
        println("La capital de $estado es $capital")
    }

    val carrito = mutableMapOf<itemListado, Int>()
    carrito.put(itemListado("Corto", "XL",14.99),2)
    carrito.put(itemListado("Pantis", "32",19.99),4)
    carrito.put(itemListado("Gorra", "8.5",24.00),2)
    println(carrito)
}