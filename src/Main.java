import Characters.Personnage;
import Characters.Players;
import Display.Terminal;
import Display.TerminalDisplay;
import Environnement.Map;
import Objects.Fruits;
import Objects.Items;

public class Main {
    public static void main(String[] args) {
        Personnage.n = 2;

        Map map = new Map(30, 30);

        Personnage[] personnages = new Personnage[] {
            new Players("d", new int[] {1, 1}),
            new Players("e", new int[] {28, 28})
        };

        Terminal.edges = true;
        new Terminal(map, personnages);
    }
}
