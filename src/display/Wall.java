package display;

import java.util.Arrays;

public enum Wall {
    ALL("\u2550\u256C\u2550", new boolean[] {true, true, true, true}),
    AUCUN("\u2550\u256C\u2550", new boolean[] {false, false, false, false}),

    HAUT_BAS("\u2551", new boolean[] {true, true, false, false}),
    GAUCHE_DROITE("\u2550\u2550\u2550", new boolean[] {false, false, true, true}),

    HAUT_GAUCHE("\u255D   ", new boolean[]{true, false, true, false}),
    HAUT_DROITE("\u255A", new boolean[]{true, false, false, true}),

    BAS_GAUCHE("\u2557   ", new boolean[]{false, true, true, false}),
    BAS_DROITE("\u2554", new boolean[] {false, true, false, true}),

    HAUT_BAS_DROITE("\u2560", new boolean[] {true, true, false, true}),
    HAUT_BAS_GAUCHE("\u2563", new boolean[] {true, true, true, false}),

    HAUT_GAUCHE_DROITE("\u2550\u2569\u2550", new boolean[] {true, false, true, true}),
    BAS_GAUCHE_DROITE("\u2550\u2566\u2550", new boolean[] {false, true, true, true});

    private final boolean[] POSITION;
    private final String ASCII;

    Wall(String ascii, boolean[] position) {
        this.POSITION = position;
        this.ASCII = ascii;
    }

    public String getAscii() {
        return this.ASCII;
    }

    public boolean isEqual(boolean[] position) {
        return Arrays.equals(this.POSITION, position);
    }

    public boolean[] getPosition() {
        return POSITION;
    }
}
