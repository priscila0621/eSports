package models;

import java.util.List;

public class Categoria {
    private int id;
    private String nombre;

    // una categoría puede agrupar varios juegos
    private List<Juego> juegos;

}
