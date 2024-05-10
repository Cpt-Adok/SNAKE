package Objects;

public enum Fruits {
    FRAISE("fraise", Effects.VOID);

    private final String NAME;
    private final Effects EFFECT;

    Fruits(String name, Effects effect) {
        this.NAME = name;
        this.EFFECT = effect;
    }

    /**
     * <p> type de variable pour recuperer le nom des fruits:
     * <pre><code>String name = Item.FRAISE.getName()</code></pre>
     * @return Avoir le nom de l'item 
     */
    public String getName() {
        return this.NAME;
    }

    /**
     * <p> type de variable pour recuperer l'effet des fruits:
     * <pre><code>Effects effect = Item.FRAISE.getEffects()</code></pre>
     * @return Avoir l'effet de l'item
     */
    public Effects getEffects() {
        return this.EFFECT;
    }
}
