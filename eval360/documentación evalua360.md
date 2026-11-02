# Proyecto Final — EVALUA360: DESAFÍO & CONOCIMIENTO

**Materia:** Programación II 
**Institución:** CeRP del Suroeste   
**Integrante:** Pedro Rossi  
**Fecha de entrega:** Octubre de 2026  
**Repositorio:** `https://github.com/pedrok1218/Evalua360/tree/main/eval360` 

---

## 1. Descripción general

### 1.1. Nombre del proyecto
**EVALUA360: DESAFÍO & CONOCIMIENTO** (Sistema de Cuestionarios y Evaluación Multitemática)

### 1.2. Resumen
EVALUA360 es una aplicación de escritorio desarrollada en Java que transforma las herramientas tradicionales de evaluación en una plataforma multitemática interactiva y modular.

El sistema incorpora un flujo obligatorio de identificación de estudiantes (`VentanaLogin`), un panel de administración para la carga y gestión de preguntas de opción múltiple y verdadero/falso (`FormularioPreguntaDialog`), y un registro automatizado de puntajes para el seguimiento del desempeño.

Las preguntas se almacenan en un archivo JSON estructurado (`preguntas_quiz.json`) mediante flujos E/S nativos de Java, mientras que los resultados históricos de los participantes se conservan en un archivo de texto plano (`resultados_quiz.txt`). La aplicación funciona de manera 100% local sin necesidad de servidores externos ni dependencias de terceros.

### 1.3. Contexto y problema
En el ámbito educativo y de autoevaluación, con frecuencia se carece de herramientas de escritorio locales, ligeras y ágiles que permitan a los docentes o usuarios crear bancos de preguntas personalizados por categoría, asegurar la identificación de cada participante y registrar de forma persistente los resultados sin depender de conexiones a internet o complejas configuraciones de bases de datos.

En un cuestionario en papel o manual, corregir cada respuesta y consolidar notas requiere tiempo considerable. EVALUA360 automatiza la evaluación en tiempo real, suma +10 puntos por acierto y conserva el historial de desempeño.

El proyecto permite integrar los conceptos fundamentales de Programación II: arquitectura MVC, patrones de diseño (Singleton), herencia, polimorfismo, encapsulamiento, manejo de excepciones y persistencia de datos.

### 1.4. Objetivos

#### Objetivo general
Desarrollar una aplicación de escritorio en Java con interfaz gráfica Swing que permita gestionar y resolver cuestionarios interactivos de múltiple temática aplicando arquitectura MVC, conceptos de POO y estrategias de persistencia dual local.

#### Objetivos específicos
- Implementar la identificación obligatoria de estudiantes antes de habilitar el cuestionario.
- Desarrollar un panel docente/administrador para agregar y gestionar preguntas por categoría.
- Aplicar **Herencia y Polimorfismo** para manipular de forma uniforme preguntas de **Opción Múltiple** y **Verdadero/Falso**.
- Diseñar una persistencia dual basada en archivos locales: JSON estructurado para preguntas (`preguntas_quiz.json`) y texto plano para resultados (`resultados_quiz.txt`), utilizando **E/S nativa de Java** (`PrintWriter`/`FileWriter`) para evitar dependencias externas.
- Organizar la estructura del código mediante **MVC** y aplicar el patrón de diseño **Singleton** en el gestor de la lógica (`QuizManager`).

---

## 2. Alcance

### 2.1. Funcionalidades incluidas
- **Identificación de Estudiante:** Cuadro modal obligatorio (`VentanaLogin`) para registrar el nombre del usuario antes de iniciar el desafío.
- **Panel de Modo Administrador:** Creación dinámica de preguntas clasificadas por categoría (Cine, Series, Cocina, Autos, etc.) y modalidad (Opción Múltiple o Verdadero/Falso).
- **Modo Estudiante (Desafío):** Interfaz interactiva secuencial que presenta las preguntas una a una, adapta los componentes visuales según la tipología de la pregunta y evalúa en tiempo real.
- **Calificación Automática:** Asignación de +10 puntos por cada respuesta correcta alcanzada.
- **Persistencia Dual Nativa:** 
  - Guardado del banco de preguntas en formato JSON estructurado (`preguntas_quiz.json`).
  - Volcado acumulativo del nombre del estudiante y su puntaje final en `resultados_quiz.txt`.
