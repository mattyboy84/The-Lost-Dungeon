package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class dungeon extends Application implements EventHandler<ActionEvent> {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
//
        FileInputStream input = new FileInputStream("C:\\Users\\user\\Pictures\\6.png");
        Image image = new Image("file:C:\\Users\\user\\Pictures\\6.png");
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,new BackgroundSize(1,1,true,true,false,false));
        Background background = new Background(backgroundimage);
        ImageView imageView = new ImageView(image);
        //
        imageView.setPreserveRatio(true);
        //imageView.setFitHeight(455);
        //imageView.setFitWidth(1500);
        Circle circle1 = new Circle(1000,1000,10, Color.BLUE);
        HBox pane = new HBox(10);
        //pane.setBackground(background);
        pane.getChildren().add(circle1);
//
        pane.getChildren().add(imageView);
//
        Scene scene = new Scene(pane, 8000, 6000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("derp");
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {

    }
}
