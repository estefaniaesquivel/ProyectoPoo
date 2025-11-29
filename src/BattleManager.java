
// ==========================================
// BattleManager
// ==========================================
class BattleManager {
    private EnemyAI enemyAI;
    
    public BattleManager() {
        this.enemyAI = new AggressiveAI();
    }
    
    public void iniciarBatalla(Character jugador, Character enemigo) {
        GameEventManager.getInstance().notifyEvent("===== BATALLA INICIADA =====");
        GameEventManager.getInstance().notifyEvent(jugador.getNombre() + " vs " + enemigo.getNombre());
    }
    
    public boolean ejecutarTurnoJugador(Character jugador, Character enemigo, String accion) {
        if (!jugador.estaVivo() || !enemigo.estaVivo()) {
            return false;
        }
        
        switch (accion.toLowerCase()) {
            case "atacar":
                jugador.attack(enemigo);
                break;
            case "ataque_poderoso":
                new PowerAttackStrategy().executeAttack(jugador, enemigo);
                break;
            case "descansar":
                if (jugador instanceof Mage) {
                    ((Mage) jugador).descansar();
                } else {
                    GameEventManager.getInstance().notifyEvent(jugador.getNombre() + " descansa y recupera 10 HP");
                    jugador.setSalud(jugador.getSalud() + 10);
                }
                break;
            default:
                jugador.attack(enemigo);
        }
        
        return true;
    }
    
    public void ejecutarTurnoEnemigo(Character enemigo, Character jugador) {
        if (enemigo.estaVivo() && jugador.estaVivo()) {
            enemyAI.decideTurn(enemigo, jugador);
        }
    }
    
    public boolean batallaTerminada(Character jugador, Character enemigo) {
        if (!enemigo.estaVivo()) {
            GameEventManager.getInstance().notifyEvent("===== VICTORIA =====");
            GameEventManager.getInstance().notifyEvent(jugador.getNombre() + " ha derrotado a " + enemigo.getNombre());
            jugador.ganarExperiencia(50 * enemigo.getNivel());
            return true;
        }
        
        if (!jugador.estaVivo()) {
            GameEventManager.getInstance().notifyEvent("===== DERROTA =====");
            GameEventManager.getInstance().notifyEvent(jugador.getNombre() + " ha sido derrotado");
            return true;
        }
        
        return false;
    }
}
