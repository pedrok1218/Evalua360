package persistencia;

import modelo.Pregunta;
import modelo.PreguntaOpcionMultiple;
import modelo.PreguntaVerdaderoFalso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersistenciaArchivo {
    private static final String RUTA_RAIZ = System.getProperty("user.dir");
    private static final File ARCHIVO_PREGUNTAS = new File(RUTA_RAIZ, "preguntas_quiz.json");
    private static final File ARCHIVO_RESULTADOS = new File(RUTA_RAIZ, "resultados_quiz.txt");

    // Guardar preguntas en formato JSON válido en la raíz del proyecto
    public void guardarPreguntas(List<Pregunta> preguntas) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_PREGUNTAS))) {
            pw.println("["); // Abre el arreglo JSON general
            for (int i = 0; i < preguntas.size(); i++) {
                Pregunta p = preguntas.get(i);
                pw.println("  {");
                pw.println("    \"tipo\": \"" + p.getTipo() + "\",");
                pw.println("    \"categoria\": \"" + p.getCategoria() + "\",");
                pw.println("    \"enunciado\": \"" + p.getEnunciado() + "\"");
                
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

    // Cargar preguntas leyendo el archivo de la raíz con los constructores exactos
    public List<Pregunta> cargarPreguntas() {
        List<Pregunta> lista = new ArrayList<>();
        if (!ARCHIVO_PREGUNTAS.exists()) {
            return lista; // Si no existe el archivo, retorna lista vacía
        }

        try (Scanner scanner = new Scanner(ARCHIVO_PREGUNTAS, "UTF-8")) {
            StringBuilder contenido = new StringBuilder();
            while (scanner.hasNextLine()) {
                contenido.append(scanner.nextLine()).append("\n");
            }

            String texto = contenido.toString().trim();
            if (texto.length() > 2) {
                texto = texto.substring(1, texto.length() - 1); // Quitar corchetes
                String[] bloques = texto.split("\\},\\s*\\{");

                for (String bloque : bloques) {
                    bloque = bloque.replace("{", "").replace("}", "").trim();
                    
                    String tipo = extraerValor(bloque, "tipo");
                    String categoria = extraerValor(bloque, "categoria");
                    String enunciado = extraerValor(bloque, "enunciado");

                    // Instanciación exacta usando los constructores reales de tus clases
                    if (tipo != null && tipo.toLowerCase().contains("verdadero")) {
                        lista.add(new PreguntaVerdaderoFalso(enunciado, categoria, true));
                    } else {
                        String[] opcionesDefault = {"Opción 1", "Opción 2"};
                        lista.add(new PreguntaOpcionMultiple(enunciado, categoria, opcionesDefault, 0));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
        }

        return lista;
    }

    // Método auxiliar para extraer los valores de texto del JSON plano
    private static String extraerValor(String textoBloque, String clave) {
        try {
            String patron = "\"" + clave + "\": \"";
            int inicio = textoBloque.indexOf(patron);
            if (inicio == -1) return "";
            inicio += patron.length();
            int fin = textoBloque.indexOf("\"", inicio);
            return textoBloque.substring(inicio, fin);
        } catch (Exception e) {
            return "";
        }
    }

    // Guardar el resultado del estudiante en un archivo de texto plano en la raíz
    public static void guardarResultado(String nombreEstudiante, int puntajeFinal) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_RESULTADOS, true))) {
            pw.println("Estudiante: " + nombreEstudiante + " | Puntaje Final: " + puntajeFinal + " pts");
        } catch (IOException e) {
            System.err.println("Error al guardar el resultado: " + e.getMessage());
        }
    }
}