package Display;

import java.util.ArrayList;

import Environnements.Map;
import Objets.Items;
import Personnages.Personnage;
import Personnages.Snake;

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
    public static void printMap(Object[][] map) {
        for (Object[] object : map) {
            for (Object value : object) {
                System.out.print(value.toString() + " ");
            }
            System.out.println();
        }
    }

    /**
     * cette fonction print le snake dans le terminal avec des 
     * caracteres speciaux, (du utf-8).
     * @param map
     * @param personnages
     */
    public static void printMap(Object[][] map, Personnage[] personnages) {
        for (int i = 0; i<map.length; i++) {
            for(int k = 0; k<map[0].length; k++) {
                Object object = map[i][k];

                if(object instanceof Items) printItems((Items)object, map, k, i);
                if(object instanceof Snake) printSnake((Snake)object, personnages, k, i);
            }
            System.out.println();
        }
    }

    public static void printInformation(int round, int playerIndex, Personnage personnage) {
        int[] coordinate = personnage.getHeadCoordinate();

        System.out.println(
            "Round : " + round + " | N : " + Personnage.n + 
            "\n  Joueur " + (playerIndex+1) + " : " + personnage.getName() + " (" + 
            coordinate[0]+", "+ coordinate[1] +") | size : " + personnage.getSize()
        );
    }

    private static void printSnake(Snake item, Personnage[] personnages, int x, int y) {
        switch (item) {
            case BODY: System.out.print(" \u25A1 "); break;
            case HEAD: printHead(personnages, x, y); break;
        }
    }

    private static void printItems(Items item, Object[][] map, int x, int y) {
        switch (item) {
            case FRAISE: System.out.print(" \uF353 "); break;

            case WALL: printWall(map, x, y); break;
            case VOID: System.out.print("   "); break;
        }
    }


    private static void printWall(Object[][] map, int x, int y) {
        // TOP BOT LEFT RIGHT
        boolean[] position = new boolean[] {
            (y > 0) ? map[y - 1][x] == Items.WALL : false,
            (y < map.length - 1) ? map[y+1][x] == Items.WALL : false,
            (x > 0) ? map[y][x - 1] == Items.WALL : false,
            (x < map[y].length - 1) ? map[y][x + 1] == Items.WALL : false,
        };

        System.out.print(whichWall(position));
    }

    private static String whichWall(boolean[] isWall) {
        String positionWall = new String();

        if (isWall[0] && isWall[1] && isWall[2] && isWall[3]) positionWall = "\u2550\u256C\u2550";
        
        else if (isWall[0] && isWall[1] && isWall[3]) positionWall = "\u2560";
        else if (isWall[0] && isWall[1] && isWall[2]) positionWall = "\u2563";
        else if (isWall[0] && isWall[2] && isWall[3]) positionWall = "\u2550\u2569\u2550";
        else if (isWall[1] && isWall[2] && isWall[3]) positionWall = "\u2550\u2566\u2550";
        
        else if (isWall[0] && isWall[1]) positionWall = "\u2551";
        else if (isWall[2] && isWall[3]) positionWall = "\u2550\u2550\u2550";
        else if (isWall[0] && isWall[2]) positionWall = "\u255D   ";
        else if (isWall[0] && isWall[3]) positionWall = "\u255A";
        else if (isWall[1] && isWall[2]) positionWall = "\u2557   ";
        else if (isWall[1] && isWall[3]) positionWall = "\u2554";

        else positionWall = "\u2550\u256C\u2550";

        return positionWall;
    }

    private static void printHead(Personnage[] personnages, int x, int y) {
        Personnage personnage = searchSnake(personnages, x, y);

        if (personnage != null) {
            ArrayList<int[]> personnageCoordinate = personnage.getCoordinate();
        
        int[] primaryCoordinate = personnage.getHeadCoordinate();
        
        if (personnageCoordinate.size() > 1) {
            int[] secondCoordinate = personnageCoordinate.get(1);

            // UP DOWN LEFT RIGHT
            boolean[] isHead = new boolean[] {
                primaryCoordinate[0] == secondCoordinate[0] && primaryCoordinate[1] > secondCoordinate[1], // UP
                primaryCoordinate[0] == secondCoordinate[0] && primaryCoordinate[1] < secondCoordinate[1], // DOWN
                primaryCoordinate[1] == secondCoordinate[1] && primaryCoordinate[0] > secondCoordinate[0], // LEFT
                primaryCoordinate[1] == secondCoordinate[1] && primaryCoordinate[0] < secondCoordinate[0] // RIGHT
            };

            if (isHead[0]) {System.out.print(" \u21E9 ");return;} 
            else if (isHead[1]) {System.out.print(" \u21E7 ");return;} 
            else if (isHead[2]) {System.out.print("\u21E8  ");return;} 
            else if (isHead[3]) {System.out.print("  \u21E6");return;} 
        }
        }
        System.out.print(" \u25CF ");
    }

    private static Personnage searchSnake(Personnage[] personnages, int x, int y) {
        for (Personnage personnage : personnages) {
            int[] primaryCoordinate = personnage.getHeadCoordinate();
            
            int primaryY = primaryCoordinate[1] + 1;
            int primaryX = primaryCoordinate[0] + 1;

            if (primaryX == x && primaryY == y) {
                return personnage;
            }
        }
        return null;
    }
}
