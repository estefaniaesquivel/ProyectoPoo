// ============================================
// PARTE 3: SISTEMA DE QUESTS Y FACADE
// ============================================

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// ==========================================
// Clase Quest (Agregacion)
// ==========================================
class Quest implements Serializable {
    private String nombre;
    private String descripcion;
    private List<Character> personajesAsignados;
    private boolean completada;
    private int recompensaExp;
    private List<Weapon> recompensasArmas;
    private List<Armor> recompensasArmaduras;
    private Character enemigo;
    
    public Quest(String nombre, String descripcion, int recompensaExp) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.recompensaExp = recompensaExp;
        this.personajesAsignados = new ArrayList<>();
        this.recompensasArmas = new ArrayList<>();
        this.recompensasArmaduras = new ArrayList<>();
        this.completada = false;
    }
    
    public void asignarPersonaje(Character personaje) {
        if (!personajesAsignados.contains(personaje)) {
            personajesAsignados.add(personaje);
            GameEventManager.getInstance().notifyEvent(personaje.getNombre() + " se une a la mision: " + nombre);
        }
    }
    
    public void setEnemigo(Character enemigo) {
        this.enemigo = enemigo;
    }
    
    public Character getEnemigo() {
        return enemigo;
    }
    
    public void addRecompensaArma(Weapon arma) {
        recompensasArmas.add(arma);
    }
    
    public void addRecompensaArmadura(Armor armadura) {
        recompensasArmaduras.add(armadura);
    }
    
    public void completarMision() {
        if (!completada && !personajesAsignados.isEmpty()) {
            completada = true;
            GameEventManager.getInstance().notifyEvent("MISION COMPLETADA: " + nombre);
            GameEventManager.getInstance().notifyEvent("Recompensa: " + recompensaExp + " EXP");
            
            for (Character p : personajesAsignados) {
                if (p.estaVivo()) {
                    p.ganarExperiencia(recompensaExp);
                }
            }
            
            // Agregar recompensas al inventario
            GameState state = GameState.getInstance();
            for (Weapon arma : recompensasArmas) {
                state.addArmaInventario(arma);
                GameEventManager.getInstance().notifyEvent("Recompensa obtenida: " + arma.getNombre());
            }
            for (Armor armadura : recompensasArmaduras) {
                state.addArmaduraInventario(armadura);
                GameEventManager.getInstance().notifyEvent("Recompensa obtenida: " + armadura.getNombre());
            }
        }
    }
    
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public boolean isCompletada() { return completada; }
    public List<Character> getPersonajesAsignados() { return personajesAsignados; }
    public int getRecompensaExp() { return recompensaExp; }
}


// ==========================================
// QuestService
// ==========================================
class QuestService {
    public void crearQuest(String nombre, String descripcion, int recompensaExp) {
        Quest quest = new Quest(nombre, descripcion, recompensaExp);
        GameState.getInstance().addQuest(quest);
        GameEventManager.getInstance().notifyEvent("Nueva mision disponible: " + nombre);
    }
    
    public void asignarPersonajeAQuest(Quest quest, Character personaje) {
        quest.asignarPersonaje(personaje);
    }
    
    public void completarQuest(Quest quest) {
        quest.completarMision();
    }
    
    public List<Quest> obtenerQuestsActivas() {
        List<Quest> activas = new ArrayList<>();
        for (Quest quest : GameState.getInstance().getQuests()) {
            if (!quest.isCompletada()) {
                activas.add(quest);
            }
        }
        return activas;
    }
}

