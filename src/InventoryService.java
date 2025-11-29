// ==========================================
// InventoryService
// ==========================================
class InventoryService {
    public void equiparArma(Character personaje, Weapon arma) {
        personaje.agregarYEquiparArma(arma);
    }
    
    public void equiparArmadura(Character personaje, Armor armadura) {
        personaje.agregarYEquiparArmadura(armadura);
    }
    
    public void desequiparArma(Character personaje) {
        personaje.desequiparArma();
    }
    
    public void desequiparArmadura(Character personaje) {
        personaje.desequiparArmadura();
    }
}