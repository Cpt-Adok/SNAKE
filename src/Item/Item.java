package Item;

/**
 * Cette enumération contient tout les items à effets disponnible dans le jeu.
 */
public enum Item {
    Mur("mur", Effects.IMPASSABLE),

    FRAISE("fraise", Effects.INVINCIBILITY),
    ORANGE("orange", Effects.POWER),
    BANANE("banane", Effects.BOOST);

    private final String nom;
    private final Effects effect;

    Item(String nom, Effects effects) {
        this.nom = nom;
        this.effect = effects;
    }

    public String getName() {
        return this.nom;
    }

    public Effects getEffects() {
        return effect;
    }
}

