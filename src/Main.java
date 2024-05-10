import Characters.*;
import Display.Terminal;
import Environnement.Map;

public class Main {
    // public static void print(Players player) {
    //     for (int[] coordinate : player.getCoordinate()) {
    //         for(int value : coordinate) {
    //             System.out.print(value + "      ");
    //         }
    //         System.out.println();
    //     }
    //     System.out.println();
    // }

    public static void main(String[] args) {
        // Players player = new Players(new int[] {-1, 1}, 2);

        // while(true) {
        //     int[] value = player.keepLatestCoordinate();

        //     player.incrementRound();
        //     print(player);
        //     if (player.isIncreaseSize()) player.moveToLatestCoordinate(value);
        // }

        Terminal.run(new Personnage[]{
            new Players(new int[] {1, 1}, 2)
        }, new Map(10, 10), 2);
    }
}
