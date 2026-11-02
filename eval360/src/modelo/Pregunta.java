package modelo;

import java.io.Serializable;

public abstract class Pregunta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String enunciado;
    private String categoria;

    public Pregunta(String enunciado, String categoria) {
        this.enunciado = enunciado;
        this.categoria = categoria;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public abstract boolean evaluarRespuesta(Object respuesta);
    public abstract String getTipo();
}
