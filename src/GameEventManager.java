// ============================================
// PARTE 2: PATRONES DE DISEÑO GoF
// ============================================

import java.util.ArrayList;
import java.util.List;

// ==========================================
// PATRON SINGLETON: GameEventManager
// ==========================================
public class GameEventManager {
    private static GameEventManager instance;
    private List<GameEventListener> listeners;
    
    private GameEventManager() {
        listeners = new ArrayList<>();
    }
    
    public static GameEventManager getInstance() {
        if (instance == null) {
            instance = new GameEventManager();
        }
        return instance;
    }
    
    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(GameEventListener listener) {
        listeners.remove(listener);
    }
    
    public void notifyEvent(String mensaje) {
        for (GameEventListener listener : listeners) {
            listener.onGameEvent(mensaje);
        }
    }
}



