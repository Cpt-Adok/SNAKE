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

## AVANT TOUT : Comment lancer le jeu.

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


# JEU et GAMEPLAY

Ce jeu est un 1vs1 snake tactique tour par tour avec une gestion de mur et de fruits (que l'on peut ajouter aléatoirement ou en le directement en le placant par x et y), nous pouvons se déplacer avec les touches **z q s d ou/et w a s d**, le jeu se termine quand l'un des 2 snake meurt soit en foncant dans un corps, soit par un mur.

le programme pour le joueur recupère la touche envoyé et le transforme en ascii et une autre fonction le prend et le change en mouvement.

![ASCII](res/ASCII-Table.svg)

Nous pouvons changer quelque settings comme le nombre de tour avant que la taille du snake s'accumule en faisant `Personnage.n = n`.

# RÉSEAU ET COMMUNICATION

## - Channel

Channel est le module d'échange entre la machine de l'utilisateur et son adversaire via le site Padiflac.
Nous l'avons définie comme une sous-classe de Personnage.

Pour communiquer, elle reprend essentiellement des méthodes de Réseau, comme sendContent ou getLastedContent. Son travail est donc en grande partie consacré à la conversion de ce qui est reçu et de ce qui va être envoyé.

Le channel récupère les données contenues sur le channel qui y est dédié (le sien) et les représente dans le jeu du joueur local après conversion. Quand c'est au tour de celui-ci de jouer, il envoie le coup sur le channel adversaire et le tranforme en direction lisible, pour que celui-ci puisse l'utiliser.

Nous avons testé Channel à distance sur 2 machines et la partie s'est déroulée correctement.


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

## Resultat : 

![IA](res/video/ia_solo_15min_apprentissage.gif)

Dans cette vidéo, l'ia s'est entrainé pendant 15min tout seul et il a trouvé le meilleur chemin selon lui jusqu'à sa derniere erreur sur son apprentissage.
si je l'apprennais encore un peu plus, il pourra rester le plus longtemps possible.

# IA (sans Q-Learning)

Nous avons rédigé une deuxième IA, beaucoup plus simple, pour prévenir des problèmes rencontrés avec la première.

Celle-ci repose sur des mécaniques très simples.
Dans un premier temps, elle analyse les cases situées autour de sa tête à une distance de une seule case. Elle détermine ensuite lesquelles sont des coups possibles et n'entrainent pas une mort directe. Pour faire le choix final, elle utilise la méthode nextInt(index) de Random.
Quand elle est coincée et qu'aucune des cases l'entourant n'est un choix possible, elle gènère à nouveau un choix aléatoire parmi les 4 cases l'entourant.

Les choix qu'elle fait sont donc partiellement aléatoires et évitent essentiellement une mort au coup suivant.

# GRAPHIQUE 

Nous avons utiliser le terminal pour faire notre parti graphique avec de l'utf-8, il est très important d'activer l'utf-8 sur les terminaux avant de lancer le jeu car vous pourrez avoir des problèmes comme des "?".

# PROBLÈME ET SOLUTION RENCONTRÉES

## - Impressions FARIA Théo:

Durant ce projet j'ai parfois été complètement perdu par certaines méthodes très avancées utilisées par mon camarade.
Cependant, grâce à lui j'ai énormément appris et son aide m'a souvent été précieuse. Je lui suis très reconnaissant de sa patience et de sa volonté de m'inclure malgré tout. Il a réussi à me déléguer des taches plus simples et à m'expliquer ce que lui faisait.

Je trouve notre projet bien construit, utilisant des moyens adaptés à chacuns des points du jeu. La division des classes est pertinente et les commentaires apportent une grande plus-value à ceux qui voudrait l'utiliser.

## - Impressions GUEZO Loïc 

Durant ce projet, j'ai pu tester et apprendre de nouvelles choses. J'ai pu aussi "tester" à gérer une "mini équipe" (on etait que 2). Mon camarade s'est quand même bien débrouillé comparé à notre différence de penser et comment gérer les problèmes.

# CRÉDITS

Ce projet à été réalisé par GUEZO Loïc et FARIA Théo.
