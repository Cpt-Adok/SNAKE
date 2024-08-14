package configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import game.environnement.Map;
import personnage.IA;
import personnage.Personnage;
import personnage.Player;
import personnage.Robot;
import personnage.IAQLearning.QTable;
import personnage.types.Item;

public class ConfigGame {
    private HashMap<String, ArrayList<HashMap<String, String>>> data;

    public ConfigGame(String path) {
        try {
            FileReaderXml fileReaderXml = new FileReaderXml(path);
            this.data = fileReaderXml.getElements();

            if (this.data.get("Configuration") == null || this.data == null) {
                System.err.println("Erreur: le fichier de configuration est introuvable.");
                System.exit(-1);
            }

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Map getMap() {
        ArrayList<HashMap<String,String>> mapList = data.get("Configuration.Map");
        
        if (mapList == null) {
            System.err.println("Erreur: La balise Map est introuvable.");
            System.exit(-1);
        }

        if (mapList.size() != 1) {
            System.err.println("Erreur: Plusieurs ou Aucune Coordonnées trouvées.");
            System.exit(-1);
        }

        Map map = new Map(
            Integer.parseInt(mapList.get(0).get("x")), 
            Integer.parseInt(mapList.get(0).get("y"))
        );

        addItems(map);

        return map;
    }

    public int getN() {
        HashMap<String, String> n;

        if ((n = data.get("Configuration").get(0)).isEmpty()) {
            return 4;
        } 
        
        return Integer.parseInt(n.get("n"));
    }

    public Personnage[] getPersonnages() {
        String[] personnagesList = new String[] {"Player", "Robot", "IA"};
        Personnage[] personnages = new Personnage[2];
        int index = 0;

        if (data.get("Configuration.Personnage") == null) {
            System.err.println("Erreur: La balise Personnage est introuvable.");
            System.exit(-1);
        }

        for (String personnage : personnagesList) {
            ArrayList<HashMap<String, String>> informations = data.get("Personnage."+personnage);
            if (informations != null) {
                for(HashMap<String, String> information : informations) {
                    if (index > 2) {
                        System.err.println("Erreur: Il doit y avoir au ]0; 3[ personnages");
                        System.exit(-1);
                    } 

                    personnages[index++] = choosePersonnage(personnage, information);
                }
            }
        }

        return personnages;
    }

    private Personnage choosePersonnage(String name, HashMap<String, String> information) throws Error {
        int[] coordinate = new int[] {
            Integer.parseInt(information.get("x")),
            Integer.parseInt(information.get("y")),
        };
        
        switch (name.toLowerCase()) {
            case "player": return new Player(coordinate, information.get("name"));
            case "robot": return new Robot(information.get("name"), coordinate);
            case "ia": {
                String path = information.get("QTable");
                return new IA(coordinate, (path.equals("")) ? new QTable("res/save/") : new QTable(path), name);
            }
        
            default: {
                System.err.println("Erreur: Il n'existe aucun personnage jouable dans la balise Personnage");
                System.exit(-1);
                return null;
            }
        }
    }

    private void addItems(Map map) {
        String[] itemList = new String[]{"Wall", "Fraise"};

        for(String item : itemList) {
            ArrayList<HashMap<String, String>> informations = data.get("Map."+item);

            if(informations != null) {
                for(HashMap<String, String> information : informations) {
                    map.addObjects(
                        chooseItem(item, information), 
                        Integer.parseInt(information.get("x")), 
                        Integer.parseInt(information.get("y"))
                    );
                }
            }
        }
    }

    private Item chooseItem(String name, HashMap<String, String> information) {
        switch (name.toLowerCase()) {
            case "wall": return Item.WALL;
            case "fraise": return Item.FRAISE;

            default: {
                System.err.println("Erreur: Il n'existe aucun item valide dans la balise Map");
                System.exit(-1);
                return null;
            }
        }
    }
}
