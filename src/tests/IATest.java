package tests;

import java.io.File;
import java.util.Arrays;

import IA.QTable;
import IA.State;
import display.Display;
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

        int totalEpisodes = 200;

        Personnage.n = 4;

        for(int episode = 0; episode < totalEpisodes; episode++) {
            QTable qTable = new QTable();
            IAQLearning iaqLearning = new IAQLearning(new int[] {2, 2}, qTable, alpha, gamma, epsilon);
            Map map = new Map(12, 22);
    
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
        double epsilon = 0.1;

        int maxEpisode = 1000;

        Personnage.n = 4;

        for (int episode = 0; episode < maxEpisode; episode++) {
            QTable qTable = new QTable();
            qTable.getValues(path);

            Map map = new Map(12, 22);

            IAQLearning[] iaqLearnings = new IAQLearning[] {
                new IAQLearning(new int[] {2, 2}, qTable, alpha, gamma, epsilon),
                new IAQLearning(new int[] {9, 19}, qTable, alpha, gamma, epsilon),
            };

            boolean isGameOver = false;

            while(true) {
                for (int personnages = 0; personnages < iaqLearnings.length; personnages++) {
                    IAQLearning iaqLearning = iaqLearnings[personnages];
                    Map mapIA = new Map(map.getGrid()[0].length, map.getGrid().length);

                    for (IAQLearning value : iaqLearnings) {
                        map.placePersonnages(value);
                    }

                    State currentState = iaqLearning.getCurrentState(map.getGrid());
                    Mouvement mouvement = iaqLearning.bestMouvement(currentState);

                    iaqLearning.moveSnake(mouvement);

                    int[] coordinate = iaqLearning.getHeadCoordinate();

                    if (map.isGameOver(coordinate) || iaqLearning.applyEffects(map.getEffect(coordinate))) {
                        iaqLearning.receiveReward(currentState, mouvement, -1000, currentState);
                        isGameOver = true;
                        break;
                    }

                    int value = (personnages + 1) % 2; 

                    for (int[] snakeCoordinate : iaqLearnings[value].getCoordinate()) {
                        if (Arrays.equals(coordinate, snakeCoordinate)) {
                            iaqLearnings[value].receiveReward(currentState, mouvement, 1000, currentState);
                            iaqLearning.receiveReward(currentState, mouvement, -500, currentState);
                        
                            isGameOver = true;
                            break;
                        }
                    }

                    mapIA.placePersonnages(iaqLearning);

                    State nextState = iaqLearning.getCurrentState(mapIA.getGrid());
                    iaqLearning.receiveReward(currentState, mouvement, -0.01, nextState);

                    iaqLearning.increaseRound();

                    mapIA.clearMap();
                    map.clearMap();
                }
            
                if(isGameOver) break;
                qTable.save(path);

                System.out.println("Episode: " + episode + " States: " + qTable.getqValues().size());
            }
        }
    }
}
