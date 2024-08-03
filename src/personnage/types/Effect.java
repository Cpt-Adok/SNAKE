package personnage.types;

/**
 * cette enumérateur {@link Effects} contient tout les effets 
 * necessaire pour le bon déroulement du jeu et quelque effets
 * amusant qui pousse un peu plus les mecaniques du jeu.
 */
public enum Effect {
    /**
     * Effet : Réduit la taille du serpent de 1 segment.
     */
    DECREASESIZE,
    
    /**
     * Effet : Intransposable, entraînant la mort du joueur lors du contact.
     */
    IMPASSABLE, 

    /**
     * Effet : Vide, aucun effet.
     */
    VOID;    
}