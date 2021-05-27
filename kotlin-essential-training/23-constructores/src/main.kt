fun main(args: Array<String>){
    val item = Listado("Corto", "L", 19.99)
    println(item)

    item.precio = 19.99
    println(item)

    val item2 = Listado("m",14.99)
    println(item2)
}