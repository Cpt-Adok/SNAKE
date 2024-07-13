package game;

import connexion.Channel;
import display.Display;
import environnement.Map;
import personnage.Personnage;


/**
 * cette classe permet en tout de tout lancer dans le jeu,
 * un tournoi local ou un tournoi multijoueur en ligne grâce
 * au site donnée par l'université.
 * 
 * <p> Pour lancer en local, vous devez juste lancer le programme 
 * en faisant :
 * 
 * <pre><code> 
 * new Terminal(new Map(...), new Personnage[] {...}).run();
 * </code></pre>
 * 
 * Pour lancer en Multijoueur, vous devez lancer en faisant :
 * 
 * <pre><code> 
 * new Terminal(new Map(...), new Personnage[] {...}).run("channeluser", "channeladversaire"); 
 * </code></pre>
 */
public class Terminal {
    Map map;
    Personnage[] personnages;

    private void placePersonnages(Personnage[] personnages) {
        for(Personnage personnage : personnages) {
            map.placePersonnages(personnage);
        }
    }

    /** 
     * <p> Pour lancer en local, vous devez juste lancer le programme 
     * en faisant :
     * 
     * <pre><code> 
     * new Terminal(new Map(...), new Personnage[] {...}).run();
     * </code></pre>
     * 
     * Pour lancer en Multijoueur, vous devez lancer en faisant :
     * 
     * <pre><code> 
     * new Terminal(new Map(...), new Personnage[] {...}).run("channeluser", "channeladversaire"); 
     * </code></pre>
     */
    public Terminal(Map map, Personnage[] personnages) {
        this.personnages = personnages;
        this.map = map;
    }

    public void run(String channel, String channelAdversaire) {
        Personnage[] personnageChannel = new Personnage[] {
            personnages[0], 
            new Channel(map, channel, channelAdversaire)
        };

        loop(personnageChannel, channel);
    }

    public void run() {
        loop(personnages, null);
    }

    private void loop(Personnage[] personnages, String channel) {
        while(true) {
            for(Personnage personnage : personnages) {
                Display.clearTerminal();

                map.placeObjects();
                placePersonnages(personnages);

                Display.printInformation(personnage);
                Display.printMap(map.addEdges());
                
                if(personnage.round(map, channel)) {
                    Display.clearTerminal();
                    System.out.println(personnage.getName() + " à perdu!");
                    Display.resetRound();
                    return;
                }
                map.clearMap();
            }
        }
    }
}