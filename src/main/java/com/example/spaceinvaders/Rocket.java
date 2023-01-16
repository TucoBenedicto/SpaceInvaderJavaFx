package com.example.spaceinvaders;

import javafx.scene.image.Image;
// Icon made by Freepik from www.flaticon.com
// visit: https://www.youtube.com/user/CbX397/


//Etape 2
//player
public class Rocket { //Creation de la class Rocket propre au joueur

    int posX, posY, size; //size : taille du vaisseau du joueur
    boolean exploding, //exploading : si true  declanche la sequence d'images explosions.png
            destroyed; //destroyed : si true alors la sequence d'image explosion.png est terminée, nous permettra
    Image img; //permet de recuperer le lien de l'image
    int explosionStep = 0; //1ere image de la sequence d'image "explosion.png"

    //Constructeur
    public Rocket(int posX, int posY, int size, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        img = image;
    }

    //Methode tire
    public Shot shoot() { //Methode shoot qui permet de tirer les missiles du joueurs
        //elle est typé avec la class "Shot" parce qu'elle retourne "Shot"
        return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
        //on positionne les tires au millieu haut du vaisseau
        //size  = Constants.PLAYER_SIZE = 60
    }

    public void update() { //methode qui va permettre de lancer la sequence d'image "explosion.png"
        if (exploding)
            explosionStep++; //si exploading true , on incremente explisionStep qui represent les images de "explosion.png
        destroyed = explosionStep > Constants.EXPLOSION_STEPS; // destroyed = true si explosion depasse les 15 images de "explosion.png"
    }

    //methode draw en lien direct avec la methode update
    public void draw() {//Methode permettant d'afficher l'explosion (du fichier "explosion.png)
        //argument drawimage :
        // img - the image to be drawn or null.
        //v : sx - the source rectangle's X coordinate position.
        //v1 : sy - the source rectangle's Y coordinate position.
        //v3 : sw - the source rectangle's width.
        //v4 : sh - the source rectangle's height.
        //dx - the destination rectangle's X coordinate position.
        //dy - the destination rectangle's Y coordinate position.
        //dw - the destination rectangle's width.
        //dh - the destination rectangle's height.
        if (exploding) {
            SpaceInvaders.gc.drawImage(Constants.EXPLOSION_IMG,
                    explosionStep % Constants.EXPLOSION_COL * Constants.EXPLOSION_W, // //The statement "% EXPLOSION_COL * EXPLOSION_W" makes sure that the explosion gif is played at the exact position where the space ship is shot. You could omit the instruction or replace it with a number
                    (explosionStep / Constants.EXPLOSION_ROWS) * Constants.EXPLOSION_H + 1,
                    Constants.EXPLOSION_W,
                    Constants.EXPLOSION_H,
                    posX,
                    posY,
                    size,
                    size);
        } else { //si exploding false on laisse le vaisseau du joueur affiché
            SpaceInvaders.gc.drawImage(img, posX, posY, size, size);
        }
    }

    //Si le joueur(Rocket) entre en collision avec l'enemie(Bombe) //on ne parle ici que de collision pas en encore d'explosion
    public boolean colide(Bomb bomb) { //Bomb other represente l'enenemi
        int d = SpaceInvaders.distance(this.posX + this.size / 2, this.posY + this.size / 2, bomb.posX + bomb.size / 2, bomb.posY + bomb.size / 2);//Calcul distance entre 2 points, cf. methode distance plus bas
        //System.out.println(d);
        //System.out.println(other);
        return d < bomb.size / 2 + this.size / 2; //on ne recuper que la taille (other.size) des missiles
            /*
            !! cette methode methode retourne un boolean, par consequent il faut comprendre le retour comme ceci :
            Si "d" est inferieur au resultat du calcul (other.size / 2 + this.size / 2) alors d = true et va retourner true , sinon false
             */
    }

    //si les condition sont respecter alors in declanche l'explosion
    public void explode() {
        exploding = true;
        //explosionStep = -1;  cette ligne de code ne sert a priori a rien
    }

}
