package personnages;

public class Player extends Personnage {
    public Player(int size, int[] coordinate) {
        super(size, coordinate);
    }

    public void changeCoordinate(int keys) {
        switch (keys) {
            case 77:    Mouvement.HAUT.editCoordinate(coordinate); // w
            case 73:    Mouvement.BAS.editCoordinate(coordinate); // s
            case 61:    Mouvement.GAUCHE.editCoordinate(coordinate); // a
            case 64:    Mouvement.DROITE.editCoordinate(coordinate); // d
            default:    break;
        }
    }
}
