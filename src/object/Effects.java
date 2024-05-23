package object;

/**
 * cette enumérateur {@link Effects} contient tout les effets 
 * necessaire pour le bon déroulement du jeu et quelque effets
 * amusant qui pousse un peu plus les mecaniques du jeu.
 */
public enum Effects {
    /**
     * reduis le corps de 1.
     */
    DECREASESIZE,
    
    /**
     * tue le joueur si contact.
     */
    IMPASSABLE, 

    /**
     * le vide.
     */
    VOID;
}
