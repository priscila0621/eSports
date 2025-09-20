package run;

import models.*;
import java.util.*;

/**
 * Main de consola para interactuar con el sistema.
 * Usa las clases en package models.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    // "repositorios" en memoria
    private static final List<Torneo> torneos = new ArrayList<>();
    private static final List<Equipo> equipos = new ArrayList<>();
    private static final List<Jugador> jugadores = new ArrayList<>();
    private static final List<Categoria> categorias = new ArrayList<>();
    private static final List<Juego> juegos = new ArrayList<>();
    private static final List<Arbitro> arbitros = new ArrayList<>();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione opción: ");
            switch (opcion) {
                case 1 -> crearCategoria();
                case 2 -> crearJuego();
                case 3 -> crearArbitro();
                case 4 -> crearEquipo();
                case 5 -> crearJugador();
                case 6 -> crearTorneo();
                case 7 -> crearPartida();
                case 8 -> listarTorneosDetalle();
                case 9 -> eliminarTorneo();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== GESTIÓN DE TORNEOS ===");
        System.out.println("1. Crear Categoría");
        System.out.println("2. Crear Juego");
        System.out.println("3. Crear Árbitro");
        System.out.println("4. Crear Equipo");
        System.out.println("5. Crear Jugador");
        System.out.println("6. Crear Torneo");
        System.out.println("7. Crear Partida (en Torneo)");
        System.out.println("8. Listar Torneos (detalle)");
        System.out.println("9. Eliminar Torneo (borra sus partidas)");
        System.out.println("0. Salir");
    }

    // ----- creación de modelos -----
    private static void crearCategoria() {
        try {
            String nombre = leerTexto("Nombre de la categoría: ");
            Categoria c = new Categoria(nombre);
            categorias.add(c);
            System.out.println("Categoría creada: " + c);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void crearJuego() {
        if (categorias.isEmpty()) {
            System.out.println("Primero crea al menos una categoría.");
            return;
        }
        try {
            String nombre = leerTexto("Nombre del juego: ");
            listar(categorias);
            int idx = leerEntero("Seleccione la categoría (número): ") - 1;
            if (!valido(idx, categorias)) {
                System.out.println("Índice inválido.");
                return;
            }
            Juego j = new Juego(nombre, categorias.get(idx));
            juegos.add(j);
            System.out.println("Juego creado: " + j);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void crearArbitro() {
        try{
            String nombre = leerTexto("Nombre del árbitro: ");
            Arbitro a = new Arbitro(nombre);
            arbitros.add(a);
            System.out.println("Árbitro creado: " + a);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void crearEquipo() {
        String nombre = leerTexto("Nombre del equipo: ");

        // Validar duplicado en la lista local antes de crear
        for (Equipo eq : equipos) {
            if (eq.getNombre().equalsIgnoreCase(nombre.trim())) {
                System.out.println("Error: Ya existe un equipo con el nombre '" + nombre + "'.");
                return;
            }
        }

        try {
            Equipo e = new Equipo(nombre);
            equipos.add(e);
            System.out.println("Equipo creado: " + e);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void crearJugador() {
        try{
            String nombre = leerTexto("Nombre del jugador: ");
            String alias = leerTexto("Alias: ");
            int ranking = leerEntero("Ranking (entero): ");
            Jugador j = new Jugador(nombre, alias, ranking);
            jugadores.add(j);
            System.out.println("Jugador creado: " + j);

            // opción de asignarlo a un equipo
            if (!equipos.isEmpty() && confirmar("¿Asignar a un equipo ahora? (s/n): ")) {
                listar(equipos);
                int idx = leerEntero("Seleccione equipo (número): ") - 1;
                if (valido(idx, equipos)) {
                    equipos.get(idx).addJugador(j);
                    System.out.println("Jugador asignado al equipo " + equipos.get(idx).getNombre());
                } else {
                    System.out.println("Índice de equipo inválido.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void crearTorneo() {
        try {
            String nombre = leerTexto("Nombre del torneo: ");
            Torneo t = new Torneo(nombre);
            torneos.add(t);
            System.out.println("Torneo creado: " + t);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void crearPartida() {
        if (torneos.isEmpty() || equipos.size() < 2 || juegos.isEmpty() || arbitros.isEmpty()) {
            System.out.println("Requisitos: al menos 1 torneo, 2 equipos, 1 juego y 1 árbitro.");
            return;
        }

        listar(torneos);
        int idxT = leerEntero("Seleccione torneo (número): ") - 1;
        if (!valido(idxT, torneos)) {
            System.out.println("Torneo inválido.");
            return; }
        Torneo torneo = torneos.get(idxT);

        listar(equipos);
        int idxE1 = leerEntero("Seleccione Equipo 1 (número): ") - 1;
        int idxE2 = leerEntero("Seleccione Equipo 2 (número): ") - 1;
        if (!valido(idxE1, equipos) || !valido(idxE2, equipos) || idxE1 == idxE2) {
            System.out.println("Equipos inválidos o iguales.");
            return;
        }
        Equipo e1 = equipos.get(idxE1);
        Equipo e2 = equipos.get(idxE2);

        // Validación de jugadores en ambos equipos
        StringBuilder errores = new StringBuilder();
        if (e1.getJugadores().isEmpty()) {
            errores.append(" - El equipo ").append(e1.getNombre()).append(" no tiene jugadores.\n");
        }
        if (e2.getJugadores().isEmpty()) {
            errores.append(" - El equipo ").append(e2.getNombre()).append(" no tiene jugadores.\n");
        }
        if (errores.length() > 0) {
            System.out.println("No se puede crear la partida:\n" + errores);
            return;
        }

        listar(juegos);
        int idxJ = leerEntero("Seleccione Juego (número): ") - 1;
        if (!valido(idxJ, juegos)) {
            System.out.println("Juego inválido.");
            return; }
        Juego juego = juegos.get(idxJ);

        listar(arbitros);
        int idxA = leerEntero("Seleccione Árbitro (número): ") - 1;
        if (!valido(idxA, arbitros)) {
            System.out.println("Árbitro inválido.");
            return; }
        Arbitro arbitro = arbitros.get(idxA);

        // ahora sí creamos la partida con datos válidos
        Partida p = new Partida(e1, e2, juego, arbitro);

        torneo.addPartida(p);    // composición: torneo posee la partida
        torneo.addEquipo(e1);
        torneo.addEquipo(e2);
        arbitro.addPartida(p);
        juego.addPartida(p);

        System.out.println("Partida creada: " + p);
    }

    // ----- listar / eliminar -----
    private static void listarTorneosDetalle() {
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos.");
            return; }
        for (Torneo t : torneos) {
            System.out.println("\nTorneo: " + t.getNombre());
            System.out.println(" Equipos:");
            if (t.getEquipos().isEmpty()) {
                System.out.println("   (sin equipos)");
            } else {
                for (Equipo e : t.getEquipos()) {
                    System.out.println("   - " + e);
                    if (!e.getJugadores().isEmpty()) {
                        for (Jugador j : e.getJugadores()) {
                            System.out.println("       * " + j + " (ranking: " + j.getRanking() + ")");
                        }
                    } else {
                        System.out.println("       (sin jugadores)");
                    }
                }
            }
            System.out.println(" Partidas:");
            if (t.getPartidas().isEmpty()) {
                System.out.println("   (sin partidas)");
            } else {
                for (Partida p : t.getPartidas()) {
                    System.out.println("   - " + p);
                }
            }
        }
    }

    private static void eliminarTorneo() {
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos para eliminar.");
            return; }
        listar(torneos);
        int idx = leerEntero("Seleccione torneo a eliminar (número): ") - 1;
        if (!valido(idx, torneos)) {
            System.out.println("Índice inválido.");
            return; }
        Torneo t = torneos.get(idx);

        // borrar partidas (esto limpia referencias en Juego/Arbitro)
        t.clearPartidas();

        // quitar la asociación M:N con equipos
        for (Equipo e : new ArrayList<>(t.getEquipos())) {
            t.removeEquipo(e);
        }

        torneos.remove(t);
        System.out.println("Torneo eliminado y sus partidas borradas.");
    }

    // ----- utilitarios de consola -----
    private static String leerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = sc.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
    }

    private static <T> void listar(List<T> lista) {
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
    }

    private static boolean valido(int idx, List<?> lista) {
        return idx >= 0 && idx < lista.size();
    }

    private static boolean confirmar(String prompt) {
        String r = leerTexto(prompt);
        return r.equalsIgnoreCase("s") || r.equalsIgnoreCase("y");
    }
}
