package Characters;

import java.util.ArrayList;
import java.util.Arrays;

import Objects.Snake;

/**
 * Cet classe est la classe precurseur de tout les heritage pour creer un 
 * personnage jouable.
 */
public class Personnage {
    private int n;
    private int round;
    private int size = 0;

    /**
     * <p> la liste de toute les coordonnées en fonction de N. Si N = 2,
     * tout les deux tours, la taille du serpent augmente de 1. Si N = 3,
     * tous les 3 tours, ça va augmenter de 1. On peut l'ecrire comme 
     * Round/N (les deux variables en int).
     * <p> Le premier index est la coordonnée de la tête et les autres 
     * sont les coordonnées de son corps.
     */
    private ArrayList<int[]> coordinate;
    
    /**
     * <p> le constructor definie un arrayList pour {@link #coordinate}
     * et defini n.
     * 
     * @param n est une variable qui contient le nombre de tour avant 
     * l'augmentation de taille.
     * @param coordinate est la variable qui contient les coordonnées 
     * qui sont placé par la suite dans {@link #coordinate}[0]}
     */
    protected Personnage(int n, int[] coordinate) {
        this.coordinate = new ArrayList<int[]>();
        this.coordinate.add(coordinate);

        this.n = n;
    } 

    /**
     * <p> cette fonction retourne la premiere coordonnée de la liste 
     * {@link #coordinate} qui la tête du personnage.
     * @return la tête du personnage.
     */
    public int[] getPrimaryCoordinate() {
        return coordinate.get(0);
    }

    /**
     * <p> cette fonction retourne toute la liste 
     * {@link #coordinate} de coordonnée du serpent.
     * @return toute la liste des coordonnées du serpent
     */
    public ArrayList<int[]> getCoordinate() {
        return coordinate;
    }

    /**
     * @return augmente le tour après que le personnage a jouer
     */
    public int incrementRound() {
        return ++this.round;
    }
    
    /**
     * @return retourn un bool pour savoir si la taille s'est 
     * aggrandi 
     */
    public boolean isIncreaseSize() {
        int size = this.round/n;
        if (this.size < size) {
            this.size = size;
            return true;
        }
        return false;
    }

    /**
     * @return retourn un bool pour savoir si la taille s'est 
     * retreci.
     */
    public boolean isDecreaseSize() {
        int size = this.round/n;
        if (this.size > size) {
            this.size = size;
            return true;
        }
        return false;
    }

    /**
     * <p> cette fonction est très pratique aggrandir le serpent 
     * car elle garde la derniere coordonnée et si on la fusionne
     * avec {@link #increaseSnake()}, on peut l'utiliser
     * ajouter la coordonnée pour justement l'aggrandir. 
     * @return garde la derniere coordonnée du serpent (sa queue)
     */
    public int[] keepLatestCoordinate() {
        return this.coordinate.get(getCoordinate().size()-1).clone();
    }

    /**
     * <p> ajoute au dernier index, la valeur en parametre, très utile
     * en combinant avec {@link #keepLatestCoordinate()} pour aggrandir
     * le serpent.
     * @param coordinate ajout de la nouvelle coordonnée
     */
    public void increaseSnake(int[] coordinate) {
        this.coordinate.add(coordinate);
    }

    /**
     * <p> modifie toute la liste {@link #coordinate} pour deplacer tout le
     * serpent.
     * @param mouvements le mouvement utilisé pour deplacer le serpent
     */
    public void moveSnake(Mouvements mouvements) {
        mouvements.editCoordinate(this.coordinate.get(0));

        for(int i = this.coordinate.size() - 1; i>1; i--) {
            int[] value = this.coordinate.get(i-1);
            this.coordinate.set(i, value);
        }
    }   
    
    public Mouvements getMouvement(Integer keys) {
        switch (keys) {
            case 0x77:      return Mouvements.HAUT;    // w
            case 0x73:      return Mouvements.BAS;     // s
            case 0x61:      return Mouvements.GAUCHE;  // a
            case 0x64:      return Mouvements.DROITE;  // d
            case null:      return null;
            default:        return null;
        }
    }
}
