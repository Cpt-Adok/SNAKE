package Characters;

public class Players extends Personnage {
    public Players(String name, int[] coordinate) {
        super(name, coordinate);
    }
    
    public Integer changeCoordinate(String input) {
        if (input.length() > 0) {
            return (int)input.charAt(0);
        }
        return null;
    }

    public boolean moveCoordinate(int keys) {
        Mouvements value = getMouvement(keys);
        if (value != null) {
            moveSnake(value);
            return true;
        }
        return false;
    }
}
