fun main(args: Array<String>) {
    print("Ingrese la abreviatura de su Departamento de origen: ")
    val departamento = readLine()
    if (departamento == "Cun") {
        print("La Capital de Cundinamarca es Bogotá")
    } else if (departamento == "Antioquía") {
        print("La Capital de Antioquía es Medellín")
    } else {
        print("Espero sepa cual es su departamento")
    }
}