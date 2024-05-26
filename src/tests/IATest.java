package tests;

import java.io.File;

import IA.QTable;
import IA.State;
import display.Display;
import environnements.Grid;
import environnements.Map;
import personnages.IAQLearning;
import personnages.Personnage;
import types.Mouvement;

public class IATest {
    private final static String path = "res" + File.separator + 
                                    "save" + File.separator + 
                                    "learn.ser";

    public static void learnIA() {
        double alpha = 0.1;
        double gamma = 0.9; 
        double epsilon = 1.0; 
        double decay_rate = 0.995; 
        double minEpsilon = 0.01;

        int totalEpisodes = 1000;

        Personnage.n = 2;

        for(int episode = 0; episode < totalEpisodes; episode++) {
            QTable qTable = new QTable();
            IAQLearning iaqLearning = new IAQLearning(new int[] {0, 0}, qTable, alpha, gamma, epsilon);
            Map map = new Map(20, 20);
    
            qTable.getValues(path);
    
            while (true) {
                Map mapIA = new Map(map.getGrid()[0].length, map.getGrid().length);
                mapIA.replaceGrid(map.getGrid());
                
                map.placePersonnages(iaqLearning);

                State currentState = iaqLearning.getCurrentState(map.getGrid());
                Mouvement mouvement = iaqLearning.bestMouvement(currentState);

                iaqLearning.moveSnake(mouvement);

                int[] coordinate = iaqLearning.getHeadCoordinate();

                if(map.isGameOver(coordinate) || iaqLearning.applyEffects(map.getEffect(coordinate))) {
                    iaqLearning.receiveReward(currentState, mouvement, -1.0, currentState);
                    break;
                }

                mapIA.placePersonnages(iaqLearning);

                State nextState = iaqLearning.getCurrentState(mapIA.getGrid());

                iaqLearning.receiveReward(currentState, mouvement, 0.1, nextState);
                iaqLearning.increaseRound();

                mapIA.clearMap();
                map.clearMap(); 
            }

            qTable.save(path);

            epsilon = Math.max(minEpsilon, epsilon * decay_rate);
            System.out.println(episode);
        }
    }

    public static void learnIAvsIA() {
        double alpha = 0.1;
        double gamma = 0.9;
        double[] epsilon = new double[] {1.0, 1.0};
        double decay_rate = 0.995;
        double minEpsilon = 0.01;

        String file1 =  "res" + File.separator + "save" + File.separator + "file1.ser";
        String file2 =  "res" + File.separator + "save" + File.separator + "file2.ser";

        int totalEpisodes = 1;

        Personnage.n = 2;

        for(int episode = 0; episode < totalEpisodes; episode++) {
            QTable qTable1 = new QTable();
            QTable qTable2 = new QTable();

            boolean[] isGameOver = new boolean[]{false, false};

            IAQLearning[] iaqLearningList = new IAQLearning[] {
                new IAQLearning(new int[] {0, 0}, qTable1, alpha, gamma, epsilon[0]),
                new IAQLearning(new int[] {19, 19}, qTable2, alpha, gamma, epsilon[0])
            };

            Map map = new Map(20, 20);

            qTable1.getValues(file1);
            qTable2.getValues(file2);

            int i;

            while (true) {
                State currentState = null;
                Mouvement mouvement = null;
                State nextState = null;

                for(i = 0; i<iaqLearningList.length; i++) {
                    IAQLearning iaqLearning = iaqLearningList[i];

                    Grid[][] gridMap = map.getGrid();
                    Map mapIA = new Map(gridMap[0].length, map.getGrid().length);
                    mapIA.replaceGrid(gridMap);

                    map.placePersonnages(iaqLearning);

                    Display.printMap(map.addEdges());


                    currentState = iaqLearning.getCurrentState(map.getGrid());
                    mouvement = iaqLearning.bestMouvement(currentState);

                    iaqLearning.moveSnake(mouvement);

                    int[] coordinate = iaqLearning.getHeadCoordinate();

                    if(map.isGameOver(coordinate) || iaqLearning.applyEffects(map.getEffect(coordinate))) {
                        iaqLearning.receiveReward(currentState, mouvement, -1.0, currentState);
                        // Display.printMap(gridMap);
                        nextState = currentState;
                        isGameOver[i] = true;
                        break;
                    }

                    mapIA.placePersonnages(iaqLearning);

                    nextState = iaqLearning.getCurrentState(mapIA.getGrid());

                    iaqLearning.receiveReward(currentState, mouvement, 0.1, nextState);
                    iaqLearning.increaseRound();
                }

                if (isGameOver[0] == true) {
                    iaqLearningList[1].receiveReward(currentState, mouvement, 2.1, nextState);
                    break;
                }

                if (isGameOver[1] == true) {
                    iaqLearningList[0].receiveReward(currentState, mouvement, 2.1, nextState);
                    break;
                }
            }

            qTable1.save(file1);
            qTable2.save(file2);

            epsilon[i] = Math.max(minEpsilon, epsilon[i] * decay_rate);
            System.out.println("Robot 1 : State : " + qTable1.getqValues().size() + " Robot 2 : State : " + qTable2.getqValues().size() + ", Episode : " + episode);  
        }
    }
}
