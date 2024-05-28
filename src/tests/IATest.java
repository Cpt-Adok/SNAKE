package tests;

import java.io.File;

import IA.QTable;
import IA.State;
import environnement.Grid;
import environnement.Map;
import personnage.IAQLearning;
import personnage.Personnage;
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
            System.out.println("Episode : " + episode + " | Robot 1 States : " + qTable.getqValues().size());
        }
    }

    public static void learnIAvsIA() {
        double alpha = 0.1;
        double gamma = 0.9;

        double[] epsilon = new double[] {1.0,};
        
        double decay_rate = 0.995;
        double minEpsilon = 0.01;

        int totalEpisodes = 1000;

        Personnage.n = 4;

        for (int episode = 0; episode < totalEpisodes; episode++) {
            QTable qTable = new QTable();

            IAQLearning[] iaqLearnings = new IAQLearning[] {
                new IAQLearning(new int[] {2, 2}, qTable, alpha, gamma, epsilon[0]),
                new IAQLearning(new int[] {9, 19}, qTable, alpha, gamma, epsilon[1])
            };

            Map map = new Map(12, 22);
            
            boolean isGameOver = false;

            qTable.getValues(path);

            while(true) {
                for (int i = 0; i < iaqLearnings.length; i++) {
                    IAQLearning iaqLearning = iaqLearnings[i];

                    Grid[][] gridMap = map.getGrid();
                    Map mapIA = new Map(gridMap[0].length, gridMap.length);
                    mapIA.replaceGrid(gridMap);

                    for (IAQLearning value : iaqLearnings) {
                        map.placePersonnages(value);
                    }

                    State currentState = iaqLearning.getCurrentState(map.getGrid());
                    Mouvement mouvement = iaqLearning.bestMouvement(currentState);
    
                    iaqLearning.moveSnake(mouvement);

                    int[] coordinate = iaqLearning.getHeadCoordinate();

                    for (int[] snakeCoordinate : iaqLearnings[(i + 1) % 2].getCoordinate()) {
                        if (coordinate[0] == snakeCoordinate[0] && coordinate[1] == snakeCoordinate[1]) {
                            iaqLearning.receiveReward(currentState, mouvement, -10.0, currentState);
                            iaqLearnings[(i + 1) % 2].receiveReward(currentState, mouvement, 10.0, currentState);
                            break;
                        }
                    }

                    mapIA.placePersonnages(iaqLearning);

                    State nextState = iaqLearning.getCurrentState(mapIA.getGrid());
                    iaqLearning.receiveReward(currentState, mouvement, -0.1, nextState);

                    iaqLearning.increaseRound();

                    mapIA.clearMap();
                    map.clearMap();
                }

                if(isGameOver) break;
            }

            qTable.save(path);

            for (int i = 0; i < epsilon.length; i++) {
                epsilon[i] = Math.max(minEpsilon, epsilon[i] * decay_rate);
            }

            System.out.println("Episode: " + episode + " | Robot 1 States: " + qTable.getqValues().size());
        }
    }
}
