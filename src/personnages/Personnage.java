package Personnages;

import java.util.ArrayList;

import Item.Effects;

public class Personnage {
    private int size;
    protected int[] coordinate;

    private ArrayList<Effects> effectsList;

    protected Personnage(int size, int[] coordinate) {
        this.coordinate = coordinate;
        this.size = size;
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
