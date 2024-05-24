package tests;

import java.io.File;

import IA.QTable;
import IA.State;
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

                State nextState = iaqLearning.getCurrentState(mapIA.getGrid());

                int[] coordinate = iaqLearning.getHeadCoordinate();

                if(map.isGameOver(coordinate) || iaqLearning.applyEffects(map.getEffect(coordinate))) {
                    iaqLearning.receiveReward(currentState, mouvement, -1.0, nextState);
                    break;
                }
                mapIA.placePersonnages(iaqLearning);

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
        int totalEpisodes = 1000;
        double alpha = 0.1;
        double gamma = 0.9; 
    
        // ia 1 et ia 2
        double[] epsilon = new double[] {1.0, 1.0}; 
    
        double decay_rate = 0.995; 
        double minEpsilon = 0.01;
    
        Personnage.n = 2;

        for (int episode = 0; episode < totalEpisodes; episode++) {
            QTable qTable = new QTable();
            qTable.getValues(path); 

            Map map = new Map(20, 20);
    
            IAQLearning[] personnages = new IAQLearning[] {
                new IAQLearning(new int[] {0, 0}, qTable, alpha, gamma, epsilon[0]),
                new IAQLearning(new int[] {0, 0}, qTable, alpha, gamma, epsilon[1])
            };
    
            boolean isGameOver = false;
    
            while (true) {
                for (int i = 0; i < personnages.length; i++) {
                    IAQLearning personnage = personnages[i];
                    Map mapIA = new Map(map.getGrid()[0].length, map.getGrid().length);
                    mapIA.replaceGrid(map.getGrid());
    
                    State currentState = personnage.getCurrentState(map.getGrid());
                    Mouvement mouvement = personnage.bestMouvement(currentState);
    
                    personnage.moveSnake(mouvement);
    
                    State nextState = personnage.getCurrentState(mapIA.getGrid());
    
                    int[] coordinate = personnage.getHeadCoordinate();
    
                    if (map.isGameOver(coordinate) || personnage.applyEffects(map.getEffect(coordinate))) {
                        personnage.receiveReward(currentState, mouvement, -1.0, nextState);
                        isGameOver = true;
                        break;
                    }
    
                    mapIA.placePersonnages(personnage);
    
                    personnage.receiveReward(currentState, mouvement, 0.1, nextState);
                    personnage.increaseRound();
    
                    mapIA.clearMap();
                    map.clearMap();
                }
                if (isGameOver) break;
            }
    
            qTable.save(path);
    
            for (int i = 0; i < epsilon.length; i++) {
                epsilon[i] = Math.max(minEpsilon, epsilon[i] * decay_rate);
            }
    
            System.out.println("Episode: " + episode);
        }
    }
}
