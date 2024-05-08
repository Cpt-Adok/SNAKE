package Environnements;

import java.util.Random;

public class Map{
    int longueur;
    int largeur;
    int nbMurs;
    int frequencesFruits;
    Object [][] grille;

    public Map(int longueur,int largeur, int nbMurs,int frequencesFruits){
        this.largeur=largeur;
        this.longueur=longueur;
        this.nbMurs=nbMurs;
        this.frequencesFruits=frequencesFruits;

        grille=new Object[longueur][largeur];

        for (int i=0;i<nbMurs;i++){
            this.placeMur();
        }

        for (int j=0;j<this.largeur*this.longueur;j+=this.frequencesFruits){
            this.placeFruit();
        }
    }

    public int getLongueur(){  //Accéder à la variable Longueur
        System.out.println("Longueur : "+ this.longueur);
        return this.longueur;
    }

    public int getLargeur(){  //Accéder à la variable Largeur
        System.out.println("Largeur : "+ this.largeur);
        return this.largeur;
    }

    public void printGrille(){  //Affichage simple de la grille de jeu
        for (int i=0;i<longueur;i++){
            for (int j=0;j<largeur;j++){
                System.out.print(this.grille[i][j]);
            }
            System.out.println();
        }
    }

    // Réfléchir pour mettre plusieurs murs à des endroits différents
    public void placeMur(){       //Positionner un mur défini aléatoirement sur la Map
        Random r=new Random();
        int x=r.nextInt(this.longueur-1);   // Le "-1" permet d'éviter les murs faisant déjà partie de
        int y=r.nextInt(this.largeur-1);    // la bordure de la Map
        int L=r.nextInt(this.longueur-x);
        int l=r.nextInt(this.largeur-y);

        Murs mur =new Murs(x, y,L, l);
        mur.insereMur(this);
    }

    public void placeFruit(){
        Random r=new Random();
        Fruits fruit=new Fruits();

        int x=r.nextInt(this.longueur);
        int y=r.nextInt(this.largeur);

        this.grille[x][y]=fruit;
    }
}