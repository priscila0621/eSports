package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Categoria (FPS, MOBA, etc.) agrupa Juegos.
 */
public class Categoria {
    private static int NEXT_ID = 1;
    private int id;
    private String nombre;
    private List<Juego> juegos = new ArrayList<>();

    public Categoria() { this.id = NEXT_ID++; }
    public Categoria(String nombre) { this(); this.nombre = nombre; }

    public void addJuego(Juego j) {
        if (j == null) return;
        if (!juegos.contains(j)) {
            juegos.add(j);
            if (j.getCategoria() != this) j.setCategoria(this);
        }
    }

    public void removeJuego(Juego j) {
        if (j == null) return;
        if (juegos.remove(j)) j.setCategoria(null);
    }

    // getters / setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Juego> getJuegos() { return juegos; }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Categoria#" + id;
    }
}

