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
        this.nombre = nombre.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            throw new IllegalArgumentException("El alias del jugador no puede estar vacío.");
        }
        this.alias = alias.trim();
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

    /**
     * Mantiene la relación bidireccional de forma segura (sin recursión).
     */
    public void setEquipo(Equipo nuevo) {
        if (this.equipo == nuevo) return; // nada que hacer

        Equipo anterior = this.equipo;
        this.equipo = nuevo;

        // Quitar del equipo anterior
        if (anterior != null) {
            anterior.removeJugadorInternal(this);
        }

        // Agregar al nuevo equipo
        if (nuevo != null) {
            nuevo.addJugadorInternal(this);
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
