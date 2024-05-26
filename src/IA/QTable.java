package IA;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import types.Mouvement;

/**
 * cette classe permet de sauvegarder chaque action que l'IA 
 * fait, il est essentiel pour le bon fonctionnement du programme
 * car sans lui, aucun moyen que le programme puisse verifier 
 * si dans son {@link QTable}, il contient les informations 
 * pour le bon fonctionnement. Elle peut etre comparé à un 
 * <strong>tuple({@link State}, {@link Mouvement})</strong>.
 */
public class QTable {
    /**
     * cette variable est utilisé pour stocker toute les informations
     * necessaire pour que le bot puisse faire des actions.
     */
    private HashMap<Actions, Double> qValues;

    /**
     * Constructeur de la classe QTabl cree le HashMap qValues.
     */
    public QTable() {
        qValues = new HashMap<>();
    }
    
    /**
     * cette fonction renvoie soit la valeur associé à l'action de l'etat 
     * et du mouvement ou la crée dans le hashmap.
     * @param state
     * @param action
     * @return
     */
    public double getQValue(State state, Mouvement mouvement) {
        return qValues.getOrDefault(new Actions(state, mouvement), 0.0);
    }
 
    /**
     * Cette méthode ajoute une valeur Q associée à une paire état-action dans la QTable.
     * @param state L'état pour lequel la valeur Q est associée.
     * @param mouvement L'action pour laquelle la valeur Q est associée.
     * @param value La valeur Q associée à la paire état-action.
     */
    public void setQValue(State state, Mouvement mouvement, double value) {
        qValues.put(new Actions(state, mouvement), value);
    }

    /**
     * Cette méthode sauvegarde les valeurs Q dans un fichier spécifié.
     * @param path le chemin du fichier où sauvegarder les données
     */
    public void save(String path) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(qValues);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    /**
     * Cette méthode charge les valeurs Q depuis un fichier spécifié.
     * @param path le chemin du fichier à partir duquel charger les données
     */
    @SuppressWarnings("unchecked")
    public void getValues(String path) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            qValues = (HashMap<Actions, Double>) ois.readObject();

        } catch (IOException  | ClassNotFoundException e) {
            save(path);
        }  
    }

    /**
     * cette méthode renvoie dans le terminal tout les elements du
     * hashmap.
     */
    public void printValues() {
        for (Map.Entry<Actions, Double> value : qValues.entrySet()) {
            System.out.println(value.getKey().toString() + " -> " + value.getValue());
        }
    }

    public HashMap<Actions, Double> getqValues() {
        return qValues;
    }
}