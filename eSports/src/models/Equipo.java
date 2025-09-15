package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Equipo: contiene Jugadores (agregación), participa en Torneos (M:N).
 */
public class Equipo {
    private static int NEXT_ID = 1;
    private int id;
    private String nombre;

    private List<Jugador> jugadores = new ArrayList<>();
    private List<Torneo> torneos = new ArrayList<>();

    public Equipo() { this.id = NEXT_ID++; }
    public Equipo(String nombre) { this(); this.nombre = nombre; }

    // ----- Jugadores -----
    public void addJugador(Jugador j) {
        if (j == null) return;
        // delegamos la consistencia a setEquipo en Jugador
        j.setEquipo(this);
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
            if (!t.getEquipos().contains(this)) t.getEquipos().add(this);
        }
    }

    public void removeTorneo(Torneo t) {
        if (t == null) return;
        if (torneos.remove(t)) {
            t.getEquipos().remove(this);
        }
    }

    // ----- getters / setters -----
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

    public List<Torneo> getTorneos() { return torneos; }
    public void setTorneos(List<Torneo> torneos) { this.torneos = torneos; }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Equipo#" + id;
    }
}
