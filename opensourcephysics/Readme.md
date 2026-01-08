# Diseño para migrar una simulación de *Open Source Physics* (OSP) a Android, usando **Jetpack Compose** 

A cotninuación se propone un **plan concreto y ejecutable** para migrar una simulación de *Open Source Physics* (OSP) a Android, usando **Jetpack Compose** (**JetBrains**) y el ecosistema **Jetpack** de Android), con enfoque en **evaluación pedagógica, pruebas técnicas y lanzamiento real**.

---

## Objetivo
Crear una **app Android nativa en Kotlin**, usando **Jetpack Compose**, que ejecute una simulación física de OSP (por ejemplo, *movimiento parabólico con resistencia del aire*), reutilizando su lógica científica sin alterarla, y que esté lista para ser **probada con estudiantes, evaluada formativamente y publicada**.

---

## Arquitectura propuesta (capas limpias)

```
[ Capa de Presentación ]   ← Jetpack Compose (UI en Android)
            ↑
[ Capa de Dominio ]        ← ViewModel + Use Cases (Kotlin)
            ↑
[ Capa de Datos / Motor ]  ← Módulo "osp-core" (Java/Kotlin, sin UI)
```

---

## Paso 1: Preparar el **módulo científico (osp-core)**

### Acción:
- Eligir una simulación existente de OSP (por ejemplo, `ProjectileAirResistance.java` de un paquete tipo `org.opensourcephysics.example`).
- Extraer **solo la lógica de integración numérica y cálculos físicos**:
  - Estado: posición, velocidad, tiempo.
  - Método: `step(dt)` o `evolve()`.
  - Parámetros: masa, coeficiente de arrastre, gravedad, etc.
- **Eliminar toda dependencia de AWT, Swing, Display, etc.**
- Empaquétarlo como un módulo **`osp-core`** en un proyecto Gradle multi-módulo.

### Resultado:
```kotlin
// Ejemplo ideal del núcleo
class ProjectileModel(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var vx: Double = 10.0,
    var vy: Double = 10.0,
    val mass: Double = 1.0,
    val drag: Double = 0.1,
    val g: Double = 9.8
) {
    fun step(dt: Double) {
        val v = sqrt(vx*vx + vy*vy)
        val ax = -drag * v * vx / mass
        val ay = -g - drag * v * vy / mass
        vx += ax * dt
        vy += ay * dt
        x += vx * dt
        y += vy * dt
    }

    fun isGrounded() = y <= 0.0
}
```

> Este código puede estar en **Java** o **Kotlin**; si está en Java, Kotlin lo usará sin problemas.

---

## Paso 2: Crear la app Android con **Jetpack Compose**

### Estructura del proyecto (Gradle multi-módulo):
```
osp-android-app/
├── app/               ← Módulo Android (Kotlin + Compose)
├── osp-core/          ← Módulo compartido (Java/Kotlin puro, sin Android)
└── build.gradle.kts
```

### En `app/build.gradle.kts`:
```kotlin
dependencies {
    implementation(project(":osp-core"))
    implementation("androidx.compose.ui:ui:1.7.0")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
}
```

---

## Paso 3: Diseñar la UI con Jetpack Compose

### Componentes clave:
- **Canvas personalizado** para dibujar la trayectoria en tiempo real.
- **Controles deslizantes** para modificar parámetros (ángulo, velocidad, arrastre).
- **Botones**: *Iniciar*, *Pausar*, *Reiniciar*.
- **Métricas en vivo**: posición, tiempo, energía.

### Ejemplo de ViewModel:
```kotlin
class SimulationViewModel : ViewModel() {
    private val model = ProjectileModel()
    private var isRunning by mutableStateOf(false)
    val trajectory = mutableStateListOf<Offset>()

    fun start() { isRunning = true }
    fun reset() { /* reinicia modelo y trayectoria */ }

    fun update(dt: Float) {
        if (!isRunning) return
        model.step(dt.toDouble())
        trajectory.add(Offset(model.x.toFloat(), (-model.y).toFloat()))
        if (model.isGrounded()) isRunning = false
    }
}
```

### En la UI (Compose):
```kotlin
@Composable
fun SimulationScreen(viewModel: SimulationViewModel) {
    val trajectory by viewModel.trajectory
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(16) // ~60 FPS
            viewModel.update(0.016f)
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Dibuja ejes, suelo, y puntos de la trayectoria
        trajectory.forEach { point ->
            drawCircle(Color.Red, radius = 4f, center = point)
        }
    }
}
```

---

## Paso 4: Pruebas (Testing)

### Niveles:
1. **Pruebas unitarias del núcleo (`osp-core`)**:
   - Verificar que la energía se disipe correctamente con arrastre.
   - Comparar resultados con soluciones analíticas (cuando existan).
   - Usar JUnit + Kotlin test.

2. **Pruebas de integración (Android)**:
   - Verificar que el ViewModel actualice el estado correctamente.
   - Usar `TestCoroutineDispatcher` para controlar el tiempo.

3. **Pruebas de usabilidad**:
   - Prototipo en 3–5 estudiantes: ¿entienden los controles? ¿ven la física claramente?
   - Usar cuestionarios breves (Google Forms) o observación directa.

---

## Paso 5: Lanzamiento (Release)

### Opciones:
- **Versión beta interna**: distribuir mediante **Google Play Console (Internal Testing)** a docentes y estudiantes de tu facultad.
- **Versión pública**: si el prototipo escalar, lánzalar como app educativa gratuita (ej. *"OSP Mobile: Física en tu bolsillo"*).
- **Código abierto**: publicar en GitHub con licencia MIT o GPL, invitando a otras universidades a contribuir.

### Requisitos técnicos mínimos:
- Android 8.0+ (API 26)
- Soporte para pantallas de 5" a 10"
- Sin dependencia de internet (todo offline)

---

##  Visión a mediano plazo

Una vez validada esta simulación:
- **Generalizar el motor**: crea una interfaz `PhysicsModel` que permita conectar cualquier simulación OSP.
- **Construir un “catálogo”**: lista de simulaciones descargables o integradas.
- **Agrega exportación de datos**: CSV, gráficos, comparación con datos reales (¡integración futura con Tracker móvil!).
