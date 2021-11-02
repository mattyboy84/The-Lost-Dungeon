package com.company;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CharacterMovement3 extends Application {

    private static final double W = 600, H = 400;

    private static final String HERO_IMAGE_LOC =
            "http://icons.iconarchive.com/icons/raindropmemory/legendora/64/Hero-icon.png";

    private Image heroImage;
    private Node hero;
    int i;

    boolean running, goNorth, goSouth, goEast, goWest;

    @Override
    public void start(Stage stage) throws Exception {
        heroImage = new Image(HERO_IMAGE_LOC);
        hero = new ImageView("http://icons.iconarchive.com/icons/raindropmemory/legendora/64/Hero-icon.png");

        Group dungeon = new Group(hero);

        moveHeroTo(W / 2, H / 2);

        Scene scene = new Scene(dungeon, W, H, Color.FORESTGREEN);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    goNorth = true;
                    break;
                case DOWN:
                    goSouth = true;
                    break;
                case LEFT:
                    goWest = true;
                    break;
                case RIGHT:
                    goEast = true;
                    break;
                case SHIFT:
                    running = true;
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    goNorth = false;
                    break;
                case DOWN:
                    goSouth = false;
                    break;
                case LEFT:
                    goWest = false;
                    break;
                case RIGHT:
                    goEast = false;
                    break;
                case SHIFT:
                    running = false;
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
        Timeline animationTimeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            int dx = 0, dy = 0;
            if (goNorth) dy -= 1;
            if (goSouth) dy += 1;
            if (goEast) dx += 1;
            if (goWest) dx -= 1;
            moveHeroBy(dx, dy);
        }));
        animationTimeline.setCycleCount(Timeline.INDEFINITE);
        animationTimeline.play();
    }

    private void moveHeroBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = hero.getBoundsInParent().getWidth() / 2;
        System.out.println("Local " + hero.getBoundsInLocal());
        System.out.println("Parent " + hero.getBoundsInParent());
        final double cy = hero.getBoundsInParent().getHeight() / 2;

        double x = cx + hero.getLayoutX() + dx;
        double y = cy + hero.getLayoutY() + dy;

        moveHeroTo(x, y);
    }

    private void moveHeroTo(double x, double y) {
        final double cx = hero.getBoundsInParent().getWidth() / 2;
        final double cy = hero.getBoundsInParent().getHeight() / 2;

       if (x - cx >= 0  && y - cy >= 0) {
            hero.relocate(x - cx, y - cy);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
