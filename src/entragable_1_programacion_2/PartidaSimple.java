// Entregable 1 - Programación 2
// Autor: Javier Fernandez
// Nro de estudiante: 172271

package entragable_1_programacion_2;

import java.util.*;

public class PartidaSimple {
    // Atributos del tablero
    private char[][] tablero;
    private boolean mostrarTitulos;
    private List<int[]> posicionesLineaGanadora;
    
    // Atributos de la partida
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private char simboloJugador1;
    private char simboloJugador2;
    private Scanner scanner;
    private boolean juegoTerminado;
    
    public PartidaSimple() {
        // Inicializar tablero
        this.tablero = new char[3][6];
        this.mostrarTitulos = true;
        this.posicionesLineaGanadora = new ArrayList<>();
        inicializarTablero();
        
        // Inicializar partida
        this.scanner = new Scanner(System.in);
        this.juegoTerminado = false;
    }
    
    public PartidaSimple(Jugador jugador1, Jugador jugador2) {
        this();
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorActual = jugador1;
        this.simboloJugador1 = 'X';  
        this.simboloJugador2 = 'O';  
    }
    
    // Método para obtener el símbolo de un jugador en esta partida
    public char getSimbolo(Jugador jugador) {
        if (jugador == jugador1) {
            return simboloJugador1;
        } else if (jugador == jugador2) {
            return simboloJugador2;
        }
        return ' ';
    }
    
