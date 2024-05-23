package object;

public enum Items {
    WALL("WALL", Effects.IMPASSABLE),
    VOID("VOID", Effects.VOID),

    FRAISE("FRAISE", Effects.DECREASESIZE);

    private final String NAME;
    private final Effects EFFECT;

    Items(String name, Effects effect) {
        this.NAME = name;
        this.EFFECT = effect;
    }

    /**
     * <p> type de variable pour recuperer le nom de l'item:
     * <pre><code>String name = Item.WALL.getName()</code></pre>
     * @return Avoir le nom de l'item 
     */
    public String getName() {
        return this.NAME;
    }

    /**
     * <p> type de variable pour recuperer l'effet de l'item:
     * <pre><code>Effects effect = Item.WALL.getEffects()</code></pre>
     * @return Avoir l'effet de l'item
     */
    public Effects getEffects() {
        return this.EFFECT;
    }
}
