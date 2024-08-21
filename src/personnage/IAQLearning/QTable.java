package personnage.IAQLearning;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import personnage.types.Mouvement;

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
    public static int folderStorage = 1000;

    private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Constructeur de la classe QTable cree le HashMap qValues.
     */
    public QTable() {
        qValues = new HashMap<>();
    }

    /**
     * Constructeur de la classe QTable cree le HashMap qValues et mets dans la liste
     * les informations du fichier dans le path.
     */
    public QTable(String pathFile, String pathname) {
        qValues = new HashMap<>();
        try {
            get(pathFile, pathname);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
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
    public void saveChunk(HashMap<Actions, Double> hashmapSlide, String path) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(hashmapSlide);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public void saveChunk(String path) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(qValues);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    /**
     * Cette méthode charge les valeurs Q depuis un fichier spécifié.
     * @param path le chemin du fichier à partir duquel charger les données
     * @throws ClassNotFoundException 
     */
    @SuppressWarnings("unchecked")
    public void getChunk(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            qValues = (HashMap<Actions, Double>) ois.readObject();
        }
    }

    @SuppressWarnings("unchecked")
    private HashMap<Actions, Double> getChunkSave(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (HashMap<Actions, Double>)ois.readObject();
        }
    }

    public void save(String pathFolderName, String name) {
        File file = new File(pathFolderName);
        if (name == null) name = "null";

        if (file.isFile()) {
            saveChunk(pathFolderName);
        } else {
            List<Map.Entry<Actions, Double>> entryList = new ArrayList<>(qValues.entrySet());
            int indexFile = 0;

            for (int i = 0; i < entryList.size(); i += folderStorage) {
                int end = Math.min(i + folderStorage, entryList.size());
                List<Map.Entry<Actions, Double>> subList = entryList.subList(i, end);
                
                HashMap<Actions, Double> subHashMap = new HashMap<>();
                for(Map.Entry<Actions, Double> subValue : subList) {
                    subHashMap.put(subValue.getKey(), subValue.getValue());
                }
                
                String fileName = pathFolderName + File.separator + name + "_part" + (++indexFile) + ".ser";

                executor.submit(() -> saveChunk(subHashMap, fileName));
            }

            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.HOURS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void get(String pathFolderName, String name) throws ClassNotFoundException, IOException {
        File file = new File(pathFolderName);
        
        if (file.isFile()) {
            getChunk(pathFolderName);
        } else if (!(file.exists() && file.isDirectory())) {
            System.err.println("Erreur : le fichier " + pathFolderName + " n'existe pas.");
            System.exit(-1);
        } else {
            // les hashmaps basique ne supporte pas bien le multithread en java >> https://www.geeksforgeeks.org/concurrenthashmap-in-java/
            ConcurrentHashMap<Actions, Double> multithreadHashMap = new ConcurrentHashMap<>();
            File[] listFiles = file.listFiles((dir, filename) -> filename.startsWith(name) && filename.endsWith(".ser"));
            if (listFiles != null) {
                for (File partFile : listFiles) {
                    executor.submit(() -> {
                        try {
                            HashMap<Actions, Double> tempMap = getChunkSave(partFile.getPath());
                            multithreadHashMap.putAll(tempMap);
                        } catch (ClassNotFoundException | IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(1, TimeUnit.HOURS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                qValues = new HashMap<>(multithreadHashMap);
            }
        }
    }

    /**
     * cette méthode renvoie dans le terminal tout les elements du
     * hashmap.
     */
    public void printHashMap() {
        for (Map.Entry<Actions, Double> value : qValues.entrySet()) {
            System.out.println(value.getKey().toString() + " -> " + value.getValue());
        }
    }

    public HashMap<Actions, Double> getqValues() {
        return qValues;
    }
}