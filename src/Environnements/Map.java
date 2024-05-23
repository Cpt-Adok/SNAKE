package environnements;

import java.util.ArrayList;
import java.util.Random;

import types.*;
import personnages.*;


/**
 * cette classe est la classe qui cree et genere 
 * tout ce qui est important pour la Map du jeu.
 */
public class Map {
    /**
     * cette variable est toute la grille où se 
     * passe tout le jeu.
     */
    private Grid grid[][];

    /**
     * cette variable recupere tout les Grids stockés,
     * elle complete {@link #coordinateItems}.
     */
    private ArrayList<Grid> GridItems;
    
    /**
     * cette variable recupere tout les coordonnées des
     * Grids stockés, elle complete {@link #GridItems}.
     */
    private ArrayList<int[]> coordinateItems;

    private int longueur;
    private int largeur;

    /**
     * le constructeur crée une grille "vide" qui
     * contient uniquement l'enumerateur Items.VOID 
     * avec la longueur et la largeur en paramètre.
     * 
     * @param longueur pour la grille du jeu
     * @param largeur pour la grille du jeu
     */
    public Map(int longueur, int largeur) {
        this.longueur = longueur;
        this.largeur = largeur;

        this.grid = new Grid[this.largeur][this.longueur];
        
        this.GridItems = new ArrayList<>();
        this.coordinateItems = new ArrayList<>();

        this.fillGrid();
    }

    /**
     * rempli toute la grille de Items.VOID pour eviter
     * des problemes de null ou des problemes de 
     * collisions.
     */
    private void fillGrid() {
        for(int i = 0; i < this.grid.length; i++) {
            for(int k = 0; k < this.grid[0].length; k++) {
                this.grid[i][k] = Item.VOID;
            }
        }
    }
    
    /**
     * renvoie la grille du jeu.
     */
    public Grid[][] getGrid() {
        return grid.clone();
    }

    public boolean isGameOver(int[] coordinate) {
        return coordinate[0] < 0 || coordinate[0] >= this.grid[0].length || 
               coordinate[1] < 0 || coordinate[1] >= this.grid.length;
    }

    /**
     * renvoie l'effet 
     * @param coordinate
     * @return un {@link Effects}
     */
    public Effect getEffect(int[] coordinate) {
        Grid gridCoordinate = this.grid[coordinate[1]][coordinate[0]];
        return gridCoordinate.get();
    }

    /**
     * clear la map.
     */
    public void clearMap() {
        this.fillGrid();
    }

    /**
     * inverse toute la grille pour soit mettre (0, 0) en bas
     * ou en haut.
     */
    public Grid[][] getInverseGrid() {
        Grid[][] grid = getGrid();
        Grid[][] inverseGrid = new Grid[this.largeur][this.longueur];
        
        for (int i = grid.length - 1; i >= 0; i--) {
            inverseGrid[i] = grid[grid.length - 1 - i];
        }
    
        return inverseGrid;
    }

    /**
     * ajoute les coordonnées et les objets de façon non aléatoire
     * @param Grid
     * @param x
     * @param y
     */
    public void addObjects(Grid Grid, int x, int y) {
        this.coordinateItems.add(new int[]{x, y});
        this.GridItems.add(Grid);
    }

    /**
     * cette fonction ajoute dans {@link #grid} les
     * Grids contenu dans {@link #coordinateItems}
     * et {@link #GridItems}.
     * @param Grids prend le type d'objets que vous voulez
     * mettre dedans.
     * @param number prend le nombre d'objets global que 
     * vous voulez mettre dedans.
     */
    public void addObjectsRandomize(Item[] item, int number) {
        int lengthGrids = item.length-1;
        Random random = new Random();

        for(int i = 0; i<lengthGrids; i++) {
            int value = random.nextInt(number);
            number -=  value;
            randomize(item[i], value);
        }
        randomize(item[lengthGrids], number);
    }

    /**
     * cette fonction prend toutes les coordonnées du personnage 
     * et la place dans la grille
     */
    public void placePersonnages(Personnage personnage) {
        int index = 0;

        for (int[] coordinate : personnage.getCoordinate()) {
            if (index == 0) {
                this.grid[coordinate[1]][coordinate[0]] = SnakePart.HEAD;
                
            } else {
                this.grid[coordinate[1]][coordinate[0]] = SnakePart.BODY;
            }
            index++;
        }
    }


    /**
     * cette fonction prend la grille et renvoie la meme grille
     * mais avec de vrais bordures avec {@link #WALL}, cette fonction
     * cree une nouvelle liste et ajoute l'ancienne avec toute les 
     * coordonnées dedans, les coordonnées mis dans grid seront ajouté
     * de 1 dedans donc (1, 1) dans grid = (2, 2) dans edgesGrid
     * @return la liste avec les murs. 
     */
    public Grid[][] addEdges() {
        Grid[][] grid = this.getGrid();
        Grid[][] edgesGrid = new Grid[this.largeur+2][this.longueur+2];

        for(int i = 0; i < edgesGrid.length; i++) {
            for(int k = 0; k < edgesGrid[0].length; k++) {
                if (i == 0 || i == edgesGrid.length - 1 || k == 0 || k == edgesGrid[0].length - 1) {
                    edgesGrid[i][k] = Item.WALL;
                } else {
                    edgesGrid[i][k] = grid[i-1][k-1];
                }
            }
        }

        return edgesGrid;
    }

    /**
     * cette fonction regarde la coordonnée de la tete du
     * snake et si il est egal au coordonnée d'un fruit,
     * il le supprime pour eviter qu'il reste sur le terrain.
     * 
     * Il faut le placer apres le gameover pour pas qu'il enleve un mur.
     * @param coordinate
     */
    public void deleteItems(int[] coordinate) {
        for(int i = 0; i<this.coordinateItems.size(); i++) {
            int[] itemCoordinate = this.coordinateItems.get(i);
            if (itemCoordinate[0] == coordinate[0] && itemCoordinate[1] == coordinate[1]) {
                this.coordinateItems.remove(i);
                this.GridItems.remove(i);
            }
        }
    }

    /** 
     * cette fonction place les objets dans la grille du 
     * jeu.
     */
    public void placeObjects() {
        for(int i = 0; i<this.coordinateItems.size(); i++) {
            int[] coordinate = this.coordinateItems.get(i);

            this.grid[coordinate[1]][coordinate[0]] = GridItems.get(i);
        }
    }

    /**
     * prend des coordonnées au hasard et regarde si c'est 
     * pas deja occupé par un item ou un corps sinon, il 
     * recommence.
     * @param Grid
     * @param number
     */
    private void randomize(Item Grid, int number) {
        Random random = new Random();

        for (int i = 0; i<number; i++) {
            int x = random.nextInt(this.grid[0].length);
            int y = random.nextInt(this.grid.length);

            if(this.grid[y][x] == Item.VOID) {
                this.addObjects(Grid, x, y);
            } else {
                i--;
            }
        }
    }
}
