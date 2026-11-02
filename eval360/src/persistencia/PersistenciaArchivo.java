package persistencia;

import modelo.Pregunta;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaArchivo {
    private static final String ARCHIVO_PREGUNTAS = "preguntas_quiz.json";

    // Guardar preguntas en formato JSON válido
    public void guardarPreguntas(List<Pregunta> preguntas) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_PREGUNTAS))) {
            pw.println("["); // Abre el arreglo JSON general
            for (int i = 0; i < preguntas.size(); i++) {
                Pregunta p = preguntas.get(i);
                pw.println("  {");
                pw.println("    \"tipo\": \"" + p.getTipo() + "\",");
                pw.println("    \"categoria\": \"" + p.getCategoria() + "\",");
                pw.println("    \"enunciado\": \"" + p.getEnunciado() + "\"");
                
                // Si es el último elemento, no lleva coma al final; si hay más, sí
                if (i < preguntas.size() - 1) {
                    pw.println("  },");
                } else {
                    pw.println("  }");
                }
            }
            pw.println("]"); // Cierra el arreglo JSON
            System.out.println("Preguntas guardadas exitosamente en formato JSON.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo JSON: " + e.getMessage());
        }
    }

    // Cargar preguntas (si no existe el archivo, devuelve lista vacía para que el QuizManager cargue las de defecto)
    public List<Pregunta> cargarPreguntas() {
        List<Pregunta> lista = new ArrayList<>();
        File file = new File(ARCHIVO_PREGUNTAS);
        if (!file.exists()) {
            return lista;
        }
        return lista;
    }

    // Guardar el resultado del estudiante en un archivo de texto plano
    public static void guardarResultado(String nombreEstudiante, int puntajeFinal) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("resultados_quiz.txt", true))) {
            pw.println("Estudiante: " + nombreEstudiante + " | Puntaje Final: " + puntajeFinal + " pts");
        } catch (IOException e) {
            System.err.println("Error al guardar el resultado: " + e.getMessage());
        }
    }
}