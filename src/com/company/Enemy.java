package com.company;

import javafx.scene.image.ImageView;

public class Enemy extends Base_Class {
    int health;
    int maxHealth;


    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getMaxHealth() {
        return maxHealth;
    }

    
    public Enemy(int roomX, int roomY) {
        this.layoutX = 885;
        this.layoutY = 465;
        switch (rand.nextInt(2)) {
            case 0:
                this.imageViewIMG = new ImageView("file:src\\game\\sword_enemy_down_1.png");
                this.name = "sword_enemy";
                this.health = 10;
                this.maxHealth = 10;
                break;
            case 1:
                this.imageViewIMG = new ImageView("file:src\\game\\troll_down_1.png");
                this.name = "troll";
                this.health = 10;
                this.maxHealth = 10;
                break;
        }
        this.roomX = roomX;
        this.roomY = roomY;
    }
}