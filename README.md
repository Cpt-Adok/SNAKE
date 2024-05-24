```
 ______   __   __   ______   __  __   ______    
/\  ___\ /\ "-.\ \ /\  __ \ /\ \/ /  /\  ___\   
\ \___  \\ \ \-.  \\ \ \_\ \\ \  _"-.\ \  __\   
 \/\_____\\ \_\\"\_\\ \_\ \_\\ \_\ \_\\ \_____\ 
  \/_____/ \/_/ \/_/ \/_/\/_/ \/_/\/_/ \/_____/ 
```

# INTRODUCTION

C'est un projet de fin de Licence L1 en Informatique à UPEC, la création d'un petit projet en binôme sur la création d'un jeu **SNAKE** en multijoueur et en affrontement 1 contre 1. Nous allons séparer cette présentation en plusieurs parties :

- La partie **JEU et GAMEPLAY**

- La partie **RÉSEAUX et COMMUNICATION**

- La partie **INTELLIGENCE ARTIFICIELLE** 

- La partie **GRAPHIQUE**

- La partie **PROBLÈME ET SOLUTION RENCONTRÉES**

### AVANT TOUT : Comment lancer le jeu.

Pour lancer le jeu, nous pouvons utiliser les addons de vscode en appuyant sur ***run*** sur la methode **main**.

Sinon vous pouvez utiliser le Makefile, il va stocker tout les fichiers `.class` dans un fichier **bin**. Nous pouvons le re-utiliser plus tard grâce à une autre commande sur le Makefile.

Les commandes pour le makefile :

- `Make` : il va lancer la compilation et va lancer l'interpréteur java (il va aussi nettoyer le fichier bin avant).

- `Make clean` : il va supprimer le fichier bin.

- `Make run` : il va uniquement lancer l'interpréteur java (Attention à ne pas supprimer le bin avant)

Pour lancer le jeu en multijoueur avec Makefile (mot1 et mot2 sont des mots à changer en fonction du canal):
- `Make channel=mot1 adversaire=mot2` : compile tout le programme et le lance en multijoueur.
- `Make run channel=mot1 adversaire=mot2` : lance uniquement le programme en multijoueur.

Sur windows, vous avez une autre option que personnellement je vous encourage, c'est l'utilisation du `run.bat`. Ce programme va lancer le Makefile mais aussi le faite de mettre l'utf-8 sur la session. Si vous utilisez pas le `run.bat`, vous aurez possiblement certains problèmes avec certains caractères qui seront remplacés par des "?", pour lancer le programme run.bat (mot1 et mot2 sont des mots à changer en fonction du canal):

- `run.bat mot1 mot2` : il va uniquement lancer `Make channel=mot1 adversaire=mot2` et en même temps lancer `chcp 65001` qui va mettre l'utf-8.
# IA (Q-Learning)

## - Explications

Le Q-Learning ou l'apprentissage par renforcement est un type d'apprentissage où le personnage ou autre apprend par une base de donnée de tout ce qu'il a réussi dans le passé. Il apprend en sauvegardant dans sa base de donnée toute les actions qu'il a pu faire au long de son apprentissage.

Il y a 2 types de temps dans son apprentissage :

- La phase d'exploration
- La phase d'apprentissage

### La phase d'exploration

La phase d'exploration se passe le plus possible au début de son apprentissage, il va tester tout les actions aléatoire qu'il a en dispositions. Il va souvent se planter et il va sauvegarder toute sa progression.

### La phase d'apprentissage

La phase d'apprentissage est souvent beaucoup plus long dans son apprentissage, il va tester des mécaniques qu'il a apprises et essayer un max possible de faire des actions qui sont dans sa base de donnée.

## - Calcul et Compréhension

$$Q(s_t, a_t) = Q(s_t, a_t) + \alpha * (R_t + \gamma * \max(Q(s_{t+1}, a)) - Q(s_t, a_t))$$

Ce calcul sera la valeur de toutes les actions que l'IA va enregistrer dans sa base de donnée, il y aura toutes les informations comme la position du snake ou la grille du jeu puis ce calcul pour définir la "fiabilité" du coup.

- $Q(s_t, a_t)$ : est la valeur de Q actuelle, il contient  $s_t$ qui l'état et $a_t$ qui est l'action de Q.

- $\alpha$ : est le taux d'apprentissage, c'est lui qui détermine si on doit écraser les valeurs ou non.
- $R_t$ : est la récompense de l'action, si c'est une bonne action ou non.
- $\gamma$ : est l'importance des futures récompenses.
- $\max(Q(s_{t+1}, a))$ : est la valeur maximale de Q du prochain tour parmi toute sa base de donnée.


# CRÉDITS

Ce projet à été réalisé par FARIA Théo et GUEZO Loïc.
