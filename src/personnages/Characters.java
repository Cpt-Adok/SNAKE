package Personnages;

public class Characters {
    protected int[] coordinate;
    public static int size; // N
    
    public static void setSize(int s) {
        size = s;
    }

    public int[] getCoordinate() {
        return this.coordinate;
    }

    public int getSize() {
        return size;
    }
}
