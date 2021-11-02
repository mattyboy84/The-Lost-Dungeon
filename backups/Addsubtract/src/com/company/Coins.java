package com.company;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Coins {
    Random rand = new Random();
    private int layoutX;
    private int layoutY;
    private boolean Visible;
    private ImageView item = new ImageView("file:E:\\game\\Coin.png");
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

    public Coins() {
        this.layoutX = rand.nextInt(1640) + 100;
        this.layoutY = rand.nextInt(850) + 100;
        this.item = new ImageView("file:E:\\game\\Coin.png");
        this.name = "Coin";
    }



}