- **Carga de Preguntas por Defecto:** En caso de no existir el archivo de datos, el sistema genera automáticamente un conjunto inicial de preguntas para garantizar la ejecución.

### 2.2. Funcionalidades no incluidas
- Conexión a bases de datos relacionales en servidores remotos (MySQL, PostgreSQL).
- Autenticación con roles complejos cifrados o contraseñas encriptadas.
- Edición posterior de preguntas ya guardadas desde la interfaz gráfica.
- Ejecución en red distribuida para múltiples usuarios concurrentes.

---

## 3. Usuarios y casos de uso

### 3.1. Tipos de usuario

| Usuario | Descripción | Permisos y responsabilidades |
| :--- | :--- | :--- |
| **Docente / Administrador** | Usuario encargado de diseñar y configurar los contenidos evaluativos. | Crear nuevas preguntas por categoría, elegir la respuesta correcta y auditar el banco de preguntas. |
| **Estudiante** | Usuario evaluado que interactúa con la plataforma. | Registrar su nombre de identificación, resolver el cuestionario interactivo y consultar su puntaje final. |

### 3.2. Casos de uso principales

#### CU01: Identificación y Acceso al Desafío
- **Actor:** Estudiante.
- **Objetivo:** Registrar la identidad del participante e iniciar el quiz.
- **Precondiciones:** La aplicación debe estar ejecutándose.
- **Flujo principal:**
  1. Presionar "Modo Estudiante" en el menú principal.
  2. Ingresar el nombre en el cuadro modal de login.
  3. Presionar "Comenzar Desafío".
  4. El sistema instancia el objeto `Estudiante` y abre la ventana del quiz.
- **Resultado esperado:** Objeto `Estudiante` inicializado con puntaje 0 y acceso concedido.

#### CU02: Creación de Preguntas (Administrador)
- **Actor:** Docente / Administrador.
- **Objetivo:** Incorporar una nueva pregunta al banco de datos.
- **Precondiciones:** Seleccionar "Modo Administrador" desde la pantalla principal.
- **Flujo principal:**
  1. Seleccionar "Agregar Pregunta".
  2. Elegir el tipo de pregunta (Opción Múltiple o Verdadero/Falso).
  3. Ingresar la categoría, el enunciado y las opciones/respuesta correcta.
  4. Presionar "Guardar".
  5. El sistema serializa la pregunta al archivo `preguntas_quiz.json`.
- **Resultado esperado:** Pregunta almacenada en formato JSON y disponible para los cuestionarios.

#### CU03: Resolución y Calificación Automática
- **Actor:** Estudiante.
- **Objetivo:** Responder las preguntas del quiz y guardar el puntaje obtenido.
- **Precondiciones:** Haber completado la identificación de estudiante.
- **Flujo principal:**
  1. Leer el enunciado y las opciones presentadas.
  2. Seleccionar la alternativa correspondiente.
  3. El sistema evalúa la respuesta mediante polimorfismo (`evaluarRespuesta`).
  4. Si es correcta, se incrementa el puntaje (+10 pts).
  5. Al completar la última pregunta, el sistema escribe el resultado en `resultados_quiz.txt`.
- **Resultado esperado:** Intento evaluado y registrado en el archivo de texto acumulativo.

---

## 4. Interfaz gráfica

