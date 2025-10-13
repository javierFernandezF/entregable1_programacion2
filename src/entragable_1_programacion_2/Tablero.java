package entragable_1_programacion_2;


public class Tablero {
    private char[][] tablero;
    private boolean mostrarTitulos;
    
    public Tablero() {
        tablero = new char[3][6];
        mostrarTitulos = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                tablero[i][j] = ' ';
            }
        }
    }
    
    public boolean colocarPieza(int fila, int columna, char orientacion, char jugador) {
        if (fila >= 0 && fila < 3 && columna >= 0 && columna < 6 && tablero[fila][columna] == ' ') {
            // Convertir orientación a símbolo visual según el jugador
            char simbolo = convertirASimboloVisual(orientacion, jugador);
            tablero[fila][columna] = simbolo;
            return true;
        }
        return false;
    }
    
    private char convertirASimboloVisual(char orientacion, char jugador) {
        // Almacenar tanto la orientación como el jugador en un solo char
        // C del jugador X = 'C', C del jugador O = 'c'
        // D del jugador X = 'D', D del jugador O = 'd'
        if (jugador == 'X') { // Jugador Negro
            return orientacion; // C o D mayúscula
        } else { // Jugador O (Blanco)
            return Character.toLowerCase(orientacion); // c o d minúscula
        }
    }
    
    public boolean invertirPieza(int fila, int columna) {
        if (fila >= 0 && fila < 3 && columna >= 0 && columna < 6 && tablero[fila][columna] != ' ') {
            char pieza = tablero[fila][columna];
            // Invertir entre C y D manteniendo el jugador
            if (pieza == 'C') {
                tablero[fila][columna] = 'D';
                return true;
            } else if (pieza == 'D') {
                tablero[fila][columna] = 'C';
                return true;
            } else if (pieza == 'c') {
                tablero[fila][columna] = 'd';
                return true;
            } else if (pieza == 'd') {
                tablero[fila][columna] = 'c';
                return true;
            }
        }
        return false;
    }
    
    public boolean moverPieza(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
        if (filaOrigen >= 0 && filaOrigen < 3 && columnaOrigen >= 0 && columnaOrigen < 6 &&
            filaDestino >= 0 && filaDestino < 3 && columnaDestino >= 0 && columnaDestino < 6 &&
            tablero[filaOrigen][columnaOrigen] != ' ' && tablero[filaDestino][columnaDestino] == ' ') {
            
            char pieza = tablero[filaOrigen][columnaOrigen];
            tablero[filaOrigen][columnaOrigen] = ' ';
            tablero[filaDestino][columnaDestino] = pieza;
            return true;
        }
        return false;
    }
    
    public char obtenerPieza(int fila, int columna) {
        if (fila >= 0 && fila < 3 && columna >= 0 && columna < 6) {
            return tablero[fila][columna];
        }
        return ' ';
    }
    
    public void establecerPieza(int fila, int columna, char pieza) {
        if (fila >= 0 && fila < 3 && columna >= 0 && columna < 6) {
            tablero[fila][columna] = pieza;
        }
    }
    
    public void mostrarTablero() {
        // Mostrar números de columnas si está habilitado
        if (mostrarTitulos) {
            System.out.println(" 1  2  3  4  5  6");
        }
        
        // Línea superior
        System.out.println("+--+--+--+--+--+--+");
        
        for (int i = 0; i < 3; i++) {
            // Cada fila del tablero se muestra en 3 líneas
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
            
            // Línea separadora
            System.out.println("+--+--+--+--+--+--+");
        }
    }
    
    public void mostrarTitulos() {
        mostrarTitulos = true;
    }
    
    public void ocultarTitulos() {
        mostrarTitulos = false;
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
            default: // Espacio vacío
                return "  ";
        }
        return "  ";
    }
    
    public boolean verificarGanador() {
        // Verificar si hay 3 letras O alineadas
        if (verificarTresLetrasAlineadas('O')) {
            return true;
        }
        // Verificar si hay 3 letras X alineadas  
        if (verificarTresLetrasAlineadas('X')) {
            return true;
        }
        return false;
    }
    
    public char obtenerGanador() {
        if (verificarTresLetrasAlineadas('O')) {
            return 'O';
        }
        if (verificarTresLetrasAlineadas('X')) {
            return 'X';
        }
        return ' ';
    }
    
    // Lista para almacenar las posiciones de la línea ganadora
    private java.util.List<int[]> posicionesLineaGanadora = new java.util.ArrayList<>();
    
    public java.util.List<int[]> obtenerPosicionesLineaGanadora() {
        return posicionesLineaGanadora;
    }
    
    
    // Detecta qué letra forma un par de casilleros adyacentes
    //pos1 Primera posición del par
    // pos2 Segunda posición del par
    //'O' si es CD, 'X' si es DC, ' ' si no forma letra
     
    private char detectarLetraEnPar(char pos1, char pos2) {
        // Normalizar a mayúsculas para simplificar comparación
        char p1 = Character.toUpperCase(pos1);
        char p2 = Character.toUpperCase(pos2);
        
        if (p1 == 'C' && p2 == 'D') {
            return 'O'; // CD = O
        } else if (p1 == 'D' && p2 == 'C') {
            return 'X'; // DC = X
        }
        return ' '; // No forma letra útil
    }
    
    /**
     * Verifica si hay 3 letras del tipo especificado alineadas
     */
    private boolean verificarTresLetrasAlineadas(char letraBuscada) {
        // Limpiar posiciones anteriores
        posicionesLineaGanadora.clear();
        
        // Verificar alineaciones horizontales de pares
        if (verificarAlineacionHorizontalPares(letraBuscada)) return true;
        
        // Verificar alineaciones verticales de pares
        if (verificarAlineacionVerticalPares(letraBuscada)) return true;
        
        // Verificar alineaciones diagonales de pares
        if (verificarAlineacionDiagonalPares(letraBuscada)) return true;
        
        return false;
    }
    
    /**
     * Verifica alineaciones horizontales de 3 letras formadas por pares horizontales
     */
    private boolean verificarAlineacionHorizontalPares(char letraBuscada) {
        for (int fila = 0; fila < 3; fila++) {
            // Verificar 3 pares horizontales consecutivos en la misma fila
            // Pares: (0,1)(2,3)(4,5) - posiciones 0,2,4
            for (int colInicio = 0; colInicio <= 0; colInicio++) {
                char letra1 = detectarLetraEnPar(tablero[fila][colInicio], tablero[fila][colInicio + 1]);
                char letra2 = detectarLetraEnPar(tablero[fila][colInicio + 2], tablero[fila][colInicio + 3]);
                char letra3 = detectarLetraEnPar(tablero[fila][colInicio + 4], tablero[fila][colInicio + 5]);
                
                if (letra1 == letraBuscada && letra2 == letraBuscada && letra3 == letraBuscada) {
                    // Guardar las posiciones de la línea ganadora
                    posicionesLineaGanadora.add(new int[]{fila, colInicio});
                    posicionesLineaGanadora.add(new int[]{fila, colInicio + 1});
                    posicionesLineaGanadora.add(new int[]{fila, colInicio + 2});
                    posicionesLineaGanadora.add(new int[]{fila, colInicio + 3});
                    posicionesLineaGanadora.add(new int[]{fila, colInicio + 4});
                    posicionesLineaGanadora.add(new int[]{fila, colInicio + 5});
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Verifica alineaciones verticales de 3 letras formadas por pares verticales
     * En un tablero 3x6, solo podemos tener 2 pares verticales por columna: (fila0,fila1) y (fila1,fila2)
     * Para tener 3 letras verticales alineadas, necesitamos verificar patrones más complejos
     */
    private boolean verificarAlineacionVerticalPares(char letraBuscada) {
        // Con solo 3 filas, no podemos tener 3 pares verticales consecutivos
        // que formen 3 letras alineadas verticalmente.
        // Esta verificación se mantiene para completitud, pero siempre retornará false
        // a menos que se implemente una lógica diferente para patrones específicos
        
        return false;
    }
    
    /**
     * Verifica alineaciones diagonales de 3 letras formadas por pares que se desplazan diagonalmente
     */
    private boolean verificarAlineacionDiagonalPares(char letraBuscada) {
        // Verificar diagonales descendentes (\) - pares que se desplazan hacia abajo-derecha
        // Ejemplo: (A1,A2), (B2,B3), (C3,C4) forman una diagonal
        
        // Diagonales descendentes (\)
        for (int colInicio = 0; colInicio <= 2; colInicio++) { // Columnas 0,1,2 como inicio
            // Verificar diagonal que empieza en (0, colInicio)
            char letra1 = detectarLetraEnPar(tablero[0][colInicio], tablero[0][colInicio + 1]);
            char letra2 = detectarLetraEnPar(tablero[1][colInicio + 1], tablero[1][colInicio + 2]);
            char letra3 = detectarLetraEnPar(tablero[2][colInicio + 2], tablero[2][colInicio + 3]);
            
            if (letra1 == letraBuscada && letra2 == letraBuscada && letra3 == letraBuscada) {
                // Guardar posiciones de la diagonal descendente
                posicionesLineaGanadora.add(new int[]{0, colInicio});
                posicionesLineaGanadora.add(new int[]{0, colInicio + 1});
                posicionesLineaGanadora.add(new int[]{1, colInicio + 1});
                posicionesLineaGanadora.add(new int[]{1, colInicio + 2});
                posicionesLineaGanadora.add(new int[]{2, colInicio + 2});
                posicionesLineaGanadora.add(new int[]{2, colInicio + 3});
                return true;
            }
        }
        
        // Diagonales ascendentes (/) - pares que se desplazan hacia arriba-derecha
        // Ejemplo: (C1,C2), (B2,B3), (A3,A4) forman una diagonal
        
        for (int colInicio = 0; colInicio <= 2; colInicio++) { // Columnas 0,1,2 como inicio
            // Verificar diagonal que empieza en (2, colInicio)
            char letra1 = detectarLetraEnPar(tablero[2][colInicio], tablero[2][colInicio + 1]);
            char letra2 = detectarLetraEnPar(tablero[1][colInicio + 1], tablero[1][colInicio + 2]);
            char letra3 = detectarLetraEnPar(tablero[0][colInicio + 2], tablero[0][colInicio + 3]);
            
            if (letra1 == letraBuscada && letra2 == letraBuscada && letra3 == letraBuscada) {
                // Guardar posiciones de la diagonal ascendente
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
    
    /**
     * Muestra el tablero resaltando la línea ganadora
     */
    public void mostrarTableroConLineaGanadora(char letraGanadora) {
        // Mostrar números de columnas si está habilitado
        if (mostrarTitulos) {
            System.out.println(" 1  2  3  4  5  6");
        }
        
        // Línea superior
        System.out.println("+--+--+--+--+--+--+");
        
        for (int i = 0; i < 3; i++) {
            // Cada fila del tablero se muestra en 3 líneas
            for (int lineaFila = 0; lineaFila < 3; lineaFila++) {
                if (mostrarTitulos && lineaFila == 1) {
                    char fila = (char)('A' + i);
                    System.out.print(fila + "|");
                } else {
                    System.out.print(" |");
                }
                
                for (int j = 0; j < 6; j++) {
                    // Verificar si esta posición es parte de la línea ganadora
                    boolean esPosicionGanadora = esPosicionEnLineaGanadora(i, j);
                    
                    String lineaPieza;
                    if (esPosicionGanadora) {
                        // Mostrar la letra ganadora en lugar de la pieza original
                        lineaPieza = obtenerLineaLetraGanadora(letraGanadora, lineaFila);
                    } else {
                        // Mostrar la pieza normal (C, D, c, d o espacio vacío)
                        char pieza = tablero[i][j];
                        lineaPieza = obtenerLineaPieza(pieza, lineaFila);
                    }
                    System.out.print(lineaPieza + "|");
                }
                System.out.println();
            }
            
            // Línea separadora
            System.out.println("+--+--+--+--+--+--+");
        }
    }
    
    /**
     * Verifica si una posición está en la línea ganadora
     */
    private boolean esPosicionEnLineaGanadora(int fila, int columna) {
        for (int[] posicion : posicionesLineaGanadora) {
            if (posicion[0] == fila && posicion[1] == columna) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Obtiene la representación visual de la letra ganadora
     */
    private String obtenerLineaLetraGanadora(char letraGanadora, int linea) {
        if (letraGanadora == 'X') {
            // Mostrar X en todas las líneas
            return "X ";
        } else if (letraGanadora == 'O') {
            // Mostrar O en todas las líneas
            return "O ";
        }
        return "  ";
    }
}
