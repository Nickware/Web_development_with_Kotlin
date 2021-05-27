fun main(args: Array<String>) {
    /*print("Ingrese la abreviatura de su Departamento de origen: ")
    val departamento = readLine()
    if (departamento == "Cun") {
        print("La Capital de Cundinamarca")
    } else if (departamento == "Ant") {
        print("La Capital de Antioquía")
    } else {
        print("Espero sepa cual es su departamento")
    }

    val capital = when (departamento){
        "Cun" -> "Bogota"
        "Ant" -> "Medellín"
        else -> "Desconocido"
    }
    println(" es $capital")

    when (departamento){
        "Cun", "Ant", "Val" -> println("Ciudad ubicada en la Región Andina")
        "Met", "Ara", "Cas" -> println("Ciudad Ubicada en la Región Llanera")
        else -> "Cualquier lugar"
    }*/
    val respuesta = 50
    when (respuesta) {
        in 1..39 -> println("Lejos")
        in 40..45 -> println("Cerca")
        else -> {
            println("Definitivamente No!")
            println("No, realmente")
        }

    }
}