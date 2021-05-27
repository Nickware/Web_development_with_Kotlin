import modelo.Camisas
import modelo.Listado
import modelo.Pantis

fun main(){
    val item1 = Camisas("XL",19.99)
    val item2 = Pantis("32",24.99)

    val masExtensivo: Listado =
        if (item1.precio > item2.precio)
            item1
        else
            item2

    val instrucciones = when(masExtensivo){
        is Camisas -> "Abajo de este"
        is Pantis -> "Grande de este"

    }
    println(instrucciones)
}