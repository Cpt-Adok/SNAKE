package tests;

import object.Effects;
import object.Mouvements;
import personnages.Personnage;
import personnages.Player;

public class PersonnageTest {
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
}
