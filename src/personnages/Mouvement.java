package personnages;

enum Mouvement {
    DROITE(1, 0), 
    HAUT(0, -1), 
    BAS(0, 1),
    GAUCHE(-1, 0);

    private final int deltaX;
    private final int deltaY;

    Mouvement(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public void editCoordinate(int[] coordinate) {
        coordinate[0] += this.deltaX;
        coordinate[1] += this.deltaY;
    }
}

