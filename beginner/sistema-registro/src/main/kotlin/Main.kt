// Sistema de Registro de Asistencia a Eventos Académicos con QR
// Proyecto para nivel principiante-intermedio en Kotlin

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

// Enums para categorizar datos
enum class TipoEvento {
    CONFERENCIA, SEMINARIO, TALLER, CONGRESO, SIMPOSIO, WEBINAR
}

enum class EstadoAsistencia {
    REGISTRADO, PRESENTE, AUSENTE, CANCELADO
}

enum class TipoParticipante {
    ESTUDIANTE, PROFESOR, INVESTIGADOR, PROFESIONAL, INVITADO
}

// Data classes para estructurar la información
data class Participante(
    val id: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String,
    val institucion: String,
    val tipoParticipante: TipoParticipante,
    val fechaRegistro: LocalDateTime = LocalDateTime.now()
)

data class EventoAcademico(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val fechaInicio: LocalDateTime,
    val fechaFin: LocalDateTime,
    val lugar: String,
    val capacidadMaxima: Int,
    val tipo: TipoEvento,
    val codigoAcceso: String, // Código que estará en el QR
    val organizador: String
)

data class RegistroAsistencia(
    val participanteId: String,
    val eventoId: String,
    val fechaHoraRegistro: LocalDateTime,
    val codigoQRUsado: String,
    val estado: EstadoAsistencia,
    val ipRegistro: String? = null,
    val dispositivoRegistro: String? = null
)

data class CodigoQR(
    val codigo: String,
    val eventoId: String,
    val fechaGeneracion: LocalDateTime,
    val activo: Boolean = true,
    val usos: Int = 0
)

// Clase para validaciones
class ValidadorFormulario {
    
    fun validarEmail(email: String): Pair<Boolean, String> {
        val emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$".toRegex()
        return if (email.matches(emailRegex)) {
            Pair(true, "Email válido")
        } else {
            Pair(false, "Formato de email inválido")
        }
    }
    
    fun validarTelefono(telefono: String): Pair<Boolean, String> {
        val telefonoLimpio = telefono.replace("\\s|-|\\(|\\)".toRegex(), "")
        return when {
            telefonoLimpio.length < 7 -> Pair(false, "Teléfono muy corto")
            telefonoLimpio.length > 15 -> Pair(false, "Teléfono muy largo") 
            !telefonoLimpio.matches("\\+?[0-9]+".toRegex()) -> Pair(false, "Solo números y + permitidos")
            else -> Pair(true, "Teléfono válido")
        }
    }
    
    fun validarContrasena(contrasena: String): Pair<Boolean, String> {
        return when {
            contrasena.length < 8 -> Pair(false, "Mínimo 8 caracteres")
            !contrasena.any { it.isUpperCase() } -> Pair(false, "Debe contener al menos una mayúscula")
            !contrasena.any { it.isLowerCase() } -> Pair(false, "Debe contener al menos una minúscula")
            !contrasena.any { it.isDigit() } -> Pair(false, "Debe contener al menos un número")
            else -> Pair(true, "Contraseña segura")
        }
    }
    
    fun validarNombre(nombre: String): Pair<Boolean, String> {
        return when {
            nombre.trim().length < 2 -> Pair(false, "Nombre debe tener al menos 2 caracteres")
            !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+".toRegex()) -> Pair(false, "Solo letras y espacios permitidos")
            else -> Pair(true, "Nombre válido")
        }
    }
}

// Sistema principal de gestión de asistencia
class SistemaAsistenciaEventos {
    private val participantes = mutableMapOf<String, Participante>()
    private val eventos = mutableMapOf<String, EventoAcademico>()
    private val registrosAsistencia = mutableListOf<RegistroAsistencia>()
    private val codigosQR = mutableMapOf<String, CodigoQR>()
    private val validador = ValidadorFormulario()
    
    init {
        // Crear evento de ejemplo
        crearEventoEjemplo()
    }
    
