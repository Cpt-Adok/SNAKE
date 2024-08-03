package tests;

import java.io.File;
import java.util.Arrays;

import display.Display;
import game.environnement.Grid;
import game.environnement.Map;
import personnage.IA;
import personnage.Personnage;
import personnage.IAQLearning.QTable;
import personnage.IAQLearning.State;
import personnage.types.Mouvement;

public class IATest {
    private final static String path1 = "res" + File.separator + 
                                    "save" + File.separator + 
                                    "learn1.ser";

    private final static String path2 = "res" + File.separator + 
                                    "save" + File.separator + 
                                    "learn2.ser";

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
            IA iaqLearning = new IA(new int[] {2, 2}, qTable, alpha, gamma, epsilon, null);
            Map map = new Map(12, 22);
    
            qTable.getValues(path1);
    
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

            qTable.save(path1);

            epsilon = Math.max(minEpsilon, epsilon * decay_rate);
            System.out.println("Episode : " + episode + " | States : " + qTable.getqValues().size());
        }
    }

    public static void learnIAvsIA() {
        double alpha = 0.9;
        double gamma = 0.9;
        double epsilon = 0.1;

        int maxEpisode = 1000000;

        Personnage.n = 4;

        QTable qTable1 = new QTable();
        qTable1.getValues(path1);

        QTable qTable2 = new QTable();
        qTable2.getValues(path2);

        for (int episode = 0; episode < maxEpisode; episode++) {
            Map map = new Map(12, 22);

            IA[] iaqLearnings = new IA[] {
                new IA(new int[] {2, 2}, qTable1, alpha, gamma, epsilon, null),
                new IA(new int[] {9, 19}, qTable2, alpha, gamma, epsilon, null),
            };

            boolean isGameOver = false;

            while(true) {
                for (int personnages = 0; personnages < iaqLearnings.length; personnages++) {
                    IA iaqLearning = iaqLearnings[personnages];
                    Map mapIA = new Map(map.getGrid()[0].length, map.getGrid().length);

                    for (IA value : iaqLearnings) {
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

                    System.out.println("States 1: " + qTable1.getqValues().size() + " States 2: " + qTable2.getqValues().size());
                }
            
                if(isGameOver) break;
            }
            System.out.println(" States 1: " + qTable1.getqValues().size() + " States 2: " + qTable2.getqValues().size() + "Episode: " + episode);
        }
        qTable1.save(path1);
    }
}
