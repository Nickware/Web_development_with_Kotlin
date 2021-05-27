package modelo

sealed class Listado(val tipo: String){
    abstract val tamaño:String
    abstract val precio:Double
}

data class Camisas(override var tamaño:String,
                   override var precio:Double): Listado("Camisas")

data class Pantis(override var tamaño:String,
                  override var precio:Double): Listado("Pantis")