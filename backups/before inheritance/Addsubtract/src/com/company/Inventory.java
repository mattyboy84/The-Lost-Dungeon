package com.company;

import javafx.scene.image.ImageView;

public class Inventory {

    private ImageView item = new ImageView("file:E:\\game\\health potion.png");
    private String name;

    public ImageView getImage() {
        return item;
    }




    public String getName() {
        return name;
    }

    public Inventory() {


        this.item = new ImageView("file:E:\\game\\health potion.png");
        this.name = "health potion";

    }
}
