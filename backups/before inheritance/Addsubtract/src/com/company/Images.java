package com.company;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Images extends  Base_Class{
    Random rand = new Random();
   // private int layoutX;
    //private int layoutY;
    private ImageView item = new ImageView("file:E:\\game\\health potion.png");
   // private String name;
    public Images(int layoutX, int layoutY, String name){
        super(layoutX,layoutY,name);
    }

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

    public Images() {
        this.layoutX = rand.nextInt(1640) + 100;
        this.layoutY = rand.nextInt(850) + 100;
        this.item = new ImageView("file:E:\\game\\health potion.png");
        this.name = "health potion";
    }


}
