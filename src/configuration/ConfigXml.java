package configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import game.environnement.Map;
import personnage.IA;
import personnage.Personnage;
import personnage.Player;
import personnage.Robot;
import personnage.IAQLearning.QTable;

public class ConfigXml {
    private HashMap<String, ArrayList<HashMap<String, String>>> informationConfig;

    public ConfigXml(String path) {
        try {
            FileReaderXml fileReader = new FileReaderXml(null);
            informationConfig = fileReader.getElements();
            System.out.println(informationConfig);

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Personnage[] getCharacters() throws Error {
        String[] playableCharacters = new String[] {"Player", "Robot", "IA"};
        ArrayList<Personnage> personnages = new ArrayList<>();

        if (informationConfig.get("Configuration.Personnage") == null) {
            throw new Error("Pas de personnage !");
        }

        for (String character : playableCharacters) {
            ArrayList<HashMap<String, String>> hashMapChar = informationConfig.get("Personnage." + character);

            if(hashMapChar != null) {
                for(int i = 0; i<hashMapChar.size(); i++) {
                    if (personnages.size() > 2) {
                        throw new Error("Trop de personnage dans la partie !");
                    } 

                    int[] coordinate = new int[] {Integer.parseInt(hashMapChar.get(i).get("x")), Integer.parseInt(hashMapChar.get(i).get("y"))};

                    if (character.toLowerCase().equals("player")) personnages.add(new Player(coordinate, hashMapChar.get(i).get("name")));
                    if (character.toLowerCase().equals("robot")) personnages.add(new Robot(hashMapChar.get(i).get("name"), coordinate));

                    if (character.toLowerCase().equals("ia")){ 
                        QTable qTable;
                        String path;

                        if((path = hashMapChar.get(i).get("QTable")).equals("")) {
                            qTable = new QTable("res/save/");
                        } else {
                            qTable = new QTable(path);
                        }
                        personnages.add(new IA(coordinate, qTable, hashMapChar.get(i).get("name")));
                    }
                }
            }
        }

        return new Personnage[] {personnages.get(0), personnages.get(1)};
    }

    public Map getMap() throws Error {
        if (informationConfig.get("Configuration.Map") == null) {
            throw new Error("Pas de Map !");
        }

        ArrayList<HashMap<String,String>> map = informationConfig.get("Map.Coordinate");

        if (map.size() != 1) {
            throw new Error("problème de coordonnée");
        }

        return new Map(Integer.parseInt(map.get(0).get("x")), Integer.parseInt(map.get(0).get("y")));
    }
}