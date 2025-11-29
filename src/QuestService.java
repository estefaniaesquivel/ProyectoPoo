import java.util.ArrayList;
import java.util.List;
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

