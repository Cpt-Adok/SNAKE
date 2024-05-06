package Environnements;

public class Map {
    public static int[][] grille;

    public static int[][] generateMap(int hauteur, int largueur) {
        int[][] generateGrille = new int[hauteur][largueur];
        int j;

        for(int i = 0; i<generateGrille.length; i++) {
            for(j = 0; j<generateGrille[0].length; i++) {
                if (i == 0 || i == generateGrille.length-1) {
                    generateGrille[i][j] = 1;
                }
            }

            if (j == 0 || j == generateGrille[i].length-1) {
                generateGrille[i][0] = 1;
                generateGrille[i][generateGrille[i].length - 1] = 1;
            }
        }

        grille = generateGrille;
        return generateGrille;
    }
}
