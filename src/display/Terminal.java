package display;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import Characters.*;
import Environnement.*;
import Objects.*;

public class Terminal {
    private static Scanner scanner;
    private static Map map;
    private static Personnage[] personnages;
    public int round = 0;

    public static boolean edges = false;

    public Terminal(Map m, Personnage[] personnage) {
        scanner = new Scanner(System.in);
        personnages = personnage;
        map = m;

        run();
        if (edges) map.addEdges();
    }

    private static void placePersonnages(Personnage[] personnages) {
        for(Personnage personnage : personnages) {
            map.placePersonnages(personnage);
        }
    }

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

    private static boolean playerRound(Players player) {
        TerminalDisplay.printMap(map, personnages);
        // TerminalDisplay.printMapType(map);
        
        int[] latestCoordinate = player.keepLatestCoordinate();
        int input = getInput(scanner, player);
        player.moveCoordinate(input);

        if(map.isGameOver(input, player)) {TerminalDisplay.clearTerminal(); System.out.println("GameOver"); return false;}
        if(player.isIncreaseSize()) player.increaseSnake(latestCoordinate);

        TerminalDisplay.clearTerminal();
        map.clearMap(edges);
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

    private void run() {
        TerminalDisplay.clearTerminal();
        if (edges) map.addEdges();
        boolean isNotGameOver = true;
        int i = 0;

        // place les personnages dans la grille.
        placePersonnages(personnages);

        while(isNotGameOver) {
            for (i = 0; i<personnages.length; i++) {
                Personnage personnage = personnages[i];

                int[] coordinate = personnage.getPrimaryCoordinate();

                System.out.println("Round : " + this.round + " | N : " + Personnage.n);
                System.out.println("  Joueur " + (i+1) + " : " + personnage.getName() + 
                " (" + coordinate[0]+", "+ coordinate[1] +") | size : " + personnage.getSize());

                isNotGameOver = instancePersonnage(personnage);
                if(isNotGameOver) placePersonnages(personnages);
                else break;
            }
            this.round++;
        }
        System.out.println("Le joueur " + (i+1) + " à perdu !");
    }
}
