package personnages;

import java.util.ArrayList;

import Item.Effects;
import Item.Items;

public class Personnage {
    /**
     * <p> taille du serpent.
     */
    private int size; 
    private int n; // N

    /**
     * <p> une variable pour changer le round pour chaque fois
     * que la manche est fini seulement pour le personnage.
     */
    private int round;

    /**
     * <p> la liste de toute les coordonnées en fonction de N. Si N = 2,
     * tout les deux tours, la taille du serpent augmente de 1. Si N = 3,
     * tous les 3 tours, ça va augmenter de 1. On peut l'ecrire comme Round/N
     * (les deux variables en int).
     * <p> Le premier index est la coordonnée de la tête et les autres 
     * sont les coordonnées de son corps.
     */
    protected ArrayList<int[]> coordinate;

    /**
     * <p> la liste est tout les effets cummulé par le Personnage.
     */
    private ArrayList<Effects> effects;

    /**
     * <p> le constructor definie un arrayList pour {@link #coordinate}
     * et defini n.
     * 
     * @param n est une variable qui contient le nombre de tour avant 
     * l'augmentation de taille.
     * @param coordinate est la variable qui contient les coordonnées 
     * qui sont placé par la suite dans {@link #coordinate}[0]
     */
    protected Personnage(int n, int[] coordinate) {
        this.effects = new ArrayList<Effects>();

        this.coordinate = new ArrayList<int[]>();
        this.coordinate.add(coordinate);

        this.n = n;
    }

    public int incrementRound() {
        return ++this.round;
    }
}
