import java.lang.Object;
import java.util.function.Supplier;

import Environnements.Map;
import Item.Items;
import personnages.Player;

public class Main {
    public static void main(String[] args) {
        int n = 2;
        Map map = new Map(10, 10);

        Player player1 = new Player(n, new int[]{1, 1});
        Player player2 = new Player(n, new int[]{9, 9});

        while(!map.isGameOver(player1.getCoordinate()) || !map.isGameOver(player2.getCoordinate())) {
            player1.changeCoordinate();
        }
    }
}
