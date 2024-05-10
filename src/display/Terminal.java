package Display;

import java.util.Scanner;

import Characters.Mouvements;
import Characters.Personnage;
import Characters.Players;
import Environnement.Map;
import Objects.Fruits;
import Objects.Items;
import Objects.Snake;

public class Terminal {
    /**
     * cette fonction clear le terminal et le remet en
     * haut a gauche de la ligne.
     */
    private static void clearTerminal() {
        System.out.println("\u001b[2J \u001b[H");
    }

    private static int getInput(Scanner scanner, Players player) {
        String value = new String();
        int input = 0;

        do {
            value = scanner.nextLine();
        } while(player.getMouvement(input = player.changeCoordinate(value)) == null);
    
        return input;
    }

    private static void printMap(Map map) {
        Object[][] mapObjects = map.getGrid();        
    }

    public static void run(Personnage[] personnages, Map map, int n) {
        Scanner scanner = new Scanner(System.in);
        printMap(map);
        scanner.close();
    }
}
