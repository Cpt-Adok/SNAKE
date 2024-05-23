package personnages;

import java.util.Scanner;

import connexion.Channel;
import environnements.Map;
import types.*;

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
        Mouvement value = getMouvement(keys);
        
        if (value != null) {
            moveSnake(value);
            return true;
        }
        return false;
    }

    public Mouvement getMouvement(Integer keys) {
        switch (keys) {
            case 0x77: case 0x7A:      return Mouvement.HAUT;    // w ou z
            case 0x73:                 return Mouvement.BAS;     // s
            case 0x61: case 0x71:      return Mouvement.GAUCHE;  // a ou q
            case 0x64:                 return Mouvement.DROITE;  // d
            case null:                 return null;
            default:                   return null;
        }
    }

    /**
     * transforme le String en prennant le premier char et 
     * le mets en ascii dans la classe Integer.
     * @param input
     * @return
     */
    private Integer changeCoordinate(String input) {
        if (input.length() > 0) {
            return (int)input.charAt(0);
        }
        return null;
    }

    /**
     * Cette fonction est uniquement destiné pour la classe
     * Players pour recuperer l'input dans le terminal.
     * @param player
     * @return il retourne int qui est le char en ascii
     */
    @SuppressWarnings("resource")
    private int getInput() {
        Scanner scanner = new Scanner(System.in);
        Integer input = null;

        while(this.getMouvement(input) == null) {
            input = this.changeCoordinate(scanner.nextLine());
        }

        // scanner.close();
        return input.intValue();
    }

    @Override
    public boolean round(Map map, String channel) {
        int keys = this.getInput();
        this.moveCoordinate(keys);

        int[] coordinate = this.getHeadCoordinate();
        if (channel != null) Channel.envoyerMessage(getMouvement(keys));
        if(map.isGameOver(coordinate) || this.applyEffects(map.getEffect(coordinate))) return true;
        map.deleteItems(coordinate);

        this.increaseRound();
        return false;
    }
}   
