import Objets.Effects;
import Objets.Items;
import Display.Display;
import Environnements.Map;
import Personnages.Mouvements;
import Personnages.Personnage;
import Personnages.Player;

public class Tests {
    public static void avancerPersonnage() {
        Player player = new Player(new int[]{1, 1}, "test");

        player.moveSnake(Mouvements.HAUT); // si la position s'est avancé, c'est normal
        int[] coordinate = player.getHeadCoordinate(); 
        
        System.out.println(coordinate[0] + " " + coordinate[1]);
    }

    public static void taillePersonnage() {
        Personnage.n = 2;

        Player player = new Player(new int[]{1, 1}, "test");
        player.increaseRound();
        player.moveSnake(Mouvements.HAUT); // si il y a 2 valeurs, c'est normal

        for (int[] coordinate : player.getCoordinate()) {
            System.out.println(coordinate[0] + " " + coordinate[1]);
        }
    }

    public static void effectsPersonnage() {
        Personnage.n = 2;

        Player player = new Player(new int[]{1, 1}, "test");
        player.applyEffects(Effects.DECREASESIZE); // si c'est vide c'est que ça marche

        System.out.println(player.getCoordinate());
    }

    public static void creationMap() {
        Map map = new Map(30, 30);
        map.addObjects(Items.FRAISE, 29, 29);
        map.placeObjects();
        
        Display.printMap(map.addEdges());
    }

    public static void drawMap() {
        Map map = new Map(30, 30);
        Display.printMap(map.addEdges(), null);
    }

    public static void placePersonnageMap() {
        Map map = new Map(30, 30);
        map.placePersonnages(new Player(new int[]{1, 1}, "null"));

        Display.printMap(map.addEdges(), new Personnage[] {new Player(new int[]{1, 1}, "null")});
    }

    public static void effects() {
        Map map = new Map(5, 1);
        Player player = new Player(new int[] {0, 0}, "null");
        map.addObjects(Items.FRAISE, 1, 0);
    }
}