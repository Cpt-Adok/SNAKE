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
        move=this.compare(this.getHeadCoordinate(), this.choix().get(0));
    }


    @Override
    public boolean round(Map map, String channel){
        System.out.println("Est-ce que ça passe par là au moins ?");
        int[] coordinate = this.getHeadCoordinate();
        if (channel != null) Channel.envoyerMessage(this.getMove());

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
        //System.out.println(x+" <= x , length grille : "+(grille.length-1)+" "+y+"<= y , length grille [0] : "+(grille[0].length-1));
        if (x>grille.length-1 || y>grille[0].length-1){
            return false;
        } else if (grille[x][y]==Effect.IMPASSABLE){
            return false;
        }
        return true;
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

    public ArrayList <int []> casesAutour(){
        ArrayList <int []> t =this.coupsPossibles(this.getCoordinate().get(0));
        ArrayList <int []> t2 = new ArrayList<> ();
        for (int i=0;i<t.size();i++){
            t.get(i)[0]+=1;
            this.fusion(t2,this.coupsPossibles(t.get(i)));
            t.get(i)[0]-=2;
            this.fusion(t2,this.coupsPossibles(t.get(i)));
            t.get(i)[0]+=1;
            t.get(i)[1]+=1;
            this.fusion(t2,this.coupsPossibles(t.get(i)));
            t.get(i)[1]-=2;
            this.fusion(t2,this.coupsPossibles(t.get(i)));
        }
        this.killDouble(t2);
        return t2;
    }

    public ArrayList<int[]> fusion(ArrayList<int[]> t, ArrayList<int[]> t2){
        for (int [] e :t2){
            t.add(e);
        }
        return t;
    }

    public ArrayList <int []> choix(){
        ArrayList <int[]> cases=casesAutour();
        ArrayList <ArrayList <int []>> w=new ArrayList<>();
        for (int i=0;i<cases.size();i++){
            w.add(this.coupsPossibles(casesAutour().get(i)));
        }
        ArrayList<int []> max=w.get(0);
        for (ArrayList <int []> e :w){
            if (e.size()>max.size()){
                max=e;
            }
        }
        if (w.size()==0){
            return this.suicide(cases);
        }
        return max;
    }

    public ArrayList <int []> suicide(ArrayList <int []> murs){
        Random r=new Random();
        ArrayList <int []> a=new ArrayList<> ();
        a.add(murs.get(r.nextInt(murs.size())));
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
        }else if (t[0]==t2[0] || t[1]==t2[1]){
            return moveRandom();
        }
        return null;
    }

    public ArrayList <int []> killDouble(ArrayList <int []> t){
        for (int i=0;i<t.size();i++){
            for (int j=i;j<t.size();j++){
                if (t.get(i)==t.get(j)){
                    t.remove(j);
                }else if(t.get(i)==this.getCoordinate().get(0)){
                    t.remove(i);
                }
            }
        }
        return t;
    }

    public Mouvement moveRandom(){
        System.out.println("Ce choix est parfaitement aléatoire");
        Random r=new Random();
        Mouvement [] m=new Mouvement[] {Mouvement.HAUT,Mouvement.BAS,Mouvement.GAUCHE,Mouvement.DROITE};
        return m[r.nextInt(4)];
    }
}
