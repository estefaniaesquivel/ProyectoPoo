// ==========================================
// PATRON STRATEGY: EnemyAI
// ==========================================
interface EnemyAI {
    void decideTurn(Character enemy, Character target);
}

class AggressiveAI implements EnemyAI {
    @Override
    public void decideTurn(Character enemy, Character target) {
        if (Math.random() < 0.3) {
            new PowerAttackStrategy().executeAttack(enemy, target);
        } else {
            enemy.attack(target);
        }
    }
}

class DefensiveAI implements EnemyAI {
    @Override
    public void decideTurn(Character enemy, Character target) {
        if (enemy.getSalud() < enemy.getSaludMaxima() * 0.3) {
            GameEventManager.getInstance().notifyEvent(enemy.getNombre() + " se defiende");
        } else {
            enemy.attack(target);
        }
    }
}

class EvasiveAI implements EnemyAI {
    @Override
    public void decideTurn(Character enemy, Character target) {
        if (Math.random() < 0.2) {
            GameEventManager.getInstance().notifyEvent(enemy.getNombre() + " esquiva el turno");
        } else {
            enemy.attack(target);
        }
    }
}
