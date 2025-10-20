// Entregable 1 - Programación 2
// Autor: Javier Fernandez
// Nro de estudiante: 172271

package entragable_1_programacion_2;

public class Jugador implements Comparable<Jugador> {
    private String nombre;
    private int edad;
    private int partidasGanadas;
    private int partidasPerdidas;
    
    public Jugador(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.partidasGanadas = 0;
        this.partidasPerdidas = 0;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    
    public int getEdad() {
        return edad;
    }
    
    public int getPartidasGanadas() {
        return partidasGanadas;
    }
    
    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }
    
    public void incrementarPartidasGanadas() {
        this.partidasGanadas++;
    }
    
    public void incrementarPartidasPerdidas() {
        this.partidasPerdidas++;
    }
    
    public int getTotalPartidas() {
        return partidasGanadas + partidasPerdidas;
    }
    
    @Override
    public int compareTo(Jugador otro) {
        // Orden descendente por partidas ganadas (más ganadas primero)
        return Integer.compare(otro.partidasGanadas, this.partidasGanadas);
    }
}
