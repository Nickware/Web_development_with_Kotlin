import modelo.itemListado

fun main() {
    val carrito = mutableMapOf<itemListado, Int>()
    carrito[itemListado("Corto", "XL",14.99)] = 2
    carrito[itemListado("Pantis", "32",19.99)] = 4
    carrito[itemListado("Gorra", "8.5",24.00)] = 2
    println(carrito)

    var total = 0.0
    for ((item, cantidad) in carrito){
        val itemTotal:Double = cantidad * item.precio
        println("Item ${item.tipo} @ ${item.precio} = $itemTotal")
        total += itemTotal
    }
    println("-------------")
    println("Total: $total")
}