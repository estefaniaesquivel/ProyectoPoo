import java.util.ArrayList;
import java.util.List;
// ==========================================
// PATRON SINGLETON: GameState
// ==========================================
public class GameState {
    private static GameState instance;
    private Character jugador;
    private List<Character> aliados;
    private List<Quest> quests;
    private List<Weapon> inventarioArmas;
    private List<Armor> inventarioArmaduras;
    
    private GameState() {
        this.aliados = new ArrayList<>();
        this.quests = new ArrayList<>();
        this.inventarioArmas = new ArrayList<>();
        this.inventarioArmaduras = new ArrayList<>();
    }
    
    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }
    
    public static void reset() {
        instance = null;
    }
    
    // Getters y setters
    public Character getJugador() { return jugador; }
    public void setJugador(Character jugador) { this.jugador = jugador; }
    
    public List<Character> getAliados() { return aliados; }
    public void addAliado(Character aliado) { aliados.add(aliado); }
    
    public List<Quest> getQuests() { return quests; }
    public void addQuest(Quest quest) { quests.add(quest); }
    
    public List<Weapon> getInventarioArmas() { return inventarioArmas; }
    public void addArmaInventario(Weapon arma) { inventarioArmas.add(arma); }
    
    public List<Armor> getInventarioArmaduras() { return inventarioArmaduras; }
    public void addArmaduraInventario(Armor armadura) { inventarioArmaduras.add(armadura); }
}