package connexion;

import java.util.Arrays;

import environnements.*;
import types.Item;
import types.Mouvement;
import personnages.Personnage;

public class Channel extends Personnage {
    private Reseau reseau;
    private static Reseau adversaire;

    private Map map;

    public Channel(Map map, String channel, String autreChannel) {
        super(new int [] {map.getGrid()[0].length, map.getGrid().length});

        this.map = map;
        this.name = autreChannel;

        reseau = new Reseau(channel);
        adversaire = new Reseau(autreChannel);
    }


    public Grid[][] getInverseGridChannel() {
        Grid[][] grid = map.getInverseGrid();
        Grid[][] inverseGrid = new Grid[grid.length][grid[0].length];
    
        for (int j = grid.length - 1; j >= 0; j--) {
            for (int i = grid[j].length - 1; i >= 0; i--) {
                inverseGrid[j][i] = grid[j][grid[j].length - 1 - i];
            }
        }
    
        return inverseGrid;
    }

    /**
     * Méthode permettant l'envoi de message, grâce à celle de Réseau
     * @param String contenant la direction voulue
     **/
    public static void envoyerMessage(Mouvement mouvement) {
        adversaire.sendContent(conversionString(mouvement));
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
    public Mouvement conversionMouvement(String s){
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

    private static String conversionString(Mouvement mouvement){
        if (mouvement == Mouvement.HAUT) {
            return "U";
        } else if (mouvement == Mouvement.BAS) {
            return "D";
        } else if (mouvement == Mouvement.GAUCHE) {
            return "L";
        } else if (mouvement == Mouvement.DROITE) {
            return "R";
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
    @Override
    public boolean round(Map map, String channel){
        int[] coordinate=this.getHeadCoordinate();

        this.moveSnake(conversionMouvement(recupererMessage()));
        
        if (map.isGameOver(coordinate) || this.applyEffects(map.getEffect(coordinate))){
            return true;
        }

        map.deleteItems(coordinate);
        this.increaseRound();
        return false;
    }
}