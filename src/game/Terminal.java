package game;

import display.Display;
import environnements.Map;
import personnages.Personnage;

public class Terminal {
    Map map;
    Personnage[] personnages;

    private void placePersonnages() {
        for(Personnage personnage : personnages) {
            map.placePersonnages(personnage);
        }
    }

    public Terminal(Map map, Personnage[] personnages) {
        this.personnages = personnages;
        this.map = map;
    }

    public void run() {
        int i = 0;

        while(true) {
            for(Personnage personnage : personnages) {
                Display.clearTerminal();

                map.placeObjects();
                placePersonnages();

                Display.printInformation(i++, personnage);
                Display.printMap(map.addEdges());
                
                if(personnage.round(map)) {
                    Display.clearTerminal();
                    System.out.println(personnage.getName() + " à perdu!");
                    return;
                }
                map.clearMap();
            }
        }
    }
}