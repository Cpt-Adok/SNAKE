package Display;

import java.util.Scanner;

import Characters.Personnage;
import Characters.Players;
import Characters.Robot;
import Environnement.Map;
import Objects.Fruits;
import Objects.Items;
import Objects.Snake;

public class Test {
    private static Scanner scanner = new Scanner(System.in);
    private static String name = new String();

    /**
     * cette fonction clear le terminal et le remet en
     * haut a gauche de la ligne.
     */
    private static void clearTerminal() {
        System.out.println("\u001b[2J \u001b[H");
    }

    private static int getInput(Scanner scanner, Players player) {
        String value = new String();
        Integer input = null;

        do {
            value = scanner.nextLine();
            input = player.changeCoordinate(value);
        } while(player.getMouvement(input) == null);
    
        return input.intValue();
    }

    private static void printMap(Map map) {
        Object[][] mapObjects = map.getGrid(); 
        for (int i = 0; i<mapObjects.length; i++) {
            for(int k = 0; k<mapObjects[0].length; k++) {
                if (mapObjects[i][k] instanceof Items) System.out.print(((Items)mapObjects[i][k]).getName() + "  ");
                if (mapObjects[i][k] instanceof Snake) System.out.print(((Snake)mapObjects[i][k]).getName() + "  ");
                if (mapObjects[i][k] instanceof Fruits) System.out.print(((Fruits)mapObjects[i][k]).getName() + "  ");
            }
            System.out.println();
        }       
    }

    private static void playerRound(Players player) {
        int input = 0;
    }

    private static void robotRound(Robot robot) {

    }

    private static void instancePersonnage(Personnage personnage) {
        if (personnage instanceof Players) {
            // tour du Player
            Players player = (Players)personnage;
            playerRound(player);
        }

        if (personnage instanceof Robot) {
            // tour du robot
            Robot robot = (Robot)personnage;
            robotRound(robot);
        }
    }

    private static void placePersonnages(Personnage[] personnages, Map map) {
        for(Personnage personnage : personnages) {
            map.placePersonnages(personnage);
        }
    }

    public static void run(Personnage[] personnages, Map map, int n) {
        while (true) {
            for (int i = 0; i<personnages.length; i++) {
                Personnage personnage = personnages[i];

                // if (personnage instanceof Players) {
                //     Players player = (Players)personnage;
                //     int input = 0;
                //     printMap(map);
                //     input = getInput(scanner, player);

                    
                //     player.moveCoordinate(input);
                    
                //     // clearTerminal();
                //     map.clearMap();

                //     if(map.isGameOver(input, personnage)) {
                //         System.out.println("le Joueur " + i+1 + " à perdu.");
                //         return;
                //     }
                //     map.placePersonnages(personnage);
                    
                // }

                if (personnage instanceof Players) {
                    Players player = (Players)personnage;
                }

                if (personnage instanceof Robot) {
                    Robot robot = (Robot)personnage;
                    // tour du bot.
                }
            }

        }
    
        // Scanner scanner = new Scanner(System.in);

        // int input = 0;
        // Players player = (Players)personnages[0];
        // int[] value = player.keepLatestCoordinate();

        // map.placePersonnages(player);
        // input = getInput(scanner, player);
        // if (map.isGameOver(input, player)) {
        //     System.out.println("pas bien");
        //     return;
        // }
        // player.moveCoordinate(input);
        // player.increaseSnake(value);
        
        // map.placePersonnages(player);

        // if (map.isGameOver(input, player)) {
        //     System.out.println("pas bien");
        //     return;
        // }

        // printMap(map);
        // map.clearMap();

        // Players players = (Players)personnages[0];
        // map.placePersonnages(players);
        // printMap(map);
        // map.clearMap();
        // int[] value = players.keepLatestCoordinate();
        // System.out.println(value[0] + "  " + value[1]);
        // players.moveCoordinate(getInput(scanner, players));
        // players.moveToLatestCoordinate(value);
        // System.out.println(value[0] + "  " + value[1]);
        // map.placePersonnages(players);
        // printMap(map);
    }
}
