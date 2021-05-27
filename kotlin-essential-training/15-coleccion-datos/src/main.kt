import java.awt.Label

fun main(args: Array<String>){
    val colores = arrayOf("Rojo", "Verde", "Azul")
    val numeros = intArrayOf(1,3,5,7,9)

    cabezaraImpresion("Por cada loop")
    for (color in colores){
        println(color)
    }
    println("*********************")
    for (numero in numeros){
        println(numero)
    }
    println("Fín")

    cabezaraImpresion("Por cada loop con indices")
    for(i in numeros.indices step 2){
        println(numeros[i])
    }
    println("*********************")
    for(i in 0 until colores.size){
        println(colores[i])
    }
    println("Fín")
}

fun cabezaraImpresion(label: String){
    println("*********************")
    println(label)
    println("*********************")
}