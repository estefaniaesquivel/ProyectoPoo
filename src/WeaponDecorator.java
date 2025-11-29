// ==========================================
// PATRON DECORATOR: WeaponDecorator
// ==========================================
abstract class WeaponDecorator extends Weapon {
    protected Weapon decoratedWeapon;
    
    public WeaponDecorator(Weapon weapon) {
        super(weapon.getNombre(), weapon.getDamage());
        this.decoratedWeapon = weapon;
    }
    
    @Override
    public abstract int getDamage();
}

class EnchantedWeapon extends WeaponDecorator {
    private int bonusDamage;
    
    public EnchantedWeapon(Weapon weapon, int bonusDamage) {
        super(weapon);
        this.bonusDamage = bonusDamage;
    }
    
    @Override
    public String getNombre() {
        return decoratedWeapon.getNombre() + " (Encantada)";
    }
    
    @Override
    public int getDamage() {
        return decoratedWeapon.getDamage() + bonusDamage;
    }
}

class FireWeapon extends WeaponDecorator {
    public FireWeapon(Weapon weapon) {
        super(weapon);
    }
    
    @Override
    public String getNombre() {
        return decoratedWeapon.getNombre() + " (Fuego)";
    }
    
    @Override
    public int getDamage() {
        return (int)(decoratedWeapon.getDamage() * 1.5);
    }
}