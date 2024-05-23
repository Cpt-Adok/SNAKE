import environnements.*;
import game.Terminal;
import personnages.*;
import types.Item;

public class Main {
    public static void main(String[] args) {
        Personnage.n = 2;

        Personnage[] personnages = new Personnage[] {
            new Player(new int[] {0, 0}, "Philippe Etchebest"),
            new Player(new int[] {19, 19}, "Luke Skywalker")
        };

        Map map = new Map(20, 20);
        // map.addObjects(Item.FRAISE, 0, 0);
        // map.addObjectsRandomize(new Item[] {Item.FRAISE}, 1);

        new Terminal(map, personnages).run();
    }
}
