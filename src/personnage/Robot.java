package personnage;

import java.util.ArrayList;
import java.util.Random;

import game.connexion.*;
import game.environnement.*;
import personnage.types.*;

public class Robot extends Personnage {

    Map m;
    Mouvement move;
    String name;
    
    public Robot(String name, int[] coordinate) {
        super(coordinate);
        this.name = name;
    }

    /**Fonction commune aux sous-classes de Personnage
     * permettant le renvoi d'un mouvement pour chacun.
     */

    @Override
    public boolean round(Map map, String channel){
        this.m = map;

        this.move=this.compare(this.choix(),this.getHeadCoordinate());
        // System.out.println("Mouvement choisi : "+this.move);
        this.moveSnake(move);

        int[] coordinate = this.getHeadCoordinate();
        if (channel != null) Channel.envoyerMessage(this.move);

        if(map.isGameOver(coordinate) || this.applyEffects(map.getEffect(coordinate))) return true;

        map.deleteItems(coordinate);
        this.increaseRound();
        return false;
    }

    /**
     * Accès à la variable move
     * @return Mouvement
     */
    public Mouvement getMove(){
        if (this.move!=null){
            return move;
        }
        return null;
    }

    /**
     * Permet de savoir si une case de la map est vide ou si elle est impassable
     * @param x coordonnée longueur
     * @param y coordonnée largeur
     * @return boolean
     */
    public boolean estPossible(int x,int y){
        Grid [][] grille=this.m.getGrid();
        if (x>=0 && x<grille.length && y>=0 && y<grille[0].length){
            if (m.getEffect(this.creerTab(x, y))!=Effect.IMPASSABLE){
                return true;
            }
        }
        return false;
    }

    /**
     * Fonction pour éviter les répétitions
     * Permet de créer un tableau à partir de
     * @param x et
     * @param y
     * @return int []
     */
    public int [] creerTab(int x,int y){
        int [] t=new int [] {x,y};
        return t;
    }

    /**
     * Identifie les cases présentes autour de la tête du serpent
     * @param co coordonnées de la tête
     * @return ArrayList<int []>, ArrayList des positions autour
     */
    public ArrayList<int []> casesAutour(int [] co){
        ArrayList<int []> autour=new ArrayList<>();
        int x=co[0];  // x= ligne
        int y=co[1];  // y= colonne
        autour.add(creerTab(x+1, y));
        autour.add(creerTab(x-1, y));
        autour.add(creerTab(x, y+1));
        autour.add(creerTab(x, y-1));
        return autour;
    }

    /**
     * Permet d'identifier les cases valables parmi les voisines de la tête du serpent
     * @param co
     * @return ArrayList<int[]> regroupant les cases voisines qui sont jouables sans mourir
     */

    public ArrayList<int []> coupsPossibles(int [] co) {
        ArrayList<int []> coupsValables=new ArrayList<int []> ();
        ArrayList<int []> autour=casesAutour(co);
        for (int [] e:autour){
            if (this.estPossible(e[0], e[1])){
                coupsValables.add(e);
            }
        }
        return coupsValables;
    }

    /**
     * Décision finale aléatoire du coup parmi ceux possibles
     * @return int [] des coordonnées du coup
     */

    public int [] choix(){
        Random r=new Random();
        ArrayList <int[]> cases=coupsPossibles(this.getHeadCoordinate());
        if (cases.size()==0){
            return this.suicide();
        }
        int [] choix=cases.get(r.nextInt(cases.size()));
        return choix;
    }

    /**
     * Permet de mettre fin à la partie quand le serpent est coincé, et que aucun n'est valable
     * @return int [] des coordonnées du coup
     */

    public int [] suicide(){
        ArrayList <int []> murs=this.casesAutour(getHeadCoordinate());
        Random r=new Random();
        return murs.get(r.nextInt(4));
    }

    /**
     * Comparaison des coordonnées de la tête par rapport à celle du coup
     * @param t coup
     * @param t2 tête
     * @return Mouvement pour jouer le coup
     */

    public Mouvement compare(int[] t,int[] t2){
        if (t[0]>t2[0]){
            return Mouvement.DROITE;
        }else if (t[0]<t2[0]){
            return Mouvement.GAUCHE;
        }else if (t[1]<t2[1]){
            return Mouvement.HAUT;
        }else if (t[1]>t2[1]){
            return Mouvement.BAS;
        }
        return null;
    }
}