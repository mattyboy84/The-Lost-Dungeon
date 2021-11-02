package com.company;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.util.*;
import java.util.*;
public class ManyBalls extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }
    final private int WIDTH = 600;
    final private int HEIGHT = 500;
    final private int BALL_SIZE = 20;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    @Override public void start(final Stage primaryStage){
        Group root = new Group();
        for (int i = 0; i < 10; i++)
        balls.add(new Ball(BALL_SIZE, WIDTH, HEIGHT, balls));
        root.getChildren().addAll(balls);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Lots of Bouncing Balls");
        primaryStage.setScene(scene);
        primaryStage.show();

        KeyFrame k = new KeyFrame(Duration.millis(10),
                e ->
                {
                    for (Ball ball : balls)
                        ball.move();
                });
        Timeline t = new Timeline(k);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }
    public class Ball extends Circle
    {
        public double x_speed;
        public double y_speed;
        public double radius;
        private double fieldWidth;
        private double fieldHeight;
 public Ball(double radius,
        double fieldWidth,
        double fieldHeight,
        ArrayList<Ball> balls)
        {
            super();
            this.radius = radius;
            this.fieldWidth = fieldWidth;
            this.fieldHeight = fieldHeight;
            super.setRadius(radius);
            super.setCenterX(
                    Math.random() * (fieldWidth - this.radius) + 1);
            super.setCenterY(
                    Math.random() * (fieldHeight - this.radius) + 1);
            this.x_speed = Math.random() * 5 + 1;
            this.y_speed = Math.random() * 5 + 1;
            RadialGradient g = new RadialGradient(
                    0, 0,
                    0.35, 0.35,
                    0.5,
                    true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0.0, Color.WHITE),
                    new Stop(1.0, Color.RED));
            super.setFill(g);
        }
        public void move()
        {
            super.setCenterX(super.getCenterX() + this.x_speed);
            super.setCenterY(super.getCenterY() + this.y_speed);
            // Detect collision with left edge ➝97
            if (super.getCenterX() <= this.radius)
            {
                super.setCenterX(this.radius);
                this.x_speed = -this.x_speed;
            }
            // Detect collision with right edge ➝104
            if (super.getCenterX() >= this.fieldWidth - this.radius)
            {
                super.setCenterX(this.fieldWidth - this.radius);
                this.x_speed = -this.x_speed;
            }
            // Detect collision with top edge ➝111
            if (super.getCenterY() <= this.radius)
            {
                super.setCenterY(this.radius);
                this.y_speed = -this.y_speed;
            }
            // Detect collision with bottom edge ➝118
            if (super.getCenterY() >= this.fieldHeight - this.radius)
            {
                super.setCenterY(this.fieldHeight - this.radius);
                this.y_speed = -this.y_speed;
            }
            // Detect collision with other balls ➝125
            for (Ball b : balls)
            {
                if (b != this &&
                        b.intersects(super.getLayoutBounds()))
                {
                    double tempx = this.x_speed;
                    double tempy = this.y_speed;
                    this.x_speed = b.x_speed;
                    this.y_speed = b.y_speed;
                    b.x_speed = tempx;
                    b.y_speed = tempy;
                    break;
                }
            }
        }
    }
}