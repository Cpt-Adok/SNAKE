package Objects;

public enum Snake {
    HEAD("TETE", Effects.IMPASSABLE),
    BODY("BODY", Effects.IMPASSABLE);

    private final String NAME;
    private final Effects EFFECT;

    Snake(String name, Effects effect) {
        this.NAME = name;
        this.EFFECT = effect;
    }

    /**
     * <p> type de variable pour recuperer le nom du corps du snake:
     * <pre><code>String name = Snake.HEAD.getName()</code></pre>
     * @return Avoir le nom de l'item 
     */
    public String getName() {
        return this.NAME;
    }

    /**
     * <p> type de variable pour recuperer l'effet du corps du snake:
     * <pre><code>Effects effect = Snake.HEAD.getEffects()</code></pre>
     * @return Avoir l'effet de l'item
     */
    public Effects getEffects() {
        return this.EFFECT;
    }
}
