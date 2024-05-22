import environnements.Map;
import game.Terminal;
import personnages.Personnage;
import personnages.Player;
import tests.*;

public class Main {
    public static void main(String[] args) {
        Personnage.n = 2;

        Personnage[] personnages = new Personnage[] {
            new Player(new int[] {0, 0}, "Philippe Etchebest"),
            new Player(new int[] {19, 19}, "Luke Skywalker")
        };

        Map map = new Map(20, 20);

        new Terminal(map, personnages);
    }
}
