package types;

import environnements.Grid;

/**
 * cette enum représente différents types d'objets dans le jeu.
 */
public enum Item implements Grid {
    /**
     * Mur impassable.
     * Effet associé :  <pre>{@code types.Effect.IMPASSABLE}</pre>
     * utf : null car c'est un autre programme qui gère le mur.
     */
    WALL(Effect.IMPASSABLE, null),

    /**
     * Zone vide sans effet.
     * Effet associé : <pre>{@code types.Effect.VOID}</pre>
     * utf : vide 
     */
    VOID(Effect.VOID, "   "),

    /**
     * Fraise.
     * Effet associé : <pre>{@code types.Effect.DECREASESIZE}</pre>
     * utf : un drapeau (0x26FF)
     */
    FRAISE(Effect.DECREASESIZE, " \u26FF ");

    private final Effect EFFECT;
    private String utfCode;

    /** 
     * @param effect L'effet associé à l'objet.
     */
    Item(Effect effect, String utf) {
        this.EFFECT = effect;
        this.utfCode = utf;
    }

    /**
     * cette methode donne l'effet associé à l'objet.
     * 
     * @return L'effet associé à l'objet.
     */

    @Override
    public Effect get() {
        return this.EFFECT;
    }

    @Override
    public Item[] getValues() {
        return values();
    }

    @Override
    public String getStringCode() {
        return this.utfCode;
    }

    @Override
    public void updateStringCode(String textCode) {
        this.utfCode = textCode;
    }

}