package IA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import environnement.Grid;

/**
 * La classe Etats est cruciale pour le Q-Learning car c'est elle
 * qui permet de stocker toutes les informations et de les comparer 
 * par la suite. Cette classe peut être comparée à un tuple avec
 * toutes les informations nécessaires.
 */
public class State implements Serializable {
    private static final long serialVersionUID = 1L; // la version du serializable
    
    /**
     * Cette variable stocke la grille du jeu de {@link Map}, 
     * c'est une des choses les plus utiles pour le bon
     * fonctionnement du Q-Learning car c'est elle qui sait dans
     * quelles conditions il doit se comporter.
     */
    public ArrayList<ArrayList<Grid>> grid; 

    /**
     * Cette variable stocke toutes les coordonnées du serpent.
     * La première coordonnée est la tête du serpent.
     */
    public ArrayList<ArrayList<Integer>> coordinate;

    /**
     * Constructeur de la classe State.
     * @param grid Un tableau 2D représentant la grille du jeu.
     * @param coordinate Une liste de tableaux d'entiers représentant les coordonnées.
     */
    public State(Grid[][] grid, ArrayList<int[]> coordinate) {
        this.coordinate = convertCoordinatesToArrayList(coordinate);
        this.grid = convertGridToArrayList(grid);

    }

    public ArrayList<ArrayList<Grid>> convertGridToArrayList(Grid[][] grid) {
        ArrayList<ArrayList<Grid>> arrayList = new ArrayList<>();
        
        for (Grid[] row : grid) {
            arrayList.add(new ArrayList<>(Arrays.asList(row)));
        }

        return arrayList;
    }

    /**
     * Convertit une ArrayList de tableaux d'entiers en ArrayList<ArrayList<Integer>>.
     * @param coordinates La ArrayList de tableaux d'entiers à convertir.
     * @return Une ArrayList<ArrayList<Integer>> représentant la ArrayList de tableaux d'entiers.
     */
    public ArrayList<ArrayList<Integer>> convertCoordinatesToArrayList(ArrayList<int[]> coordinates) {
        ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();

        for (int[] row : coordinates) {
            ArrayList<Integer> valueArrayList = new ArrayList<>();
            for (int value : row) {
                valueArrayList.add(value);
            }
            arrayList.add(valueArrayList);
        }

        return arrayList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return grid.equals(state.grid) && coordinate.equals(state.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grid, coordinate);
    }

    @Override
    public String toString() {
        return "State{grid=" + grid.toString() + ", coordinate=" + coordinate + '}';
    }
}