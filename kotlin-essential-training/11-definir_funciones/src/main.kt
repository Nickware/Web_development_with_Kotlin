fun main(args: Array<String>) {
    val num1 = 15.0
    val num2 = 10.0

    val resultado = agregarValores(param1 = num2, param2 = num1)
    println("El resultado es $resultado")

    val resultado2 = calcularValores(num1, num2, op = "-")
    println("El resultado es $resultado2")

    cambiarAlgo(5.0)

    val suma = agregarValores(1,3,5,7)
    println("Suma de los números = $suma")
}

fun agregarValores(param1: Double, param2: Double): Double {
    return param1 + param2
}

fun calcularValores(param1: Double, param2: Double, op: String = "+"): Double {
    if (op.equals("+")) {
        return param1 + param2
    } else if (op.equals("-")) {
        return param1 - param2
    } else {
        return -1.0
    }
}

fun cambiarAlgo(param: Double) {
    var copia = param
    copia++
    println("La copia del número: $copia")
}

fun agregarValores(vararg numeros: Int): Int {
    var resultado = 0
    for (i in numeros) {
        resultado += i
    }
    return resultado
}