package Display;

import java.util.Scanner;

import Characters.Personnage;
import Characters.Players;
import Characters.Robot;
import Environnement.Map;
import Objects.Fruits;
import Objects.Items;
import Objects.Snake;

public class Test2 {
    private static Scanner scanner = new Scanner(System.in);
    private static String name = new String();
    
    private static Map map;
 
    /**
     * <p> Cette fonction est uniquement destiné pour la classe
     * Players pour recuperer l'input dans le terminal.
     * @param scanner
     * @param player
     * @return il retourne int qui est le char en ascii
     */
    private static int getInput(Scanner scanner, Players player) {
        String value = new String();
        Integer input = null;

        do {
            value = scanner.nextLine();
            input = player.changeCoordinate(value);
        }while(player.getMouvement(input) == null);

        return input.intValue();
    }

    /**
     *  <p> print toute la map.
     * @param map
     */
    private static void printMap(Map map) {
        Object[][] mapObjects = map.getGrid(); 

        for (int i = 0; i<mapObjects.length; i++) {
            for(int k = 0; k<mapObjects[0].length; k++) {
                if (mapObjects[i][k] instanceof Items) System.out.print(((Items)mapObjects[i][k]).getName() + "  ");
                if (mapObjects[i][k] instanceof Fruits) System.out.print(((Fruits)mapObjects[i][k]).getName() + "  ");
                if (mapObjects[i][k] instanceof Snake) System.out.print(((Snake)mapObjects[i][k]).getName() + "  ");
            }
            System.out.println();
        }       
    }

    private static void printWall(Map map) {
        Object[][] mapObjects = map.getGrid(); 

        // for 
    }

    private static void printPersonnages(Personnage[] personnages) {
        for(int i = 0; i<personnages.length; i++) {
            int[] coordinate = personnages[i].getPrimaryCoordinate();
            System.out.println("Joueur " + (i+1) + " sont aux coordonnées : {" + coordinate[0] + "," + coordinate[1] + "}");
        }
    }
    
    /**
     * cette fonction clear le terminal et le remet en
     * haut a gauche de la ligne.
     */
    private static void clearTerminal() {
        System.out.println("\u001b[2J \u001b[H");
    }

    private static void placePersonnages(Personnage[] personnages) {
        for(Personnage personnage : personnages) {
            map.placePersonnages(personnage);
        }
    }

    private static boolean playerRound(Players player) {
        printMap(map);
        
        int[] latestCoordinate = player.keepLatestCoordinate();
        int input = getInput(scanner, player);
        player.moveCoordinate(input);

        if(map.isGameOver(input, player)) {
            System.out.println("GameOver");
            return false;
        }

        if(player.isIncreaseSize()) player.increaseSnake(latestCoordinate);

        clearTerminal();
        // map.clearMap();
        player.incrementRound();
        return true;
    }

    private static boolean robotRound(Robot robot) {
        return false;
    }

    private static boolean instancePersonnage(Personnage personnage) {
        if (personnage instanceof Players) {
            // tour du Player
            return playerRound((Players)personnage);   
        }

        if (personnage instanceof Robot) {
            // tour du robot
            return robotRound((Robot)personnage);
        }

        return false;
    }

    private static void sleepTerminal(long value) {
        try {Thread.sleep(value);}
        catch (InterruptedException e){ e.printStackTrace();}
    }

    public static void run(Personnage[] personnages, Map m, int n) {
        boolean isNotGameOver = true;
        map = m;
        
        // place les personnages dans la grille.
        placePersonnages(personnages);
        // print dans le terminal.
        printPersonnages(personnages);

        while(isNotGameOver) {
            for (int i = 0; i<personnages.length; i++) {
                System.out.println("\nJoueur " + (i+1) + " :");
                isNotGameOver = instancePersonnage(personnages[i]);
                if (isNotGameOver) placePersonnages(personnages); 
                else break;
            }
        }

        System.out.println("bien joué!");
    }
}
