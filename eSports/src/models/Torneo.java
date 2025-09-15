package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Torneo: contiene Partidas (composición) y referencia a Equipos (M:N).
 */
public class Torneo {
    private static int NEXT_ID = 1;
    private int id;
    private String nombre;
    private String estado;
    private Date fechaInicio;
    private Date fechaFin;

    // Composición: Torneo "posee" Partidas
    private List<Partida> partidas = new ArrayList<>();

    // Asociación M:N con Equipo
    private List<Equipo> equipos = new ArrayList<>();

    public Torneo() { this.id = NEXT_ID++; }
    public Torneo(String nombre) { this(); this.nombre = nombre; }

    // ----- Partidas (composición) -----
    public void addPartida(Partida p) {
        if (p == null) return;
        if (!partidas.contains(p)) {
            partidas.add(p);
            p.setTorneo(this); // establece la referencia inversa
        }
    }

    public void removePartida(Partida p) {
        if (p == null) return;
        if (partidas.remove(p)) {
            p.disassociate(); // elimina referencias en Juego/Arbitro y pone torneo = null
        }
    }

    /** Elimina todas las partidas del torneo (composición). */
    public void clearPartidas() {
        // iteración sobre copia para evitar ConcurrentModification
        for (Partida p : new ArrayList<>(partidas)) {
            removePartida(p);
        }
    }

    // ----- Equipos (M:N) -----
    public void addEquipo(Equipo e) {
        if (e == null) return;
        if (!equipos.contains(e)) {
            equipos.add(e);
            if (!e.getTorneos().contains(this)) {
                e.getTorneos().add(this);
            }
        }
    }

    public void removeEquipo(Equipo e) {
        if (e == null) return;
        if (equipos.remove(e)) {
            e.getTorneos().remove(this);
        }
    }

    // ----- getters / setters -----
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public List<Partida> getPartidas() { return partidas; }
    public void setPartidas(List<Partida> partidas) { this.partidas = partidas; }

    public List<Equipo> getEquipos() { return equipos; }
    public void setEquipos(List<Equipo> equipos) { this.equipos = equipos; }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Torneo#" + id;
    }
}

