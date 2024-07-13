import java.io.File;

import IA.QTable;
import environnement.*;
import game.Terminal;
import personnage.*;
import tests.IATest;

public class Main {
    private static Map map = new Map(12, 22);

    /**
     * Dans ce jeu, il y a 3 types de personnage disponible :
     *      - Les Joueurs (Player)
     *      - Le robot (Robot)
     *      - L'ia (IAQLearning)
     * 
     * La classe Player est la seule classe jouable avec les commande :
     *        w        z
     *      a s d    q s d
     * 
     * Les 2 robots ne sont pas la même chose, l'un utilise des principes
     * de base et l'autre utilise l'apprentissage par renforcement (il faut 
     * lui apprendre avant qu'il puisse faire quoi que ce soit)
     * 
     * Vous pouvez tous les appeler en faisant:
     *      new Player(new int[] {x, y}, "name")
     *      new Robot("name", new int[] {x, y})
     *      new IAQLearning(new int[] {x, y}, classe QTable),
     * 
     */
    private static Personnage[] personnages = new Personnage[] {
        new Player(new int[] {2, 2}, "Philippe Etchebest"),
        new Player(new int[] {map.getGrid()[0].length - 3, map.getGrid().length - 3}, "Luke Skywalker")
    };

    public static void main(String[] args) {
        Personnage.n = 4;

        if (args.length < 1) { new Terminal(map, personnages).run(); }                          // lancer en local
        else if (args.length == 2) { new Terminal(map, personnages).run(args[0], args[1]); }    // lancer en ligne
        else { System.err.println("WARNING: vous avez mis un mauvais nombre d'argument"); }   // erreur
    }
}
