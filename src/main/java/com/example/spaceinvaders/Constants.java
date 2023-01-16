package com.example.spaceinvaders;

import javafx.scene.image.Image;

import java.util.Random;

public class Constants {

      /*
    //Rappel
    Le mot-clé final attribué à une variable permet d'empêcher sa modification. En revanche, si cette variable est un objet, sache que l'on peut modifier ses données internes. Typiquement si tu déclares un tableau final, tu ne pourras le remplacer par un autre tableau, mais bel et bien modifier ses données.

Si tu appliques final sur une méthode, celle-ci ne peut être redéfinie dans une classe fille.
Si tu appliques final sur une classe, elle ne peut être dérivée.

static permet d'instancier l'objet directement, sans prendre en compte la classe l'encapsulant. Une classe contenant une variable static final n'a pas besoin d'être instanciée pour obtenir la référence vers la constante.
     */

    //Etape 1
    //variables & Constantes
    public static final Random RAND = new Random(); //afficher les etoiles de l'espace de maniere aleatoire
    public static final int WIDTH = 800; // largeur fenetre "Canvas"
    public static final int HEIGHT = 600; // hauteur fenetre "Canvas"
    public static final int PLAYER_SIZE = 60; //hauteur & largeur du vaisseaux du joueur
    public static final Image PLAYER_IMG = new Image("file:src/main/java/com/example/spaceinvaders/images/player.png");
    public static final Image EXPLOSION_IMG = new Image("file:src/main/java/com/example/spaceinvaders/images/explosion.png");
    public static final int EXPLOSION_W = 128; // Width de l'image de l'explosion // l'image initial fait 512*512 avec 4 images d'explosion par ligne, soit 512/4 = 128px (taille pour chaque image d'explosion)
    public static final int EXPLOSION_H = 128; // Height  de l'image de l'explosion //
    public static final int EXPLOSION_ROWS = 3; // notre image comporte 4 lignes d'image  (0,1,2,3) (chaque ligne comporte 4 images)
    public static final int EXPLOSION_COL = 3; // notre image comporte 4 colonnes d'image (0,1,2,3) (chaque ligne comporte 4 images)
    public static final int EXPLOSION_STEPS = 15; // notre image comporte 16 image en tous, representant les phases d'une explosion , exemple , 0 selectionnera la premiere image

    //Array liens vers les images des bombes
    public static final Image BOMBS_IMG[] = {
            new Image("file:src/main/java/com/example/spaceinvaders/images/1.png"),
            new Image("file:src/main/java/com/example/spaceinvaders/images/2.png"),
            new Image("file:src/main/java/com/example/spaceinvaders/images/3.png"),
            new Image("file:src/main/java/com/example/spaceinvaders/images/4.png"),
            new Image("file:src/main/java/com/example/spaceinvaders/images/5.png"),
            new Image("file:src/main/java/com/example/spaceinvaders/images/6.png"),
            new Image("file:src/main/java/com/example/spaceinvaders/images/7.png"),
            new Image("file:src/main/java/com/example/spaceinvaders/images/8.png"),
            new Image("file:src/main/java/com/example/spaceinvaders/images/9.png"),
            new Image("file:src/main/java/com/example/spaceinvaders/images/10.png"),
    };

    public static final int MAX_BOMBS = 10,  //nombre max de bombe afficher a l'ecran (nb de bombe a chaque fournée)
            MAX_SHOTS = MAX_BOMBS * 2;  //nombre de tire max que peux faire le joueur

}
