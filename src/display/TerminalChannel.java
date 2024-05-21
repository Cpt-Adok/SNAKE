package display;

import java.util.Scanner;

import Characters.*;
import Connexion.Reseau;
import Environnement.*;

public class TerminalChannel {
    private static Map map;
    private static Personnage[] personnages;
    public int round = 0;
    public Reseau reseau;

    public static boolean edges = false;

    public TerminalChannel(Map m, Personnage[] personnage, Reseau r) {
        personnages = personnage;
        map = m;
        this.reseau=r;

        run();
        if (edges) map.addEdges();
    }

    private static void placePersonnages(Personnage[] personnages) {
        for(Personnage personnage : personnages) {
            map.placePersonnages(personnage);
        }
    }

    public int getInput(Reseau r){
        char c=r.getLastedContent().charAt(0);
        int i=c;
        return i;
    }

    /**
     * <p> Cette fonction est uniquement destiné pour la classe
     * Players pour recuperer l'input dans le terminal.
     * @param scanner
     * @param player
     * @return il retourne int qui est le char en ascii
     */

    public boolean playerRound(Players player) {
        TerminalDisplay.printMap(map, personnages);
        // TerminalDisplay.printMapType(map);
        
        int[] latestCoordinate = player.keepLatestCoordinate();
        int input=getInput(null);
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

    private boolean instancePersonnage(Personnage personnage) {
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

    public boolean run() {
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
        return false;
    }
}

