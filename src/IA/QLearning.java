package IA;

import types.Mouvement;

/**
 * Cette classe représente un algorithme d'apprentissage par renforcement
 * utilisant la méthode de Q-learning. Elle permet d'entraîner un agent à prendre
 * des décisions dans un environnement basé sur les récompenses reçues.
 * <p>
 * Elle stocke les informations nécessaires à l'apprentissage, notamment :
 * <ol>
 * <li>Un tableau de valeurs Q (QTable) qui associe des états et des actions à des valeurs de Q.</li>
 * <li>Les paramètres d'apprentissage :
 *     <ul>
 *     <li>alpha : le taux d'apprentissage, déterminant dans quelle mesure les nouvelles informations 
 *                modifient les anciennes valeurs de Q.</li>
 *     <li>gamma : le facteur de récompense future, influençant l'importance des récompenses futures
 *                par rapport aux récompenses immédiates.</li>
 *     <li>epsilon : le taux d'exploration, contrôlant dans quelle mesure l'agent explore de nouvelles
 *                  actions par rapport à l'exploitation des actions déjà connues.</li>
 *     </ul>
 * </li>
 * </ol>
 * L'algorithme de Q-learning fonctionne en ajustant itérativement les valeurs de Q en fonction des récompenses
 * reçues par l'agent, dans le but de maximiser les récompenses cumulatives à long terme.
 */
public class QLearning {
    /**
     * cette variable stocke la liste d'action enregistrer pour ensuite la lire 
     * et voir toute les actions possibles ou crée de nouvelles actons.
     */
    protected QTable qTable;

    /**
     * la variable alpha est la courbe d'apprentissage. Si il est à 1, il va 
     * ecraser à chaque fois toute les anciennes actions pour les remplacer 
     * avec les nouvelles, parcontre si il est à 0, aucune action va pouvoir
     * être ecraser.
     */
    private double alpha;

    /**
     * la variable gamma determine l'importance des rewards futur par rapport
     * aux rewards actuels. Si il est egal à 1, il va donner beaucoup plus de
     * rewards que si il est proche 0.
     */
    private double gamma;

    /**
     * la variable epsilon determine l'importance de l'exploration, plus un
     * epsilon est grand, plus il va prendre de choix aléatoire.
     */
    private double epsilon;

    /**
     * ce constructor mets en place toute les informations sur comment va se derouler 
     * l'apprentissage par renforcement. il va definir un nouveau {@link QTable}, un 
     * {@link #alpha}, un {@link #gamma} et un {@link #epsilon}.
     * @param QTable
     * @param alpha
     * @param gamma
     * @param epsilon
     */
    public QLearning(QTable qTable, double alpha, double gamma, double epsilon) {
        this.qTable = qTable;
        
        this.alpha = alpha;
        this.gamma = gamma;
        this.epsilon = epsilon;
    }

    /**
     * cette fonction pourra choisir quelle position il va choisir en 
     * fonction de epsilon. Il prend un état en paramètre et soit choisi
     * un mouvement aléatoire, soit il va chercher dans sa base de donnée
     * @param state
     * @return <pre><code>
     * si epsilon > Math.random -> 
     *      retourner un mouvement aléatoire
     *sinon ->
     *      retourner meilleur mouvement dans la liste
     * </code></pre>
     */
    public Mouvement chooseMouvements(State state) {
        if (Math.random() < epsilon) {
            return Mouvement.values()[(int)(Math.random() * Mouvement.values().length)];
        } else {
            double maxQ = Double.NEGATIVE_INFINITY;
            Mouvement bestMouvement = null;

            for(Mouvement mouvement : Mouvement.values()) {
                double q = qTable.getQValue(state, mouvement);
                if (q > maxQ) {
                    maxQ = q;
                    bestMouvement = mouvement;
                }
            }
            return bestMouvement;
        }
    }

    /**
     * Cette fonction met à jour la valeur Q dans la QTable pour une paire (état, action) donnée.
     * Elle utilise l'algorithme de mise à jour Q-learning pour ajuster la valeur Q en fonction des récompenses
     * reçues par l'agent.
     * 
     * @param state L'état actuel dans lequel se trouve l'agent.
     * @param mouvement L'action effectuée par l'agent dans l'état actuel.
     * @param reward La récompense reçue par l'agent pour avoir effectué l'action dans l'état actuel.
     * @param nextState L'état suivant vers lequel l'agent se déplace après avoir effectué l'action.
     * 
     * @see QTable#setQValue(State, Mouvement, double) Méthode utilisée pour mettre à jour la valeur Q dans la QTable.
     * @see QTable#getQValue(State, Mouvement) Méthode utilisée pour récupérer la valeur Q actuelle d'une paire (état, action) dans la QTable.
     */
    public void updateQValue(State state, Mouvement mouvement, double reward, State nextState) {
        double q = qTable.getQValue(state, mouvement);
        double maxQNext = Double.NEGATIVE_INFINITY;

        for(Mouvement nextMouvement : Mouvement.values()) {
            double qNext = qTable.getQValue(nextState, nextMouvement);

            if (qNext > maxQNext) {
                maxQNext = qNext;
            }
        }

        double newQ = q + alpha * (reward + gamma * maxQNext - q);
        qTable.setQValue(state, mouvement, newQ);
    }
}
