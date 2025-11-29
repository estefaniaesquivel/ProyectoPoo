
// Clase Priest (NPC)
public class Priest extends Character implements Healable {
    private int poderCuracion;
    private int manaMaximo;
    private int puntosMana;
    
    public Priest(String nombre, int nivel) {
        super(nombre, nivel, 60 + nivel * 6);
        this.poderCuracion = 25 + nivel * 5;
        this.manaMaximo = 80 + nivel * 20;
        this.puntosMana = manaMaximo;
    }
    
    @Override
    public String getClasePersonaje() {
        return "Sacerdote";
    }
    
    @Override
    public void attack(Character objetivo) {
        if (puntosMana >= 8) {
            puntosMana -= 8;
            int damage = 10 + (int)(Math.random() * 8);
            GameEventManager.getInstance().notifyEvent(nombre + " lanza magia sagrada causando " + damage + " de dano");
            objetivo.defend(damage);
        } else {
            int damage = 3 + (int)(Math.random() * 4);
            GameEventManager.getInstance().notifyEvent(nombre + " sin mana, ataque basico causando " + damage + " de dano");
            objetivo.defend(damage);
        }
    }

    @Override
    public void defend(int damage) {
        int bendicion = 4;
        int defensaArmadura = (getArmaduraEquipada() != null) ? getArmaduraEquipada().getDefensa() : 0;
        int defensaTotal = bendicion + defensaArmadura;
        
        int danoReal = Math.max(1, damage - defensaTotal);
        setSalud(salud - danoReal);
        
        GameEventManager.getInstance().notifyEvent(nombre + " recibe " + danoReal + " de dano (bendicion absorbe " + defensaTotal + " puntos)");
    }

    @Override
    public void heal(Character objetivo) {
        if (puntosMana >= 15) {
            puntosMana -= 15;
            int curacion = poderCuracion + (int)(Math.random() * 10);
            int saludAnterior = objetivo.getSalud();
            objetivo.setSalud(objetivo.getSalud() + curacion);
            int curacionReal = objetivo.getSalud() - saludAnterior;
            GameEventManager.getInstance().notifyEvent(nombre + " cura a " + objetivo.getNombre() + " por " + curacionReal + " puntos");
        } else {
            GameEventManager.getInstance().notifyEvent(nombre + " no tiene suficiente mana para curar");
        }
    }
}