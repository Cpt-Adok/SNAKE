package Connexion;

import Connexion.Reseau;
import display.*;

public class Channel extends Reseau{
    
    int numJoueur;

    public Channel(String channel, Terminal t, int numJoueur){
        super(channel);
        this.numJoueur=numJoueur;
    }

    public void partie(Terminal t){
        boolean partie=true;
        while(partie){
            if (t.getRound()%2!=this.numJoueur){
                this.getNewArrayListContent();
            }
        }
    }
}
