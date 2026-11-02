package modelo;

public class PreguntaOpcionMultiple extends Pregunta {
    private String[] opciones;
    private int indiceCorrecto;

    public PreguntaOpcionMultiple(String enunciado, String categoria, String[] opciones, int indiceCorrecto) {
        super(enunciado, categoria);
        this.opciones = opciones;
        this.indiceCorrecto = indiceCorrecto;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public int getIndiceCorrecto() {
        return indiceCorrecto;
    }

    @Override
    public boolean evaluarRespuesta(Object respuesta) {
        if (respuesta instanceof Integer) {
            return (Integer) respuesta == indiceCorrecto;
        }
        return false;
    }

    @Override
    public String getTipo() {
        return "Opción Múltiple";
    }
}
