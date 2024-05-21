package Characters;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import Environnement.*;
import Objects.Effects;

public class Robot extends Personnage {

    Map m;
    Mouvements move;

    public Robot(String name, int[] coordinate,Map m) {
        super(name, coordinate);
        this.m=m;
        move=this.compare(this.getCoordinate().get(0), this.choix().get(0));
        jouer(m);
    }

    public String jouer(Map m){
        if (this.move==Mouvements.HAUT){
            return "U";
        }else if(this.move==Mouvements.BAS){
            return "D";
        }else if(this.move==Mouvements.GAUCHE){
            return "L";
        }else if(this.move==Mouvements.DROITE){
            return "R";
        }
        return "Problème";
    }

    public boolean estPossible(int x,int y){
        JOptionPane.showInputDialog((this.m.getGrid().length+"  "+ this.m.getGrid()[0].length).toString());
        Object [][] grille=new Object[][] {this.m.getGrid()};
        if (grille[x][y]==Effects.IMPASSABLE){
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
        return max;
    }

    public Mouvements compare(int[] t,int[] t2){
        if (t[0]>t2[0]){
            System.out.println("s");
            return Mouvements.BAS;
        }else if (t[0]<t2[0]){
            System.out.println("w");
            return Mouvements.HAUT;
        }else if (t[1]<t2[1]){
            System.out.println("a");
            return Mouvements.GAUCHE;
        }else if (t[1]>t2[1]){
            System.out.println("d");
            return Mouvements.DROITE;
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

    // public int [] focusTete(){

    // }
    
}