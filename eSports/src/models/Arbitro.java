package models;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Arbitro: puede supervisar muchas Partidas.
 */
public class Arbitro {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
    private final int id;
    private String nombre;
    private int experiencia;
    private String licencia;
    private final List<Partida> partidas = new ArrayList<>();

    public Arbitro() {
        this.id = NEXT_ID.getAndIncrement();
    }

    public Arbitro(String nombre) {
        this();
        setNombre(nombre);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del árbitro no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        if (experiencia < 0) {
            throw new IllegalArgumentException("La experiencia no puede ser negativa.");
        }
        this.experiencia = experiencia;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        if (licencia != null && licencia.trim().isEmpty()) {
            throw new IllegalArgumentException("La licencia no puede estar vacía si se proporciona.");
        }
        this.licencia = licencia;
    }

    public List<Partida> getPartidas() {
        return Collections.unmodifiableList(partidas);
    }

    public void addPartida(Partida p) {
        if (p == null) return;
        if (!partidas.contains(p)) {
            partidas.add(p);
            p.setArbitro(this);
        }
    }

    public void removePartida(Partida p) {
        if (p == null) return;
        if (partidas.remove(p)) {
            p.setArbitro(null);
        }
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Arbitro#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arbitro)) return false;
        Arbitro arbitro = (Arbitro) o;
        return id == arbitro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
