import modelo.itemListado

fun main(args: Array<String>){
    var conjuntoColor = mutableSetOf("Rojo","Verde", "Azul")
    println(conjuntoColor)

    conjuntoColor.add("Purpura")
    conjuntoColor.remove("Verde")
    println(conjuntoColor)

    val listaColor = conjuntoColor.toMutableList()
    listaColor.removeAt(0)
    println(listaColor)
    listaColor.add("Purpura")
    println(listaColor)

    val conjuntoNuevo = listaColor.toMutableSet()
    println(conjuntoNuevo)

    val itemConjunto = mutableSetOf<itemListado>()
    itemConjunto.add(itemListado("Corto","XL", 14.99))
    itemConjunto.add(itemListado("Corto","M", 14.99))
    println(itemConjunto)
}