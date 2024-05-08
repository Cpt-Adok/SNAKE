package Environnements;

import java.lang.Object;
import java.util.Random;

import Item.Items;

public class Map {
    private Object[][] grid;
    private Random random;
    
    public int longueur;
    public int largeur;
    
    public Map(long longueur, long largeur) {
        this.longueur = (int)longueur;
        this.largeur = (int)largeur;

        this.random = new Random();

        this.grid = new Object[this.largeur][this.longueur];
    }

    public String getCoordinate(int x, int y) {
        if (x >= 0 && x < grid[0].length && y >= 0 && y < grid.length) {
            Object coordinate = grid[y][x];
            if (coordinate instanceof Items) {
                return ((Items) coordinate).getName();
            }
        }
        return "Empty";
    }

    public String getStringGrid() {
        StringBuffer stringGrid = new StringBuffer();
        
        for(Object[] i : this.grid) {
            for(Object value : i) {
                stringGrid.append(value);
            }
            stringGrid.append("\n");
        }

        return stringGrid.toString();
    }

    public void addItems(Items[] items, int number) {
        for(int i = 0; i<(items.length-1); i++) {
            int value = this.random.nextInt(number); 
            number -= value;
            randomize(items[i], value);
        }
        randomize(items[items.length-1], number);
    }

    public Object[][] getGrid() {
        return grid;
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

    private void randomize(Items item, int number) {
        for(int i = 0; i<number; i++) {
            int x = this.random.nextInt(1, this.grid[0].length);
            int y = this.random.nextInt(1, this.grid.length);

            if(!(getGrid()[y][x] instanceof Items)) this.grid[y][x] = item;
            else i--;
            
        }
    }
}
