import java.io.Serializable;

// Clase Weapon
public class Weapon implements Serializable {
    private String nombre;
    private int damage;
    
    public Weapon(String nombre, int damage) {
        this.nombre = nombre;
        this.damage = damage;
    }
    
    public String getNombre() { return nombre; }
    public int getDamage() { return damage; }
    
    public String toString() {
        return nombre + " (+" + damage + " dano)";
    }
}
