package models;

import java.util.List;

public class Arbitro {
    private int id;
    private String nombre;
    private int experiencia;
    private String licencia;

    // un árbitro puede supervisar varias partidas
    private List<Partida> partidas;

}

