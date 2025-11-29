// Clase Warrior (Aragorn)
public class Warrior extends Character {
    private int fuerzaEspada;
    
    public Warrior(String nombre, int nivel) {
        super(nombre, nivel, 100 + nivel * 10);
        this.fuerzaEspada = 15 + nivel * 3;
    }
    
    @Override
    public String getClasePersonaje() {
        return "Guerrero";
    }
    
    @Override
    public void attack(Character objetivo) {
        int danoArma = (getArmaEquipada() != null) ? getArmaEquipada().getDamage() : 0;
        int damage = fuerzaEspada + danoArma + (int)(Math.random() * 10);
        
        String mensaje = nombre + " ataca causando " + damage + " de dano";
        if (getArmaEquipada() != null) {
            mensaje += " con " + getArmaEquipada().getNombre();
        }
        GameEventManager.getInstance().notifyEvent(mensaje);
        
        objetivo.defend(damage);
    }
    
    @Override
    public void defend(int damage) {
        int defensaBasica = 5;
        int defensaArmadura = (getArmaduraEquipada() != null) ? getArmaduraEquipada().getDefensa() : 0;
        int defensaTotal = defensaBasica + defensaArmadura;
        
        int danoReal = Math.max(1, damage - defensaTotal);
        setSalud(salud - danoReal);
        
        String mensaje = nombre + " recibe " + danoReal + " de dano";
        if (getArmaduraEquipada() != null) {
            mensaje += " (defendio " + defensaTotal + " con " + getArmaduraEquipada().getNombre() + ")";
        }
        GameEventManager.getInstance().notifyEvent(mensaje);
    }
}
