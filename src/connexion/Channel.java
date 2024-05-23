package connexion;

import environnements.*;
import types.Mouvement;
import personnages.Personnage;

public class Channel extends Personnage {
    private Reseau reseau;

    public Channel(Object[][] map, String channel) {
        super(new int [] {19,19});
        reseau=new Reseau(channel);
    }


    /**
     * Méthode permettant l'envoi de message, grâce à celle de Réseau
     * @param String contenant la direction voulue
     **/
    public void envoyerMessage(String s) {
        reseau.sendContent(s);
    }

    /**
     * Méthode permettant la réception du dernier message envoyé, grâce à celle de Réseau
     **/
    public String recupererMessage() {
        return reseau.getLastedContent(); 
    }

    /**
     * Méthode permettant de convertir un String en Mouvement
     * (Le String est celui récupéré dans le channel)
     * @param String s
     * @return Mouvement
     */
    public Mouvement conversion(String s){
        if (s.equals("U") || s.equals("u")){
            return Mouvement.HAUT;
        }else if (s.equals("D") || s.equals("d")){
            return Mouvement.BAS;
        }else if (s.equals("L") || s.equals("l")){
            return Mouvement.GAUCHE;
        }else if (s.equals("R") || s.equals("r")){
            return Mouvement.DROITE;
        }
        return null;
    }


    /**
     * Cette méthode est commune à toutes les sous-classes de personnages
     * Elle permet de jouer. Ici, on bouge le personnage en fonction du String
     * reçu dans le Channel, converti en Mouvement.
     * @param map
     * @return boolean qui indique si le Personnage est vivant ou pas.
     */
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