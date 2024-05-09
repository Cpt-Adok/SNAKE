package Item;

/**
 * Cette enumération contient tout les items à effets disponnible dans le jeu.
 */
public enum Items {
    MUR("mur", Effects.IMPASSABLE),

    BODY("corps", Effects.IMPASSABLE),
    HEAD("tete", Effects.IMPASSABLE),
    VOID("void", null),

    FRAISE("fraise", Effects.INVINCIBILITY),
    ORANGE("orange", Effects.POWER),
    BANANE("banane", Effects.BOOST);

    private final String nom;
    private final Effects effect;

    Items(String nom, Effects effects) {
        this.nom = nom;
        this.effect = effects;
    }

    /**
     * <p> type de variable pour recuperer le nom :
     * <pre><code>String name = Item.FRAISE.getName()</code></pre>
     * @return Avoir le nom de l'item 
     */
    public String getName() {
        return this.nom;
    }

    /**
     * <p> type de variable pour recuperer l'effet :
     * <pre><code>Effects effect = Item.FRAISE.getEffects()</code></pre>
     * @return Avoir l'effet de l'item
     */
    public Effects getEffects() {
        return effect;
    }
}

