package models;

/**
 * Jugador: tiene nombre, alias y ranking. Pertenece a un Equipo (referencia).
 */
public class Jugador {
    private static int NEXT_ID = 1;
    private final int id;
    private String nombre;
    private String alias;
    private int ranking;

    // referencia a Equipo (puede ser null)
    private Equipo equipo;

    public Jugador() {
        this.id = NEXT_ID++;
    }

    public Jugador(String nombre, String alias, int ranking) {
        this();
        setNombre(nombre);
        setAlias(alias);
        setRanking(ranking);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            throw new IllegalArgumentException("El alias del jugador no puede estar vacío.");
        }
        this.alias = alias;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        if (ranking < 0) {
            throw new IllegalArgumentException("El ranking no puede ser negativo.");
        }
        this.ranking = ranking;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    // Cuando se asigna equipo, actualizamos la lista del equipo (bidireccional)
    public void setEquipo(Equipo equipo) {
        if (this.equipo == equipo) return;

        // quitar de equipo anterior
        if (this.equipo != null) {
            this.equipo.removeJugador(this);
        }

        this.equipo = equipo;

        // agregar a nuevo equipo si aplica
        if (equipo != null && !equipo.getJugadores().contains(this)) {
            equipo.addJugador(this);
        }
    }

    @Override
    public String toString() {
        return nombre + " (" + alias + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;
        Jugador jugador = (Jugador) o;
        return id == jugador.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
