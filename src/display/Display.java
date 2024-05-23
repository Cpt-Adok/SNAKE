package display;

import environnements.Grid;
import personnages.Personnage;
import types.Item;

public class Display {
    /**
     * cette fonction clear le terminal et le remet en
     * haut a gauche de la ligne.
     */
    public static void clearTerminal() {
        System.out.println("\u001b[2J \u001b[H");
    }

    /**
     * cette fonction dessine la grille avec des noms 
     * pour chaque items. 
     */
    public static void printMapName(Grid[][] map) {
        for(Grid[] mapLine : map) {
            for(Grid mapValue : mapLine) {
                System.out.print(" " + mapValue.getName());
            }
            System.out.println();
        }
    }

    public static void printMap(Grid[][] map) {
        for (int i = 0; i<map.length; i++) {
            for(int k = 0; k<map[0].length; k++) {
                if (map[i][k] == Item.WALL) printWall(map, k, i);
                print(map[i][k]);                    
            }
            System.out.println();
        }
    }

    private static void print(Grid gridTypes) {
        for(Grid value : gridTypes.getValues()) {
            if (value == gridTypes) System.out.print(value.getStringCode());
        }
    }

    private static void printWall(Grid[][] map, int x, int y) {
        // TOP BOT LEFT RIGHT
        boolean[] position = new boolean[] {
            (y > 0) ? map[y - 1][x] == Item.WALL : false,
            (y < map.length - 1) ? map[y+1][x] == Item.WALL : false,
            (x > 0) ? map[y][x - 1] == Item.WALL : false,
            (x < map[y].length - 1) ? map[y][x + 1] == Item.WALL : false,
        };

        for(Wall value : Wall.values()) {
            if(value.isEqual(position)) map[y][x].updateStringCode(value.getAscii());
        }
    }

    public static void printInformation(int round, Personnage personnage) {
        int[] coordinate = personnage.getHeadCoordinate();

        System.out.println(
            "Round : " + round + " | N : " + Personnage.n +"\n  Joueur : " + personnage.getName() + 
            " (" + coordinate[0]+", "+ coordinate[1] +") | size : " + personnage.getSize()
        );
    }
}
