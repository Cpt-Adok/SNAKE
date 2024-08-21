package tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import game.environnement.Grid;
import personnage.IAQLearning.QTable;
import personnage.IAQLearning.State;
import personnage.types.Mouvement;

public class QTableTest {
    private final static String path = "res" + File.separator + 
                                       "save" + File.separator;

    public static void searchValue() {
        QTable qTable = new QTable();
        State state = new State(new Grid[3][3], new ArrayList<>());
        Mouvement mouvement = Mouvement.BAS;

        qTable.setQValue(state, mouvement, 10.2);

        qTable.printHashMap();

        System.out.println(qTable.getQValue(state, mouvement)); // Devrait retourner 10.2
    }

    public static void writeValueFile() {
        QTable.folderStorage = 1;
        QTable qTable = new QTable();
        State state = new State(new Grid[3][3], new ArrayList<>());
        
        qTable.setQValue(state, Mouvement.BAS, 10.3);

        qTable.setQValue(new State(new Grid[3][3], new ArrayList<>()), Mouvement.HAUT, 12.3);


        qTable.save(path, "name");
    }

    public static void searchValueFile() {
        QTable qTable = new QTable();
        // qTable.get(path);

        State state = new State(new Grid[3][3], new ArrayList<>());
        qTable.printHashMap();
        System.out.println(qTable.getQValue(state, Mouvement.BAS)); // Devrait retourner 10.3
    }

    public static void getSaveValue() {
        writeValueFile();
        searchValueFile();
    }

    public static void getRealInformation() {
        // State state = new State(new Grid[3][3], new ArrayList<>(Arrays.asList(new int[] {1, 1})));
        // Mouvement mouvement = Mouvement.GAUCHE;

        // QTable qTableSend = new QTable();
        // QTable qTableReceived = new QTable();

        // qTableSend.setQValue(state, mouvement, 102.0);
        // qTableSend.save(path, "fromage");

        // try {qTableReceived.get(path, "fromage");} catch(ClassNotFoundException | IOException e) {e.printStackTrace();}
        // System.out.println(qTableReceived.getQValue(state, mouvement));

        QTable.folderStorage = 1;

        QTable qTable = new QTable();

        for(int i = 0; i<10000; i++) {
            qTable.setQValue(new State(new Grid[3][3], new ArrayList<>(Arrays.asList(new int[] {i, 1}))), Mouvement.BAS, i);
        }

        qTable.save("res/save/", "file");

        QTable qTable2 = new QTable();

        try {
            qTable2.get("res/save/", "file");
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        qTable2.printHashMap();
    }

    public static void main(String[] args) {
        getRealInformation();
    }
}
