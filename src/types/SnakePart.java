package types;

import environnements.Grid;

public enum SnakePart implements Grid {
    HEAD(Effect.IMPASSABLE, " \u25CF "),
    BODY(Effect.IMPASSABLE, " \u25A1 ");

    private final Effect EFFECT;
    private String utfCode;

    SnakePart(Effect effect, String utf) {
        this.EFFECT = effect;
        this.utfCode = utf;
    }

    @Override
    public Effect get() {
        return this.EFFECT;
    }

    @Override
    public SnakePart[] getValues() {
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
