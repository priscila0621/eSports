package models;

/**
 * Jugador: tiene nombre, alias y ranking. Pertenece a un Equipo (referencia).
 */
public class Jugador {
    private static int NEXT_ID = 1;
    private int id;
    private String nombre;
    private String alias;
    private int ranking;

    // referencia a Equipo (puede ser null)
    private Equipo equipo;

    public Jugador() { this.id = NEXT_ID++; }
    public Jugador(String nombre, String alias, int ranking) {
        this();
        this.nombre = nombre;
        this.alias = alias;
        this.ranking = ranking;
    }

    // Cuando se asigna equipo, actualizamos la lista del equipo (bidireccional)
    public void setEquipo(Equipo equipo) {
        if (this.equipo == equipo) return;

        // quitar de equipo anterior
        if (this.equipo != null) {
            this.equipo.getJugadores().remove(this);
        }

        this.equipo = equipo;

        // agregar a nuevo equipo si aplica
        if (equipo != null && !equipo.getJugadores().contains(this)) {
            equipo.getJugadores().add(this);
        }
    }

    public Equipo getEquipo() { return equipo; }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public int getRanking() { return ranking; }
    public void setRanking(int ranking) { this.ranking = ranking; }

    @Override
    public String toString() {
        return nombre + " (" + alias + ")";
    }
}
