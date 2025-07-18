# Sistema de Gestión de Estudiantes en Kotlin

Este es un **ejemplo educativo** de un sistema sencillo para gestionar estudiantes, realizado en el lenguaje **Kotlin** y pensado para ser probado en **IntelliJ IDEA 2025.1.2**. El proyecto muestra cómo trabajar con clases de datos, colecciones, funciones de extensión, manejo seguro de valores nulos y otras características modernas del lenguaje.

## Características

- Gestión de múltiples estudiantes.
- Agregado de materias y calificaciones por estudiante.
- Cálculo automático de promedios.
- Búsqueda eficiente de estudiantes por nombre.
- Estadísticas del sistema (promedios generales, total de materias y estudiantes).
- Ejemplo de uso de funciones de orden superior, expresiones `when` y seguridad para valores nulos.

## Estructura del Código

- `Estudiante`: Data class que representa un estudiante, con nombre, edad, materias y promedio.
- `GestorEstudiantes`: Clase que administra la lista de estudiantes, búsquedas y estadísticas.
- Función de extensión sobre `Int` para validar la edad.
- Ejemplo de uso en la función `main`.

## Requisitos

- **Kotlin** 1.8 o superior.
- **IntelliJ IDEA** 2024.1 o superior (opcional pero recomendado para una mejor experiencia).

## Uso

1. Clonar este repositorio o copiar el archivo Kotlin en el proyecto:
    ```
    git clone <tu-repositorio>
    ```
2. Abrir el archivo en **IntelliJ IDEA**.
3. Ejecutar el archivo `main.kt` directamente.
4. Observar el resultado en la consola: se mostrarán los estudiantes registrados, sus materias, promedios y estadísticas útiles.

## Ejemplo de Salida

```bash
Sistema de Gestión de Estudiantes
Estudiante Ana García agregado al sistema
Estudiante Carlos López agregado al sistema
Estudiante María Rodríguez agregado al sistema
Materia 'Matemáticas' agregada para Ana García
...
Información del Estudiante:
Nombre: Ana García
Edad: 20 años
Materias: Matemáticas, Física, Química
Promedio: 9.13
...
Mejor estudiante:
María Rodríguez con promedio de 9.67
EOF
```
## Personalización

Se puede ampliar este sistema:
- Permitiendo entrada por teclado.
- Guardando la información en archivos.
- Creando una interfaz gráfica.

## Licencia

MIT License.

---

**Autor:** N.Torres  
**Propósito:** Ejemplo de aprendizaje con Kotlin para usuarios de IntelliJ IDEA

