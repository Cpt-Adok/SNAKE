package personnages;

import java.util.ArrayList;

import Item.Effects;
import Item.Items;

public class Personnage {
    private int size;
    protected int[] coordinate;

    public Items item;

    private ArrayList<Effects> effectsList;

    protected Personnage(int size, int[] coordinate) {
        this.coordinate = coordinate;
        this.size = size;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public int getSize() {
        return size;
    }

    public void incrementSize(long size) {
        this.size += size;
    }

    public void addEffects(Effects effect) {
        this.effectsList.add(effect);
    }

    public ArrayList<Effects> getEffects() {
        return this.effectsList;
    }

    public boolean haveEffect(Effects effect) {
        return effectsList.contains(effect);
    }
}
