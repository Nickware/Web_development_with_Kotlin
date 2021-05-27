fun main(args: Array<String>){
    val listaColores = listOf("Rojo","Verde","Azul")
    println(listaColores)

    println("Numero de colores: ${listaColores.size}")
    println(listaColores::class.simpleName)

    val listaColores2 = mutableListOf<String>()
    listaColores2.add("Rojo")
    listaColores2.add("Verde")
    listaColores2.add("Azul")
    println(listaColores2)

    listaColores2.sort()
    println(listaColores2)

    val listaCorta = listaColores2.sortedDescending()
    println(listaCorta)

    listaColores2.removeAt(0)
    println(listaColores2)

    listaColores2.remove("Rojo")
    println(listaColores2)
}