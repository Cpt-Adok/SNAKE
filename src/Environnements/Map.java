package environnements;

import java.lang.Object;
import java.util.Random;

import item.Effects;
import item.Items;

public class Map {
    private Object[][] grid;
    private Object[][] emptyGrid;
    private Random random;
    
    public int longueur;
    public int largeur;
    
    public Map(long longueur, long largeur) {
        this.longueur = (int)longueur;
        this.largeur = (int)largeur;

        this.random = new Random();

        this.grid = new Object[this.largeur][this.longueur];
        this.emptyGrid = new Object[this.largeur][this.longueur];

        this.fillGrid();
    }

    public Items getCoordinate(int x, int y) {
        if (x >= 0 && x < grid[0].length && y >= 0 && y < grid.length) {
            return (Items)grid[y][x];
        }
        return null;
    }

    public Items getItems(int[] coordinate) {
        return (Items)getGrid()[coordinate[1]][coordinate[0]];
    }

    public boolean isGameOver(int[] coordinate) {
        Items item = getCoordinate(coordinate[0], coordinate[1]);

        if (item != null) {
            Effects effect = item.getEffects();
            return effect == Effects.IMPASSABLE;
        }
        return true;
    }

    public String getStringGrid() {
        StringBuffer stringGrid = new StringBuffer();
        
        for(Object[] listValue : this.grid) {
            for(Object value : listValue) {
                stringGrid.append(value);
            }
            stringGrid.append("\n");
        }

        return stringGrid.toString();
    }

    public void addItems(Items[] items, int number) {
        int lengthItems = (items.length-1);
        for(int i = 0; i<lengthItems; i++) {
            int value = this.random.nextInt(number); 
            number -= value;
            randomize(items[i], value);
        }
        randomize(items[lengthItems], number);
    }

    public Object[][] getGrid() {
        return grid;
    }

    public void cleanGrid() {
        this.grid = this.emptyGrid;
    }

    public void ajoutBordure() {
        for(int i = 0; i < this.grid.length; i++) {
            for(int k = 0; k < this.grid[0].length; k++) {
                if (i == 0 || i == this.grid.length - 1 || k == 0 || k == this.grid[0].length - 1) {
                    this.grid[i][k] = Items.MUR;
                }
            }
        }
    }

    private void fillGrid() {
        for(int i = 0; i < this.grid.length; i++) {
            for(int k = 0; k < this.grid[0].length; k++) {
                this.grid[i][k] = Items.VOID;
            }
        }
    }

    public void addCoordinate(int[] coordinate, Items items) {
        for(int i = 0; i < this.grid.length; i++) {
            for(int k = 0; k < this.grid[0].length; k++) {
                if(i == coordinate[1] && k == coordinate[0]) {
                    this.grid[i][k] = items;
                }
            }
        }
    }

    private void randomize(Items item, int number) {
        for(int i = 0; i<number; i++) {
            int x = this.random.nextInt(1, this.grid[0].length);
            int y = this.random.nextInt(1, this.grid.length);

            if(!(getGrid()[y][x] instanceof Items)) this.grid[y][x] = item;
            else i--;
            
        }
    }
}
