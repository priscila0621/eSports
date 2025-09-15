package models;

import java.util.List;

public class Juego {
    private int id;
    private String nombre;
    private String descripcion;

    private Categoria categoria;   // cada juego pertenece a una categoría

    // opcional: lista de partidas que usan este juego
    private List<Partida> partidas;

}

