package modelo;

public class Estudiante {
    private String nombre;
    private int puntaje;

    public Estudiante(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void sumarPuntaje(int puntos) {
        this.puntaje += puntos;
    }
}
