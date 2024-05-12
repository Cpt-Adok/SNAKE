import Characters.Personnage;
import Characters.Players;
import Display.Terminal;
import Environnement.Map;

public class Main {
    public static void main(String[] args) {
        int N = 2;

        Personnage[] personnages = new Personnage[] {
            new Players(new int[] {1, 1}, N),
            new Players(new int[] {8, 8}, N)
        };

        Map map = new Map(10, 10);
        // map.addEdges();

        Terminal.run(personnages, map, N);

        // Players player = new Players(new int[]{1, 1}, 1);
        // player.increaseSnake(player.keepLatestCoordinate());
        // player.moveCoordinate(0x77);
        
        // for(int[] value : player.getCoordinate()) {
        //     for (int valueInt : value) {
        //         System.out.print(valueInt + "  ");
        //     }
        //     System.out.println();
        // }
    }
}
