import java.util.ArrayList;
import java.util.List;
// ==========================================
// PATRON COMPOSITE: EnemyGroup
// ==========================================
public class EnemyGroup extends Character {
    private List<Character> enemies;
    
    public EnemyGroup(String nombre) {
        super(nombre, 1, 1);
        this.enemies = new ArrayList<>();
    }
    
    public void addEnemy(Character enemy) {
        enemies.add(enemy);
        recalcularEstadisticas();
    }
    
    public void removeEnemy(Character enemy) {
        enemies.remove(enemy);
        recalcularEstadisticas();
    }
    
    private void recalcularEstadisticas() {
        int totalSalud = 0;
        int totalNivel = 0;
        
        for (Character enemy : enemies) {
            totalSalud += enemy.getSaludMaxima();
            totalNivel += enemy.getNivel();
        }
        
        this.saludMaxima = totalSalud;
        this.salud = totalSalud;
        this.nivel = enemies.isEmpty() ? 1 : totalNivel / enemies.size();
    }
    
    @Override
    public String getClasePersonaje() {
        return "Grupo de Enemigos";
    }
    
    @Override
    public void attack(Character objetivo) {
        for (Character enemy : enemies) {
            if (enemy.estaVivo()) {
                enemy.attack(objetivo);
            }
        }
    }
    
    @Override
    public void defend(int damage) {
        if (!enemies.isEmpty()) {
            Character target = enemies.get((int)(Math.random() * enemies.size()));
            target.defend(damage);
            
            enemies.removeIf(e -> !e.estaVivo());
            recalcularEstadisticas();
        }
    }
    
    public List<Character> getEnemies() {
        return enemies;
    }
}
