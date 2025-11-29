
// Clase Mage (Gandalf)
public class Mage extends Character {
    private int puntosMana;
    private int manaMaximo;
    private int poderMagico;
    
    public Mage(String nombre, int nivel) {
        super(nombre, nivel, 70 + nivel * 7);
        this.manaMaximo = 50 + nivel * 15;
        this.puntosMana = manaMaximo;
        this.poderMagico = 20 + nivel * 4;
    }
    
    @Override
    public String getClasePersonaje() {
        return "Mago";
    }
    
    @Override
    public void attack(Character objetivo) {
        if (puntosMana >= 10) {
            puntosMana -= 10;
            int damage = poderMagico + (int)(Math.random() * 15);
            GameEventManager.getInstance().notifyEvent(nombre + " lanza hechizo causando " + damage + " de dano (Mana: " + puntosMana + "/" + manaMaximo + ")");
            objetivo.defend(damage);
        } else {
            int damage = 5 + (int)(Math.random() * 5);
            GameEventManager.getInstance().notifyEvent(nombre + " sin mana, ataca fisicamente causando " + damage + " de dano");
            objetivo.defend(damage);
        }
    }
    
    @Override
    public void defend(int damage) {
        int danoReal = damage;
        setSalud(salud - danoReal);
        GameEventManager.getInstance().notifyEvent(nombre + " recibe " + danoReal + " de dano");
    }

    public void descansar() {
        puntosMana = manaMaximo;
        GameEventManager.getInstance().notifyEvent(nombre + " descansa y recupera todo su mana");
    }
    
    public int getPuntosMana() { return puntosMana; }
    public int getManaMaximo() { return manaMaximo; }
}