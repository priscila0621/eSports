package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Juego: pertenece a una Categoria y puede usarse en muchas Partidas.
 */
public class Juego {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
    private final int id;
    private String nombre;
    private String descripcion;
    private Categoria categoria;
    private final List<Partida> partidas = new ArrayList<>();

    public Juego() {
        this.id = NEXT_ID.getAndIncrement();
    }

    public Juego(String nombre, Categoria categoria) {
        this();
        setNombre(nombre);
        setCategoria(categoria);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del juego no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && descripcion.length() > 500) {
            throw new IllegalArgumentException("La descripción no puede exceder los 500 caracteres.");
        }
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Asigna la categoría al juego y actualiza la relación bidireccional.
     */
    public void setCategoria(Categoria categoria) {
        // Si es la misma categoría, no hacemos nada
        if (this.categoria == categoria) return;

        // Quitarse de la categoría anterior
        if (this.categoria != null) {
            this.categoria.removeJuego(this);
        }

        // Asignar la nueva categoría
        this.categoria = categoria;

        // Añadirse a la nueva categoría
        if (categoria != null) {
            categoria.addJuego(this);
        }
    }

    public List<Partida> getPartidas() {
        return Collections.unmodifiableList(partidas);
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
        if (partidas.remove(p)) {
            p.setJuego(null);
        }
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Juego#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Juego)) return false;
        Juego juego = (Juego) o;
        return id == juego.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
