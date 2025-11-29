// ==========================================
// PATRON DECORATOR: ArmorDecorator
// ==========================================
abstract class ArmorDecorator extends Armor {
    protected Armor decoratedArmor;
    
    public ArmorDecorator(Armor armor) {
        super(armor.getNombre(), armor.getDefensa());
        this.decoratedArmor = armor;
    }
    
    @Override
    public abstract int getDefensa();
}

class ReinforcedArmor extends ArmorDecorator {
    private int bonusDefensa;
    
    public ReinforcedArmor(Armor armor, int bonusDefensa) {
        super(armor);
        this.bonusDefensa = bonusDefensa;
    }
    
    @Override
    public String getNombre() {
        return decoratedArmor.getNombre() + " (Reforzada)";
    }
    
    @Override
    public int getDefensa() {
        return decoratedArmor.getDefensa() + bonusDefensa;
    }
}