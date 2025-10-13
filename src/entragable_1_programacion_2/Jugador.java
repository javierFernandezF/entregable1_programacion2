package entragable_1_programacion_2;

/**
 * Clase que representa un jugador del juego Medio Tateti
 */
public class Jugador {
    private String nombre;
    private char simbolo;
    
    public Jugador(String nombre, char simbolo) {
        this.nombre = nombre;
        this.simbolo = simbolo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public char getSimbolo() {
        return simbolo;
    }
}
