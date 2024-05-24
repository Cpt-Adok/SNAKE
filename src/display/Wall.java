package display;

public enum Wall {
    ALL("\u2550\u256C\u2550", new boolean[] {true, true, true, true}),
    AUCUN("\u2550\u256C\u2550", new boolean[] {false, false, false, false}),

    HAUT("\u2551  ", new boolean[] {true, false, false, false}),
    BAS("\u2551  ", new boolean[] {false, true, false, false}),

    GAUCHE("\u2550\u2550\u2550", new boolean[] {false, false, true, false}),
    DROITE("\u2550\u2550\u2550", new boolean[] {false, false, false, true}),
    
    HAUT_BAS("\u2551  ", new boolean[] {true, true, false, false}),
    GAUCHE_DROITE("\u2550\u2550\u2550", new boolean[] {false, false, true, true}), 
    
    HAUT_GAUCHE("\u2550\u255D ", new boolean[]{true, false, true, false}), 
    HAUT_DROITE("\u255A\u2550", new boolean[]{true, false, false, true}), 
    
    BAS_GAUCHE("\u2550\u2557 ", new boolean[]{false, true, true, false}), 
    BAS_DROITE("\u2554\u2550", new boolean[] {false, true, false, true}), 
    
    HAUT_BAS_DROITE("\u2560\u2550 ", new boolean[] {true, true, false, true}), 
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
        boolean[] positionAscii = this.getPosition();

        for (int i = 0; i < position.length; i++) {
            if (positionAscii[i] != position[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean[] getPosition() {
        return POSITION;
    }
}
