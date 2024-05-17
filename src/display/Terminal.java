package Display;

import java.util.Scanner;

import Environnements.Map;
import Objets.Effects;
import Objets.Items;
import Personnages.Mouvements;
import Personnages.Personnage;
import Personnages.Player;
import Personnages.Robot;

public class Terminal {
    private int round = 0;

    private static Map map;
    private static Personnage[] personnages;

    private static Scanner scanner;


    public Terminal(Map m, Personnage[] personnage) {
        scanner = new Scanner(System.in);
        personnages = personnage;
        map = m;

        run();
    }

    /**
     * Cette fonction est uniquement destiné pour la classe
     * Players pour recuperer l'input dans le terminal.
     * @param player
     * @return il retourne int qui est le char en ascii
     */
    private static int getInput(Player player) {
        String value = new String();
        Integer input = null;

        do {
            value = scanner.nextLine();
            input = changeCoordinate(value);
        }while(player.getMouvement(input) == null);

        return input.intValue();
    }

    /**
     * transforme le String en prennant le premier char et 
     * le mets en ascii dans la classe Integer.
     * @param input
     * @return
     */
    private static Integer changeCoordinate(String input) {
        if (input.length() > 0) {
            return (int)input.charAt(0);
        }
        return null;
    }

    /**
     * place tout les personnages dans la fonction {@link #map.placePersonnages()}
     * @param personnages
     */
    private static void placePersonnages(Personnage[] personnages) {
        for(Personnage personnage : personnages) {
            map.placePersonnages(personnage);
        }
    }

    /**
     * cette fonction est spécialement conçu pour gerer le gameplay du joueur.
     * @param player
     * @return
     */
    private static boolean playerRound(Player player) {
        int input = getInput(player);
        player.moveCoordinate(input);

        int[] coordinate = player.getHeadCoordinate();
        if(map.isGameOver(player.getHeadCoordinate()) || player.applyEffects(map.getEffect(coordinate))) return true;

        player.increaseRound();
        return false;
    }

    /**
     * cette fonction est spécialement conçu pour gerer le gameplay du robot.
     * @param player
     * @return
     */
    private static boolean robotRound(Robot robot) {
        return false;
    }

    /**
     * cette fonction cherche si le personnage mis en paramètre 
     * est un {@link Player} ou un {@link Robot}.
     * @param player
     * @return
     */
    private static boolean instancePersonnage(Personnage personnage) {
        if (personnage instanceof Player) {
            // tour du Player
            return playerRound((Player)personnage);   
        }

        if (personnage instanceof Robot) {
            // tour du robot
            return robotRound((Robot)personnage);
        }

        return false;
    }

    /**
     * la fonction principal qui lance tout le jeu en terminal.
     */
    private void run() {
        boolean isGameOver = false;
        int i = 0;

        while(!isGameOver) {
            for (i = 0; i<personnages.length; i++) {
                Personnage personnage = personnages[i];
                Display.clearTerminal();

                map.placeObjects();
                placePersonnages(personnages);

                Display.printInformation(this.round, i, personnage);

                map.placeObjects();
                Display.printMap(map.addEdges(), personnages);

                isGameOver = instancePersonnage(personnage);
                if(!isGameOver) placePersonnages(personnages);
                else break;

                map.clearMap();
            }
            this.round++;
        }
        System.out.println("GAMEOVER \nLe joueur " + (i+1) + " à perdu !");
    }
}
