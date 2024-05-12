package Environnement;

import java.util.ArrayList;
import java.util.Random;

import Characters.Personnage;
import Objects.*;

/**
 * <p> cette classe est la classe qui cree et genere 
 * tout ce qui est important pour la Map du jeu
 */
public class Map {
    /**
     * <p> cette variable est toute la grille où se 
     * passe tout le jeu.
     */
    private Object[][] grid;

    /**
     * <p> cette variable recupere tout les objects stockés 
     */
    private ArrayList<Object> ObjectItems;
    
    /**
     * <p> cette variable recupere tout les coordonnées des
     * objects stockés 
     */
    private ArrayList<int[]> coordinateItems;

    private int longueur;
    private int largeur;
    
    /**
     * <p> le constructeur crée une grille "vide" qui
     * contient uniquement l'enumerateur Items.VOID 
     * avec la longueur et la largeur en paramètre.
     * 
     * @param longueur pour la grille du jeu
     * @param largeur pour la grille du jeu
     */
    public Map(int longueur, int largeur) {
        this.longueur = longueur;
        this.largeur = largeur;

        this.grid = new Object[this.longueur][this.largeur];
        this.fillGrid();
    }

    /**
     * <p> cette fonction clear toute la grille qui 
     * contient le jeu.
     */
    public void clearMap() {
        this.fillGrid();
    }

    /**
     * <p> cette fonction renvoie toute la grille Object[][]
     * qui contient tout la grille du jeu
     * @return la grille du jeu
     */
    public Object[][] getGrid() {
        return grid;
    }

    /**
     * <p> cette fonction ajoute les bordures sur la grille
     */
    public void addEdges() {
        for(int i = 0; i < this.grid.length; i++) {
            for(int k = 0; k < this.grid[0].length; k++) {
                if (i == 0 || i == this.grid.length - 1 || k == 0 || k == this.grid[0].length - 1) {
                    this.grid[i][k] = Items.WALL;
                }
            }
        }
    }

    /**
     * <p> cette fonction ajoute dans {@link #grid} les
     * objects contenu dans {@link #coordinateItems}
     * et {@link #ObjectItems}.
     * @param objects prend le type d'objets que vous voulez
     * mettre dedans.
     * @param number prend le nombre d'objets global que 
     * vous voulez mettre dedans.
     */
    public void addObjects(Object[] objects, int number) {
        int lengthObjects = objects.length-1;
        Random random = new Random();

        for(int i = 0; i<lengthObjects; i++) {
            int value = random.nextInt(number);
            number -=  value;
            randomize(objects[i], value);
        }
        randomize(objects[lengthObjects], number);
    }

    /** 
     * <p> cette fonction place les objets dans la grille du 
     * jeu.
     */
    public void placeObjects() {
        for(int i = 0; i<this.coordinateItems.size(); i++) {
            int[] coordinate = this.coordinateItems.get(i);

            this.grid[coordinate[0]][coordinate[1]] = ObjectItems.get(i);
        }
    }
    
    public void placePersonnages(Personnage personnage) {
        int index = 0;

        for (int[] coordinate : personnage.getCoordinate()) {
            if (index == 0) {
                this.grid[coordinate[1]][coordinate[0]] = Snake.HEAD;
                
            } else {
                this.grid[coordinate[1]][coordinate[0]] = Snake.BODY;
            }
            index++;
        }
    }

    private boolean isGamefinishedImpassable(int key, Personnage personnage) {
        int[] coordinate = personnage.getMouvement(key).getCoordinate();
        int[] playerCoordinate = personnage.getPrimaryCoordinate();

        int y = coordinate[1] + playerCoordinate[1];
        int x = coordinate[0] + playerCoordinate[0];

        Object grid = this.getGrid()[y][x];

        if (grid instanceof Snake) return ((Snake)grid).getEffects() == Effects.IMPASSABLE;
        else if (grid instanceof Items) return ((Items)grid).getEffects() == Effects.IMPASSABLE;
        else return false;
    }

    public boolean isGameOver(int key, Personnage personnage) {
        int[] personnageCoordinate = personnage.getPrimaryCoordinate();

        boolean isOutX = personnageCoordinate[0] < 0 || this.grid[0].length - 1 < personnageCoordinate[0];
        boolean isOutY = personnageCoordinate[1] < 0 || this.grid.length - 1 < personnageCoordinate[1];
        
        if (isOutX || isOutY) {
            return true;
        }
        return false;
    }

    private void fillGrid() {
        for(int i = 0; i < this.grid.length; i++) {
            for(int k = 0; k < this.grid[0].length; k++) {
                this.grid[i][k] = Items.VOID;
            }
        }
    }

    private void randomize(Object item, int number) {
        Random random = new Random();

        for(int i = 0; i<number; i++) {
            int x = random.nextInt(1, this.grid[0].length);
            int y = random.nextInt(1, this.grid.length);

            if(!(getGrid()[y][x] instanceof Items)) {this.coordinateItems.add(new int[]{y, x}); this.ObjectItems.add(grid);}
            else if(!(getGrid()[y][x] instanceof Snake)) {this.coordinateItems.add(new int[]{y, x}); this.ObjectItems.add(grid);}
            else if(!(getGrid()[y][x] instanceof Fruits)) {this.coordinateItems.add(new int[]{y, x}); this.ObjectItems.add(grid);}
            else i--;  
        }
    }
}