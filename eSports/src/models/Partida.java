package models;

import java.util.Date;

/**
 * Partida: juega entre dos Equipos, asociada a un Juego, supervisada por un Arbitro,
 * y pertenece a un Torneo (composición).
 */
public class Partida {
    private static int NEXT_ID = 1;
    private final int id;
    private String resultado;
    private Date fecha;

    private Juego juego;
    private Equipo equipo1;
    private Equipo equipo2;
    private Arbitro arbitro;
    private Torneo torneo;

    public Partida() {
        this.id = NEXT_ID++;
        this.fecha = new Date();
    }

    public Partida(Equipo e1, Equipo e2, Juego juego, Arbitro arbitro) {
        this();
        // Valida ambos equipos antes de asignar
        if (validarEquipos(e1, e2)) {
            this.equipo1 = e1;
            this.equipo2 = e2;
        }
        setJuego(juego);
        setArbitro(arbitro);
    }

    // ----- Asociaciones -----
    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
        if (torneo != null && !torneo.getPartidas().contains(this)) {
            torneo.addPartida(this);
        }
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        if (this.juego == juego) return;
        if (this.juego != null) {
            this.juego.removePartida(this);
        }
        this.juego = juego;
        if (juego != null && !juego.getPartidas().contains(this)) {
            juego.addPartida(this);
        }
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        if (this.arbitro == arbitro) return;
        if (this.arbitro != null) {
            this.arbitro.removePartida(this);
        }
        this.arbitro = arbitro;
        if (arbitro != null && !arbitro.getPartidas().contains(this)) {
            arbitro.addPartida(this);
        }
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public int getId() {
        return id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        if (fecha == null) {
            // no lanza excepción; solo ignora
            return;
        }
        this.fecha = fecha;
    }

    /**
     * Valida que ambos equipos tengan al menos un jugador.
     * Si alguno falla, muestra un mensaje indicando exactamente cuáles están vacíos.
     */
    private boolean validarEquipos(Equipo e1, Equipo e2) {
        StringBuilder errores = new StringBuilder();

        if (e1 == null || e1.getJugadores() == null || e1.getJugadores().isEmpty()) {
            errores.append("El equipo ")
                    .append(e1 != null ? e1.getNombre() : "Equipo1")
                    .append(" no tiene jugadores.\n");
        }

        if (e2 == null || e2.getJugadores() == null || e2.getJugadores().isEmpty()) {
            errores.append("El equipo ")
                    .append(e2 != null ? e2.getNombre() : "Equipo2")
                    .append(" no tiene jugadores.\n");
        }

        if (errores.length() > 0) {
            System.out.println("No se puede crear la partida:\n" + errores);
            return false;
        }
        return true;
    }

    /**
     * Disassociate: quita la Partida de referencias en Juego y Árbitro,
     * y limpia la referencia a Torneo. Se usa al eliminar un Torneo (composición).
     */
    public void disassociate() {
        if (arbitro != null) {
            arbitro.removePartida(this);
            arbitro = null;
        }
        if (juego != null) {
            juego.removePartida(this);
            juego = null;
        }
        torneo = null;
    }

    @Override
    public String toString() {
        String e1 = (equipo1 != null) ? equipo1.getNombre() : "Equipo?";
        String e2 = (equipo2 != null) ? equipo2.getNombre() : "Equipo?";
        String g = (juego != null) ? juego.getNombre() : "Juego?";
        String a = (arbitro != null) ? arbitro.getNombre() : "Sin árbitro";
        return String.format("%s vs %s | %s | Árbitro: %s | %s",
                e1, e2, g, a, resultado == null ? "(sin resultado)" : resultado);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partida)) return false;
        Partida partida = (Partida) o;
        return id == partida.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
