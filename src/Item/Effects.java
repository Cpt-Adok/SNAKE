package Item;

/**
 * <p>Ceci est l'enumération où il y aura tout les effets disponible dans le projet.
 */
public enum Effects {
    /**
     * <p>pouvoir faire un dash de 4 ligne pendant le round prochain après
     * la recupération.
     */
    POWER, 

    /**
     * <p> Modifie la valeur de N pendant 2 tours en le multipliant par 2
     * <p> <strong>Exemple : </strong> si N = 2, il va être *2 donc N sera 
     * egal à 4 et le prochain tour quand N = 3, il sera égal à 6.   
     */
    BOOST, 

    /**
     * <p>pouvoir etre invincible pendant le prochain round.
     */
    INVINCIBILITY,

    /**
     * <p> impossible à passer à travers.
     */
    IMPASSABLE;
}
