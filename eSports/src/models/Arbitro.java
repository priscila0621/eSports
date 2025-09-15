package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Arbitro: puede supervisar muchas Partidas.
 */
public class Arbitro {
    private static int NEXT_ID = 1;
    private int id;
    private String nombre;
    private int experiencia;
    private String licencia;

    private List<Partida> partidas = new ArrayList<>();

    public Arbitro() { this.id = NEXT_ID++; }
    public Arbitro(String nombre) { this(); this.nombre = nombre; }

    public void addPartida(Partida p) {
        if (p == null) return;
        if (!partidas.contains(p)) {
            partidas.add(p);
            p.setArbitro(this);
        }
    }

    public void removePartida(Partida p) {
        if (p == null) return;
        if (partidas.remove(p)) p.setArbitro(null);
    }

    // getters / setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getExperiencia() { return experiencia; }
    public void setExperiencia(int experiencia) { this.experiencia = experiencia; }
    public String getLicencia() { return licencia; }
    public void setLicencia(String licencia) { this.licencia = licencia; }
    public List<Partida> getPartidas() { return partidas; }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Arbitro#" + id;
    }
}
