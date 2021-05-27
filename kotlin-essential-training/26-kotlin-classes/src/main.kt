import java.text.NumberFormat
import java.util.*

fun main(args: Array<String>){
    val item = Listado("Corto","L",19.99)
    println(item)

    item.precio = 15.99
    println(item)

    val item2 = Listado("M", 14.99)
    println(item2)
    println("Tipo de item = ${item2.tipo}")

    item2.precio = 10.0
    val f = "%.2f"
    println("Precio del item = $${f.format(item2.precio)}")

    Locale.setDefault(Locale.US)
    var formatear = NumberFormat.getCurrencyInstance()
    println("Precio del item = ${formatear.format(item2.precio)}")


    val person = Persona("Nicolas", "Torres")
    println("Mi nombre es ${person.nombreCompleto}")
}