package Connexion;

import environnements.*;
import object.Mouvements;
import personnages.Personnage;

public class Channel extends Personnage{
    private Reseau reseau;

    public Channel(Object[][] map, String channel) {
        super(new int [] {19,19});
        reseau=new Reseau(channel);
    }

    public void envoyerMessage(String s) {
        reseau.sendContent(s);
    }

    public String recupererMessage() {
        return reseau.getLastedContent(); 
    }

    public Mouvements conversion(String s){
        if (s.equals("U") || s.equals("u")){
            return Mouvements.HAUT;
        }else if (s.equals("D") || s.equals("d")){
            return Mouvements.BAS;
        }else if (s.equals("L") || s.equals("l")){
            return Mouvements.GAUCHE;
        }else if (s.equals("R") || s.equals("r")){
            return Mouvements.DROITE;
        }
        return null;
    }

    // @Override
    public boolean round(Map map){
        int [] coordinate=this.getHeadCoordinate();
        this.moveSnake(conversion(recupererMessage()));
        if (map.isGameOver(coordinate) || this.applyEffects(map.getEffect(coordinate))){
            return true;
        }
        map.deleteItems(coordinate);
        this.increaseRound();
        return false;
    }
}