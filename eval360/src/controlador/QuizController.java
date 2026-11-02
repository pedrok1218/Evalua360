package controlador;

import modelo.*;
import java.util.List;

public class QuizController {
    private QuizManager manager;

    public QuizController() {
        this.manager = QuizManager.getInstancia();
    }

    public List<Pregunta> obtenerPreguntas() {
        return manager.getListaPreguntas();
    }

    public void agregarOpcionMultiple(String enunciado, String categoria, String[] opciones, int correcta) {
        Pregunta p = new PreguntaOpcionMultiple(enunciado, categoria, opciones, correcta);
        manager.agregarPregunta(p);
    }

    public void agregarVerdaderoFalso(String enunciado, String categoria, boolean correcta) {
        Pregunta p = new PreguntaVerdaderoFalso(enunciado, categoria, correcta);
        manager.agregarPregunta(p);
    }

    public void eliminarPregunta(int indice) {
        manager.eliminarPregunta(indice);
    }

    public boolean evaluar(Pregunta p, Object respuesta) {
        return p.evaluarRespuesta(respuesta);
    }
}