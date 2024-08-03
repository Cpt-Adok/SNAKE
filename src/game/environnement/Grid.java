package game.environnement;

import java.io.Serializable;

import personnage.types.Effect;

public interface Grid extends Serializable {
    /**
     * Retourne le nom de la grille.
     * Par défaut, il convertit la représentation toString de l'objet en minuscules.
     *
     * @return le nom de la grille.
     */
    public default String getName() {
        return this.toString().toLowerCase();
    }

    /**
     * Retourne l'effet par défaut associé à la grille.
     * Par défaut, il retourne null.
     *
     * @return l'effet par défaut ou null.
     */
    public default Effect get() {
        return null;
    }

    /**
     * Met à jour la représentation du code de la grille sous forme de chaîne.
     *
     * @param textCode le nouveau code de chaîne à définir.
     */
    public void updateStringCode(String textCode);

    /**
     * Retourne la représentation du code de la grille sous forme de chaîne.
     *
     * @return le code de chaîne.
     */
    public String getStringCode();

    /**
     * Retourne un tableau de toutes les valeurs possibles de la grille.
     * Cette méthode peut être utilisée de manière similaire à la méthode values d'une énumération.
     *
     * @return un tableau de toutes les valeurs de la grille.
     */
    public Grid[] getValues();
}