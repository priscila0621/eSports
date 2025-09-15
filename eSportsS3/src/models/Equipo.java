package models;

import java.util.List;

public class Equipo {
    private int id;
    private String nombre;

    // Agregación: jugadores (no se eliminan al borrar equipo)
    private List<Jugador> jugadores;

    // Asociación muchos a muchos con Torneo
    private List<Torneo> torneos;

}

