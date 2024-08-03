package personnage.IAQLearning;

import java.io.Serializable;
import java.util.Objects;

import personnage.types.Mouvement;

/**
 * Cette classe permet de sauvegarder chaque action que l'IA 
 * fait. Il est essentiel pour le bon fonctionnement du programme
 * car sans lui, aucun moyen que le programme puisse vérifier 
 * si dans son {@link QTable}, il contient les informations 
 * pour le bon fonctionnement. Elle peut être comparée à un 
 * <strong>tuple({@link State}, {@link Mouvement})</strong>.
 */
public class Actions implements Serializable {
    private static final long serialVersionUID = 1L; // la version du serializable

    /**
     * Cette variable prend l'état du processus, c'est lui qui
     * va sauvegarder <strong>la grille, la longueur du serpent
     * et ses coordonnées</strong>.
     */
    public State state;

    /**
     * Cette variable prend l'action que le serpent fait. Il 
     * peut prendre comme action : <strong>HAUT BAS GAUCHE
     * DROITE</strong>.
     */
    public Mouvement mouvement;
    
    /**
     * Le constructeur prend l'état et le mouvement de l'action
     * que le serpent fait.
     * @param state
     * @param mouvement
     */
    public Actions(State state, Mouvement mouvement) {
        this.mouvement = mouvement;
        this.state = state;
    }

    /**
     * Cette fonction prend en paramètre un objet et vérifie si
     * cette action est la même que celle en paramètre. Sinon, il retourne
     * false.
     * @param grid
     * @return <pre><code>
     * Si l'objet == la classe ou (objet.state == this.state et objet.mouvement == this.mouvement) -> 
     *      retourner true
     * Si l'objet == null ou la classe != la classe du paramètre ->
     *      retourner false
     * </code></pre>
     */
    @Override
    public boolean equals(Object grid) {
        if (this == grid) return true;
        if (grid == null || getClass() != grid.getClass()) return false;
        
        Actions action = (Actions) grid;
        return action.state.equals(this.state) && action.mouvement.equals(this.mouvement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, mouvement);
    }

    @Override
    public String toString() {
        return "Actions{state=" + state + ", mouvement=" + mouvement + '}';
    }
}
