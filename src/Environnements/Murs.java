package Environnements;

import Item.Items;

public class Murs{
    int debut_horizontal;
    int debut_vertical;
    int longueur;
    int largeur;

    public Murs(int debut_horizontal,int debut_vertical,int longueur,int largeur){
        this.debut_horizontal=debut_horizontal;
        this.debut_vertical=debut_vertical;
        this.longueur=longueur;
        this.largeur=largeur;
    }

    /**
     * <p>Accéder à la variable de position horizontale
     * @return {@code debut_horizontal}
     */
    public int getDebutHorizontal(){  
        return this.debut_horizontal;
    }

    public int getDebutVertical(){    //Accéder à la variable de position verticale
        return this.debut_vertical;
    }

    public int getLongueur(){   //Accéder à la variable Longueur
        return this.longueur;
    }

    public int getLargeur(){   //Accéder à la variable Largeur
        return this.largeur;
    }

    public boolean murValide(Map m){   //Vérifie que l'emplacement du mur est correct
        if (this.debut_horizontal+this.longueur>m.longueur || this.debut_vertical+largeur>m.largeur){
            System.out.println("Emplacement de mur invalide");
            return false;
        }
        return true;
    }

    public void insereMur(Map m){  //Positionner un mur à un endroit prédéfini
        boolean b=this.murValide(m);
        if (b){
            for (int i=this.debut_horizontal;i<this.debut_horizontal+this.longueur;i++){
                for (int j=this.debut_vertical;j<this.debut_vertical+this.largeur;j++){
                    m.getGrid()[i][j]=Items.MUR;
                }
            }
        }
    }
}