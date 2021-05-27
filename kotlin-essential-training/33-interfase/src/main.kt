fun main(){
    val pinina = golden()
    //pinina.habla()
    haceEstoHablar(pinina)
    reporteMascota("Pinina", pinina)
}

fun haceEstoHablar(perro: golden){
    perro.habla()
}

fun reporteMascota(name: String, perro: perro){
    println("$name es un ${perro::class.simpleName}")
    println("Este perro ${perro.furioso} es furioso ")
}

interface perro{
    var furioso: String
    fun habla(){
        println("Woof")
    }
}

interface gato{
    var furioso: String
    fun habla(){
        println("Miau")
    }

}

class golden : perro, gato{
    override var furioso: String
    get() = "Golden"
    set(value){}

    override fun habla() {
        super<perro>.habla()
        super<gato>.habla()
    }
}