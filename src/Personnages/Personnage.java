package Personnages;

import java.util.ArrayList;

import Objets.Effects;

/**
 * Cette classe est la primitive des classes
 * {@link Player} et {@link Robot}. Elle contient
 * tout le necessaire pour tout les personnages
 * jouable du jeu.
 */
public class Personnage {
    /**
     * cette variable contient la valeur dans laquelle on sait 
     * quand le corps du snake grandi. il est le même pour tout
     * les personnages du jeu et c'est pour ça que c'est un 
     * static. on peut le changer directement dans le main en 
     * faisant la commande par exemple : <pre><code>
     * Personnage.n = 2</code></pre> sans appeler
     * player ou robot, les deux valeurs vont "automatiquement" 
     * changer.
     */
    public static int n;

    /**
     * cette variable est le nombre de round fait par le snake, 
     * il s'incremente tout les tours quand le snake avance.
     */
    private int round;

    /**
     * cette variable est le nom du snake présent dans le tournois,
     * il peut etre un mot aléatoire ou quelque chose de personnalisé
     */
    protected String name; 

    /**
     * cette variable accumule toute les coordonnées du serpent, elle 
     * contient le corps et la tête et la première coordonnées est la tête.
     * Le programme peut ajouter des coordonnées en fonction de round et n, 
     * exemple :
     * <pre><code>
     * si n > 0 et round > 0 -> 
     *    retourner round%n == 0
     * sinon
     *    retourner faux
     * </code></pre> 
     */
    private ArrayList<int[]> coordinate;

    /**
     * Le constructor initialise la variable {@link
     * #coordinate} et prend en paramètre <strong>
     * personnageCoordinate</strong> qui est la tête
     * de notre snake. Il place dans la liste {@link
     * #coordinate} la coordonnée. Nous pourrons 
     * recuperer les coordonnées de la tête en 
     * utilisant la fonction {@link #getHeadCoordinate()}.
     */
    protected Personnage(int[] personnageCoordinate) {
        coordinate = new ArrayList<>();
        coordinate.add(personnageCoordinate);
    }

    /**
     * cette fonction applique tout les effets defini dans
     * {@link Effects} et en même temps, sert de fermeture
     * du programme avec le boolean si l'effet choisi est 
     * IMPASSABLE.
     * @param effect est la variable qui contient tout les
     * effets de {@link Effects}
     * @return <pre><code>
     * si effect == IMPASSABLE ->
     *      retourner true
     *sinon
     *      retourner false
     * </code></pre>
     */
    public boolean applyEffects(Effects effect) {
        switch (effect) {
            case DECREASESIZE: this.coordinate.removeLast(); break;
            
            case VOID: break;
            case IMPASSABLE: return true;
        }

        return false;
    }

    /**
     * cette fonction renvoie la tête du snake mais en clone
     * donc elle fait une copie de la position existante pour
     * eviter de prendre l'adresse mémoire de la liste et pouvoir 
     * utiliser celle là comme on veut sans affecter la liste.
     */
    public int[] getHeadCoordinate() {
        return this.coordinate.getFirst().clone();
    }

    /**
     * cette fonction renvoie la queue du snake mais en clone
     * donc elle fait une copie de la position existante pour
     * eviter de prendre l'adresse mémoire de la liste et pouvoir 
     * utiliser celle là comme on veut sans affecter la liste.
     */
    public int[] getLatestCoordinate() {
        return this.coordinate.getLast().clone();
    }

    /**
     * cette fonction renvoie un clone de la liste de coordonnée
     * donc elle fait une copie des positions existante pour
     * eviter de prendre l'adresse mémoire de l'ArrayList et pouvoir 
     * utiliser celle là comme on veut sans affecter l'ArrayList.
     */
    public ArrayList<int[]> getCoordinate() {
        return new ArrayList<>(this.coordinate);
    }

    /**
     * cette fonction incremente la variable {@link #round}.
     */
    public void increaseRound() {
        ++this.round;
    }

    /**
     * cette fonction renvoie la variable {@link #round}.
     */
    public int getRound() {
        return round;
    }

    /**
     * cette fonction renvoie la variable {@link #name}.
     */
    public String getName() {
        return name;
    }
    
    /**
     * cette fonction renvoie la longueur de {@link #getCoordinate()}.
     */
    public int getSize() {
        return this.getCoordinate().size();
    }

    /**
     * cette fonction change tout les coordonnées du serpent en mettant 
     * la coordonnée de celui d'avant, il prend [1, 2, 3, 4] et fais en
     * premier temps [1, 1, 2, 3] puis modifie la premiere coordonnée
     * avec Mouvement, si il y a -1, ça fait [0, 1, 2, 3].
     * @param mouvement est le mouvement du personnage, comme HAUT, BAS,
     * GAUCHE et DROITE.
     */
    public void moveSnake(Mouvements mouvement) {
        int[] latestCoordinate = getLatestCoordinate();

        for (int i = this.coordinate.size() - 1; i > 0; i--) {
            this.coordinate.set(i, this.coordinate.get(i - 1).clone());
        }

        mouvement.updateCoordinate(this.coordinate.getFirst());
        increaseSnake(latestCoordinate);
    }

    /**
     * ajoute la coordonnée mis en paramètre dans le dernier emplacement si
     * round > 0 et n > 0 et round%n = 0.
     */
    private void increaseSnake(int[] coordinate) {
        if(round > 0 && n > 0) if (round%n == 0) this.coordinate.add(coordinate);
    }
}
