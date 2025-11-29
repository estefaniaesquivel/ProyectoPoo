// Clase Archer (Legolas)
public class Archer extends Character {
    private int precisionArco;
    private int flechasRestantes;
    
    public Archer(String nombre, int nivel) {
        super(nombre, nivel, 80 + nivel * 8);
        this.precisionArco = 12 + nivel * 2;
        this.flechasRestantes = 30 + nivel * 5;
    }
    
    @Override
    public String getClasePersonaje() {
        return "Arquero";
    }
    
    @Override
    public void attack(Character objetivo) {
        if (flechasRestantes > 0) {
            flechasRestantes--;
            int damage = precisionArco + (int)(Math.random() * 12);
            
            if (Math.random() < 0.2) {
                damage *= 2;
                GameEventManager.getInstance().notifyEvent(nombre + " dispara flecha CRITICA causando " + damage + " de dano! (Flechas: " + flechasRestantes + ")");
            } else {
                GameEventManager.getInstance().notifyEvent(nombre + " dispara flecha causando " + damage + " de dano (Flechas: " + flechasRestantes + ")");
            }
            objetivo.defend(damage);
        } else {
            int damage = 6 + (int)(Math.random() * 6);
            GameEventManager.getInstance().notifyEvent(nombre + " sin flechas, ataca cuerpo a cuerpo causando " + damage + " de dano");
            objetivo.defend(damage);
        }
    }
    
    @Override
    public void defend(int damage) {
        int danoReal = Math.max(1, damage);
        setSalud(salud - danoReal);
        GameEventManager.getInstance().notifyEvent(nombre + " recibe " + danoReal + " de dano");
    }
    
    public int getFlechasRestantes() { return flechasRestantes; }
}

