// ==========================================
// PATRON FACADE: GameFacade
// ==========================================
public class GameFacade {
    private static GameFacade instance;
    private BattleManager battleManager;
    private QuestService questService;
    private InventoryService inventoryService;
    
    private GameFacade() {
        this.battleManager = new BattleManager();
        this.questService = new QuestService();
        this.inventoryService = new InventoryService();
    }
    
    public static GameFacade getInstance() {
        if (instance == null) {
            instance = new GameFacade();
        }
        return instance;
    }
    
    // Metodos de batalla
    public void iniciarBatalla(Character jugador, Character enemigo) {
        battleManager.iniciarBatalla(jugador, enemigo);
    }
    
    public boolean ejecutarTurnoJugador(Character jugador, Character enemigo, String accion) {
        return battleManager.ejecutarTurnoJugador(jugador, enemigo, accion);
    }
    
    public void ejecutarTurnoEnemigo(Character enemigo, Character jugador) {
        battleManager.ejecutarTurnoEnemigo(enemigo, jugador);
    }
    
    public boolean batallaTerminada(Character jugador, Character enemigo) {
        return battleManager.batallaTerminada(jugador, enemigo);
    }
    
    // Metodos de quests
    public void crearQuest(String nombre, String descripcion, int recompensaExp) {
        questService.crearQuest(nombre, descripcion, recompensaExp);
    }
    
    public void asignarPersonajeAQuest(Quest quest, Character personaje) {
        questService.asignarPersonajeAQuest(quest, personaje);
    }
    
    public void completarQuest(Quest quest) {
        questService.completarQuest(quest);
    }
    
    public List<Quest> obtenerQuestsActivas() {
        return questService.obtenerQuestsActivas();
    }
    
    // Metodos de inventario
    public void equiparArma(Character personaje, Weapon arma) {
        inventoryService.equiparArma(personaje, arma);
    }
    
    public void equiparArmadura(Character personaje, Armor armadura) {
        inventoryService.equiparArmadura(personaje, armadura);
    }
    
    public void desequiparArma(Character personaje) {
        inventoryService.desequiparArma(personaje);
    }
    
    public void desequiparArmadura(Character personaje) {
        inventoryService.desequiparArmadura(personaje);
    }
    
    // Metodo para crear personaje
    public Character crearPersonaje(String tipo, String nombre, int nivel) {
        CharacterFactory factory = CharacterFactory.getFactory(tipo);
        Character personaje = factory.createCharacter(nombre, nivel);
        GameEventManager.getInstance().notifyEvent("Personaje creado: " + personaje.getNombre() + " (" + personaje.getClasePersonaje() + ")");
        return personaje;
    }
    
    // Metodo para inicializar juego
    public void inicializarJuego(Character jugador) {
        GameState.getInstance().setJugador(jugador);
        inicializarInventarioInicial();
        crearQuestsIniciales();
    }
    
    private void inicializarInventarioInicial() {
        GameState state = GameState.getInstance();
        state.addArmaInventario(new Weapon("Espada Corta", 10));
        state.addArmaInventario(new Weapon("Arco Simple", 8));
        state.addArmaduraInventario(new Armor("Armadura de Cuero", 5));
    }
    
    private void crearQuestsIniciales() {
        // Quest 1: La Comarca en Peligro
        Quest quest1 = new Quest("La Comarca en Peligro", 
                                 "Orcos han invadido la Comarca. Debes detenerlos.", 
                                 200);
        quest1.setEnemigo(EnemyFactory.createOrc(2));
        quest1.addRecompensaArma(new Weapon("Espada Elfica", 20));
        GameState.getInstance().addQuest(quest1);
        
        // Quest 2: El Paso de Caradhras
        Quest quest2 = new Quest("El Paso de Caradhras", 
                                 "Atraviesa las montanas enfrentando a los Uruk-Hai.", 
                                 350);
        quest2.setEnemigo(EnemyFactory.createUrukHai(4));
        quest2.addRecompensaArmadura(new Armor("Cota de Mithril", 25));
        GameState.getInstance().addQuest(quest2);
        
        // Quest 3: La Amenaza de los Nazgul
        Quest quest3 = new Quest("La Amenaza de los Nazgul", 
                                 "Un Nazgul acecha en las sombras. Debes enfrentarlo.", 
                                 500);
        quest3.setEnemigo(EnemyFactory.createNazgul(6));
        quest3.addRecompensaArma(new Weapon("Anduril", 35));
        GameState.getInstance().addQuest(quest3);
    }
}
