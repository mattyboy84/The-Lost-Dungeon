package com.company;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Base_Class {
    int layoutX;
    int layoutY;
    String name;
    ImageView item = new ImageView();

    public Base_Class(int layoutX, int layoutY, String name, ImageView item) {
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.name = name;
        this.item = item;
    }

    public int getLayoutX(){
        return layoutX;
    }

    public int getLayoutY(){
        return layoutY;
    }

    public ImageView getImage() {
        return item;
    }

    public String getName(){
        return name;
    }

    public Base_Class() {

    }
    public void setImage(Image item){
        this.item.setImage(item);
    }
}