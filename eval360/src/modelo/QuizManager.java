package modelo;

import persistencia.PersistenciaArchivo;
import java.util.List;

public class QuizManager {
    private static QuizManager instancia;
    private List<Pregunta> listaPreguntas;
    private PersistenciaArchivo persistencia;

    private QuizManager() {
        persistencia = new PersistenciaArchivo();
        listaPreguntas = persistencia.cargarPreguntas();
        
        if (listaPreguntas.isEmpty()) {
            cargarPreguntasPorDefecto();
        }
    }

    public static synchronized QuizManager getInstancia() {
        if (instancia == null) {
            instancia = new QuizManager();
        }
        return instancia;
    }

    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void agregarPregunta(Pregunta p) {
        listaPreguntas.add(p);
        persistencia.guardarPreguntas(listaPreguntas);
    }

    public void eliminarPregunta(int indice) {
        if (indice >= 0 && indice < listaPreguntas.size()) {
            listaPreguntas.remove(indice);
            persistencia.guardarPreguntas(listaPreguntas);
        }
    }

    private void cargarPreguntasPorDefecto() {
        listaPreguntas.add(new PreguntaOpcionMultiple(
            "¿Quién dirigió la película 'Interestelar'?",
            "Cine",
            new String[]{"Steven Spielberg", "Christopher Nolan", "Quentin Tarantino", "Martin Scorsese"},
            1
        ));
        listaPreguntas.add(new PreguntaVerdaderoFalso(
            "¿La serie 'Breaking Bad' se estrenó en el año 2008?",
            "Series",
            true
        ));
        persistencia.guardarPreguntas(listaPreguntas);
    }
}