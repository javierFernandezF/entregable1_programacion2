// Entregable 1 - Programación 2
// Autor: Javier Fernandez
// Nro de estudiante: 172271

package entragable_1_programacion_2;

import java.util.*;

public class GestorJugadores {
    private ArrayList<Jugador> jugadores;
    
    public GestorJugadores() {
        this.jugadores = new ArrayList<Jugador>();
    }
    
    
    public boolean registrarJugador(String nombre, int edad) {
        if (existeJugador(nombre)) {
            return false; 
        }
        
        Jugador nuevoJugador = new Jugador(nombre, edad);
        jugadores.add(nuevoJugador);
        return true;
    }
    public boolean existeJugador(String nombre) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }
    
    public Jugador obtenerJugador(String nombre) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return jugadores.get(i);
            }
        }
        return null;
    }
    
    public ArrayList<Jugador> obtenerJugadoresOrdenados() {
        ArrayList<Jugador> jugadoresOrdenados = new ArrayList<>(jugadores);
        
        for (int i = 0; i < jugadoresOrdenados.size() - 1; i++) {
            for (int j = 0; j < jugadoresOrdenados.size() - 1 - i; j++) {
                String nombre1 = jugadoresOrdenados.get(j).getNombre().toLowerCase();
                String nombre2 = jugadoresOrdenados.get(j + 1).getNombre().toLowerCase();
                
                if (nombre1.compareTo(nombre2) > 0) {
                    // Intercambiar posiciones
                    Jugador temp = jugadoresOrdenados.get(j);
                    jugadoresOrdenados.set(j, jugadoresOrdenados.get(j + 1));
                    jugadoresOrdenados.set(j + 1, temp);
                }
            }
        }
        
        return jugadoresOrdenados;
    }
    
    public void mostrarListaJugadores() {
        ArrayList<Jugador> jugadoresOrdenados = obtenerJugadoresOrdenados();
        
        if (jugadoresOrdenados.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }
        
        System.out.println("=== LISTA DE JUGADORES ===");


        for (int i = 0; i < jugadoresOrdenados.size(); i++) {
            Jugador jugador = jugadoresOrdenados.get(i);
            System.out.println((i + 1) + ". " + jugador.getNombre() + 
                             " (Edad: " + jugador.getEdad() + 
                             ", Ganadas: " + jugador.getPartidasGanadas() + 
                             ", Perdidas: " + jugador.getPartidasPerdidas() + ")");
        }
        System.out.println("==========================");
    }
    
    
    // Mostrar ranking
    public void mostrarRanking() {
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }
        
        ArrayList<Jugador> ranking = new ArrayList<>(jugadores);
        Collections.sort(ranking);
        
        System.out.println("=== RANKING DE JUGADORES ===");
        for (int i = 0; i < ranking.size(); i++) {
            Jugador jugador = ranking.get(i);
            System.out.println((i + 1) + ". " + jugador.getNombre() + 
                             " - " + jugador.getPartidasGanadas() + " partidas ganadas");
        }
        System.out.println("============================");
    }
    
    // Mostrar jugadores invictos
    public void mostrarJugadoresInvictos() {
        ArrayList<Jugador> invictos = new ArrayList<>();
        
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getPartidasPerdidas() == 0) {
                invictos.add(jugadores.get(i));
            }
        }
        
        if (invictos.isEmpty()) {
            System.out.println("No hay jugadores invictos.");
            return;
        }
        
        for (int i = 0; i < invictos.size() - 1; i++) {
            for (int j = 0; j < invictos.size() - 1 - i; j++) {
                String nombre1 = invictos.get(j).getNombre().toLowerCase();
                String nombre2 = invictos.get(j + 1).getNombre().toLowerCase();
                
                if (nombre1.compareTo(nombre2) > 0) {
                    // Intercambiar posiciones
                    Jugador temp = invictos.get(j);
                    invictos.set(j, invictos.get(j + 1));
                    invictos.set(j + 1, temp);
                }
            }
        }
        
        System.out.println("=== JUGADORES INVICTOS ===");
        for (Jugador jugador : invictos) {
            String estado;
            if (jugador.getPartidasGanadas() == 0 && jugador.getPartidasPerdidas() == 0) {
                estado = "Nunca jugó";
            } else {
                estado = "Nunca perdió";
            }
            System.out.println("- " + jugador.getNombre() + " (" + estado + ")");
        }
        System.out.println("==========================");
    }
    
    public int getCantidadJugadores() {
        return jugadores.size();
    }
}
