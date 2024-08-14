import java.io.File;
import java.io.ObjectInputFilter.Config;
import java.util.ArrayList;

import configuration.ConfigGame;
import game.Terminal;
import game.environnement.*;
import personnage.*;
import personnage.IAQLearning.QTable;
import tests.IATest;

public class Main {
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
     *      new IAQLearning(new int[] {x, y}, class QTable)
     * 
     * Pour la QTable, il est préférable de créer une variable avec la
     * déclaration de la classe : 
     *      QTable qtable = new QTable();
     * 
     */

    public static void main(String[] args) {
        ConfigGame config = new ConfigGame(null);

        Personnage[] personnages = config.getPersonnages();
        Map map = config.getMap();
        Personnage.n = config.getN();

        if (args.length < 1) { new Terminal(map, personnages).run(); }                          // lancer en local
        else if (args.length == 2) { new Terminal(map, personnages).run(args[0], args[1]); }    // lancer en ligne
        else { System.err.println("WARNING: vous avez mis un mauvais nombre d'argument"); }   // erreur
    }
}