    private void inicializarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                tablero[i][j] = ' ';
            }
        }
    }
    
    // Metodos de tablero
    
    public boolean colocarPieza(int fila, int columna, char orientacion, char jugador) {
        if (fila < 0 || fila > 2 || columna < 0 || columna > 5) {
            return false;
        }
        
        if (tablero[fila][columna] != ' ') {
            return false;
        }
        
        char pieza;
        if (jugador == 'X') {
            pieza = Character.toUpperCase(orientacion);
        } else {
            pieza = Character.toLowerCase(orientacion);
        }
        tablero[fila][columna] = pieza;
        return true;
    }
    
    public boolean invertirPieza(int fila, int columna) {
        if (fila < 0 || fila > 2 || columna < 0 || columna > 5) {
            return false;
        }
        
        char piezaActual = tablero[fila][columna];
        if (piezaActual == ' ') {
            return false;
        }
        
        char nuevaPieza;
        if (piezaActual == 'C') nuevaPieza = 'D';
        else if (piezaActual == 'D') nuevaPieza = 'C';
        else if (piezaActual == 'c') nuevaPieza = 'd';
        else if (piezaActual == 'd') nuevaPieza = 'c';
        else return false;
        
        tablero[fila][columna] = nuevaPieza;
        return true;
    }
    
    public char obtenerPieza(int fila, int columna) {
        if (fila < 0 || fila > 2 || columna < 0 || columna > 5) {
            return ' ';
        }
        return tablero[fila][columna];
    }
    
    public void establecerPieza(int fila, int columna, char pieza) {
        if (fila >= 0 && fila < 3 && columna >= 0 && columna < 6) {
            tablero[fila][columna] = pieza;
        }
    }
    
    public void mostrarTitulos() {
        this.mostrarTitulos = true;
    }
    
    public void ocultarTitulos() {
        this.mostrarTitulos = false;
    }
    
    public void mostrarTablero() {
        if (mostrarTitulos) {
            System.out.println(" 1  2  3  4  5  6");
        }
        
        System.out.println("+--+--+--+--+--+--+");
        
        for (int i = 0; i < 3; i++) {
            for (int lineaFila = 0; lineaFila < 3; lineaFila++) {
                if (mostrarTitulos && lineaFila == 1) {
                    char fila = (char)('A' + i);
                    System.out.print(fila + "|");
                } else {
                    System.out.print(" |");
                }
                
                for (int j = 0; j < 6; j++) {
                    char pieza = tablero[i][j];
                    String lineaPieza = obtenerLineaPieza(pieza, lineaFila);
                    System.out.print(lineaPieza + "|");
                }
                System.out.println();
            }
            System.out.println("+--+--+--+--+--+--+");
        }
    }
    
    private String obtenerLineaPieza(char pieza, int linea) {
        switch (pieza) {
            case 'C': 
                if (linea == 0) return " ●";      
                if (linea == 1) return "● ";      
                if (linea == 2) return " ●";      
                break;
            case 'c': 
                if (linea == 0) return " ○";      
                if (linea == 1) return "○ ";      
                if (linea == 2) return " ○";      
                break;
            case 'D': 
                if (linea == 0) return "● ";      
                if (linea == 1) return " ●";      
                if (linea == 2) return "● ";      
                break;
            case 'd': 
                if (linea == 0) return "○ ";      
                if (linea == 1) return " ○";      
                if (linea == 2) return "○ ";      
                break;
            default:
                return "  ";
        }
        return "  ";
    }
    
    // Metodos que verifican si alguien gano
    
    public boolean verificarGanador() {
        if (verificarTresLetrasAlineadas('O')) return true;
        if (verificarTresLetrasAlineadas('X')) return true;
        return false;
    }
    
    public char obtenerGanador() {
        if (verificarTresLetrasAlineadas('O')) return 'O';
        if (verificarTresLetrasAlineadas('X')) return 'X';
        return ' ';
    }
    
    private char detectarLetraEnPar(char pos1, char pos2) {
        char p1 = Character.toUpperCase(pos1);
        char p2 = Character.toUpperCase(pos2);
        
        if (p1 == 'C' && p2 == 'D') return 'O';
        else if (p1 == 'D' && p2 == 'C') return 'X';
        return ' ';
    }
    
    private boolean verificarTresLetrasAlineadas(char letraBuscada) {
        posicionesLineaGanadora.clear();
        
        if (verificarAlineacionHorizontalPares(letraBuscada)) return true;
        if (verificarAlineacionVerticalPares(letraBuscada)) return true;
        if (verificarAlineacionDiagonalPares(letraBuscada)) return true;
        
        return false;
    }
    
    private boolean verificarAlineacionHorizontalPares(char letraBuscada) {
        for (int fila = 0; fila < 3; fila++) {
            char letra1 = detectarLetraEnPar(tablero[fila][0], tablero[fila][1]);
            char letra2 = detectarLetraEnPar(tablero[fila][2], tablero[fila][3]);
            char letra3 = detectarLetraEnPar(tablero[fila][4], tablero[fila][5]);
            
            if (letra1 == letraBuscada && letra2 == letraBuscada && letra3 == letraBuscada) {
                posicionesLineaGanadora.add(new int[]{fila, 0});
                posicionesLineaGanadora.add(new int[]{fila, 1});
                posicionesLineaGanadora.add(new int[]{fila, 2});
                posicionesLineaGanadora.add(new int[]{fila, 3});
                posicionesLineaGanadora.add(new int[]{fila, 4});
                posicionesLineaGanadora.add(new int[]{fila, 5});
                return true;
            }
        }
        return false;
    }
    
    private boolean verificarAlineacionVerticalPares(char letraBuscada) {
        for (int colInicio = 0; colInicio <= 4; colInicio += 2) {
            char letra1 = detectarLetraEnPar(tablero[0][colInicio], tablero[0][colInicio + 1]);
            char letra2 = detectarLetraEnPar(tablero[1][colInicio], tablero[1][colInicio + 1]);
            char letra3 = detectarLetraEnPar(tablero[2][colInicio], tablero[2][colInicio + 1]);
            
            if (letra1 == letraBuscada && letra2 == letraBuscada && letra3 == letraBuscada) {
                posicionesLineaGanadora.add(new int[]{0, colInicio});
                posicionesLineaGanadora.add(new int[]{0, colInicio + 1});
                posicionesLineaGanadora.add(new int[]{1, colInicio});
                posicionesLineaGanadora.add(new int[]{1, colInicio + 1});
                posicionesLineaGanadora.add(new int[]{2, colInicio});
                posicionesLineaGanadora.add(new int[]{2, colInicio + 1});
                return true;
            }
        }
        return false;
    }
    
    private boolean verificarAlineacionDiagonalPares(char letraBuscada) {
        //Descendentes
        for (int colInicio = 0; colInicio <= 2; colInicio++) {
            char letra1 = detectarLetraEnPar(tablero[0][colInicio], tablero[0][colInicio + 1]);
            char letra2 = detectarLetraEnPar(tablero[1][colInicio + 1], tablero[1][colInicio + 2]);
            char letra3 = detectarLetraEnPar(tablero[2][colInicio + 2], tablero[2][colInicio + 3]);
            
            if (letra1 == letraBuscada && letra2 == letraBuscada && letra3 == letraBuscada) {
                posicionesLineaGanadora.add(new int[]{0, colInicio});
                posicionesLineaGanadora.add(new int[]{0, colInicio + 1});
                posicionesLineaGanadora.add(new int[]{1, colInicio + 1});
                posicionesLineaGanadora.add(new int[]{1, colInicio + 2});
                posicionesLineaGanadora.add(new int[]{2, colInicio + 2});
                posicionesLineaGanadora.add(new int[]{2, colInicio + 3});
                return true;
            }
        }
        
        //Ascendentes
        for (int colInicio = 0; colInicio <= 2; colInicio++) {
            char letra1 = detectarLetraEnPar(tablero[2][colInicio], tablero[2][colInicio + 1]);
            char letra2 = detectarLetraEnPar(tablero[1][colInicio + 1], tablero[1][colInicio + 2]);
            char letra3 = detectarLetraEnPar(tablero[0][colInicio + 2], tablero[0][colInicio + 3]);
            
            if (letra1 == letraBuscada && letra2 == letraBuscada && letra3 == letraBuscada) {
                posicionesLineaGanadora.add(new int[]{2, colInicio});
                posicionesLineaGanadora.add(new int[]{2, colInicio + 1});
                posicionesLineaGanadora.add(new int[]{1, colInicio + 1});
                posicionesLineaGanadora.add(new int[]{1, colInicio + 2});
                posicionesLineaGanadora.add(new int[]{0, colInicio + 2});
                posicionesLineaGanadora.add(new int[]{0, colInicio + 3});
                return true;
            }
        }
        
        return false;
    }
    
    public void mostrarTableroConLineaGanadora(char letraGanadora) {
        if (mostrarTitulos) {
            System.out.println(" 1  2  3  4  5  6");
        }
        
        System.out.println("+--+--+--+--+--+--+");
        
        for (int i = 0; i < 3; i++) {
            for (int lineaFila = 0; lineaFila < 3; lineaFila++) {
                if (mostrarTitulos && lineaFila == 1) {
                    char fila = (char)('A' + i);
                    System.out.print(fila + "|");
                } else {
                    System.out.print(" |");
                }
                
                for (int j = 0; j < 6; j++) {
                    boolean esPosicionGanadora = esPosicionEnLineaGanadora(i, j);
                    
                    String lineaPieza;
                    if (esPosicionGanadora) {
                        lineaPieza = obtenerLineaLetraGanadora(letraGanadora, lineaFila);
                    } else {
                        char pieza = tablero[i][j];
                        lineaPieza = obtenerLineaPieza(pieza, lineaFila);
                    }
                    System.out.print(lineaPieza + "|");
                }
                System.out.println();
            }
            System.out.println("+--+--+--+--+--+--+");
        }
    }
    
    private boolean esPosicionEnLineaGanadora(int fila, int columna) {
        for (int[] posicion : posicionesLineaGanadora) {
            if (posicion[0] == fila && posicion[1] == columna) {
                return true;
            }
        }
        return false;
    }
    
    private String obtenerLineaLetraGanadora(char letraGanadora, int linea) {
        if (letraGanadora == 'X') {
            return "X ";
        } else if (letraGanadora == 'O') {
            return "O ";
        }
        return "  ";
    }
    
    // Metodos de partida
    
    public char iniciarJuego() {
        System.out.println("=== MEDIO TATETI ===");
        
        if (jugador1 == null || jugador2 == null) {
            configurarJugadores();
        }
        
        while (!juegoTerminado) {
            mostrarTablero();
            mostrarTurno();
            
            boolean turnoCompletado = ejecutarJugada();
            
            if (juegoTerminado) {
                Jugador ganadorPorRendicion;
                if (jugadorActual == jugador1) {
                    ganadorPorRendicion = jugador2;
                } else {
                    ganadorPorRendicion = jugador1;
                }
                return getSimbolo(ganadorPorRendicion);
            }
            
            char ganador = verificarGanadorYMostrar();
            if (ganador != ' ') {
                return ganador;
            }
            
            if (turnoCompletado && !juegoTerminado) {
                cambiarTurno();
            }
        }
        
        mostrarResultado();
        return ' ';
    }
    
    public char continuarPartida(String secuenciaMovimientos) {
        System.out.println("=== CONTINUACIÓN DE PARTIDA ===");
        System.out.println("Aplicando secuencia: " + secuenciaMovimientos);
        
        aplicarSecuenciaMovimientos(secuenciaMovimientos);
        
        System.out.println("\nSecuencia aplicada. Continuando partida...");
        
        while (!juegoTerminado) {
            mostrarTablero();
            mostrarTurno();
            
            boolean turnoCompletado = ejecutarJugada();
            
            if (juegoTerminado) {
                Jugador ganadorPorRendicion;
                if (jugadorActual == jugador1) {
                    ganadorPorRendicion = jugador2;
                } else {
                    ganadorPorRendicion = jugador1;
                }
                return getSimbolo(ganadorPorRendicion);
            }
            
            char ganador = verificarGanadorYMostrar();
            if (ganador != ' ') {
                return ganador;
            }
            
            if (turnoCompletado && !juegoTerminado) {
                cambiarTurno();
            }
        }
        
        mostrarResultado();
        return ' ';
    }
    
    private void configurarJugadores() {
        System.out.print("Nombre Jugador 1 (O): ");
        String nombre1 = scanner.nextLine();
        System.out.print("Nombre Jugador 2 (X): ");
        String nombre2 = scanner.nextLine();
        
        jugador1 = new Jugador(nombre1, 'O');
        jugador2 = new Jugador(nombre2, 'X');
        jugadorActual = jugador1;
    }
    
    private void mostrarTurno() {
        System.out.println("Turno: " + jugadorActual.getNombre() + " (" + getSimbolo(jugadorActual) + ")");
    }
    
    private boolean ejecutarJugada() {
        System.out.print("Ingrese jugada: ");
        String input = scanner.nextLine().toUpperCase();
        return procesarJugada(input, true);
    }
    
    private boolean procesarJugada(String input, boolean mostrarMensajes) {
        if (mostrarMensajes && procesarComandosEspeciales(input)) {
            return false;
        }
        
        if (input.length() != 3) {
            if (mostrarMensajes) {
                System.out.println("Formato invalido. Use 3 caracteres (ej: A1C, B2I)");
            }
            return false;
        }
        
        char letraFila = input.charAt(0);
        char numeroColumna = input.charAt(1);
        char orientacion = input.charAt(2);
        
        int fila;
        if (letraFila == 'A') {
            fila = 0;
        } else if (letraFila == 'B') {
            fila = 1;
        } else if (letraFila == 'C') {
            fila = 2;
        } else {
            fila = -1; // Valor inválido
        }
        
        int columna;
        if (numeroColumna == '1') {
            columna = 0;
        } else if (numeroColumna == '2') {
            columna = 1;
        } else if (numeroColumna == '3') {
            columna = 2;
        } else if (numeroColumna == '4') {
            columna = 3;
        } else if (numeroColumna == '5') {
            columna = 4;
        } else if (numeroColumna == '6') {
            columna = 5;
        } else {
            columna = -1; // Valor inválido
        }
        
        if (fila < 0 || fila > 2 || columna < 0 || columna > 5) {
            if (mostrarMensajes) {
                System.out.println("Coordenadas invalidas. Use A-C para filas y 1-6 para columnas");
            }
            return false;
        }
        
        if (orientacion == 'I') {
            if (obtenerPieza(fila, columna) == ' ') {
                if (mostrarMensajes) {
                    System.out.println("No hay pieza para invertir");
                }
                return false;
            }
            if (invertirPieza(fila, columna)) {
                if (mostrarMensajes) {
                    System.out.println("Pieza invertida en " + input.substring(0, 2));
                }
                return true;
            } else {
                if (mostrarMensajes) {
                    System.out.println("No se puede invertir esa pieza");
                }
                return false;
            }
        }
        
        if (orientacion == 'C' || orientacion == 'D') {
            if (colocarPieza(fila, columna, orientacion, getSimbolo(jugadorActual))) {
                if (mostrarMensajes) {
                    System.out.println("Pieza colocada en " + input);
                }
                return true;
            } else {
                if (mostrarMensajes) {
                    System.out.println("Posicion ocupada");
                }
                return false;
            }
        }
        
        if (mostrarMensajes) {
            System.out.println("Orientacion invalida. Use C, D o I");
        }
        return false;
    }
    
    private char verificarGanadorYMostrar() {
        if (verificarGanador()) {
            juegoTerminado = true;
            char letraGanadora = obtenerGanador();
            
            System.out.println("\n=== TABLERO FINAL ===");
            mostrarTableroConLineaGanadora(letraGanadora);
            
            String nombreGanador = "";
            if (letraGanadora == 'O') {
                if (getSimbolo(jugador1) == 'O') {
                    nombreGanador = jugador1.getNombre();
                } else {
                    nombreGanador = jugador2.getNombre();
                }
                System.out.println("¡¡¡ " + nombreGanador + " GANA !!!");
                System.out.println("Formó 3 letras 'O' alineadas");
            } else if (letraGanadora == 'X') {
                if (getSimbolo(jugador1) == 'X') {
                    nombreGanador = jugador1.getNombre();
                } else {
                    nombreGanador = jugador2.getNombre();
                }
                System.out.println("¡¡¡ " + nombreGanador + " GANA !!!");
                System.out.println("Formó 3 letras 'X' alineadas");
            }
            System.out.println("=====================");
            
            return letraGanadora;
        }
        return ' ';
    }
    
    private void cambiarTurno() {
        if (!juegoTerminado) {
            if (jugadorActual == jugador1) {
                jugadorActual = jugador2;
            } else {
                jugadorActual = jugador1;
            }
        }
    }
    
    private boolean procesarComandosEspeciales(String input) {
        if (input.equals("X")) {
            System.out.println(jugadorActual.getNombre() + " se rinde. Pierde la partida.");
            
            Jugador ganador;
            if (jugadorActual == jugador1) {
                ganador = jugador2;
            } else {
                ganador = jugador1;
            }
            System.out.println("¡¡¡ " + ganador.getNombre() + " GANA por rendición !!!");
            
            juegoTerminado = true;
            return true;
        }
        
        if (input.equals("B")) {
            mostrarTitulos();
            System.out.println("Titulos mostrados");
            return true;
        }
        
        if (input.equals("N")) {
            ocultarTitulos();
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
        
        return false;
    }
    
    private String buscarJugadaGanadora() {
        char letraJugadorActual;
        if (getSimbolo(jugadorActual) == 'X') {
            letraJugadorActual = 'X';
        } else {
            letraJugadorActual = 'O';
        }
        
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                // Probar colocar pieza C
                if (obtenerPieza(fila, columna) == ' ') {
                    if (colocarPieza(fila, columna, 'C', getSimbolo(jugadorActual))) {
                        if (verificarGanador()) {
                            char ganador = obtenerGanador();
                            if (ganador == letraJugadorActual) {
                                establecerPieza(fila, columna, ' ');
                                char filaChar = (char)('A' + fila);
                                char columnaChar = (char)('1' + columna);
                                return "" + filaChar + columnaChar + "C";
                            }
                        }
                        establecerPieza(fila, columna, ' ');
                    }
                }
                
                // Probar colocar pieza D
                if (obtenerPieza(fila, columna) == ' ') {
                    if (colocarPieza(fila, columna, 'D', getSimbolo(jugadorActual))) {
                        if (verificarGanador()) {
                            char ganador = obtenerGanador();
                            if (ganador == letraJugadorActual) {
                                establecerPieza(fila, columna, ' ');
                                char filaChar = (char)('A' + fila);
                                char columnaChar = (char)('1' + columna);
                                return "" + filaChar + columnaChar + "D";
                            }
                        }
                        establecerPieza(fila, columna, ' ');
                    }
                }
                
                // Probar invertir pieza existente
                if (obtenerPieza(fila, columna) != ' ') {
                    if (invertirPieza(fila, columna)) {
                        if (verificarGanador()) {
                            char ganador = obtenerGanador();
                            if (ganador == letraJugadorActual) {
                                invertirPieza(fila, columna);
                                char filaChar = (char)('A' + fila);
                                char columnaChar = (char)('1' + columna);
                                return "" + filaChar + columnaChar + "I";
                            }
                        }
                        invertirPieza(fila, columna);
                    }
                }
            }
        }
        return null;
    }
    
    private boolean aplicarSecuenciaMovimientos(String secuencia) {
        int i = 0;
        
        while (i < secuencia.length()) {
            while (i < secuencia.length() && secuencia.charAt(i) == ' ') {
                i++;
            }
            
            if (i >= secuencia.length()) {
                break;
            }
            
            String movimiento = "";
            for (int j = 0; j < 3; j++) {
                movimiento += secuencia.charAt(i + j);
            }
            
            procesarJugada(movimiento, false);
            System.out.println("Después de " + movimiento + " (" + jugadorActual.getNombre() + "):");
            mostrarTablero();
            
            if (verificarGanador()) {
                System.out.println("¡Partida terminada durante la secuencia!");
                return true;
            }
            
            cambiarTurno();
            
            // Avanzar 3 posiciones para agarrar la siguiente jugada
            i += 3;
        }
        
        return true;
    }
    
    private void mostrarResultado() {
        System.out.println("¡Gracias por jugar Medio Tateti!");
    }
}