    private fun crearEventoEjemplo() {
        val evento = EventoAcademico(
            id = "EVT-001",
            nombre = "Conferencia Internacional de Inteligencia Artificial 2024",
            descripcion = "Conferencia sobre avances en IA y machine learning",
            fechaInicio = LocalDateTime.now().plusDays(1),
            fechaFin = LocalDateTime.now().plusDays(2),
            lugar = "Auditorio Principal - Universidad Nacional",
            capacidadMaxima = 200,
            tipo = TipoEvento.CONFERENCIA,
            codigoAcceso = "AI2024CONF",
            organizador = "Facultad de Ingeniería"
        )
        eventos[evento.id] = evento
        
        // Generar código QR para el evento
        generarCodigoQR(evento.id)
    }
    
    private fun generarCodigoQR(eventoId: String): String {
        val codigo = "QR-${eventoId}-${Random.nextInt(1000, 9999)}"
        val codigoQR = CodigoQR(
            codigo = codigo,
            eventoId = eventoId,
            fechaGeneracion = LocalDateTime.now()
        )
        codigosQR[codigo] = codigoQR
        return codigo
    }
    
    fun mostrarFormularioRegistro() {
        println("\n" + "=".repeat(60))
        println("📋 REGISTRO DE ASISTENCIA - EVENTO ACADÉMICO")
        println("=".repeat(60))
        println("Por favor, complete todos los campos obligatorios:")
        println()
    }
    
    fun registrarParticipante(): String? {
        val scanner = Scanner(System.`in`)
        mostrarFormularioRegistro()
        
        // Recopilar información básica
        print("👤 Nombre: ")
        val nombre = scanner.nextLine().trim()
        val (nombreValido, mensajeNombre) = validador.validarNombre(nombre)
        if (!nombreValido) {
            println("❌ Error en nombre: $mensajeNombre")
            return null
        }
        
        print("👤 Apellido: ")
        val apellido = scanner.nextLine().trim()
        val (apellidoValido, mensajeApellido) = validador.validarNombre(apellido)
        if (!apellidoValido) {
            println("❌ Error en apellido: $mensajeApellido")
            return null
        }
        
        print("📧 Email: ")
        val email = scanner.nextLine().trim()
        val (emailValido, mensajeEmail) = validador.validarEmail(email)
        if (!emailValido) {
            println("❌ Error en email: $mensajeEmail")
            return null
        }
        
        print("📱 Teléfono: ")
        val telefono = scanner.nextLine().trim()
        val (telefonoValido, mensajeTelefono) = validador.validarTelefono(telefono)
        if (!telefonoValido) {
            println("❌ Error en teléfono: $mensajeTelefono")
            return null
        }
        
        print("🏛️ Institución: ")
        val institucion = scanner.nextLine().trim()
        
        // Seleccionar tipo de participante
        println("\n👥 Tipo de participante:")
        TipoParticipante.values().forEachIndexed { index, tipo ->
            println("${index + 1}. ${tipo.name.lowercase().replaceFirstChar { it.uppercase() }}")
        }
        print("Seleccione (1-${TipoParticipante.values().size}): ")
        
        val tipoIndex = scanner.nextLine().toIntOrNull()?.minus(1)
        if (tipoIndex == null || tipoIndex !in TipoParticipante.values().indices) {
            println("❌ Selección de tipo inválida")
            return null
        }
        val tipoParticipante = TipoParticipante.values()[tipoIndex]
        
        print("🔒 Contraseña para su cuenta: ")
        val contrasena = scanner.nextLine()
        val (contrasenaValida, mensajeContrasena) = validador.validarContrasena(contrasena)
        if (!contrasenaValida) {
            println("❌ Error en contraseña: $mensajeContrasena")
            return null
        }
        
        // Generar ID único para el participante
        val participanteId = "PART-${System.currentTimeMillis()}"
        
        // Crear participante
        val participante = Participante(
            id = participanteId,
            nombre = nombre,
            apellido = apellido,
            email = email,
            telefono = telefono,
            institucion = institucion,
            tipoParticipante = tipoParticipante
        )
        
        participantes[participanteId] = participante
        
        println("\n✅ ¡Registro exitoso!")
        println("📧 Se ha enviado un email de confirmación a: $email")
        println("🎫 Su ID de participante es: $participanteId")
        println("📱 Guarde este ID para acceder al evento")
        
        return participanteId
    }
    
