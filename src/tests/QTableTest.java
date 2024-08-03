package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import game.environnement.Grid;
import personnage.IAQLearning.QTable;
import personnage.IAQLearning.State;
import personnage.types.Mouvement;

public class QTableTest {
    private final static String path = "res" + File.separator + 
                                       "save" + File.separator + 
                                       "test.ser";

    public static void searchValue() {
        QTable qTable = new QTable();
        State state = new State(new Grid[3][3], new ArrayList<>());
        Mouvement mouvement = Mouvement.BAS;

        qTable.setQValue(state, mouvement, 10.2);

        qTable.printValues();

        System.out.println(qTable.getQValue(state, mouvement)); // Devrait retourner 10.2
    }

    public static void writeValueFile() {
        QTable qTable = new QTable();
        State state = new State(new Grid[3][3], new ArrayList<>());
        qTable.setQValue(state, Mouvement.BAS, 10.3);
        qTable.save(path);  // Devrait sauvegarder dans test.ser le state avec la valeur 10.3 
    }

    public static void searchValueFile() {
        QTable qTable = new QTable();
        qTable.getValues(path);

        State state = new State(new Grid[3][3], new ArrayList<>());
        qTable.printValues();
        System.out.println(qTable.getQValue(state, Mouvement.BAS)); // Devrait retourner 10.3
    }

    public static void getSaveValue() {
        writeValueFile();
        searchValueFile();
    }

    public static void getRealInformation() {
        State state = new State(new Grid[3][3], new ArrayList<>(Arrays.asList(new int[] {1, 1})));
        Mouvement mouvement = Mouvement.GAUCHE;

        QTable qTableSend = new QTable();
        QTable qTableReceived = new QTable();

        qTableSend.setQValue(state, mouvement, 102.0);
        qTableSend.save(path);

        qTableReceived.getValues(path);
        System.out.println(qTableReceived.getQValue(state, mouvement));
    }
}
