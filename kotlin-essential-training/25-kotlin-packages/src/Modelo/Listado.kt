package Modelo

data class Listado (private var _tipo: String?,
                    val tamaño : String,
                    private var _precio: Double){
    var tipo: String? = _tipo
        get() = field ?: "Desconocido"

    var precio = _precio
        set(valor) {
            field = valor * .9
        }

    constructor(tamaño: String, precio: Double): this(null, tamaño, precio)
}