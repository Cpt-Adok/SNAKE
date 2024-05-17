package display;

import Objects.Fruits;
import Objects.Items;
import Objects.Snake;

import java.util.ArrayList;

import Characters.Personnage;
import Environnement.Map;

public class TerminalDisplay {
    public static void printMap(Map map, Personnage[] personnages) {
        Object[][] mapObjects = map.getGrid(); 

        for (int i = 0; i<mapObjects.length; i++) {
            for(int k = 0; k<mapObjects[0].length; k++) {
                Object object = mapObjects[i][k];

                if(object instanceof Items)  printItems((Items)object, mapObjects, k, i);
                if(object instanceof Fruits) printFruits((Fruits)object, i, k);
                if(object instanceof Snake)  printSnake((Snake)object, personnages, i, k);
            }
            System.out.println();
        }
    }

    /**
     * cette fonction clear le terminal et le remet en
     * haut a gauche de la ligne.
     */
    public static void clearTerminal() {
        System.out.println("\u001b[2J \u001b[H");
    }


    public static void printMapType(Map map) {
        for (Object[] object : map.getGrid()) {
            for (Object value : object) {
                System.out.print(value.toString() + "      ");
            }
            System.out.println();
        }
    }

    private static void printItems(Items item, Object[][] map, int x, int y) {
        switch (item) {
            case WALL: printWall(map, x, y); break;
            case VOID: System.out.print("   "); break;
        }
    }

    private static void printSnake(Snake item, Personnage[] personnages, int x, int y) {
        switch (item) {
            case BODY: System.out.print(" = "); break;
            case HEAD: printHead(personnages, x, y); break;
        }
    }

    private static void printFruits(Fruits item, int x, int y) {
        switch (item) {
            case FRAISE: System.out.print(" \uF353 ");break;
        }
    }

    private static void printHead(Personnage[] personnages, int x, int y) {
        Personnage personnage = searchSnake(personnages, x, y);

        if (personnage != null) {
            ArrayList<int[]> personnageCoordinate = personnage.getCoordinate();
            
            int[] primaryCoordinate = personnage.getPrimaryCoordinate();
            
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
                else if (isHead[2]) {System.out.print(" \u21E8 ");return;} 
                else if (isHead[3]) {System.out.print(" \u21E6 ");return;} 
            }
        }
        System.out.print(" \u21E8 ");return;
    }



    private static Personnage searchSnake(Personnage[] personnages, int x, int y) {
        for (Personnage personnage : personnages) {
            int[] primaryCoordinate = personnage.getPrimaryCoordinate();
            
            int primaryY = primaryCoordinate[0];
            int primaryX = primaryCoordinate[1];

            if (primaryX == x && primaryY == y) {
                return personnage;
            }
        }
        return null;
    }

    private static void printWall(Object[][] map, int x, int y) {
        // UP DOWN LEFT RIGHT
        boolean[] isWall = new boolean[4];
    
        if (y > 0) isWall[0] = map[y - 1][x] == Items.WALL; else isWall[0] = false;
        if (y < map.length - 1) isWall[1] = map[y + 1][x] == Items.WALL; else isWall[1] = false;
        if (x > 0) isWall[2] = map[y][x - 1] == Items.WALL; else isWall[2] = false;
        if (x < map[0].length - 1) isWall[3] = map[y][x + 1] == Items.WALL; else isWall[3] = false;

        System.out.print(whichWall(isWall));
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
}
