package personnages;

import object.*;

/**
 * la classe Player a comme classe parent {@link Personnage}
 * et qui contient tout les besoins primaire pour le bon 
 * fonctionnement de la classe Player. cette classse est très
 * utile pour qu'un humain puisse jouer.
 */
public class Player extends Personnage {
    /**
     * la classe Player a comme classe parent {@link Personnage}
     * et qui contient tout les besoins primaire pour le bon 
     * fonctionnement de la classe Player. Il comporte les coordonnées 
     * initiales pour placer correctement le personnage dans la grille 
     * du jeu.
     * @param coordinate
     * @param name
     */
    public Player(int[] coordinate, String name) {
        super(coordinate);

        this.name = name;
    }

    public boolean moveCoordinate(int keys) {
        Mouvements value = getMouvement(keys);
        
        if (value != null) {
            moveSnake(value);
            return true;
        }
        return false;
    }

    public Mouvements getMouvement(Integer keys) {
        switch (keys) {
            case 0x77: case 0x7A:      return Mouvements.HAUT;    // w ou z
            case 0x73:                 return Mouvements.BAS;     // s
            case 0x61: case 0x71:      return Mouvements.GAUCHE;  // a ou q
            case 0x64:                 return Mouvements.DROITE;  // d
            case null:                 return null;
            default:                   return null;
        }
    }
}
