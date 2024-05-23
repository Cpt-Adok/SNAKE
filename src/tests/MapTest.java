package tests;

import display.Display;
import environnements.Map;
import personnages.Player;
import types.Item;

public class MapTest {
    public static void creationMap() {
        Map map = new Map(30, 30);
        map.addObjects(Item.FRAISE, 29, 29);
        map.placeObjects();
        
        Display.printMap(map.addEdges());
    }

    public static void drawMap() {
        Map map = new Map(30, 30);
        Display.printMap(map.addEdges());
    }

    public static void placePersonnageMap() {
        Map map = new Map(30, 30);
        map.placePersonnages(new Player(new int[]{1, 1}, "null"));

        Display.printMap(map.addEdges());
    }

    public static void effects() {
        Map map = new Map(5, 1);
        Player player = new Player(new int[] {0, 0}, "null");
        map.addObjects(Item.FRAISE, 0, 0);

        player.applyEffects(map.getEffect(player.getHeadCoordinate()));
        System.out.println(player.getSize());
    }
}
