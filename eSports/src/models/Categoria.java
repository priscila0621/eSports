package models;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Categoria (FPS, MOBA, etc.) agrupa Juegos.
 */
public class Categoria {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
    private final int id;
    private String nombre;
    private final List<Juego> juegos = new ArrayList<>();

    public Categoria() {
        this.id = NEXT_ID.getAndIncrement();
    }

    public Categoria(String nombre) {
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
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            throw new IllegalArgumentException("El nombre de la categoría no debe contener números ni símbolos.");
        }
        this.nombre = nombre;
    }

    public List<Juego> getJuegos() {
        return Collections.unmodifiableList(juegos);
    }

    public void addJuego(Juego j) {
        if (j == null) return;
        if (!juegos.contains(j)) {
            juegos.add(j);
            if (j.getCategoria() != this) {
                j.setCategoria(this);
            }
        }
    }

    public void removeJuego(Juego j) {
        if (j == null) return;
        if (juegos.remove(j)) {
            j.setCategoria(null);
        }
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Categoria#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria that = (Categoria) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

