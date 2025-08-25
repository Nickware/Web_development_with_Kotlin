// Piedra, Papel, Tijeras, Lagarto, Spock
// Basado en el juego de The Big Bang Theory (Sheldon Cooper)
// "Tijeras cortan papel, papel cubre piedra, piedra aplasta lagarto,
//  lagarto envenena a Spock, Spock aplasta tijeras, tijeras decapitan lagarto,
//  lagarto se come papel, papel desaprueba a Spock, Spock vaporiza piedra,
//  y como siempre, piedra aplasta tijeras."

import kotlin.random.Random

enum class Jugada(val emoji: String, val descripcion: String) {
    PIEDRA("🪨", "Piedra"),
    PAPEL("📄", "Papel"),
    TIJERAS("✂️", "Tijeras"),
    LAGARTO("🦎", "Lagarto"),
    SPOCK("🖖", "Spock")
}

data class Regla(
    val ganador: Jugada,
    val perdedor: Jugada,
    val accion: String
)

data class Estadisticas(
    var partidasJugadas: Int = 0,
    var victoriasJugador: Int = 0,
    var victoriasComputadora: Int = 0,
    var empates: Int = 0
) {
    fun porcentajeVictorias(): Double {
        return if (partidasJugadas > 0) {
            (victoriasJugador.toDouble() / partidasJugadas) * 100
        } else 0.0
    }

    fun mostrarEstadisticas() {
        println("\n📊 === ESTADÍSTICAS ===")
        println("Partidas jugadas: $partidasJugadas")
        println("Tus victorias: $victoriasJugador")
        println("Victorias de la computadora: $victoriasComputadora")
        println("Empates: $empates")
        println("Porcentaje de victorias: ${String.format("%.1f", porcentajeVictorias())}%")
        println("========================\n")
    }
}

enum class Resultado {
    JUGADOR_GANA,
    COMPUTADORA_GANA,
    EMPATE
}

class JuegoPiedraPapelTijerasLagartoSpock {

    private val reglas = listOf(
        // Piedra
        Regla(Jugada.PIEDRA, Jugada.LAGARTO, "Piedra aplasta lagarto"),
        Regla(Jugada.PIEDRA, Jugada.TIJERAS, "Piedra aplasta tijeras"),

        // Papel
        Regla(Jugada.PAPEL, Jugada.PIEDRA, "Papel cubre piedra"),
        Regla(Jugada.PAPEL, Jugada.SPOCK, "Papel desaprueba a Spock"),

        // Tijeras
        Regla(Jugada.TIJERAS, Jugada.PAPEL, "Tijeras cortan papel"),
        Regla(Jugada.TIJERAS, Jugada.LAGARTO, "Tijeras decapitan lagarto"),

        // Lagarto
        Regla(Jugada.LAGARTO, Jugada.SPOCK, "Lagarto envenena a Spock"),
        Regla(Jugada.LAGARTO, Jugada.PAPEL, "Lagarto se come papel"),

        // Spock
        Regla(Jugada.SPOCK, Jugada.TIJERAS, "Spock aplasta tijeras"),
        Regla(Jugada.SPOCK, Jugada.PIEDRA, "Spock vaporiza piedra")
    )

    private val estadisticas = Estadisticas()

    fun mostrarReglas() {
        println("\n🎮 === REGLAS DEL JUEGO ===")
        println("Como dijo Sheldon Cooper:")
        println("\"Tijeras cortan papel, papel cubre piedra,")
        println(" piedra aplasta lagarto, lagarto envenena a Spock,")
        println(" Spock aplasta tijeras, tijeras decapitan lagarto,")
        println(" lagarto se come papel, papel desaprueba a Spock,")
        println(" Spock vaporiza piedra, y como siempre,")
        println(" piedra aplasta tijeras.\"")
        println("\nCada jugada puede ganar contra 2 jugadas y perder contra 2 jugadas.")

        println("\n🏆 QUIEN LE GANA A QUIEN:")
        reglas.forEach { regla ->
            println("${regla.ganador.emoji} ${regla.ganador.descripcion}: ${regla.accion}")
        }
        println("==========================\n")
    }

    fun mostrarOpciones() {
        println("Elige tu jugada:")
        Jugada.values().forEachIndexed { index, jugada ->
            println("${index + 1}. ${jugada.emoji} ${jugada.descripcion}")
        }
        print("\nTu elección (1-5): ")
    }

    fun obtenerJugadaComputadora(): Jugada {
        return Jugada.values()[Random.nextInt(Jugada.values().size)]
    }

    fun determinarGanador(jugadaJugador: Jugada, jugadaComputadora: Jugada): Resultado {
        if (jugadaJugador == jugadaComputadora) {
            return Resultado.EMPATE
        }

        // Buscar si el jugador gana
        val jugadorGana = reglas.any { regla ->
            regla.ganador == jugadaJugador && regla.perdedor == jugadaComputadora
        }

        return if (jugadorGana) Resultado.JUGADOR_GANA else Resultado.COMPUTADORA_GANA
    }

