// Entregable 1 - Programación 2
// Autor: Javier Fernandez
// Nro de estudiante: 172271

package entragable_1_programacion_2;

import java.util.*;

public class PartidaSimple {
    // Atributos del tablero
    private final char[][] tablero;
    private boolean mostrarTitulos;
    private final List<int[]> posicionesLineaGanadora;
    
    // Atributos de la partida
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private final char simboloJugador1;
    private final char simboloJugador2;
    private final Scanner scanner;
    private boolean juegoTerminado;
    
    
    public PartidaSimple(Jugador jugador1, Jugador jugador2) {
        // Inicializar tablero (duplicado del constructor vacío)
        this.tablero = new char[3][6];
        this.mostrarTitulos = true;
        this.posicionesLineaGanadora = new ArrayList<>();
        inicializarTablero();
        
        // Inicializar partida (duplicado del constructor vacío)
        this.scanner = new Scanner(System.in);
        this.juegoTerminado = false;
        
        // Inicializar jugadores específicos
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorActual = jugador1;
        this.simboloJugador1 = 'O';  
        this.simboloJugador2 = 'X';   
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
    
    public boolean invertirPieza(int fila, int columna, char jugador) {
        if (fila < 0 || fila > 2 || columna < 0 || columna > 5) {
            return false;
        }
        
        char piezaActual = tablero[fila][columna];
        if (piezaActual == ' ') {
            return false;
        }
        
        // Verificar que la pieza pertenece al jugador actual
        boolean esPiezaDelJugador = false;
        if (jugador == 'X') {
            // Jugador X tiene piezas en mayúsculas (C, D)
            esPiezaDelJugador = (piezaActual == 'C' || piezaActual == 'D');
        } else if (jugador == 'O') {
            // Jugador O tiene piezas en minúsculas (c, d)
            esPiezaDelJugador = (piezaActual == 'c' || piezaActual == 'd');
        }
        
        if (!esPiezaDelJugador) {
            return false; 
        }
        
        char nuevaPieza;
        switch (piezaActual) {
            case 'C' -> nuevaPieza = 'D';
            case 'D' -> nuevaPieza = 'C';
            case 'c' -> nuevaPieza = 'd';
            case 'd' -> nuevaPieza = 'c';
            default -> {
                return false;
            }
        }
        
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
                    char fila = convertirFilaALetra(i);
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
            case 'C' -> {
                if (linea == 0) return " ●";
                if (linea == 1) return "● ";
                if (linea == 2) return " ●";
            }
            case 'c' -> {
                if (linea == 0) return " ○";
                if (linea == 1) return "○ ";
                if (linea == 2) return " ○";
            }
            case 'D' -> {
                if (linea == 0) return "● ";
                if (linea == 1) return " ●";
                if (linea == 2) return "● ";
            }
            case 'd' -> {
                if (linea == 0) return "○ ";
                if (linea == 1) return " ○";
                if (linea == 2) return "○ ";
            }
            default -> {
                return "  ";
            }
        }
        return "  ";
    }
    
    // Metodos que verifican si alguien gano
    
    public boolean verificarGanador() {
        if (verificarTresLetrasAlineadas('O')) return true;
        return verificarTresLetrasAlineadas('X');
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
        boolean estanAlineadas = false;
        if (verificarAlineacionHorizontalPares(letraBuscada)){
            estanAlineadas= true;
        }
        if (verificarAlineacionVerticalPares(letraBuscada)){
            estanAlineadas= true;
        }
        if( verificarAlineacionDiagonalPares(letraBuscada)){
            estanAlineadas= true;
        }
        
        return estanAlineadas;
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
                    char fila = convertirFilaALetra(i);
                    System.out.print(fila + "|");
                } else {
                    System.out.print(" |");
                }
                
                for (int j = 0; j < 6; j++) {
                    boolean esPosicionGanadora = false;
                    int k = 0;
                    while (k < 6 && !esPosicionGanadora) {
                        int[] posicion = posicionesLineaGanadora.get(k);
                        if (posicion[0] == i && posicion[1] == j) {
                            esPosicionGanadora = true;
                        }
                        k++;
                    }
                    
                    String lineaPieza;
                    if (esPosicionGanadora) {
                        lineaPieza = obtenerLineaLetraGanadora(letraGanadora);
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
    
    private String obtenerLineaLetraGanadora(char letraGanadora) {
        String letra = "X ";
        if (letraGanadora == 'O') {
            letra = "O ";
        }
        return letra;
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
        
        System.out.println("Secuencia aplicada. Continuando partida...");
        
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
        
        int fila = convertirLetraAFila(letraFila);
        int columna = convertirNumeroAColumna(numeroColumna);
        
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
            if (invertirPieza(fila, columna, getSimbolo(jugadorActual))) {
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
                                char filaChar = convertirFilaALetra(fila);
                                char columnaChar = convertirColumnaANumero(columna);
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
                                char filaChar = convertirFilaALetra(fila);
                                char columnaChar = convertirColumnaANumero(columna);
                                return "" + filaChar + columnaChar + "D";
                            }
                        }
                        establecerPieza(fila, columna, ' ');
                    }
                }
                
                // Probar invertir pieza existente
                if (obtenerPieza(fila, columna) != ' ') {
                    if (invertirPieza(fila, columna, getSimbolo(jugadorActual))) {
                        if (verificarGanador()) {
                            char ganador = obtenerGanador();
                            if (ganador == letraJugadorActual) {
                                invertirPieza(fila, columna, getSimbolo(jugadorActual));
                                char filaChar = convertirFilaALetra(fila);
                                char columnaChar = convertirColumnaANumero(columna);
                                return "" + filaChar + columnaChar + "I";
                            }
                        }
                        invertirPieza(fila, columna, getSimbolo(jugadorActual));
                    }
                }
            }
        }
        return null;
    }
    
    private char convertirFilaALetra(int fila) {
        return switch (fila) {
            case 0 -> 'A';
            case 1 -> 'B';
            case 2 -> 'C';
            default -> 'A';
        };
    }
    
    private char convertirColumnaANumero(int columna) {
        return switch (columna) {
            case 0 -> '1';
            case 1 -> '2';
            case 2 -> '3';
            case 3 -> '4';
            case 4 -> '5';
            case 5 -> '6';
            default -> '1';
        };
    }
    
    private int convertirLetraAFila(char letra) {
        return switch (letra) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            default -> -1;
        };
    }
    
    private int convertirNumeroAColumna(char numero) {
        return switch (numero) {
            case '1' -> 0;
            case '2' -> 1;
            case '3' -> 2;
            case '4' -> 3;
            case '5' -> 4;
            case '6' -> 5;
            default -> -1;
        };
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
