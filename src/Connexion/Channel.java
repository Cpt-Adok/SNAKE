package Connexion;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Environnement.*;
import Characters.*;
import display.TerminalChannel;

public class Channel{
    
    Reseau reseau;
    ArrayList<Personnage> personnages;
    int numJoueur;
    TerminalChannel term;
    Map m;

    public Channel(Reseau r, ArrayList<Personnage> p, int j,TerminalChannel term,Map m){
        this.reseau=r;
        this.personnages=p;
        this.numJoueur=j;
        this.term=term;
        this.m=m;
    }

    public String typePersonnage(int i){
        if (i>=0 && i<personnages.size()){
            Personnage p=personnages.get(i);
            if (p instanceof Characters.Players){
                return "J";
            }
            return "R";
        }
        return null;
    }

    public void jeu(){
        String j1=this.typePersonnage(0);
        Players player=(Players) this.personnages.get(0);
        while (term.run()){
            if (term.round%2!=0){
                term.playerRound(player);
            }else{
                reseau.sendContent(this.jouer(j1));
            }
        }
    }

    public String jouer(String j){
        if (j=="J"){
            Players player=(Players) this.personnages.get(0);
            return this.getInput(reseau);
        }
        Robot bot=(Robot) this.personnages.get(0);
        return bot.jouer(m);
    }

    public String getInput(Reseau r){
        String c=" ";
        return c;
    }
}
