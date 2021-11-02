package com.company;

import javafx.scene.image.ImageView;

public class Enemy extends Base_Class {
    public Enemy(int roomX, int roomY) {
        this.layoutX = 885;
        this.layoutY = 465;
        switch (rand.nextInt(2)) {
            case 0:
                this.imageViewIMG = new ImageView("file:src\\game\\sword_enemy_down_1.png");
                this.name = "sword_enemy";
                break;
            case 1:
                this.imageViewIMG = new ImageView("file:src\\game\\troll_down_1.png");
                this.name = "troll";
                break;
        }
        this.roomX = roomX;
        this.roomY = roomY;
    }
}