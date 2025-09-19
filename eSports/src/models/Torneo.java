package models;

import java.util.*;

/**
 * Torneo: contiene Partidas (composición) y referencia a Equipos (M:N).
 */
public class Torneo {
    private static int NEXT_ID = 1;
    private final int id;
    private String nombre;
    private String estado;
    private Date fechaInicio;
    private Date fechaFin;

    // Composición: Torneo "posee" Partidas
    private final List<Partida> partidas = new ArrayList<>();

    // Asociación M:N con Equipo
    private final List<Equipo> equipos = new ArrayList<>();

    public Torneo() {
        this.id = NEXT_ID++;
    }

    public Torneo(String nombre) {
        this();
        setNombre(nombre);
    }

    // ----- Partidas (composición) -----
    public void addPartida(Partida p) {
        if (p == null) return;
        if (!partidas.contains(p)) {
            partidas.add(p);
            p.setTorneo(this);
        }
    }

    public void removePartida(Partida p) {
        if (p == null) return;
        if (partidas.remove(p)) {
            p.disassociate();
        }
    }

    public void clearPartidas() {
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
                e.addTorneo(this);
            }
        }
    }

    public void removeEquipo(Equipo e) {
        if (e == null) return;
        if (equipos.remove(e)) {
            e.removeTorneo(this);
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
            throw new IllegalArgumentException("El nombre del torneo no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado != null && estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío si se proporciona.");
        }
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula.");
        }
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null && fechaInicio != null && fechaFin.before(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        this.fechaFin = fechaFin;
    }

    public List<Partida> getPartidas() {
        return Collections.unmodifiableList(partidas);
    }

    public List<Equipo> getEquipos() {
        return Collections.unmodifiableList(equipos);
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Torneo#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Torneo)) return false;
        Torneo torneo = (Torneo) o;
        return id == torneo.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
