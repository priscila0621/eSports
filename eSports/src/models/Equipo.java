package models;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Equipo: contiene Jugadores (agregación), participa en Torneos (M:N).
 */
public class Equipo {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
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
    public void addJugador(Jugador j) {
        if (j == null) return;
        j.setEquipo(this); // delegamos consistencia a Jugador
    }

    public void removeJugador(Jugador j) {
        if (j == null) return;
        if (jugadores.remove(j)) {
            j.setEquipo(null);
        }
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
        this.nombre = nombre;
    }

    public List<Jugador> getJugadores() {
        return Collections.unmodifiableList(jugadores);
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores.clear();
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
