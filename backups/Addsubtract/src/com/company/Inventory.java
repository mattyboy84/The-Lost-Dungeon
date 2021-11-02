package com.company;

import javafx.scene.image.ImageView;

public class Inventory {

    private ImageView item = new ImageView();
    private String name;

    public ImageView getImage() {
        return item;
    }


    public String getName() {
        return name;
    }

    public Inventory(int Id) {
        switch (Id){
            case 0:
                this.item = new ImageView("file:E:\\game\\attack boost.png");
                this.name = "attack boost";
                break;
            case 1:
                this.item = new ImageView("file:E:\\game\\health potion.png");
                this.name = "health potion";
                break;
        }
    }
}
