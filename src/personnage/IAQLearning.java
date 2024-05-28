package personnage;

import java.util.UUID;

import IA.*;
import connexion.Channel;
import environnement.Grid;
import environnement.Map;
import types.Mouvement;

/**
 * La classe IAQLearning représente un joueur contrôlé par une Intelligence Artificielle
 * utilisant l'algorithme de Q-learning pour prendre des décisions dans un jeu.
 */
public class IAQLearning extends Personnage {
    private QLearning qLearning; // L'algorithme de Q-learning utilisé par l'IA.

    /** 
     * @param coordinate Les coordonnées initiales de l'IA sur la grille de jeu.
     * @param qTable La table Q utilisée par l'IA pour stocker les valeurs Q.
     * @param alpha Le taux d'apprentissage de l'algorithme de Q-learning.
     * @param gamma Le facteur de récompense future de l'algorithme de Q-learning.
     * @param epsilon Le taux d'exploration de l'algorithme de Q-learning.
     */
    public IAQLearning(int[] coordinate, QTable qTable, double alpha, double gamma, double epsilon) {
        super(coordinate); // Appel au constructeur de la classe mère.

        // Initialisation de l'algorithme de Q-learning avec les paramètres spécifiés.
        this.qLearning = new QLearning(qTable, alpha, gamma, epsilon);

        // Attribution d'un nom unique à l'IA.
        this.name = "IA : " + UUID.randomUUID();
    }

    /**
     * ce constructeur est uniquement pour lancer le programme en tant que joueur,
     * il n'apprendra rien du tout dans cette configuration.
     * @param coordinate
     * @param qTable
     */
    public IAQLearning(int[] coordinate, QTable qTable) {
        super(coordinate); // Appel au constructeur de la classe mère.
        this.qLearning = new QLearning(qTable, 0.0, 0.0, 0.0);
        this.name = "IA : " + UUID.randomUUID();
    }

    /**
     * cette classe obtient l'état actuel de l'IA à partir de la grille de jeu.
     * 
     * @param grid La grille de jeu.
     * @return L'état actuel de l'IA.
     */
    public State getCurrentState(Grid[][] grid) {
        return new State(grid, this.getCoordinate());
    }

    /**
     * cette classe sélectionne le meilleur mouvement à effectuer dans l'état actuel selon l'algorithme de Q-learning.
     * 
     * @param state L'état actuel de l'IA.
     * @return Le meilleur mouvement à effectuer.
     */
    public Mouvement bestMouvement(State state) {
        return qLearning.chooseMouvements(state);
    }

    /**
     * cette classe reçoit une récompense pour l'action effectuée dans l'état actuel et met à jour la valeur Q correspondante.
     * 
     * @param state L'état actuel de l'IA.
     * @param mouvement Le mouvement effectué dans l'état actuel.
     * @param reward La récompense reçue pour l'action effectuée.
     * @param nextState L'état suivant vers lequel l'IA se déplace après avoir effectué l'action.
     */
    public void receiveReward(State state, Mouvement mouvement, double reward, State nextState) {
        qLearning.updateQValue(state, mouvement, reward, nextState);
    }

    @Override
    public boolean round(Map map, String channel) {
        Mouvement mouvement = this.bestMouvement(this.getCurrentState(map.getGrid()));
        this.moveSnake(mouvement);

        int[] coordinate = this.getHeadCoordinate();

        if (channel != null) Channel.envoyerMessage(mouvement);
        if(map.isGameOver(coordinate) || this.applyEffects(map.getEffect(coordinate))) return true;
        map.deleteItems(coordinate);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.increaseRound();
        return false;
    }
}