package es.cifpcm;

public class Persona {
    public String nombre;
    public String apellidos;
    public int edad;

    public Persona(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return String.format("%s %s, %s años", this.nombre, this.apellidos, this.edad);
    }
}
