// Entregable 1 - Programación 2
// Autor: Javier Fernandez
// Nro de estudiante: 172271
package entragable_1_programacion_2;

import java.util.*;


public class Sistema {
    
    public Sistema() {
    }
    
    public void iniciar() {
        GestorJugadores gestorJugadores = new GestorJugadores();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== BIENVENIDO A MEDIO TATETI ===");
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion(scanner);
            
            switch (opcion) {
                case 1 -> registrarJugador(gestorJugadores, scanner);
                case 2 -> iniciarPartidaComun(gestorJugadores, scanner);
                case 3 -> continuarPartida(gestorJugadores, scanner);
                case 4 -> mostrarRankingEInvictos(gestorJugadores);
                case 0 -> {
                    continuar = false;
                    System.out.println("¡Gracias por jugar Medio Tateti!");
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
            
            if (continuar) {
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
            }
        }
        scanner.close();
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
    
    private int leerOpcion(Scanner scanner) {
        String input = scanner.nextLine();
        int opcion;
        
        if (input.length() == 1) {
            char caracter = input.charAt(0);
            
            opcion = switch (caracter) {
                case '0' -> 0;
                case '1' -> 1;
                case '2' -> 2;
                case '3' -> 3;
                case '4' -> 4;
                default -> -1;
            };
        } else {
            opcion = -1; 
        }
        
        return opcion;
    }
    
private void registrarJugador(GestorJugadores gestorJugadores, Scanner scanner) {
        System.out.println("\n=== REGISTRAR JUGADOR ===");
        
        System.out.println("Ingrese el nombre del jugador: ");
        String nombre = scanner.nextLine();
        
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        
        if (gestorJugadores.existeJugador(nombre)) {
            System.out.println("Ya existe un jugador con ese nombre.");
            return;
        }
        
        System.out.println("Ingrese la edad del jugador: ");
        String edadInput = scanner.nextLine();
        
        // Convertir a número sin validación (0 si no es número válido)
        int edad = Integer.parseInt(edadInput);
        
        if (gestorJugadores.registrarJugador(nombre, edad)) {
            System.out.println("Jugador registrado exitosamente: " + nombre + " (Edad: " + edad + ")");
        } else {
            System.out.println("Error al registrar el jugador.");
        }
    }
    
    
private void iniciarPartidaComun(GestorJugadores gestorJugadores, Scanner scanner) {
        System.out.println("\n=== COMIENZO DE PARTIDA COMÚN ===");
        
        if (gestorJugadores.getCantidadJugadores() < 2) {
            System.out.println("Se necesitan al menos 2 jugadores registrados para iniciar una partida.");
            return;
        }
        
        // Mostrar lista de jugadores
        gestorJugadores.mostrarListaJugadores();
        
        // Seleccionar jugador 1 (Blanco - O)
        Jugador jugador1 = seleccionarJugador("Seleccione el jugador 1 (Blanco - O): ", gestorJugadores, scanner);
        if (jugador1 == null) return;
        
        // Seleccionar jugador 2 (Negro - X)
        Jugador jugador2;
        do {
            jugador2 = seleccionarJugador("Seleccione el jugador 2 (Negro - X): ", gestorJugadores, scanner);
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
        actualizarEstadisticas(gestorJugadores, jugador1, jugador2, resultado);
    }
    
private void continuarPartida(GestorJugadores gestorJugadores, Scanner scanner) {
        System.out.println("\n=== CONTINUACIÓN DE PARTIDA ===");
        
        if (gestorJugadores.getCantidadJugadores() < 2) {
            System.out.println("Se necesitan al menos 2 jugadores registrados para continuar una partida.");
            return;
        }
        
     
        gestorJugadores.mostrarListaJugadores();
        
        
        Jugador jugador1 = seleccionarJugador("Seleccione el jugador 1 (Blanco - O): ", gestorJugadores, scanner);
        if (jugador1 == null) return;
        
        Jugador jugador2;
        do {
            jugador2 = seleccionarJugador("Seleccione el jugador 2 (Negro - X): ", gestorJugadores, scanner);
            if (jugador2 == null) return;
            
            if (jugador1.getNombre().equals(jugador2.getNombre())) {
                System.out.println("Debe seleccionar jugadores diferentes.");
                jugador2 = null;
            }
        } while (jugador2 == null);
        

        System.out.print("Ingrese la secuencia de movimientos (ej: A1C B3D C2C): ");
        String secuencia = scanner.nextLine();
        
        if (secuencia.isEmpty()) {
            System.out.println("Debe ingresar una secuencia de movimientos.");
            return;
        }
        
        
        // Iniciar partida con secuencia

        PartidaSimple juego = new PartidaSimple(jugador1, jugador2);
        char resultado = juego.continuarPartida(secuencia);
        
        // Actualizar estadísticas
        actualizarEstadisticas(gestorJugadores, jugador1, jugador2, resultado);
    }
    
private Jugador seleccionarJugador(String mensaje, GestorJugadores gestorJugadores, Scanner scanner) {
        System.out.print(mensaje);
        String input = scanner.nextLine();
        
        int numero = Integer.parseInt(input);
        ArrayList<Jugador> jugadoresOrdenados = gestorJugadores.obtenerJugadoresOrdenados();
        
        if (numero >= 1 && numero <= jugadoresOrdenados.size()) {
            return jugadoresOrdenados.get(numero - 1);
        } else {
            return null;
        }
    }
    
private void actualizarEstadisticas(GestorJugadores gestorJugadores, Jugador jugador1, Jugador jugador2, char resultado) {
        if (resultado == 'O') {
            // Gana jugador1 (Blanco)
            jugador1.incrementarPartidasGanadas();
            jugador2.incrementarPartidasPerdidas();
        } else if (resultado == 'X') {
            // Gana jugador2 (Negro)
            jugador2.incrementarPartidasGanadas();
            jugador1.incrementarPartidasPerdidas();
        }
    }
    
private void mostrarRankingEInvictos(GestorJugadores gestorJugadores) {
        System.out.println("\n=== RANKING Y JUGADORES INVICTOS ===");
        
        
        gestorJugadores.mostrarRanking();
        System.out.println();
        gestorJugadores.mostrarJugadoresInvictos();
    }
}
