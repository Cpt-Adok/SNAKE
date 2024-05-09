package personnages;

import java.util.Scanner;

/**
 * <p>la classe Player est la classe principale pour l'utilisation de toute 
 * input et output que l'utilisateur peut faire. 
 */
public class Player extends Personnage {
    /**
     * <p>le constructor definie les coordonnées de la tête et defini n.
     * 
     * @param n est une variable qui contient le nombre detour avant 
     * l'augmentation de taille.
     * @param coordinate est un array de <strong>2 entiers</strong> qui 
     * est représenté comme <strong>{x, y}</strong> et qui represente les 
     * coordonnées de la tête du personnage.
     */
    public Player(int n, int[] coordinate) {
        super(n, coordinate);
    }

    public void changeCoordinate() {
        Scanner scanner = new Scanner(System.in);
        String value;

        do {
            value = scanner.nextLine();
        } while (!moveCoordinate((int)'w'));
        
        scanner.close();
    }

    public int[] getCoordinate() {
        return coordinate.get(0);
    }

    private boolean moveCoordinate(int keys) {
        switch (keys) {
            case 119:    Mouvement.HAUT.editCoordinate(getCoordinate()); break;
            case 115:    Mouvement.BAS.editCoordinate(getCoordinate()); break;
            case 97:     Mouvement.GAUCHE.editCoordinate(getCoordinate()); break;
            case 100:    Mouvement.DROITE.editCoordinate(getCoordinate()); break;
            default:    return false;
        }
        return true;
    }
}