### 4.1. Descripción
La interfaz gráfica fue desarrollada íntegramente en Java **Swing** (`javax.swing`). La interacción se basa en componentes como `JFrame`, `JDialog`, `JButton`, `JRadioButton`, `ButtonGroup`, `JLabel` y `JTextField`, organizados con gestores de diseño (`BorderLayout`, `GridLayout`, `FlowLayout`) que aseguran una distribución adaptable.

### 4.2. Pantallas principales

| Pantalla | Propósito y funcionamiento |
| :--- | :--- |
| **VentanaPrincipal** | Menú inicial del programa. Ofrece el título del sistema y los botones para acceder al Modo Estudiante o Modo Administrador. |
| **VentanaLogin** | Diálogo modal que solicita el nombre del estudiante para inicializar su sesión antes del cuestionario. |
| **Interfaz del Quiz** | Muestra el progreso actual, la categoría, el enunciado y los controles radiales adaptados según el tipo de pregunta. |
| **FormularioPreguntaDialog** | Panel modal que permite al administrador ingresar categoría, enunciado y respuestas de nuevas preguntas. |

### 4.3. Navegación
1. Desde `VentanaPrincipal` se selecciona el rol.
2. Al elegir "Modo Estudiante", se despliega `VentanaLogin` de forma modal (bloqueando la ventana principal hasta ingresar el nombre).
3. Tras autenticarse, `VentanaPrincipal` conmuta internamente su panel para mostrar el flujo de preguntas.
4. Al finalizar el cuestionario, se muestra una ventana de diálogo con la puntuación obtenida y se retorna al menú principal.

---

## 5. Persistencia

### 5.1. Estrategia utilizada (Persistencia Dual Nativa)
Se implementó un esquema de almacenamiento local adaptado a las necesidades de cada entidad, utilizando únicamente las clases nativas de Java E/S (`java.io.*`):

- **`preguntas_quiz.json`:** Guarda el banco de preguntas en un formato JSON estructurado mediante `PrintWriter` y `FileWriter`.
- **`resultados_quiz.txt`:** Registra en modo *append* el nombre de los alumnos y sus puntajes finales (`Estudiante: [Nombre] | Puntaje Final: [X] pts`).

### 5.2. Datos persistidos

| Entidad / Archivo | Formato | Descripción |
| :--- | :--- | :--- |
| **Preguntas** (`preguntas_quiz.json`) | JSON Estructurado | Guarda tipo, categoría, enunciado y datos de opciones/respuestas. |
| **Resultados** (`resultados_quiz.txt`) | Texto Plano | Registro histórico de intentos finalizados. |

### 5.3. Carga y recuperación
- Al iniciar la aplicación, `QuizManager` invoca a `PersistenciaArchivo.cargarPreguntas()`.
- Si el archivo `preguntas_quiz.json` no existe o está vacío, el gestor carga las preguntas por defecto preprogramadas.
- Al guardar resultados, la clase abre el archivo en modo de edición continua (*append: true*) para preservar los registros previos.

---

## 6. Evaluación

### 6.1. ¿Qué se evalúa?
Se evalúa la precisión de las respuestas ingresadas por el estudiante frente al banco de preguntas cargado en el sistema.

### 6.2. Criterios de evaluación

| Criterio | Descripción | Puntaje asignado |
| :--- | :--- | :--- |
| **Respuesta Correcta** | La opción seleccionada coincide con la lógica de la subclase (`Pregunta`). | +10 Puntos |
| **Respuesta Incorrecta** | La opción no coincide con la respuesta válida. | 0 Puntos |

### 6.3. Método de evaluación
El método abstracto `evaluarRespuesta(Object respuesta)` en `Pregunta` es sobreescrito por cada clase hija:
- En `PreguntaOpcionMultiple`, se compara el índice seleccionado (`Integer`) con `indiceCorrecto`.
- En `PreguntaVerdaderoFalso`, se compara el valor booleano (`Boolean`) con la respuesta correcta.

Al finalizar el cuestionario, el puntaje total se consolida en la clase `Estudiante`.

