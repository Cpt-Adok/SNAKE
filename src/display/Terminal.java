package display;

import java.util.Scanner;

import environnements.Map;
import item.Items;
import personnages.Personnage;
import personnages.Player;

public class Terminal {
    private static void clearTerminal() {
        System.out.println("\u001b[2J \u001b[H");
    }

    private static void showMap(Object[][] grid) {
        for (int i = 0; i<grid.length; i++) {
            for(int k = 0; k<grid[0].length; k++) {
                System.out.print(((Items)grid[i][k]).getName() + "  ");
            }
            System.out.println();
        }
    }

    private static void getInput(Scanner scanner, Player player) {
        String value = new String();

        do {
            value = scanner.nextLine();
        } while(!player.changeCoordinate(value));
    }

    private static boolean isGameOver(Personnage[] personnages, Map map) {
        boolean gameover = false;
        for(Personnage personnage : personnages) {
            if (gameover = map.isGameOver(personnage.getCoordinate())) {
                return true;
            }
        }
        return gameover;
    }

    public static void run(Personnage[] personnages, Map map, long n) {
        Scanner scanner = new Scanner(System.in);

        for(Personnage personnage : personnages) {
            map.addCoordinate(personnage.getCoordinate(), Items.HEAD);
        }
        
        showMap(map.getGrid());

        System.out.println(map.isGameOver(personnages[0].getCoordinate()));

        while (!isGameOver(personnages, map)) {
            for(Personnage personnage : personnages) {
                getInput(scanner, (Player)personnage);
                map.addCoordinate(((Player)personnage).getCoordinate(), Items.HEAD);
            }
            
            map.cleanGrid();
            clearTerminal();
        }
    }
}
