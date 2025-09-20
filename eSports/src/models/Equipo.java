package models;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Equipo: contiene Jugadores (agregación), participa en Torneos (M:N).
 */
public class Equipo {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
    private static final Set<String> NOMBRES_USADOS = new HashSet<>(); // registro de nombres únicos

    private final int id;
    private String nombre;

    private final List<Jugador> jugadores = new ArrayList<>();
    private final List<Torneo> torneos = new ArrayList<>();

    public Equipo() {
        this.id = NEXT_ID.getAndIncrement();
    }

    public Equipo(String nombre) {
        this();
        setNombre(nombre);
    }

    // ----- Jugadores -----
    /**
     * API pública para agregar un jugador.
     * Delegamos la sincronización completa a Jugador.setEquipo(...)
     * para garantizar que ambos extremos queden consistentes sin recursión.
     */
    public void addJugador(Jugador j) {
        if (j == null) return;
        if (j.getEquipo() == this) {
            // por seguridad: si no está en la lista, lo añadimos
            if (!jugadores.contains(j)) jugadores.add(j);
            return;
        }
        // Esto llamará a j.setEquipo(this), que a su vez usará addJugadorInternal(...)
        j.setEquipo(this);
    }

    /**
     * API pública para remover un jugador.
     * Elimina de la lista y actualiza el equipo del jugador (si corresponde).
     */
    public void removeJugador(Jugador j) {
        if (j == null) return;
        if (jugadores.remove(j)) {
            if (j.getEquipo() == this) {
                j.setEquipo(null);
            }
        }
    }

    /**
     * Métodos package-private (internos) usados por Jugador.setEquipo(...) para
     * modificar la lista sin provocar llamadas recursivas.
     */
    void addJugadorInternal(Jugador j) {
        if (j == null) return;
        if (!jugadores.contains(j)) jugadores.add(j);
    }

    void removeJugadorInternal(Jugador j) {
        if (j == null) return;
        jugadores.remove(j);
    }

    // ----- Torneos (M:N) -----
    public void addTorneo(Torneo t) {
        if (t == null) return;
        if (!torneos.contains(t)) {
            torneos.add(t);
            if (!t.getEquipos().contains(this)) {
                t.getEquipos().add(this);
            }
        }
    }

    public void removeTorneo(Torneo t) {
        if (t == null) return;
        if (torneos.remove(t)) {
            t.getEquipos().remove(this);
        }
    }

    // ----- Getters / Setters -----
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacío.");
        }

        String nombreLimpio = nombre.trim();

        // Si ya tiene nombre, liberamos el viejo antes de cambiar
        if (this.nombre != null) {
            NOMBRES_USADOS.remove(this.nombre);
        }

        // Validar duplicado (insensible a mayúsc/minúsc por consistencia)
        for (String n : NOMBRES_USADOS) {
            if (n.equalsIgnoreCase(nombreLimpio)) {
                // restauramos el valor antiguo en caso de fallo
                if (this.nombre != null) NOMBRES_USADOS.add(this.nombre);
                throw new IllegalArgumentException("Ya existe un equipo con el nombre: " + nombreLimpio);
            }
        }

        NOMBRES_USADOS.add(nombreLimpio);
        this.nombre = nombreLimpio;
    }

    public List<Jugador> getJugadores() {
        return Collections.unmodifiableList(jugadores);
    }

    public void setJugadores(List<Jugador> jugadores) {
        // reemplazamos por la colección nueva, usando addJugador para mantener consistencia
        for (Jugador j : new ArrayList<>(this.jugadores)) {
            removeJugador(j);
        }
        if (jugadores != null) {
            for (Jugador j : jugadores) {
                addJugador(j);
            }
        }
    }

    public List<Torneo> getTorneos() {
        return Collections.unmodifiableList(torneos);
    }

    public void setTorneos(List<Torneo> torneos) {
        this.torneos.clear();
        if (torneos != null) {
            for (Torneo t : torneos) {
                addTorneo(t);
            }
        }
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Equipo#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipo)) return false;
        Equipo equipo = (Equipo) o;
        return id == equipo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
