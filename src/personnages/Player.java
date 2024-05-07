package personnages;

public class Player extends Personnage {
    public Player(int size, int[] coordinate) {
        super(size, coordinate);
    }

    public void moveCoordinate(int keys) {
        switch (keys) {
            case 77:    // w
                this.coordinate[1]++;
                break;

            case 73:    // s
                this.coordinate[1]--;
                break;
            
            case 61:    // a
                this.coordinate[0]--;
                break;

            case 64:    // d
                this.coordinate[0]++;
                break;
            
            default:    // autre
                break; 
        }
    }
}
