import display.Display;
import environnements.*;
import game.Terminal;
import personnages.*;
import types.Item;

public class Main {
    public static void main(String[] args) {
        Personnage.n = 2;

        Map map = new Map(20, 20);

        // lancer en local
        if (args.length < 2) {
            Grid[][] grid = map.getGrid();

            Personnage[] personnages = new Personnage[] {
                new Player(new int[] {0, 0}, "Philippe Etchebest"),
                new Player(new int[] {grid[0].length - 1, grid.length - 1}, "Luke Skywalker")
            };

            map.addObjects(Item.WALL, 19, 7);
            map.addObjects(Item.WALL, 19, 6);
            map.placeObjects();
            Display.printMap(map.addEdges());
            Display.printMapName(grid);

            // new Terminal(map, personnages).run();
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
