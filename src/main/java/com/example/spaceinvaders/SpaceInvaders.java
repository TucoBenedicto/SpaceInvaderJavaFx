package com.example.spaceinvaders;

// Icon made by Freepik from www.flaticon.com
// visit: https://www.youtube.com/user/CbX397/

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SpaceInvaders extends Application {

    boolean gameOver = false; //Par defaut le gameOver est false, en debut de partie

    //Contexte graphique ,
    //On ne dessine pas directement dans le Canvas, mais dans un objet de type "GraphicsContext".
    //Canvas is an image that can be drawn on using a set of graphics commands provided by a GraphicsContext. It is a high-level tool for doing painting.
    public static GraphicsContext gc;

    Rocket player; // player : Vaisseau du joueur recuperant sa positionx, y , son image ... grace à la class "Rocket"
    List<Shot> shots; // shots : va recuper la position x,y des tires , leur taille , leur vitesse ... , grace à la class "Shot" que l'on mettra sous forme de "List"(ArrayList) ne pas confondre avec Array "[]" voir def plus bas
    List<Universe> univ; //univ : recupere la position x,y , taille , forme , couleur ... des etoiles/particules , grace à la class "Univers" et que l'on place sous forme de List
    List<Bomb> Bombs; //Bombs (tire secondaire des 20 enemies abattus : recupere la position x,y , taille , forme , couleur ... des tires  , grace à la class "Bomb" et que l'on place sous forme de List

    private double mouseX; // le deplacment du jeux se fera à l'aide de la sourie uniquement sur l'axe horizontal, on recupera donc la position x.
    public static int score; //Score du jeu

    //start
    public void start(Stage stage) throws Exception {
        /*
        ici on aurait pu utiliser "Pane" (panel) mais on prefera "Canvas"
        A canvas gives you more flexibility than a pane. Also, if you need performance, you should use a canvas.
        Dans notre cas il y aura beaucoup d'elements en mouvement , l'univers , les vaisseau , les missiles...
         */
        Canvas canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT); //New background (canvas)

        gc = canvas.getGraphicsContext2D();//Voir explication ci dessus
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
        canvas.setOnMouseClicked(e -> {
            if (shots.size() < Constants.MAX_SHOTS) shots.add(player.shoot());
            if (gameOver) {
                gameOver = false;
                setup();
            }
        });
        setup();
        //On affiche du texte dans la barre de menu
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Space Invaders");
        stage.show();
        System.out.println();
    }

    //Etape 7
    //setup the game
    private void setup() {
        univ = new ArrayList<>(); // ArrayList :  Array is a fixed length data structure whereas ArrayList is a variable length Collection class. We cannot change length of array once created in Java but ArrayList can be changed.
        shots = new ArrayList<>();
        Bombs = new ArrayList<>();
        player = new Rocket(Constants.WIDTH / 2, Constants.HEIGHT - Constants.PLAYER_SIZE, Constants.PLAYER_SIZE, Constants.PLAYER_IMG);//Creation du vaiseau du joueur
        score = 0;
        //API Stream est equivalent a une boocle "for" , elle permet de faire les operation sur une seul ligne , dans le cas de l'IntStream il recuper des int (entier)
        IntStream.range(0, Constants.MAX_BOMBS).mapToObj(i -> this.newBomb()).forEach(Bombs::add);
        /*
        ici on ajoute (add) à notre variable (ArrayList) "bombs", l'objet "newBomb" (de la class Bomb) et le transformer en stream (object-value) grace à la methode "mapToObj", tache que l'on fait 10 fois grace a "range".
         */
        //mapToObj: va transformer/retourner un stream d'elements (object-value) "newBomb"
        // operaot :: = The double colon (::) operator, also known as method reference operator in Java, is used to call a method by referring to it with the help of its class directly. They behave exactly as the lambda expressions. The only difference it has from lambda expressions is that this uses direct reference to the method by name instead of providing a delegate to the method.
        //System.out.println(Bombs.get(0));
    }

    //run Graphics
    //Etape 8
    private void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        //Affichage du score
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        //Affichage du message de gameover , si gameOver = true
        if (gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", Constants.WIDTH / 2, Constants.HEIGHT / 2.5);
            //	return;
        }
        univ.forEach(Universe::draw); //on ajoute chaque element de la class "Universe" et on la dessine

        player.update(); //lance l'animation de l'explosion du joueur quand le joueur est touché par une bombe.
        player.draw(); // affiche le vaisseau du joueur
        player.posX = (int) mouseX; // Recupere la position de la sourie (uniquement sur l'axe des x)

        //API stream
        //peek() : renvoi le stream de la valeur/fonction en argument
        //ce stream va permettre de lancer l'explosion + dessiner la class Bombs
        Bombs.stream().peek(Rocket::update).peek(Rocket::draw).forEach(e -> {
            //e = contient tous les elements "bomb" dans la list "Bombs"
            //System.out.println(e);
            //Si le joueur entre en colision avec une bombe (true)
            if (player.colide(e) && !player.exploding) {
                player.explode();//
            }
        });

        //On limite le nombre de vaisseau enemie (bombe) à 20 sur le canvas
        for (int i = shots.size() - 1; i >= 0; i--) { //shots.size = 20
            Shot shot = shots.get(i);
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw();
            for (Bomb bomb : Bombs) {
                if (shot.colide(bomb) && !bomb.exploding) {
                    score++;//Get points for every bomb you shot
                    bomb.explode();
                    shot.toRemove = true; //si on met sur false , les tirent devienent traversant et peuvent abbatre les enemeies derrieres.
                }
            }
        }
        //Si le nombre de vaisseau enemie (bomb )est inferieur a 10, on creer un nouveau vaisse enemie
        for (int i = Bombs.size() - 1; i >= 0; i--) { //Bombs.size() = 10
            if (Bombs.get(i).destroyed) {
                Bombs.set(i, newBomb());
            }
        }

        gameOver = player.destroyed; //gameOver passe à "true"

        if (Constants.RAND.nextInt(10) > 2) {
            univ.add(new Universe());//Affiche l'univers
        }
        for (int i = 0; i < univ.size(); i++) {
            if (univ.get(i).posY > Constants.HEIGHT)
                univ.remove(i);
        }
    }


    //Etape 6
    //Creation d'une methode bombe qui va creer des nouveau vaisseau enemies/Bombe
    Bomb newBomb() {
        return new Bomb(50 + Constants.RAND.nextInt(Constants.WIDTH - 100), 0, Constants.PLAYER_SIZE, Constants.BOMBS_IMG[Constants.RAND.nextInt(Constants.BOMBS_IMG.length)]);
    }

    static int distance(int x1, int y1, int x2, int y2) {
        //Methode permettant de calcuer la distance entre 2 point A(x,y) et B(x,y)
        //Ici on calcule la distance entre notre vaisseau et les rocket enemies.
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)); //La formule est , distance (A,B) = √(x2 -x1)²+(y2-y1)²
        //on oublie pas le cast en int (int) car cette formule nous renvoi un double.
    }

    //  Le but de votre fonction   main  est de démarrer votre programme.
    public static void main(String[] args) {
        /*
        Launch()
        The launch() method is a static method located in the Application class.
        This method launches the JavaFX runtime and your JavaFX application.
        The launch() method will detect from which class it is called, so you don't
        have to tell it explicitly what class to launch
         */
        launch();
    }
}
