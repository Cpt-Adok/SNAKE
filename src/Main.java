import java.lang.Object;
import java.util.function.Supplier;

import Environnements.Map;
import Item.Items;
import personnages.Player;

public class Main {
    public static void main(String[] args) {
        // Items[] items = {Items.BANANE, Items.FRAISE, Items.ORANGE};

        // Map map = new Map(10, 10);
        
        // map.ajoutBordure();
        // map.addItems(new Items[]{Items.BANANE, Items.MUR, Items.FRAISE}, 3);

        // System.out.println(map.getStringGrid());

        Player player = new Player(0, new int[]{1, 1});
        System.out.println("donne moi w s d q");
        player.changeCoordinate();
    

        System.out.println(player.getCoordinate()[0] + "  " + player.getCoordinate()[1]);
    }
}
