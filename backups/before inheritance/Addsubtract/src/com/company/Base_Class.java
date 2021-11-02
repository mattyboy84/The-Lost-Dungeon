package com.company;

import javafx.scene.image.ImageView;

public class Base_Class {
    int layoutX;
    int layoutY;
    String name;


    public Base_Class(int layoutX, int layoutY, String name) {
        this.layoutX=layoutX;
        this.layoutY=layoutY;
        this.name=name;

    }

    public Base_Class(){

    }
    private ImageView item = new ImageView("file:E:\\game\\blank.png");
    public ImageView getImage(){
        return item;
    }



}
