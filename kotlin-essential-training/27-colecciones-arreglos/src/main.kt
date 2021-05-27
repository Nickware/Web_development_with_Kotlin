fun main(args: Array<String>){
    val arreglo = arrayOf("Rojo","Verde","Azul")
    for (elemento in arreglo){
        println(elemento)
    }
    val salpicon = arrayOf("Un string", 12)
    for (elemento in salpicon){
        println(elemento)

    }
    val nulos = kotlin.arrayOfNulls<String>(3)
    for (elemento in nulos){
        println(elemento)
    }
    nulos[0] = "Rojo"
    nulos[1] = "Azul"
    nulos[2] = "Verde"
    for (elemento in nulos){
        println(elemento)
    }

    val intArreglo = intArrayOf(3,4,5)
    for (elemento in intArreglo){
        println(elemento)
    }
    arreglo.sort()
    for (elemento in arreglo){
        println(elemento)
    }
    val arreglo2 = arreglo.sortedArrayDescending()
    for (elemento in arreglo2){
        println(elemento)
    }
}