# Jeu de Pong

## Présentation

Ce Pong est un jeu de raquettes programmé en Java 17 avec JavaFX. Le projet est configuré avec Gradle utilisant le plugin JavaFX. Ce jeu est largement inspiré du jeu [Pong](https://fr.wikipedia.org/wiki/Pong), un classique des salles d'arcades dans les années 1970.

Le principe est simple : un terrain (le "*court*") deux raquettes et une balle. Le jeu se joue à deux, chaque joueur pouvant déplacer sa raquette sur un axe haut/bas et ayant pour but de ne pas laisser passer la balle derrière sa raquette (ce qui cause sa défaite immédiate). La balle se déplace à vitesse constante et rebondit sur les parois (limites haute et basse de la fenêtre) et sur les raquettes.

Pour l'instant, seules les fonctionnalités basiques sont implémentées : 2 raquettes contrôlées par 2 paires de touches sur le clavier ; gestion basique des rebonds de la balle et détection de sortie du terrain par les côtés (but marqué).



## Exécution, compilation

Après avoir téléchargé/cloné les sources, vous pouvez compiler et exécuter le projet à l'aide de gradle.
Le principe c'est que le script `gradlew` dans le répertoire du projet téléchargera puis utilisera la version de gradle qui fonctionne avec le projet.

Pour compiler, il suffit d'exécuter, depuis le répertoire `pong` :

```bash
`./gradlew build`
```

Pour exécuter, il suffit d'exécuter, depuis le répertoire `pong` :

```bash
`./gradlew run`

## Joueur 
Joueur 1 :   z s  correspondent monter , descendre.
Joueur 2 (raquette droite) :  UP ,DOWN
Joueur 3 (raquette en haut) :  q d 
Joueur 4 (raquette en bas ): LEFT RIGHT


## lien pour la video  :
 https://www.youtube.com/watch?v=D0OmC5YeXB4 
 
 ## lien pour le fichier qui contient tous les noms du groupe
 
https://drive.google.com/file/d/16PohREX1Ry-H3pGbNdh2spHNNJf_Lzkz/view?usp=sharing

```




