package entragable_1_programacion_2;

import java.util.Scanner;

/**
 * Clase principal del juego Medio Tateti
 */
public class MedioTateti {
    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private Scanner scanner;
    private boolean juegoTerminado;
    
    public MedioTateti() {
        this.tablero = new Tablero();
        this.scanner = new Scanner(System.in);
        this.juegoTerminado = false;
    }
    
    public void iniciarJuego() {
        System.out.println("=== MEDIO TATETI ===");
        configurarJugadores();
        
        while (!juegoTerminado) {
            tablero.mostrarTablero();
            mostrarTurno();
            
            boolean turnoCompletado = ejecutarJugada();
            
            verificarGanador();
            
            // Solo cambiar turno si el turno se completó exitosamente
            if (turnoCompletado && !juegoTerminado) {
                cambiarTurno();
            }
        }
        
        mostrarResultado();
    }
    
    private void configurarJugadores() {
        System.out.print("Nombre Jugador 1 (X): ");
        String nombre1 = scanner.nextLine();
        System.out.print("Nombre Jugador 2 (O): ");
        String nombre2 = scanner.nextLine();
        
        jugador1 = new Jugador(nombre1, 'X');
        jugador2 = new Jugador(nombre2, 'O');
        jugadorActual = jugador1;
    }
    
    private void mostrarTurno() {
        System.out.println("Turno: " + jugadorActual.getNombre() + " (" + jugadorActual.getSimbolo() + ")");
    }
    
    private boolean ejecutarJugada() {
        System.out.print("Ingrese jugada: ");
        String input = scanner.nextLine().toUpperCase();
        
        // Comandos especiales
        if (procesarComandosEspeciales(input)) {
            return false; // No cambiar turno para comandos especiales
        }
        
        // Validar formato de jugada normal (ej: A1C, B2I)
        if (input.length() != 3) {
            System.out.println("Formato invalido. Use 3 caracteres (ej: A1C, B2I)");
            return false;
        }
        
        char letraFila = input.charAt(0);
        char numeroColumna = input.charAt(1);
        char orientacion = input.charAt(2);
        
        int fila = letraFila - 'A';
        int columna = numeroColumna - '1'; // Convertir de 1-6 a 0-5
        
        // Validar coordenadas
        if (fila < 0 || fila > 2 || columna < 0 || columna > 5) {
            System.out.println("Coordenadas invalidas. Use A-C para filas y 1-6 para columnas");
            return false;
        }
        
        // Procesar jugada de invertir
        if (orientacion == 'I') {
            if (tablero.obtenerPieza(fila, columna) == ' ') {
                System.out.println("No hay pieza para invertir");
                return false;
            }
            if (tablero.invertirPieza(fila, columna)) {
                System.out.println("Pieza invertida en " + input.substring(0, 2));
                return true;
            } else {
                System.out.println("No se puede invertir esa pieza");
                return false;
            }
        }
        
        // Procesar jugada de colocar pieza
        if (orientacion == 'C' || orientacion == 'D') {
            if (tablero.colocarPieza(fila, columna, orientacion, jugadorActual.getSimbolo())) {
                System.out.println("Pieza colocada en " + input);
                return true;
            } else {
                System.out.println("Posicion ocupada");
                return false;
            }
        }
        
        System.out.println("Orientacion invalida. Use C, D o I");
        return false;
    }
    
    
    private void verificarGanador() {
        if (tablero.verificarGanador()) {
            juegoTerminado = true;
            char letraGanadora = tablero.obtenerGanador();
            
            // Mostrar el tablero con la línea ganadora resaltada
            System.out.println("\n=== TABLERO FINAL ===");
            tablero.mostrarTableroConLineaGanadora(letraGanadora);
            
            // Mostrar mensaje de victoria personalizado
            String nombreGanador = "";
            if (letraGanadora == 'O') {
                nombreGanador = (jugador1.getSimbolo() == 'O') ? jugador1.getNombre() : jugador2.getNombre();
                System.out.println("¡¡¡ " + nombreGanador + " GANA !!!");
                System.out.println("Formó 3 letras 'O' alineadas");
            } else if (letraGanadora == 'X') {
                nombreGanador = (jugador1.getSimbolo() == 'X') ? jugador1.getNombre() : jugador2.getNombre();
                System.out.println("¡¡¡ " + nombreGanador + " GANA !!!");
                System.out.println("Formó 3 letras 'X' alineadas");
            }
            System.out.println("=====================");
        }
    }
    
