package com.company;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Images extends  Base_Class{
    Random rand = new Random();
    public Images(int layoutX, int layoutY, String name, ImageView item){
        super(layoutX,layoutY,name, item);
    }

    public Images() {
        this.layoutX = rand.nextInt(1640) + 100;
        this.layoutY = rand.nextInt(850) + 100;
        this.item = new ImageView("file:E:\\game\\health potion.png");
        this.name = "health potion";
    }


}
