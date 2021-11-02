package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MovingThing extends Application {
    Boolean goUp, goDown, goLeft, goRight;
    Node icon;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        /*
        HBox pane = new HBox();
        Circle circle = new Circle(200, 200, 10, Color.BLUE);
        pane.getChildren().addAll(circle);
        Scene scene = new Scene(pane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("sdf");
        stage.show();
*/
        Group group = new Group();
        Text title = new Text(260,10,"sdafg");
        group.getChildren().add(title);
        Image myIconImage = new Image("http://www.iconarchive.com/show/superhero-avatar-icons-by-hopstarter/Avengers-Iron-Man-icon.html");
        ImageView myIcon = new ImageView(myIconImage);
        icon=myIcon;
        icon.relocate(300,200);
        group.getChildren().add(icon);
        Scene scene = new Scene(group,600,400,Color.BLUE);
        stage.setScene(scene);
        stage.show();



        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        goUp = true;
                        break;
                    case DOWN:
                        goDown = true;
                        break;
                    case LEFT:
                        goLeft = true;
                        break;
                    case RIGHT:
                        goRight = true;
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        goUp = false;
                        break;
                    case DOWN:
                        goDown = false;
                        break;
                    case LEFT:
                        goLeft = false;
                        break;
                    case RIGHT:
                        goRight = false;
                        break;
                }
            }
        });


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long e) {
                double delta = 4;
                double CurrX = icon.getLayoutX();
                double CurrY = icon.getLayoutY();

                if (goUp) CurrY -= delta;
                if (goDown) CurrY += delta;
                if (goLeft) CurrX -= delta;
                if (goRight) CurrX += delta;
                icon.relocate(CurrX, CurrY);

            }
        };
        timer.start();
    }
}