    private void cambiarTurno() {
        if (!juegoTerminado) {
            jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
        }
    }
    
    private boolean procesarComandosEspeciales(String input) {
        if (input.equals("X")) {
            System.out.println(jugadorActual.getNombre() + " se rinde. Pierde la partida.");
            juegoTerminado = true;
            return true;
        }
        
        if (input.equals("B")) {
            tablero.mostrarTitulos();
            System.out.println("Titulos mostrados");
            return true;
        }
        
        if (input.equals("N")) {
            tablero.ocultarTitulos();
            System.out.println("Titulos ocultos");
            return true;
        }
        
        if (input.equals("T")) {
            System.out.print("¿Confirma empate? (S/N): ");
            String confirmacion = scanner.nextLine().toUpperCase();
            if (confirmacion.equals("S")) {
                System.out.println("Empate por mutuo acuerdo");
                juegoTerminado = true;
            }
            return true;
        }
        
        if (input.equals("H")) {
            String jugadaGanadora = buscarJugadaGanadora();
            if (jugadaGanadora != null) {
                System.out.println("Jugada ganadora sugerida: " + jugadaGanadora);
            } else {
                System.out.println("No hay jugada ganadora disponible");
            }
            return true;
        }
        
        return false; // No es un comando especial
    }
    
    private String buscarJugadaGanadora() {
        // Buscar si hay una jugada que permita ganar al jugador actual
        char letraJugadorActual = (jugadorActual.getSimbolo() == 'X') ? 'X' : 'O';
        
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                // Probar colocar pieza C
                if (tablero.obtenerPieza(fila, columna) == ' ') {
                    if (tablero.colocarPieza(fila, columna, 'C', jugadorActual.getSimbolo())) {
                        if (tablero.verificarGanador()) {
                            // Verificar que el jugador actual es quien gana
                            char ganador = tablero.obtenerGanador();
                            if (ganador == letraJugadorActual) {
                                // Deshacer el movimiento
                                tablero.establecerPieza(fila, columna, ' ');
                                char filaChar = (char)('A' + fila);
                                char columnaChar = (char)('1' + columna);
                                return "" + filaChar + columnaChar + "C";
                            }
                        }
                        // Deshacer el movimiento
                        tablero.establecerPieza(fila, columna, ' ');
                    }
                }
                
                // Probar colocar pieza D
                if (tablero.obtenerPieza(fila, columna) == ' ') {
                    if (tablero.colocarPieza(fila, columna, 'D', jugadorActual.getSimbolo())) {
                        if (tablero.verificarGanador()) {
                            // Verificar que el jugador actual es quien gana
                            char ganador = tablero.obtenerGanador();
                            if (ganador == letraJugadorActual) {
                                // Deshacer el movimiento
                                tablero.establecerPieza(fila, columna, ' ');
                                char filaChar = (char)('A' + fila);
                                char columnaChar = (char)('1' + columna);
                                return "" + filaChar + columnaChar + "D";
                            }
                        }
                        // Deshacer el movimiento
                        tablero.establecerPieza(fila, columna, ' ');
                    }
                }
                
                // Probar invertir pieza existente
                if (tablero.obtenerPieza(fila, columna) != ' ') {
                    if (tablero.invertirPieza(fila, columna)) {
                        if (tablero.verificarGanador()) {
                            // Verificar que el jugador actual es quien gana
                            char ganador = tablero.obtenerGanador();
                            if (ganador == letraJugadorActual) {
                                // Deshacer el movimiento
                                tablero.invertirPieza(fila, columna);
                                char filaChar = (char)('A' + fila);
                                char columnaChar = (char)('1' + columna);
                                return "" + filaChar + columnaChar + "I";
                            }
                        }
                        // Deshacer el movimiento
                        tablero.invertirPieza(fila, columna);
                    }
                }
            }
        }
        return null; // No hay jugada ganadora
    }
    
    private void mostrarResultado() {
        // El resultado ya se mostró en verificarGanador()
        System.out.println("¡Gracias por jugar Medio Tateti!");
    }
}
