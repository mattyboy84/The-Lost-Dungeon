package com.company;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Effects extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group group = new Group();

        Circle circle = new Circle(500, 500, 20);
        circle.setFill(Color.RED);
        Rectangle rectangle = new Rectangle(1920, 1080);
        rectangle.setFill(Color.BLACK);
        Lighting lighting = new Lighting();
        Light.Point light = new Light.Point();
        lighting.setLight(light);
        circle.setEffect(lighting);
        group.getChildren().add(rectangle);
        group.getChildren().add(circle);


        Scene scene = new Scene(group, 900, 700);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
