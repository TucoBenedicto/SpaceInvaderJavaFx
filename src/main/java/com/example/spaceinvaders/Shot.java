package com.example.spaceinvaders;

import javafx.scene.paint.Color;

//Etape 4
//bullets
//Tire du joueur
public class Shot {
    public boolean toRemove; //si false = va permettre d'effacer le tire une fois l'avion enemi toucher , si true les tirent deviennent traversant.
    int posX, posY, speed = 10;
    static final int size = 6; //Taille de la balle.
    public Shot(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    //Methode permettant le deplacement de la balle vers le haut , d'ou la decrementation
    public void update() {
        posY -= speed;
    }
    //Dessin de la ball
    public void draw() {
        if (SpaceInvaders.score >= 50 && SpaceInvaders.score <= 70 || SpaceInvaders.score >= 120) { // Si score entre 50 & 70 ou > à 120 , alors on utilise le mega tire
            SpaceInvaders.gc.setFill(Color.YELLOWGREEN); //sera vert clair
            speed = 50; //vitesse accrue
            SpaceInvaders.gc.fillRect(posX - 5, posY - 10, size + 10, size + 30); // taille des tires plus grand pour de plus gros degat
        } else { // Sinon on utilise le tire classique
            SpaceInvaders.gc.setFill(Color.RED);
            SpaceInvaders.gc.fillOval(posX, posY, size, size);
        }
    }
    public boolean colide(Bomb bomb) {// Colision entre les tires du joueur et les bombes enemies
        //Methode mesurant la distance entre les tirent et les
        int distance = SpaceInvaders.distance(this.posX + size / 2, this.posY + size / 2, bomb.posX + bomb.size / 2, bomb.posY + bomb.size / 2);
        return distance < bomb.size / 2 + size / 2; //si la distance entre la position du tire et la position de la bombe , est inferieur a la taille de la bombe il y colision donc true.
    }
}