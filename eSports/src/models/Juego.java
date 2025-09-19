package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Juego: pertenece a una Categoria y puede usarse en muchas Partidas.
 */
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    public void setCategoria(Categoria categoria) {
        if (this.categoria == categoria) return;

        if (this.categoria != null) {
            this.categoria.getJuegos().remove(this);
        }

        this.categoria = categoria;

        if (categoria != null && !categoria.getJuegos().contains(this)) {
            categoria.getJuegos().add(this);
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

