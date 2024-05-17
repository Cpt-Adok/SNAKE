import Characters.Personnage;
import Characters.Players;
import display.Terminal;
import Environnement.Map;

public class Main {
    public static void main(String[] args) {
        Personnage.n = 4;

        Map map = new Map(30, 30);

        Personnage[] personnages = new Personnage[] {
            new Players("Phillipe", new int[] {1, 1}),
            new Players("Edouard", new int[] {28, 28})
        };

        Terminal.edges = true;
        new Terminal(map, personnages);
    }
}
