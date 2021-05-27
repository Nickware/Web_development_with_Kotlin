import java.awt.Label

fun main(args: Array<String>){
    val estados = arrayOf("Cun", "Ant", "Val")

    imprimirEncabezado("Mientras el loop")
    var contador = 0;
    while (contador < estados.size){
        println("Contador = $contador")
        println("estado = ${estados[contador]}")
        contador ++
    }

    contador = 0
    imprimirEncabezado("Hacer loop")
    do{
        println("Estado = ${estados.get(contador)}")
        contador ++
    } while (contador <estados.size)
}

fun imprimirEncabezado(label: String){
    println("**********************")
    println(label)
    println("**********************")
}