import java.io.Serializable;

// Clase Armor
class Armor implements Serializable {
    private String nombre;
    private int defensa;
    
    public Armor(String nombre, int defensa) {
        this.nombre = nombre;
        this.defensa = defensa;
    }
    
    public String getNombre() { return nombre; }
    public int getDefensa() { return defensa; }
    
    public String toString() {
        return nombre + " (+" + defensa + " defensa)";
    }
}