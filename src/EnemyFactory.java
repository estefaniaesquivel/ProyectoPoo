// ==========================================
// PATRON FACTORY: EnemyFactory
// ==========================================
public class EnemyFactory {
    public static Character createEnemy(String tipo, int nivel) {
        CharacterFactory factory = CharacterFactory.getFactory(tipo);
        return factory.createCharacter("Enemigo " + tipo, nivel);
    }
    
    public static Character createOrc(int nivel) {
        Warrior orc = new Warrior("Orco", nivel);
        return orc;
    }
    
    public static Character createUrukHai(int nivel) {
        Warrior uruk = new Warrior("Uruk-Hai", nivel + 2);
        return uruk;
    }
    
    public static Character createNazgul(int nivel) {
        Mage nazgul = new Mage("Nazgul", nivel + 3);
        return nazgul;
    }
}