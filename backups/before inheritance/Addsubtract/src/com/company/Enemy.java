package com.company;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Enemy extends Base_Class{
    Random rand = new Random();
    //private int layoutX;
    //private int layoutY;
    private ImageView item = new ImageView();
    //private String name;

    public ImageView getImage() {
        return item;
    }

    public void setImage(Image item) {
        this.item.setImage(item);
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

    public Enemy() {
        this.layoutX = rand.nextInt(1)+780;
        this.layoutY = rand.nextInt(1)+540;
        switch (rand.nextInt(2)) {
            case 0:
                this.item = new ImageView("file:E:\\game\\sword_enemy_down_1.png");
                this.name = "sword_enemy";
                break;
            case 1:
                this.item = new ImageView("file:E:\\game\\troll_down_1.png");
                this.name = "troll";
                break;

        }
    }
}
