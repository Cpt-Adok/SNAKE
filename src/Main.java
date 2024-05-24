import java.io.File;

import IA.QTable;
import environnements.*;
import game.Terminal;
import personnages.*;

public class Main {
    public static void main(String[] args) {
        Personnage.n = 2;

        Map map = new Map(20, 20);

        // lancer en local
        if (args.length < 2) {
            Grid[][] grid = map.getGrid();
            QTable qTable = new QTable();
            qTable.getValues("res" + File.separator + "save" + File.separator + "learn.ser");


            // Avant de jouer contre l'ia, vous pouvez essayer de l'entrainer avec la fonction tests.IATest.learnIAvsIA()
            // il jouera avec lui meme et mettra les sauvegardes dans le dossier learn.ser, 

            // Attention lors de l'apprentissage, ne pas couper le processus sinon vous allez perdre toute vos donnees
            Personnage[] personnages = new Personnage[] {
                // new IAQLearning(new int[] {0, 0}, qTable),
                new Player(new int[] {0, 0}, "Philippe Etchebest"),
                new Player(new int[] {grid[0].length - 1, grid.length - 1}, "Luke Skywalker")
            };

            // map.addObjectsRandomize(new Item[] {Item.FRAISE, Item.WALL}, 2);
            // map.addObjects(Item.FRAISE, 2, 2);

            new Terminal(map, personnages).run();
        } 
        // lancer en ligne
        else {
            Personnage[] personnages = new Personnage[] {
                new Player(new int[] {0, 0}, "Philippe Etchebest"),
            };

            new Terminal(map, personnages).run(args[0], args[1]);
        }

        // tests.IATest.learnIA();
        // tests.IATest.learnIAvsIA();
    }
}
