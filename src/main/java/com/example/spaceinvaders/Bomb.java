package com.example.spaceinvaders;

import javafx.scene.image.Image;

//Etape 3
//computer player (vaisseaux enemies ou bombes enemies
public class Bomb extends Rocket {
    int speed = (SpaceInvaders.score / 5) + 2; // on augmente la vitesse du jeux tous les 5 points de score , on demarre le jeux avec une vitesse de 2.

    //Constructeur
    public Bomb(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);// "super" indique que l'on va utiliser les variables (posX, posY ,size, image) de la class parent (class Rocket), ce qui evitera de surcharger la class actuel(current)
    }

    //Methode permettant l'actualisation/declanchement de l'explosion des vaisseaux enenmies + augmentation de la vitesse
    public void update() {
        super.update(); //on recupere la methode update de la class parent (Rocket Class), qui pour rappel lance la sequence d'image liée à l'explosion
            /*
            //Deplacment des vaisseaux enemies ,si "!exploading & !destroyed = false" comme c'est le cas ci-dessous ,
            alors  on increment "posY" (qui est le deplacement des vaiseeaus enemies) grace à la valeur de "speed"
             */
        if (!exploding && !destroyed) posY += speed;
        //System.out.println(posY);
        //Si la position des enemeies (posY) est superieur à la hauteur de notre vaisseau , cela indique une collision , alors destroyed = true.
        if (posY > Constants.HEIGHT) destroyed = true; // Renvoi true a chaque vaisseau enenmie detruit
    }
}
