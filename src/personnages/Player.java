package personnages;

import static java.util.Arrays.toString;

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
    public Player(String name, int n, int[] coordinate) {
        super(name, n, coordinate);
    }

    public boolean changeCoordinate(String input) {
        if (input.length() > 0) {
            return moveCoordinate(input.charAt(0));
        }
        return false;
    }
    
    private boolean moveCoordinate(int keys) {
        switch (keys) {
            case 0x77:    Mouvement.HAUT.editCoordinate(getCoordinate()); break;    // w
            case 0x73:    Mouvement.BAS.editCoordinate(getCoordinate()); break;     // s
            case 0x61:    Mouvement.GAUCHE.editCoordinate(getCoordinate()); break;  // a
            case 0x64:    Mouvement.DROITE.editCoordinate(getCoordinate()); break;  // d
            default:      return false;
        }
        return true;
    }
}
