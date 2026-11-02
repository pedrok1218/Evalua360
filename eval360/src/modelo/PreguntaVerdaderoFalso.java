package modelo;

public class PreguntaVerdaderoFalso extends Pregunta {
    private boolean respuestaCorrecta;

    public PreguntaVerdaderoFalso(String enunciado, String categoria, boolean respuestaCorrecta) {
        super(enunciado, categoria);
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public boolean isRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    @Override
    public boolean evaluarRespuesta(Object respuesta) {
        if (respuesta instanceof Boolean) {
            return (Boolean) respuesta == respuestaCorrecta;
        }
        return false;
    }

    @Override
    public String getTipo() {
        return "Verdadero / Falso";
    }
}