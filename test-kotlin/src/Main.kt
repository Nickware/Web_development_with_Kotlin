// Ejemplo básico en Kotlin para probar IntelliJ IDEA 2025.1.2

// Clase de datos para representar un estudiante
data class Estudiante(
    val nombre: String,
    val edad: Int,
    val materias: MutableList<String> = mutableListOf(),
    var promedio: Double = 0.0
) {
    // Función para agregar una materia
    fun agregarMateria(materia: String) {
        if (materia.isNotBlank()) {
            materias.add(materia)
            println("✅ Materia '$materia' agregada para $nombre")
        }
    }

    // Función para calcular y actualizar el promedio
    fun actualizarPromedio(calificaciones: List<Double>) {
        if (calificaciones.isNotEmpty()) {
            promedio = calificaciones.average()
            println("📊 Promedio actualizado para $nombre: ${"%.2f".format(promedio)}")
        }
    }

    // Función para mostrar información del estudiante
    fun mostrarInfo() {
        println("\n👤 Información del Estudiante:")
        println("   Nombre: $nombre")
        println("   Edad: $edad años")
        println("   Materias: ${materias.joinToString(", ")}")
        println("   Promedio: ${"%.2f".format(promedio)}")
    }
}

// Clase para gestionar múltiples estudiantes
class GestorEstudiantes {
    private val estudiantes = mutableListOf<Estudiante>()

    fun agregarEstudiante(estudiante: Estudiante) {
        estudiantes.add(estudiante)
        println("🎓 Estudiante ${estudiante.nombre} agregado al sistema")
    }

    fun buscarEstudiante(nombre: String): Estudiante? {
        return estudiantes.find { it.nombre.equals(nombre, ignoreCase = true) }
    }

    fun mostrarTodosLosEstudiantes() {
        if (estudiantes.isEmpty()) {
            println("❌ No hay estudiantes registrados")
            return
        }

        println("\n📋 Lista de todos los estudiantes:")
        estudiantes.forEach { it.mostrarInfo() }
    }

    fun obtenerMejorEstudiante(): Estudiante? {
        return estudiantes.maxByOrNull { it.promedio }
    }

    fun obtenerEstadisticas() {
        if (estudiantes.isEmpty()) return

        val promedioGeneral = estudiantes.map { it.promedio }.average()
        val totalMaterias = estudiantes.sumOf { it.materias.size }

        println("\n📈 Estadísticas del Sistema:")
        println("   Total de estudiantes: ${estudiantes.size}")
        println("   Promedio general: ${"%.2f".format(promedioGeneral)}")
        println("   Total de materias registradas: $totalMaterias")
    }
}

// Función de extensión para validar edad
fun Int.esEdadValida(): Boolean = this in 15..100

// Función main - punto de entrada del programa
fun main() {
    println("🏫 Sistema de Gestión de Estudiantes")
    println("=====================================")

    // Crear instancia del gestor
    val gestor = GestorEstudiantes()

    // Crear algunos estudiantes de ejemplo
    val estudiante1 = Estudiante("Ana García", 20)
    val estudiante2 = Estudiante("Carlos López", 19)
    val estudiante3 = Estudiante("María Rodríguez", 21)

    // Validar edades usando la función de extensión
    listOf(estudiante1, estudiante2, estudiante3).forEach { estudiante ->
        if (estudiante.edad.esEdadValida()) {
            gestor.agregarEstudiante(estudiante)
        } else {
            println("⚠️ Edad inválida para ${estudiante.nombre}")
        }
    }

    // Agregar materias a los estudiantes
    estudiante1.apply {
        agregarMateria("Matemáticas")
        agregarMateria("Física")
        agregarMateria("Química")
        actualizarPromedio(listOf(9.5, 8.7, 9.2))
    }

    estudiante2.apply {
        agregarMateria("Historia")
        agregarMateria("Literatura")
        actualizarPromedio(listOf(8.5, 9.0))
    }

    estudiante3.apply {
        agregarMateria("Programación")
        agregarMateria("Base de Datos")
        agregarMateria("Algoritmos")
        actualizarPromedio(listOf(9.8, 9.5, 9.7))
    }

    // Mostrar todos los estudiantes
    gestor.mostrarTodosLosEstudiantes()

    // Buscar un estudiante específico
    println("\n🔍 Buscando estudiante 'Ana García':")
    val estudianteEncontrado = gestor.buscarEstudiante("Ana García")
    estudianteEncontrado?.mostrarInfo() ?: println("❌ Estudiante no encontrado")

    // Mostrar el mejor estudiante
    println("\n🏆 Mejor estudiante:")
    val mejorEstudiante = gestor.obtenerMejorEstudiante()
    mejorEstudiante?.let {
        println("🥇 ${it.nombre} con promedio de ${"%.2f".format(it.promedio)}")
    }

    // Mostrar estadísticas
    gestor.obtenerEstadisticas()

    // Demostrar algunas características de Kotlin
    println("\n🔧 Demostrando características de Kotlin:")

    // Funciones de orden superior
    val nombresEstudiantes = listOf("Ana", "Carlos", "María")
    val nombresMayusculas = nombresEstudiantes
        .filter { it.length > 3 }
        .map { it.uppercase() }

    println("   Nombres filtrados y en mayúsculas: $nombresMayusculas")

    // When expression
    val calificacion = 9.2
    val categoria = when {
        calificacion >= 9.0 -> "Excelente"
        calificacion >= 8.0 -> "Muy Bueno"
        calificacion >= 7.0 -> "Bueno"
        calificacion >= 6.0 -> "Regular"
        else -> "Necesita Mejorar"
    }
    println("   Calificación $calificacion es: $categoria")

    // Null safety
    val estudianteOpcional: Estudiante? = gestor.buscarEstudiante("Pedro")
    estudianteOpcional?.let {
        println("   Estudiante encontrado: ${it.nombre}")
    } ?: println("   ℹ️ Estudiante 'Pedro' no existe en el sistema")

    println("\n✨ ¡Ejemplo completado! Tu IntelliJ IDEA está funcionando correctamente.")
}