---

## 7. Diseño y arquitectura

### 7.1. Arquitectura MVC (Modelo-Vista-Controlador)
El proyecto está estructurado en 4 paquetes independientes:

- **`modelo`:** Clases de dominio (`Estudiante`, `Pregunta`, `PreguntaOpcionMultiple`, `PreguntaVerdaderoFalso`) y gestor lógico (`QuizManager`).
- **`vista`:** Clases visuales Swing (`VentanaPrincipal`, `VentanaLogin`, `FormularioPreguntaDialog`).
- **`controlador`:** Clase `QuizController`, que escucha las acciones de la vista y coordina el flujo de datos hacia el modelo.
- **`persistencia`:** Clase `PersistenciaArchivo`, encargada del manejo de archivos E/S.

### 7.2. Patrón de diseño Singleton
- **Patrón seleccionado:** Singleton.
- **Aplicación:** Implementado en `QuizManager`. Su constructor es privado (`private QuizManager()`) y ofrece un método estático `getInstancia()`.
- **Justificación:** Garantiza que exista una única instancia del gestor de preguntas en toda la aplicación, asegurando coherencia de datos entre el panel de administración y el modo juego.

### 7.3. Diagrama conceptual de jerarquía de preguntas

```text
               +----------------------+
               |     /Pregunta/       | <--- (Clase Abstracta)
               +----------------------+
               | # categoria: String  |
               | # enunciado: String  |
               | # tipo: String       |
               +----------------------+
                          |
             +------------+------------+
             |                         |
  +--------------------+     +--------------------+
  |PreguntaOpcionMult. |     |PreguntaVerdaderoFals|
  +--------------------+     +--------------------+
  | - opciones: String[]|    | - respuestaV: bool |
  | - correcta: int    |     |                    |
  +--------------------+     +--------------------+
```

---

## 8. Conceptos de programación utilizados

### 8.1. Herencia
- `PreguntaOpcionMultiple` y `PreguntaVerdaderoFalso` heredan de la clase abstracta `Pregunta`.
- Las ventanas personalizadas heredan de `JFrame` o `JDialog`.

### 8.2. Polimorfismo
El controlador administra listas de tipo genérico `List<Pregunta>`. Durante la ejecución del quiz, se invoca `pregunta.evaluarRespuesta(...)` sin importar la subclase concreta, ejecutándose la lógica particular de cada tipo de pregunta.

### 8.3. Encapsulamiento
Todos los atributos se definen como `private` o `protected`. El acceso y modificación se realiza exclusivamente mediante métodos *getters* y *setters*.

### 8.4. Constructores
Uso de constructores parametrizados en el modelo (por ejemplo, `new Estudiante(nombre)` inicializa el atributo `nombre` y asigna `puntaje = 0`). Las subclases de `Pregunta` utilizan la instrucción `super(enunciado, categoria)` para delegar la inicialización a la clase padre.

### 8.5. Abstracción
La clase `Pregunta` define la firma del método abstracto `evaluarRespuesta(Object respuesta)`, obligando a las clases hijas a definir sus reglas particulares.

### 8.6. Manejo de excepciones y E/S nativa
Uso de estructuras `try-with-resources` en `PersistenciaArchivo` para garantizar la liberación de recursos E/S (`PrintWriter`, `FileWriter`) ante posibles excepciones `IOException`.

---

## 9. Organización del proyecto

### 9.1. Estructura general

```text
Evalua360/
├── Main.java
├── controlador/
│   └── QuizController.java
├── modelo/
│   ├── Estudiante.java
│   ├── Pregunta.java (Abstracta)
│   ├── PreguntaOpcionMultiple.java
│   ├── PreguntaVerdaderoFalso.java
│   └── QuizManager.java
├── persistencia/
│   └── PersistenciaArchivo.java
└── vista/
    ├── VentanaPrincipal.java
    ├── VentanaLogin.java
    └── FormularioPreguntaDialog.java
```