    fun procesarCodigoQR(codigoQR: String, participanteId: String): Boolean {
        // Verificar que el código QR existe y está activo
        val codigoInfo = codigosQR[codigoQR]
        if (codigoInfo == null || !codigoInfo.activo) {
            println("❌ Código QR inválido o expirado")
            return false
        }
        
        // Verificar que el participante existe
        val participante = participantes[participanteId]
        if (participante == null) {
            println("❌ Participante no encontrado")
            return false
        }
        
        // Verificar que el evento existe
        val evento = eventos[codigoInfo.eventoId]
        if (evento == null) {
            println("❌ Evento no encontrado")
            return false
        }
        
        // Verificar que no se haya registrado ya la asistencia
        val yaRegistrado = registrosAsistencia.any { 
            it.participanteId == participanteId && it.eventoId == codigoInfo.eventoId 
        }
        if (yaRegistrado) {
            println("⚠️ Ya se registró asistencia para este evento")
            return false
        }
        
        // Crear registro de asistencia
        val registro = RegistroAsistencia(
            participanteId = participanteId,
            eventoId = codigoInfo.eventoId,
            fechaHoraRegistro = LocalDateTime.now(),
            codigoQRUsado = codigoQR,
            estado = EstadoAsistencia.PRESENTE,
            ipRegistro = "192.168.1.${Random.nextInt(1, 255)}", // Simulado
            dispositivoRegistro = "Android App"
        )
        
        registrosAsistencia.add(registro)
        
        // Actualizar usos del código QR
        codigosQR[codigoQR] = codigoInfo.copy(usos = codigoInfo.usos + 1)
        
        println("\n✅ ¡ASISTENCIA REGISTRADA EXITOSAMENTE!")
        println("👤 Participante: ${participante.nombre} ${participante.apellido}")
        println("🎓 Tipo: ${participante.tipoParticipante}")
        println("🏛️ Institución: ${participante.institucion}")
        println("📅 Evento: ${evento.nombre}")
        println("🕐 Hora de llegada: ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
        println("📍 Lugar: ${evento.lugar}")
        
        return true
    }
    
    fun simularEscaneoQR() {
        println("\n📱 === SIMULADOR DE ESCANEO QR ===")
        println("En una app real, aquí se abriría la cámara para escanear el código QR")
        println("Para esta simulación, usaremos el código QR generado automáticamente")
        
        val codigoQRDisponible = codigosQR.keys.firstOrNull()
        if (codigoQRDisponible != null) {
            println("🔍 Código QR detectado: $codigoQRDisponible")
            
            print("🎫 Ingrese su ID de participante: ")
            val scanner = Scanner(System.`in`)
            val participanteId = scanner.nextLine().trim()
            
            procesarCodigoQR(codigoQRDisponible, participanteId)
        } else {
            println("❌ No hay códigos QR disponibles")
        }
    }
    
    fun mostrarEstadisticas() {
        println("\n📊 === ESTADÍSTICAS DEL EVENTO ===")
        
        eventos.forEach { (eventoId, evento) ->
            val asistenciasEvento = registrosAsistencia.filter { it.eventoId == eventoId }
            val presentes = asistenciasEvento.filter { it.estado == EstadoAsistencia.PRESENTE }
            
            println("\n🎓 Evento: ${evento.nombre}")
            println("📅 Fecha: ${evento.fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
            println("📍 Lugar: ${evento.lugar}")
            println("👥 Capacidad máxima: ${evento.capacidadMaxima}")
            println("✅ Asistentes registrados: ${presentes.size}")
            println("📈 Porcentaje de ocupación: ${String.format("%.1f", (presentes.size.toDouble() / evento.capacidadMaxima) * 100)}%")
            
            // Estadísticas por tipo de participante
            println("\n📋 Asistencia por tipo:")
            TipoParticipante.values().forEach { tipo ->
                val count = presentes.count { registro ->
                    participantes[registro.participanteId]?.tipoParticipante == tipo
                }
                if (count > 0) {
                    println("   ${tipo.name.lowercase().replaceFirstChar { it.uppercase() }}: $count")
                }
            }
        }
        
        println("\n" + "=".repeat(50))
    }
    
    fun mostrarListaAsistentes() {
        println("\n📋 === LISTA DE ASISTENTES ===")
        
        val asistentesPresentes = registrosAsistencia.filter { it.estado == EstadoAsistencia.PRESENTE }
        
        if (asistentesPresentes.isEmpty()) {
            println("No hay asistentes registrados aún.")
            return
        }
        
        asistentesPresentes.forEachIndexed { index, registro ->
            val participante = participantes[registro.participanteId]
            val evento = eventos[registro.eventoId]
            
            if (participante != null && evento != null) {
                println("\n${index + 1}. 👤 ${participante.nombre} ${participante.apellido}")
                println("   📧 ${participante.email}")
                println("   📱 ${participante.telefono}")
                println("   🏛️ ${participante.institucion}")
                println("   👥 ${participante.tipoParticipante}")
                println("   🕐 Llegada: ${registro.fechaHoraRegistro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
                println("   🎓 Evento: ${evento.nombre}")
            }
        }
        
        println("\n" + "=".repeat(50))
    }
    
    fun exportarDatos(): String {
        val reporte = StringBuilder()
        reporte.append("REPORTE DE ASISTENCIA - EVENTOS ACADÉMICOS\n")
        reporte.append("Generado: ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}\n")
        reporte.append("=".repeat(80) + "\n\n")
        
        eventos.forEach { (eventoId, evento) ->
            reporte.append("EVENTO: ${evento.nombre}\n")
            reporte.append("Fecha: ${evento.fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}\n")
            reporte.append("Lugar: ${evento.lugar}\n\n")
            
            val asistentesEvento = registrosAsistencia.filter { it.eventoId == eventoId && it.estado == EstadoAsistencia.PRESENTE }
            reporte.append("ASISTENTES (${asistentesEvento.size}):\n")
            reporte.append("-".repeat(80) + "\n")
            reporte.append("NOMBRE\tAPELLIDO\tEMAIL\tTELEFONO\tINSTITUCIÓN\tTIPO\tHORA_LLEGADA\n")
            
            asistentesEvento.forEach { registro ->
                val participante = participantes[registro.participanteId]
                if (participante != null) {
                    reporte.append("${participante.nombre}\t${participante.apellido}\t${participante.email}\t")
                    reporte.append("${participante.telefono}\t${participante.institucion}\t${participante.tipoParticipante}\t")
                    reporte.append("${registro.fechaHoraRegistro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}\n")
                }
            }
            reporte.append("\n")
        }
        
        return reporte.toString()
    }
}

// Función principal del programa
fun main() {
    val sistema = SistemaAsistenciaEventos()
    val scanner = Scanner(System.`in`)
    
    println("🎓 SISTEMA DE ASISTENCIA A EVENTOS ACADÉMICOS")
    println("=".repeat(60))
    
    while (true) {
        println("\n📋 MENÚ PRINCIPAL:")
        println("1. 📝 Registrar nuevo participante")
        println("2. 📱 Escanear código QR (simular)")
        println("3. 📊 Ver estadísticas del evento")
        println("4. 📋 Lista de asistentes")
        println("5. 📄 Exportar reporte")
        println("6. ❌ Salir")
        
        print("\nSeleccione una opción (1-6): ")
        
        when (scanner.nextLine().trim()) {
            "1" -> {
                val participanteId = sistema.registrarParticipante()
                if (participanteId != null) {
                    println("\n💡 Ahora puede usar la opción 2 para escanear el código QR y registrar su asistencia")
                }
            }
            "2" -> sistema.simularEscaneoQR()
            "3" -> sistema.mostrarEstadisticas()
            "4" -> sistema.mostrarListaAsistentes()
            "5" -> {
                println("\n📄 === REPORTE EXPORTADO ===")
                println(sistema.exportarDatos())
            }
            "6" -> {
                println("\n👋 ¡Gracias por usar el Sistema de Asistencia!")
                println("🎓 Evento académico gestionado exitosamente")
                break
            }
            else -> println("❌ Opción inválida. Seleccione 1-6.")
        }
    }
}
