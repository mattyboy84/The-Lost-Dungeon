package com.company;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Fireballs {
    Random rand = new Random();
    private int layoutX;
    private int layoutY;
    private boolean Visible;
    private ImageView item = new ImageView("file:E:\\game\\fireball.png");
    private String name;

    public ImageView getImage(){
        return item;
    }

    public int getLayoutX() {
        return layoutX;
    }

    public int getLayoutY() {
        return layoutY;
    }

    public String getName() {
        return name;
    }

    public Fireballs() {
        this.layoutX = rand.nextInt(1640) + 100;
        this.layoutY = rand.nextInt(850) + 100;
        this.item = new ImageView("file:E:\\game\\fireball.png");
        this.name = "fireball";
    }
}
