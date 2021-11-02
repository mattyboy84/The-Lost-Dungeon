package com.company;

import javafx.scene.image.ImageView;


public class Items extends Base_Class {

    public Items(int roomX, int roomY) {
        this.layoutX = rand.nextInt(1640) + 100;
        this.layoutY = rand.nextInt(850) + 100;
        switch (rand.nextInt(2)) {
            case 0:
                this.imageViewIMG = new ImageView("file:E:\\game\\health potion.png");
                this.name = "health potion";
                break;
            case 1:
                this.imageViewIMG = new ImageView("file:E:\\game\\health potion.png");//place holder for new imageViewIMG
                this.name = "health potion";
                break;
        }
        this.roomX = roomX;
        this.roomY = roomY;
    }

}
