import connexion.Channel;
import connexion.Reseau;
import environnements.*;
import game.Terminal;
import personnages.*;
import types.Item;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Personnage.n = 2;

        Map map = new Map(20, 20);

        Personnage[] personnages = new Personnage[] {
            new Player(new int[] {0, 0}, "Philippe Etchebest"),
        };

        
        // map.addObjects(Item.FRAISE, 0, 0);
        // map.addObjectsRandomize(new Item[] {Item.FRAISE}, 1);

        new Terminal(map, personnages).run(new String(), new String());
    }
}
