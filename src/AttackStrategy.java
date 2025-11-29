// ==========================================
// PATRON STRATEGY: AttackStrategy
// ==========================================
interface AttackStrategy {
    void executeAttack(Character attacker, Character target);
}

class NormalAttackStrategy implements AttackStrategy {
    @Override
    public void executeAttack(Character attacker, Character target) {
        attacker.attack(target);
    }
}

class PowerAttackStrategy implements AttackStrategy {
    @Override
    public void executeAttack(Character attacker, Character target) {
        GameEventManager.getInstance().notifyEvent(attacker.getNombre() + " realiza un ATAQUE PODEROSO!");
        attacker.attack(target);
        attacker.attack(target);
    }
}

class DefensiveAttackStrategy implements AttackStrategy {
    @Override
    public void executeAttack(Character attacker, Character target) {
        GameEventManager.getInstance().notifyEvent(attacker.getNombre() + " ataca con cautela");
        attacker.attack(target);
    }
}
