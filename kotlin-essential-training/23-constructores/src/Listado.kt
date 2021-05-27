data class Listado (var tipo: String?,
                    val tamaño: String,
                    var precio: Double){
    init {
        tipo = tipo?.toUpperCase() ?: "Desconocido"
    }

    constructor(tamaño: String, precio: Double) : this (null, tamaño, precio){
    }
}