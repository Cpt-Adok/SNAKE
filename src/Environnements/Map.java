package Environnements;

import java.util.ArrayList;
import java.util.Random;

import Objets.Effects;
import Objets.Items;
import Personnages.Personnage;
import Personnages.Snake;

/**
 * cette classe est la classe qui cree et genere 
 * tout ce qui est important pour la Map du jeu.
 */
public class Map {
    /**
     * cette variable est toute la grille où se 
     * passe tout le jeu.
     */
    private Object grid[][];

    /**
     * cette variable recupere tout les objects stockés,
     * elle complete {@link #coordinateItems}.
     */
    private ArrayList<Object> ObjectItems;
    
    /**
     * cette variable recupere tout les coordonnées des
     * objects stockés, elle complete {@link #ObjectItems}.
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

        this.grid = new Object[this.largeur][this.longueur];
        
        this.ObjectItems = new ArrayList<>();
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
                this.grid[i][k] = Items.VOID;
            }
        }
    }
    
    /**
     * renvoie la grille du jeu.
     */
    public Object[][] getGrid() {
        return grid.clone();
    }

    public boolean isGameOver(int[] coordinate) {
        return coordinate[0] < 0 || coordinate[0] >= this.grid[coordinate[1]].length || 
               coordinate[1] < 0 || coordinate[1] >= this.grid.length;
    }

    /**
     * renvoie l'effet 
     * @param coordinate
     * @return un {@link Effects}
     */
    public Effects getEffect(int[] coordinate) {
        Object object = this.grid[coordinate[1]][coordinate[0]];
        return (object instanceof Items) ? ((Items)object).getEffects() : ((Snake)object).getEffects();
    }

    /**
     * clear la map.
     */
    public void clearMap() {
        this.fillGrid();
    }

    /**
     * inverse toute la grille pour soit mettre 0, 0 en bas
     * ou en haut.
     */
    public Object[][] getInverseGrid() {
        Object[][] grid = getGrid();
        Object[][] inverseGrid = new Object[this.largeur][this.longueur];
        int k = 0; 

        for (int i = grid.length; i> 0; --i) {
            inverseGrid[k++] = inverseGrid[i];
        }

        return inverseGrid;
    }

    /**
     * ajoute les coordonnées et les objets de façon non aléatoire
     * @param object
     * @param x
     * @param y
     */
    public void addObjects(Object object, int x, int y) {
        this.coordinateItems.add(new int[]{x, y});
        this.ObjectItems.add(object);
    }

    /**
     * cette fonction ajoute dans {@link #grid} les
     * objects contenu dans {@link #coordinateItems}
     * et {@link #ObjectItems}.
     * @param objects prend le type d'objets que vous voulez
     * mettre dedans.
     * @param number prend le nombre d'objets global que 
     * vous voulez mettre dedans.
     */
    public void addObjectsRandomize(Object[] objects, int number) {
        int lengthObjects = objects.length-1;
        Random random = new Random();

        for(int i = 0; i<lengthObjects; i++) {
            int value = random.nextInt(number);
            number -=  value;
            randomize(objects[i], value);
        }
        randomize(objects[lengthObjects], number);
        placeObjects();
    }

    /**
     * cette fonction prend toutes les coordonnées du personnage 
     * et la place dans la grille
     */
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


    /**
     * cette fonction prend la grille et renvoie la meme grille
     * mais avec de vrais bordures avec {@link #WALL}, cette fonction
     * cree une nouvelle liste et ajoute l'ancienne avec toute les 
     * coordonnées dedans, les coordonnées mis dans grid seront ajouté
     * de 1 dedans donc (1, 1) dans grid = (2, 2) dans edgesGrid
     * @return la liste avec les murs. 
     */
    public Object[][] addEdges() {
        Object[][] grid = this.getGrid();
        Object[][] edgesGrid = new Object[this.largeur+2][this.longueur+2];

        for(int i = 0; i < edgesGrid.length; i++) {
            for(int k = 0; k < edgesGrid[0].length; k++) {
                if (i == 0 || i == edgesGrid.length - 1 || k == 0 || k == edgesGrid[0].length - 1) {
                    edgesGrid[i][k] = Items.WALL;
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
            if (this.coordinateItems.get(i) == coordinate) {
                this.coordinateItems.remove(i);
                this.ObjectItems.remove(i);
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

            this.grid[coordinate[1]][coordinate[0]] = ObjectItems.get(i);
        }
    }

    /**
     * prend des coordonnées au hasard et regarde si c'est 
     * pas deja occupé par un item ou un corps sinon, il 
     * recommence.
     * @param object
     * @param number
     */
    private void randomize(Object object, int number) {
        Random random = new Random();

        for (int i = 0; i<number; i++) {
            int x = random.nextInt(this.grid[0].length);
            int y = random.nextInt(this.grid.length);

            if(!(this.grid[y][x] instanceof Snake) && (Items)this.grid[y][x] == Items.VOID) {
                this.coordinateItems.add(new int[] {x, y});
                this.ObjectItems.add(object);
            } 
            else {
                i--;
            }
        }
    }
}
