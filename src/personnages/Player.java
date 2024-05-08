package personnages;

import java.util.Scanner;

public class Player extends Personnage {
    private int[] coordinate;

    /**
     * <p>le constructor definie les coordonnées de la tête et defini n.
     * 
     * @param n est une variable qui contient le nombre de
     * tour avant l'augmentation de taille.
     * @param coordinate est un array de <strong>2 entiers</strong> 
     * qui est <strong>{x, y}</strong>
     */
    public Player(int n, int[] coordinate) {
        super(n, coordinate);
        this.coordinate = coordinate;
    }

    public void changeCoordinate() {
        Scanner scanner = new Scanner(System.in);
        char value;

        do {
            value = scanner.nextLine().charAt(0);
        } while (!moveCoordinate((int)value));
        
        scanner.close();
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    private boolean moveCoordinate(int keys) {
        switch (keys) {
            case 119:    Mouvement.HAUT.editCoordinate(coordinate); break;
            case 115:    Mouvement.BAS.editCoordinate(coordinate); break;
            case 97:     Mouvement.GAUCHE.editCoordinate(coordinate); break;
            case 100:    Mouvement.DROITE.editCoordinate(coordinate); break;
            default:    return false;
        }
        return true;
    }
}
