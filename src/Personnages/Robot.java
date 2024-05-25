package personnages;

import java.util.ArrayList;
import java.util.Random;

import types.*;
import environnements.*; 
import connexion.*;

public class Robot extends Personnage {

    Map m;
    Mouvement move;
    String name;
    
    public Robot(String name, int[] coordinate,Map m) {
        super(coordinate);
        this.name = name;
        this.m=m;
    }


    @Override
    public boolean round(Map map, String channel){
        System.out.println(this.choix().size()+ " move : "+this.compare(this.getHeadCoordinate(), this.choix().get(0)));
        this.move=this.compare(this.getHeadCoordinate(), this.choix().get(0));

        int[] coordinate = this.getHeadCoordinate();
        if (channel != null) Channel.envoyerMessage(this.move);

        if(map.isGameOver(coordinate) || this.applyEffects(map.getEffect(coordinate))) return true;

        map.deleteItems(coordinate);
        this.increaseRound();
        return false;
    }

    public Mouvement getMove(){
        if (this.move!=null){
            return move;
        }
        return null;
    }

    public boolean estPossible(int x,int y){
        Object [][] grille=this.m.getGrid();
        if (x>=0 && x<grille.length && y>=0 && y<grille[0].length){
            if (grille[x][y]!=Effect.IMPASSABLE){
                System.out.println("Ca passe");
                return true;
            }
            System.out.println("Ca passe que le premier if");
        }
        System.out.println("Ca passe rien");
        return false;
    }

    public int [] creerTab(int x,int y){
        int [] t=new int [] {x,y};
        return t;
    }

    public ArrayList<int []> coupsPossibles(int [] co) {
        ArrayList<int []> coupsValables=new ArrayList<int []> ();
        if (this.estPossible(co[0]+1,co[1])){
            coupsValables.add(creerTab(co[0]+1, co[1]));
        }else if (this.estPossible(co[0],co[1]+1)){
            coupsValables.add(creerTab(co[0], co[1]+1));
        }else if (this.estPossible(co[0]-1,co[1])){
            coupsValables.add(creerTab(co[0]-1, co[1]));
        }else if (this.estPossible(co[0],co[1]-1)){
            coupsValables.add(creerTab(co[0], co[1]-1));
        }
        return coupsValables;
    }

    public ArrayList<int[]> fusion(ArrayList<int[]> t, ArrayList<int[]> t2){
        for (int [] e :t2){
            t.add(e);
        }
        return t;
    }

    public ArrayList <int []> choix(){
        Random r=new Random();
        ArrayList <int[]> cases=coupsPossibles(this.getHeadCoordinate());
        if (cases.size()==0){
            return this.suicide(cases);
        }
        int [] choix=cases.get(r.nextInt(cases.size()));
        ArrayList <int []> choisi =new ArrayList<int[]>();
        choisi.add(choix);
        return choisi;
    }

    public ArrayList <int []> suicide(ArrayList <int []> murs){
        Random r=new Random();
        ArrayList <int []> a=new ArrayList<> ();
        a.add(murs.get(r.nextInt(4)));
        return a;
    }

    public Mouvement compare(int[] t,int[] t2){
        if (t[0]>t2[0]){
            return Mouvement.BAS;
        }else if (t[0]<t2[0]){
            return Mouvement.HAUT;
        }else if (t[1]<t2[1]){
            return Mouvement.GAUCHE;
        }else if (t[1]>t2[1]){
            return Mouvement.DROITE;
        }
        System.out.println("Problème Robot.compare");
        return null;
    }

    public ArrayList <int []> killDouble(ArrayList <int []> t){
        for (int i=0;i<t.size();i++){
            for (int j=i;j<t.size();j++){
                if (t.get(i)==t.get(j)){
                    t.remove(j);
                }else if(t.get(i)==this.getHeadCoordinate()){
                    t.remove(i);
                }
            }
        }
        return t;
    }
}
