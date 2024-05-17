import Display.Terminal;
import Environnements.Map;
import Objets.Items;
import Personnages.Personnage;
import Personnages.Player;

public class Main {
    public static void main(String[] args) {
        Personnage.n = 2;

        Map map = new Map(12, 30);

        map.addObjects(Items.FRAISE, 5, 5);

        Personnage[] personnages = new Personnage[] {
            new Player(new int[] {0, 0}, "Phillipe")
        };

        new Terminal(map, personnages);
    }
}
