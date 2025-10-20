// Entregable 1 - Programación 2
// Autor: Javier Fernandez
// Nro de estudiante: 172271
package entragable_1_programacion_2;

import java.util.*;


public class Sistema {
    private GestorJugadores gestorJugadores;
    private Scanner scanner;
    
    public Sistema() {
        this.gestorJugadores = new GestorJugadores();
        this.scanner = new Scanner(System.in);
    }
    
  
    
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        sistema.iniciar();
    }
    
    public void iniciar() {
        System.out.println("=== BIENVENIDO A MEDIO TATETI ===");
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    registrarJugador();
                    break;
                case 2:
                    iniciarPartidaComun();
                    break;
                case 3:
                    continuarPartida();
                    break;
                case 4:
                    mostrarRankingEInvictos();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("¡Gracias por jugar Medio Tateti!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
            
            if (continuar) {
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
            }
        }
    }
    
    private void mostrarMenu() {
        System.out.println("=== MENU PRINCIPAL ===");
        System.out.println("1. Registrar un jugador");
        System.out.println("2. Comienzo de partida común");
        System.out.println("3. Continuación de partida");
        System.out.println("4. Mostrar ranking e invictos");
        System.out.println("0. Salir");
        System.out.println("Seleccione una opción: ");
    }
    
    private int leerOpcion() {
        String input = scanner.nextLine();
        
        // Validar que sea un solo dígito
        if (input.length() == 1 && input.charAt(0) >= '0' && input.charAt(0) <= '9') {
            return input.charAt(0) - '0';
        }
        
        return -1; // Opción inválida
    }
    
    // Validar que un string sea un número positivo
    private int validarNumeroPositivo(String input) {
        if (input.isEmpty()) {
            return -1;
        }
        
        // Verificar que todos los caracteres sean dígitos
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) < '0' || input.charAt(i) > '9') {
                return -1;
            }
        }
        
        // Convertir a número manualmente
        int numero = 0;
        for (int i = 0; i < input.length(); i++) {
            numero = numero * 10 + (input.charAt(i) - '0');
        }
        
        if (numero > 0) {
            return numero;
        } else {
            return -1;
        }
    }
    
    private void registrarJugador() {
        System.out.println("\n=== REGISTRAR JUGADOR ===");
        
        System.out.println("Ingrese el nombre del jugador: ");
        String nombre = scanner.nextLine().trim();
        
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        
        if (gestorJugadores.existeJugador(nombre)) {
            System.out.println("Ya existe un jugador con ese nombre.");
            return;
        }
        
        System.out.println("Ingrese la edad del jugador: ");
        String edadInput = scanner.nextLine().trim();
        
        // Validar que sea un número positivo
        int edad = validarNumeroPositivo(edadInput);
        if (edad == -1) {
            System.out.println("La edad debe ser un número válido y positivo.");
            return;
        }
        
        if (gestorJugadores.registrarJugador(nombre, edad)) {
            System.out.println("Jugador registrado exitosamente: " + nombre + " (Edad: " + edad + ")");
        } else {
            System.out.println("Error al registrar el jugador.");
        }
    }
    
    private void iniciarPartidaComun() {
        System.out.println("\n=== COMIENZO DE PARTIDA COMÚN ===");
        
        if (gestorJugadores.getCantidadJugadores() < 2) {
            System.out.println("Se necesitan al menos 2 jugadores registrados para iniciar una partida.");
            return;
        }
        
        // Mostrar lista de jugadores
        gestorJugadores.mostrarListaJugadores();
        
        // Seleccionar jugador 1 (Blanco - O)
        Jugador jugador1 = seleccionarJugador("Seleccione el jugador 1 (Blanco - O): ");
        if (jugador1 == null) return;
        
        // Seleccionar jugador 2 (Negro - X)
        Jugador jugador2;
        do {
            jugador2 = seleccionarJugador("Seleccione el jugador 2 (Negro - X): ");
            if (jugador2 == null) return;
            
            if (jugador1.getNombre().equals(jugador2.getNombre())) {
                System.out.println("Debe seleccionar jugadores diferentes.");
                jugador2 = null;
            }
        } while (jugador2 == null);
        
   
        
        // Iniciar partida
        PartidaSimple juego = new PartidaSimple(jugador1, jugador2);
        char resultado = juego.iniciarJuego();
        
        // Actualizar estadísticas
        actualizarEstadisticas(jugador1, jugador2, resultado);
    }
    
    private void continuarPartida() {
        System.out.println("\n=== CONTINUACIÓN DE PARTIDA ===");
        
        if (gestorJugadores.getCantidadJugadores() < 2) {
            System.out.println("Se necesitan al menos 2 jugadores registrados para continuar una partida.");
            return;
        }
        
        // Mostrar lista de jugadores
        gestorJugadores.mostrarListaJugadores();
        
        // Seleccionar jugadores
        Jugador jugador1 = seleccionarJugador("Seleccione el jugador 1 (Blanco - O): ");
        if (jugador1 == null) return;
        
        Jugador jugador2;
        do {
            jugador2 = seleccionarJugador("Seleccione el jugador 2 (Negro - X): ");
            if (jugador2 == null) return;
            
            if (jugador1.getNombre().equals(jugador2.getNombre())) {
                System.out.println("Debe seleccionar jugadores diferentes.");
                jugador2 = null;
            }
        } while (jugador2 == null);
        
        // Solicitar secuencia de movimientos
        System.out.print("Ingrese la secuencia de movimientos (ej: A1C B3D C2C): ");
        String secuencia = scanner.nextLine().trim();
        
        if (secuencia.isEmpty()) {
            System.out.println("Debe ingresar una secuencia de movimientos.");
            return;
        }
        
        
        // Iniciar partida con secuencia
        PartidaSimple juego = new PartidaSimple(jugador1, jugador2);
        char resultado = juego.continuarPartida(secuencia);
        
        // Actualizar estadísticas
        actualizarEstadisticas(jugador1, jugador2, resultado);
    }
    
    private Jugador seleccionarJugador(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        
        // Validar que sea un número positivo
        int numero = validarNumeroPositivo(input);
        if (numero == -1) {
            System.out.println("Debe ingresar un número válido.");
            return null;
        }
        
        ArrayList<Jugador> jugadoresOrdenados = gestorJugadores.obtenerJugadoresOrdenados();
        
        if (numero >= 1 && numero <= jugadoresOrdenados.size()) {
            return jugadoresOrdenados.get(numero - 1);
        } else {
            System.out.println("Número de jugador inválido.");
            return null;
        }
    }
    
    private void actualizarEstadisticas(Jugador jugador1, Jugador jugador2, char resultado) {
        if (resultado == 'O') {
            // Gana jugador1 (Blanco)
            jugador1.incrementarPartidasGanadas();
            jugador2.incrementarPartidasPerdidas();
        } else if (resultado == 'X') {
            // Gana jugador2 (Negro)
            jugador2.incrementarPartidasGanadas();
            jugador1.incrementarPartidasPerdidas();
        }
        //Si empatan no se actualizan las estadisticas
    }
    
    private void mostrarRankingEInvictos() {
        System.out.println("\n=== RANKING Y JUGADORES INVICTOS ===");
        
        // Mostrar ranking
        gestorJugadores.mostrarRanking();
        
        System.out.println();
        
        // Mostrar jugadores invictos
        gestorJugadores.mostrarJugadoresInvictos();
    }
}
