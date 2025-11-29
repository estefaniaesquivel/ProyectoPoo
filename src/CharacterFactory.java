
// ==========================================
// PATRON FACTORY METHOD: CharacterFactory
// ==========================================
abstract class CharacterFactory {
    public abstract Character createCharacter(String nombre, int nivel);
    
    public static CharacterFactory getFactory(String tipo) {
        switch (tipo.toLowerCase()) {
            case "warrior":
            case "guerrero":
                return new WarriorFactory();
            case "mage":
            case "mago":
                return new MageFactory();
            case "archer":
            case "arquero":
                return new ArcherFactory();
            case "priest":
            case "sacerdote":
                return new PriestFactory();
            default:
                throw new IllegalArgumentException("Tipo de personaje desconocido: " + tipo);
        }
    }
}

class WarriorFactory extends CharacterFactory {
    @Override
    public Character createCharacter(String nombre, int nivel) {
        return new Warrior(nombre, nivel);
    }
}

class MageFactory extends CharacterFactory {
    @Override
    public Character createCharacter(String nombre, int nivel) {
        return new Mage(nombre, nivel);
    }
}

class ArcherFactory extends CharacterFactory {
    @Override
    public Character createCharacter(String nombre, int nivel) {
        return new Archer(nombre, nivel);
    }
}

class PriestFactory extends CharacterFactory {
    @Override
    public Character createCharacter(String nombre, int nivel) {
        return new Priest(nombre, nivel);
    }
}
