package models;

import java.util.Date;
import java.util.List;

public class Torneo {
    private int id;
    private String nombre;
    private String estado;
    private Date fechaInicio;
    private Date fechaFin;

    // Composición: un Torneo tiene varias Partidas
    private List<Partida> partidas;

    // Asociación muchos a muchos con Equipo
    private List<Equipo> equipos;

}
