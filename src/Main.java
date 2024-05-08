import java.lang.Object;

import Environnements.Map;
import Item.Items;

public class Main {
    public static void main(String[] args) {
        // Items[] items = {Items.BANANE, Items.FRAISE, Items.ORANGE};

        Map map = new Map(10, 10);
        
        map.ajoutBordure();
        map.addItems(new Items[]{Items.BANANE, Items.MUR, Items.FRAISE}, 3);

        System.out.println(map.getCoordinate(1, 1));
    }
}
