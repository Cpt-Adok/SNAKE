package game;

import connexion.Channel;
import display.Display;
import environnements.Map;
import personnages.Personnage;


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
        int i = 0;
        Personnage[] personnageChannel = new Personnage[] {
            personnages[0], 
            new Channel(map, channel, channelAdversaire)
        };

        while(true) {
            for(Personnage personnage : personnageChannel) {
                Display.clearTerminal();

                map.placeObjects();
                placePersonnages(personnageChannel);

                Display.printInformation(i++, personnage);
                Display.printMap(map.addEdges());
                
                if(personnage.round(map, channel)) {
                    Display.clearTerminal();
                    System.out.println(personnage.getName() + " à perdu!");
                    return;
                }
                map.clearMap();
            }
        }
    }

    public void run() {
        int i = 0;

        while(true) {
            for(Personnage personnage : personnages) {
                Display.clearTerminal();

                map.placeObjects();
                placePersonnages(personnages);

                Display.printInformation(i++, personnage);
                Display.printMap(map.addEdges());
                
                if(personnage.round(map, null)) {
                    Display.clearTerminal();
                    System.out.println(personnage.getName() + " à perdu!");
                    return;
                }
                map.clearMap();
            }
        }
    }
}