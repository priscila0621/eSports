package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Juego: pertenece a una Categoria y puede usarse en muchas Partidas.
 */
public class Juego {
    private static int NEXT_ID = 1;
    private int id;
    private String nombre;
    private String descripcion;

    private Categoria categoria;
    private List<Partida> partidas = new ArrayList<>();

    public Juego() { this.id = NEXT_ID++; }
    public Juego(String nombre, Categoria categoria) {
        this();
        this.nombre = nombre;
        setCategoria(categoria);
    }

    public void setCategoria(Categoria categoria) {
        if (this.categoria == categoria) return;
        if (this.categoria != null) this.categoria.getJuegos().remove(this);
        this.categoria = categoria;
        if (categoria != null && !categoria.getJuegos().contains(this)) categoria.getJuegos().add(this);
    }

    public void addPartida(Partida p) {
        if (p == null) return;
        if (!partidas.contains(p)) {
            partidas.add(p);
            p.setJuego(this);
        }
    }

    public void removePartida(Partida p) {
        if (p == null) return;
        if (partidas.remove(p)) p.setJuego(null);
    }

    // getters / setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Categoria getCategoria() { return categoria; }
    public List<Partida> getPartidas() { return partidas; }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Juego#" + id;
    }
}