---

## 10. Decisiones de diseño

1. **Persistencia nativa sin librerías externas:**
   - **Problema:** Evitar conflictos de librerías (`.jar`) o dependencias no instaladas durante la corrección académica.
   - **Decisión:** Implementar escritura manual estructurada en formato JSON utilizando `PrintWriter` y `FileWriter` de la librería estándar `java.io.*`.

2. **Patrón Singleton en `QuizManager`:**
   - **Problema:** Evitar inconsistencias si múltiples pantallas intentan cargar o modificar el banco de preguntas por separado.
   - **Decisión:** Centralizar el estado del quiz en una única instancia global.

---

## 11. Validaciones y manejo de errores

- Comprobación de campo no vacío en `VentanaLogin`.
- Verificación de campos obligatorios al crear preguntas en `FormularioPreguntaDialog`.
- Control de excepciones `IOException` en lectura y escritura de archivos, evitando que la aplicación se detenga inesperadamente.

---

## 12. Requisitos y ejecución

### 12.1. Requisitos
- **JDK:** Java Development Kit 17 o superior.
- **IDE:** Visual Studio Code con *Extension Pack for Java* o Eclipse.
- **Sistema Operativo:** Windows, macOS o Linux.

### 12.2. Ejecución
1. Clonar o abrir la carpeta raíz del proyecto `Evalua360` en el IDE.
2. Ejecutar la clase `Main.java`.
3. Interactuar con el menú principal para iniciar el cuestionario o gestionar el banco de preguntas.

---

## 13. Pruebas realizadas

| Prueba | Situación | Resultado esperado | Resultado obtenido |
| :--- | :--- | :--- | :--- |
| **Login** | Ingreso de nombre de estudiante | Instanciación de objeto y apertura del quiz | Confirmado |
| **Evaluación V/F** | Selección de alternativa Verdadera/Falsa | Calificación instantánea y suma de puntos | Confirmado |
| **Evaluación Opción Múltiple** | Selección de una opción entre 4 | Calificación instantánea | Confirmado |
| **Persistencia JSON** | Guardado de nueva pregunta | Escritura estructurada en `preguntas_quiz.json` | Confirmado |
| **Persistencia TXT** | Finalización del quiz | Registro de línea en `resultados_quiz.txt` | Confirmado |

---

## 14. Limitaciones y posibles mejoras

### Limitaciones actuales
- El almacenamiento JSON se construye mediante concatenación de texto nativo sin parser dinámico avanzado para lectura.
- No incluye edición ni borrado directo de preguntas desde la interfaz Swing.

### Mejoras futuras
- Incorporar gráficos estadísticos de rendimiento para el docente.
- Permitir la exportación de resultados a planillas Excel/CSV.

---

## 15. Distribución del trabajo

**Integrante:** Pedro Rossi  
**Aportes:** Desarrollo integral de la arquitectura MVC, diseño de la interfaz Swing, implementación del patrón Singleton, estrategia de persistencia dual nativa y documentación general.

---

## 16. Conclusiones
El desarrollo del proyecto **EVALUA360** permitió consolidar de forma práctica los pilares de la Programación Orientada a Objetos en Java. La aplicación de MVC facilitó la separación entre la interfaz de usuario y la lógica de negocio, mientras que el uso de herencia y polimorfismo permitió gestionar distintas tipologías de preguntas con un diseño extensible.

---

## 17. Referencias
- Oracle. (s. f.). *Creating a GUI With JFC/Swing. The Java Tutorials.* https://docs.oracle.com/javase/tutorial/uiswing/
- Oracle. (s. f.). *Basic I/O. The Java Tutorials.* https://docs.oracle.com/javase/tutorial/essential/io/

---

## 18. Repositorio
**Repositorio público:** `https://github.com/pedrok1218/Evalua360/tree/main/eval360` 
