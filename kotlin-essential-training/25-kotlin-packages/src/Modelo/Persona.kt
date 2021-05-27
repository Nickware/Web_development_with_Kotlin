package Modelo

data class Persona (private val nombre: String,
                    private val apellido: String){
    val nombreCompleto:String
    get() = "${nombre} ${apellido}"
}