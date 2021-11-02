package com.company;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Magic {
    Random rand = new Random();
    private int layoutX;
    private int layoutY;
    private boolean Visible;
    private ImageView item = new ImageView("file:E:\\game\\magic.png");
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

    public Magic() {
        this.item = new ImageView("file:E:\\game\\magic.png");
        this.name = "magic";
    }
}
