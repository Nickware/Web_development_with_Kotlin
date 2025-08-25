// Quiz de Ciencias Físicas - Nivel Principiante
// Estructura básica para empezar

data class Pregunta(
    val id: Int,
    val pregunta: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int,
    val categoria: String,
    val dificultad: String,
    val explicacion: String? = null
)

data class Resultado(
    val preguntasRespondidas: Int,
    val respuestasCorrectas: Int,
    val puntuacion: Double
) {
    fun porcentajeAcierto() = (respuestasCorrectas.toDouble() / preguntasRespondidas) * 100
}

class QuizCiencias {
    private val preguntas = mutableListOf<Pregunta>()
    private var preguntaActual = 0
    private var respuestasCorrectas = 0

    init {
        cargarPreguntasEjemplo()
    }

    private fun cargarPreguntasEjemplo() {
        preguntas.addAll(listOf(
            Pregunta(
                1,
                "¿Cuál es la unidad de medida de la fuerza en el Sistema Internacional?",
                listOf("Newton", "Joule", "Watt", "Pascal"),
                0,
                "Mecánica",
                "Fácil",
                "El Newton (N) es la unidad de fuerza, definida como kg⋅m/s²"
            ),
            Pregunta(
                2,
                "¿A qué velocidad viaja la luz en el vacío?",
                listOf("300,000 km/s", "150,000 km/s", "299,792,458 m/s", "Ambas a y c son correctas"),
                3,
                "Óptica",
                "Medio",
                "La velocidad de la luz es aproximadamente 300,000 km/s o exactamente 299,792,458 m/s"
            ),
            Pregunta(
                3,
                "¿Qué establece la primera ley de Newton?",
                listOf("F = ma", "Un objeto en reposo permanece en reposo", "Acción y reacción", "E = mc²"),
                1,
                "Mecánica",
                "Fácil",
                "La ley de inercia: un objeto en reposo permanece en reposo y uno en movimiento permanece en movimiento"
            ),
            Pregunta(
                4,
                "¿Cuál es la aceleración de la gravedad en la Tierra?",
                listOf("9.8 m/s²", "10 m/s²", "9.81 m/s²", "Todas las anteriores son aproximadamente correctas"),
                3,
                "Mecánica",
                "Fácil",
                "La aceleración gravitacional estándar es 9.81 m/s², aunque se aproxima a 9.8 o 10 m/s²"
            ),
            Pregunta(
                5,
                "¿Qué tipo de lente corrige la miopía?",
                listOf("Convergente", "Divergente", "Cilíndrica", "Prismática"),
                1,
                "Óptica",
                "Medio",
                "Las lentes divergentes (cóncavas) corrigen la miopía al divergir los rayos de luz"
            )
        ))
    }

    fun obtenerPreguntaActual(): Pregunta? {
        return if (preguntaActual < preguntas.size) {
            preguntas[preguntaActual]
        } else null
    }

    fun responder(opcionSeleccionada: Int): Boolean {
        val pregunta = obtenerPreguntaActual()
        val esCorrecta = pregunta?.respuestaCorrecta == opcionSeleccionada

        if (esCorrecta) {
            respuestasCorrectas++
        }

        preguntaActual++
        return esCorrecta
    }

    fun obtenerResultado(): Resultado {
        return Resultado(preguntaActual, respuestasCorrectas, calcularPuntuacion())
    }

    private fun calcularPuntuacion(): Double {
        return if (preguntaActual > 0) {
            (respuestasCorrectas.toDouble() / preguntaActual) * 100
        } else 0.0
    }

    fun reiniciarQuiz() {
        preguntaActual = 0
        respuestasCorrectas = 0
        preguntas.shuffle() // Orden aleatorio
    }

    fun quizTerminado(): Boolean = preguntaActual >= preguntas.size

    fun obtenerProgreso(): String = "${preguntaActual}/${preguntas.size}"
}

// Ejemplo de uso en consola
fun main() {
    val quiz = QuizCiencias()
    val scanner = java.util.Scanner(System.`in`)

    println("=== QUIZ DE CIENCIAS FÍSICAS ===")
    println("Responde las siguientes preguntas seleccionando el número de la opción correcta.\n")

    while (!quiz.quizTerminado()) {
        val pregunta = quiz.obtenerPreguntaActual()
        if (pregunta != null) {
            println("Pregunta ${quiz.obtenerProgreso()}")
            println("Categoría: ${pregunta.categoria} | Dificultad: ${pregunta.dificultad}")
            println("\n${pregunta.pregunta}")

            pregunta.opciones.forEachIndexed { index, opcion ->
                println("${index + 1}. $opcion")
            }

            print("\nTu respuesta (1-4): ")
            val respuesta = scanner.nextInt() - 1

            val esCorrecta = quiz.responder(respuesta)

            if (esCorrecta) {
                println("✅ ¡Correcto!")
            } else {
                println("❌ Incorrecto. La respuesta correcta era: ${pregunta.opciones[pregunta.respuestaCorrecta]}")
            }

            pregunta.explicacion?.let {
                println("💡 $it")
            }

            println("\n" + "=".repeat(50) + "\n")
        }
    }

    val resultado = quiz.obtenerResultado()
    println("🎉 QUIZ COMPLETADO 🎉")
    println("Preguntas respondidas: ${resultado.preguntasRespondidas}")
    println("Respuestas correctas: ${resultado.respuestasCorrectas}")
    println("Puntuación: ${String.format("%.1f", resultado.porcentajeAcierto())}%")

    when {
        resultado.porcentajeAcierto() >= 90 -> println("🏆 ¡Excelente! Eres un experto en física")
        resultado.porcentajeAcierto() >= 70 -> println("👍 ¡Muy bien! Tienes buenos conocimientos")
        resultado.porcentajeAcierto() >= 50 -> println("📚 Bien, pero puedes mejorar estudiando más")
        else -> println("💪 Sigue estudiando, la física es fascinante")
    }
}