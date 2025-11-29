// ============================================
// PARTE 1: MODELOS BASE Y CLASES ABSTRACTAS
// ============================================

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Clase abstracta Character
abstract class Character implements Serializable {
    protected String nombre;
    protected int nivel;
    protected int salud;
    protected int saludMaxima;
    protected int experiencia;
    protected int experienciaParaNivel;
    private List<Weapon> armasDisponibles;
    private List<Armor> armadurasDisponibles;
    private Weapon armaEquipada;
    private Armor armaduraEquipada;
    
    public Character(String nombre, int nivel, int salud) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.salud = salud;
        this.saludMaxima = salud;
        this.experiencia = 0;
        this.experienciaParaNivel = 100 * nivel;
        this.armasDisponibles = new ArrayList<>();
        this.armadurasDisponibles = new ArrayList<>();
        this.armaEquipada = null;
        this.armaduraEquipada = null;
    }
    
    public void agregarYEquiparArma(Weapon arma) {
        armasDisponibles.add(arma);
        this.armaEquipada = arma;
        GameEventManager.getInstance().notifyEvent(nombre + " equipa: " + arma.getNombre());
    }
    
    public void agregarYEquiparArmadura(Armor armadura) {
        armadurasDisponibles.add(armadura);
        this.armaduraEquipada = armadura;
        GameEventManager.getInstance().notifyEvent(nombre + " equipa: " + armadura.getNombre());
    }
    
    public void desequiparArma() {
        if (armaEquipada != null) {
            GameEventManager.getInstance().notifyEvent(nombre + " desequipa: " + armaEquipada.getNombre());
            armaEquipada = null;
        }
    }
    
    public void desequiparArmadura() {
        if (armaduraEquipada != null) {
            GameEventManager.getInstance().notifyEvent(nombre + " desequipa: " + armaduraEquipada.getNombre());
            armaduraEquipada = null;
        }
    }
    
    public void ganarExperiencia(int exp) {
        experiencia += exp;
        GameEventManager.getInstance().notifyEvent(nombre + " gana " + exp + " EXP");
        
        while (experiencia >= experienciaParaNivel) {
            subirNivel();
        }
    }
    
    protected void subirNivel() {
        nivel++;
        experiencia -= experienciaParaNivel;
        experienciaParaNivel = 100 * nivel;
        saludMaxima += 10;
        salud = saludMaxima;
        GameEventManager.getInstance().notifyEvent(nombre + " sube al nivel " + nivel + "!");
    }

    public abstract void attack(Character objetivo);
    public abstract void defend(int damage);
    public abstract String getClasePersonaje();
    
    // Getters y setters
    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public int getSalud() { return salud; }
    public int getSaludMaxima() { return saludMaxima; }
    public int getExperiencia() { return experiencia; }
    public int getExperienciaParaNivel() { return experienciaParaNivel; }
    
    public void setSalud(int salud) {
        this.salud = Math.max(0, Math.min(salud, saludMaxima));
    }
    
    public boolean estaVivo() {
        return salud > 0;
    }
    
    public String toString() {
        return nombre + " (Nivel " + nivel + ", HP: " + salud + "/" + saludMaxima + ")";
    }

    public Weapon getArmaEquipada() { return armaEquipada; }
    public Armor getArmaduraEquipada() { return armaduraEquipada; }
    public List<Weapon> getArmasDisponibles() { return armasDisponibles; }
    public List<Armor> getArmadurasDisponibles() { return armadurasDisponibles; }
}



