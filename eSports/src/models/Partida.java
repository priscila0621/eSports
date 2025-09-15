package models;

import java.util.Date;

/**
 * Partida: juega entre dos Equipos, asociada a un Juego, supervisada por un Arbitro,
 * y pertenece a un Torneo (composición).
 */
public class Partida {
    private static int NEXT_ID = 1;
    private int id;
    private String resultado;
    private Date fecha;

    private Juego juego;
    private Equipo equipo1;
    private Equipo equipo2;
    private Arbitro arbitro;
    private Torneo torneo; // referencia a contenedor

    public Partida() { this.id = NEXT_ID++; this.fecha = new Date(); }

    public Partida(Equipo e1, Equipo e2, Juego juego, Arbitro arbitro) {
        this();
        this.equipo1 = e1;
        this.equipo2 = e2;
        this.juego = juego;
        this.arbitro = arbitro;
    }

    // ----- Asociaciones -----
    public void setTorneo(Torneo torneo) {
        // evita recursión infinita: sólo setea y asegura consistencia de listas
        this.torneo = torneo;
        if (torneo != null && !torneo.getPartidas().contains(this)) {
            torneo.getPartidas().add(this);
        }
    }

    public Torneo getTorneo() { return torneo; }

    public Juego getJuego() { return juego; }
    public void setJuego(Juego juego) {
        // maneja lista en Juego
        if (this.juego == juego) return;
        if (this.juego != null) this.juego.getPartidas().remove(this);
        this.juego = juego;
        if (juego != null && !juego.getPartidas().contains(this)) juego.getPartidas().add(this);
    }

    public Arbitro getArbitro() { return arbitro; }
    public void setArbitro(Arbitro arbitro) {
        if (this.arbitro == arbitro) return;
        if (this.arbitro != null) this.arbitro.getPartidas().remove(this);
        this.arbitro = arbitro;
        if (arbitro != null && !arbitro.getPartidas().contains(this)) arbitro.getPartidas().add(this);
    }

    public Equipo getEquipo1() { return equipo1; }
    public void setEquipo1(Equipo equipo1) { this.equipo1 = equipo1; }

    public Equipo getEquipo2() { return equipo2; }
    public void setEquipo2(Equipo equipo2) { this.equipo2 = equipo2; }

    public int getId() { return id; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    /**
     * Disassociate: quita la Partida de referencias en Juego y Árbitro,
     * y limpia la referencia a Torneo. Se usa al eliminar un Torneo (composición).
     */
    public void disassociate() {
        if (arbitro != null) {
            arbitro.getPartidas().remove(this);
            arbitro = null;
        }
        if (juego != null) {
            juego.getPartidas().remove(this);
            juego = null;
        }
        // quitar referencia a torneo (la lista del torneo ya fue actualizada)
        torneo = null;
    }

    @Override
    public String toString() {
        String e1 = (equipo1 != null) ? equipo1.getNombre() : "Equipo?";
        String e2 = (equipo2 != null) ? equipo2.getNombre() : "Equipo?";
        String g = (juego != null) ? juego.getNombre() : "Juego?";
        String a = (arbitro != null) ? arbitro.getNombre() : "Sin árbitro";
        return String.format("%s vs %s | %s | Árbitro: %s | %s", e1, e2, g, a, resultado == null ? "(sin resultado)" : resultado);
    }
}
