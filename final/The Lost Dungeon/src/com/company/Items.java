package com.company;

import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;


public class Items extends Base_Class {

    public Items(int roomX, int roomY) {//for random generation
        this.layoutX = rand.nextInt(1640) + 100;
        this.layoutY = rand.nextInt(850) + 100;
        switch (rand.nextInt(2)) {
            case 0:
                this.imageViewIMG = new ImageView("file:src\\game\\health potion.png");
                this.name = "health potion";
                this.ID = 1;
                break;
            case 1:
                this.imageViewIMG = new ImageView("file:src\\game\\health potion.png");//place holder for new item / imageViewIMG
                this.name = "health potion";
                this.ID = 1;
                break;
        }
        this.roomX = roomX;
        this.roomY = roomY;
    }
}
