fun main(){
    val holaMundo = {
        println("Hola Mundo")
    }
    holaMundo()
    holaMundo.invoke()

    val diHola = {
        usuario: String, edad: Int -> println("Hola, $usuario, tu tienes $edad años")
    }
    diHola("Nicolas",40)

    val estados = arrayOf("Chia","Gachancipa","Sopo","Tocancipa","Zipaquira")
    val almacenado = estados.sortedBy {it.length}
        .filter { it.startsWith("S",true) }
    for (estados in almacenado){
        println(estados)
    }
}
