import environnements.Map;
import personnages.Personnage;
import personnages.Player;

public class Main {
    public static void main(String[] args) {
        int n = 2;
        
        Personnage[] players = new Personnage[] {
            new Player("player1", n, new int[]{2, 2})
        };

        display.Terminal.run(players, new Map(10, 10), 2);
    }
}