    fun obtenerExplicacion(jugadaJugador: Jugada, jugadaComputadora: Jugada): String {
        return reglas.find { regla ->
            (regla.ganador == jugadaJugador && regla.perdedor == jugadaComputadora) ||
                    (regla.ganador == jugadaComputadora && regla.perdedor == jugadaJugador)
        }?.accion ?: "Empate - misma jugada"
    }

    fun jugarRonda(): Boolean {
        mostrarOpciones()

        val scanner = java.util.Scanner(System.`in`)
        val entrada = scanner.nextLine().trim()

        // Verificar si quiere salir
        if (entrada.equals("salir", ignoreCase = true) ||
            entrada.equals("exit", ignoreCase = true) ||
            entrada.equals("q", ignoreCase = true)) {
            return false
        }

        // Verificar si quiere ver estadísticas
        if (entrada.equals("stats", ignoreCase = true) ||
            entrada.equals("estadisticas", ignoreCase = true)) {
            estadisticas.mostrarEstadisticas()
            return true
        }

        // Verificar si quiere ver reglas
        if (entrada.equals("reglas", ignoreCase = true) ||
            entrada.equals("rules", ignoreCase = true)) {
            mostrarReglas()
            return true
        }

        // Convertir entrada a número
        val opcion = entrada.toIntOrNull()
        if (opcion == null || opcion !in 1..5) {
            println("❌ Opción inválida. Elige un número del 1 al 5.")
            return true
        }

        val jugadaJugador = Jugada.values()[opcion - 1]
        val jugadaComputadora = obtenerJugadaComputadora()
        val resultado = determinarGanador(jugadaJugador, jugadaComputadora)
        val explicacion = obtenerExplicacion(jugadaJugador, jugadaComputadora)

        // Mostrar jugadas
        println("\n" + "=".repeat(40))
        println("Tu jugada: ${jugadaJugador.emoji} ${jugadaJugador.descripcion}")
        println("Computadora: ${jugadaComputadora.emoji} ${jugadaComputadora.descripcion}")
        println("=".repeat(40))

        // Mostrar resultado
        when (resultado) {
            Resultado.JUGADOR_GANA -> {
                println("🎉 ¡GANASTE!")
                println("💡 $explicacion")
                estadisticas.victoriasJugador++
            }
            Resultado.COMPUTADORA_GANA -> {
                println("😔 Perdiste...")
                println("💡 $explicacion")
                estadisticas.victoriasComputadora++
            }
            Resultado.EMPATE -> {
                println("🤝 ¡Empate!")
                println("💡 Ambos eligieron lo mismo")
                estadisticas.empates++
            }
        }

        estadisticas.partidasJugadas++

        // Mostrar puntuación rápida
        println("\n📊 Puntuación: Tú $${estadisticas.victoriasJugador} - $${estadisticas.victoriasComputadora} Computadora")
        println("=".repeat(50) + "\n")

        return true
    }

    fun mostrarMensajeDespedida() {
        println("\n🎭 === JUEGO TERMINADO ===")
        estadisticas.mostrarEstadisticas()

        when {
            estadisticas.victoriasJugador > estadisticas.victoriasComputadora -> {
                println("🏆 ¡Felicitaciones! Eres el campeón.")
                println("🤓 Como diría Sheldon: \"Fascinating! Your pattern recognition is superior.\"")
            }
            estadisticas.victoriasComputadora > estadisticas.victoriasJugador -> {
                println("🤖 La computadora ganó esta vez.")
                println("🤓 Como diría Sheldon: \"The random number generator has prevailed!\"")
            }
            else -> {
                println("🤝 Terminaron empatados.")
                println("🤓 Como diría Sheldon: \"A statistical tie. How... adequate.\"")
            }
        }

        println("\n📺 \"Bazinga! Gracias por jugar el juego favorito de Sheldon Cooper.\"")
        println("🖖 Live long and prosper!")
    }
}

fun main() {
    val juego = JuegoPiedraPapelTijerasLagartoSpock()

    println("🎮 ¡Bienvenido al Juego de Big Bang Theory!")
    println("🤓 Piedra, Papel, Tijeras, Lagarto, Spock")
    println("📺 \"Como dijo Sheldon Cooper...\"")

    juego.mostrarReglas()

    println("💡 Comandos especiales:")
    println("   • 'reglas' - Ver reglas del juego")
    println("   • 'stats' - Ver estadísticas")
    println("   • 'salir' - Terminar juego")
    println("\n" + "=".repeat(50))

    // Bucle principal del juego
    while (true) {
        if (!juego.jugarRonda()) {
            break
        }
    }

    juego.mostrarMensajeDespedida()
}

// Función adicional para testing
fun mostrarTodasLasReglas() {
    println("🧪 === TESTING: TODAS LAS COMBINACIONES ===")
    val juego = JuegoPiedraPapelTijerasLagartoSpock()

    for (jugada1 in Jugada.values()) {
        for (jugada2 in Jugada.values()) {
            if (jugada1 != jugada2) {
                val resultado = juego.determinarGanador(jugada1, jugada2)
                val explicacion = juego.obtenerExplicacion(jugada1, jugada2)
                println("${jugada1.emoji} vs ${jugada2.emoji}: ${if (resultado == Resultado.JUGADOR_GANA) jugada1.descripcion else jugada2.descripcion} gana - $explicacion")
            }
        }
    }
}