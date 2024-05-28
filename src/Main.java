import java.io.File;

import IA.QTable;
import environnements.*;
import game.Terminal;
import personnages.*;

public class Main {
    public static void main(String[] args) {
        Personnage.n = 4;

        Map map = new Map(12, 22);

        // lancer en local
        if (args.length < 2) {
            Grid[][] grid = map.getGrid();

            // QTable qTable = new QTable();
            // qTable.getValues("res" + File.separator + "save" + File.separator + "learn.ser");

            // Avant de jouer contre l'ia, vous pouvez essayer de l'entrainer avec la fonction tests.IATest.learnIAvsIA()
            // il jouera avec lui meme et mettra les sauvegardes dans le dossier learn.ser, 

            // Attention lors de l'apprentissage, ne pas couper le processus sinon vous allez perdre toute vos donnees
            Personnage[] personnages = new Personnage[] {
                new Player(new int[] {2, 2}, "Philippe Etchebest"),
                new Player(new int[] {grid[0].length - 3, grid.length - 3}, "Luke Skywalker"),
                // new Robot("Robot", new int[] {grid[0].length - 3, grid.length - 3}),
                // new IAQLearning(new int[] {grid[0].length - 3, grid.length - 3),
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
    }
}
