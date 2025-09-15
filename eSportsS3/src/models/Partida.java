package models;

import java.util.Date;

public class Partida {
    private int id;
    private String resultado;
    private Date fecha;

    // Referencias a otras clases
    private Juego juego;           // cada Partida tiene 1 Juego
    private Equipo equipo1;        // primer equipo
    private Equipo equipo2;        // segundo equipo
    private Arbitro arbitro;       // 1 árbitro supervisa la partida
    private Torneo torneo;         // partida pertenece a un torneo

}

