package com.example.spaceinvaders;

import javafx.scene.paint.Color;

//environment
//Etape 5
public class Universe {
    int posX, posY;
    private int h, w, r, g, b;
    private double opacity;
    public Universe() {
        posX = Constants.RAND.nextInt(Constants.WIDTH);// random valu entre 0 et Constants.WIDTH
        posY = 0;
        //w , h , r , g , b = sont les differents elements de l'univers
        //w & h = taille des elements de l'univers
        w = Constants.RAND.nextInt(5) + 1; //+1 pour eviter le 0, ce qui n'affciherait plus la forme
        h = Constants.RAND.nextInt(5) + 1;
        //r,g,b : couleur des elements
        //r = Constants.RAND.nextInt(100) + 150;
        r = Constants.RAND.nextInt(100)+150; //+150 pour avoir des couleurs plus pastel
        g = Constants.RAND.nextInt(100)+150;
        b = Constants.RAND.nextInt(100)+150;
        //opacité (comprise entre 0 et 1)
        opacity = Constants.RAND.nextFloat();
        //if (opacity < 0) opacity *= -1; ??
        //if (opacity > 0.5) opacity = 0.5; ??
    }
    public void draw() {
        //opacité comprise entre 0.1 & 0.8
        if (opacity > 0.8) opacity -= 0.01;
        if (opacity < 0.1) opacity += 0.01;
        //System.out.println(opacity);

        SpaceInvaders.gc.setFill(Color.rgb(r, g, b, opacity)); //Couleur
        SpaceInvaders.gc.fillOval(posX, posY, w, h); //position et taille random
        posY += 20; //Vitesse de defilement des particules
    }
}
