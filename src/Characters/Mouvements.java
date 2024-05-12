package Characters;

/**
 * Cet enumerateur prend en charge tout les mouvements possible
 * pour le serpent, il a uniquement la possibilité de se déplacer
 * grâce a {@link Mouvements} pour la classe Player et Robot.
 */
public enum Mouvements {
    /**
     * HAUT prend comme coordonnée (0, -1) pour se déplacer. 
     * @param x = 0
     * @param y = -1
     */
    HAUT(0, -1),
    
    /**
     * BAS prend comme coordonnée (0, 1) pour se déplacer. 
     * @param x = 0
     * @param y = 1
     */
    BAS(0, 1),

    /**
     * GAUCHE prend comme coordonnée (1, 0) pour se déplacer. 
     * @param x = -1
     * @param y = 0
     */
    GAUCHE(-1, 0),

    /**
     * @DROITE prend comme coordonnée (-1, 0) pour se déplacer. 
     * @param x = 1
     * @param y = 0
     */
    DROITE(1, 0);

    private final int deltaX;
    private final int deltaY;

    Mouvements(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Cette fonction prend les coordonnées mis en paramètre et 
     * modifie avec les coordonnées de l'enum.
     * @param coordinate prend principalement les coordonnées du 
     * personnage
     */
    public void editCoordinate(int[] coordinate) {
        coordinate[0] += this.deltaX;
        coordinate[1] += this.deltaY;
    }

    /**
     * Cette fonction retourne les coordonnées des valeurs du mouvement.
     * @return la liste qui contient [0] = x et [1] = y
     */
    public int[] getCoordinate() {
        return new int[] {this.deltaX, this.deltaY};
    }
}
