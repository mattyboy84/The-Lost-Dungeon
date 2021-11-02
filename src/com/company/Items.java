package com.company;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class Items extends Base_Class {
    Label label;
    ImageView coinIcon;

    public Label getLabel() {
        return label;
    }
    public void setLabel(Label label) {
        this.label = label;
    }

    public ImageView getCoinIcon() {
        return coinIcon;
    }
    public void setCoinIcon(ImageView coinIcon) {
        this.coinIcon = coinIcon;
    }



    public Items(int roomX, int roomY, String state, int shopItemNum) {//for random item generation

        switch (state) {
            case "random":
                this.layoutX = rand.nextInt(1640) + 100;
                this.layoutY = rand.nextInt(850) + 100;
                switch (rand.nextInt(2)) {
                    case 0:
                        this.imageViewIMG = new ImageView("file:src\\game\\health potion.png");
                        this.name = "health potion";
                        this.state = state;
                        this.ID = 1;
                        break;
                    case 1:
                        this.imageViewIMG = new ImageView("file:src\\game\\health potion.png");//place holder for new item / imageViewIMG
                        this.name = "health potion";
                        this.state = state;
                        this.ID = 1;
                        break;
                }
                break;

            case "shop":
                this.layoutX = 750 + (150 * shopItemNum);
                this.layoutY = 500;
                switch (shopItemNum) {
                    case 0:
                        //staff
                        this.imageViewIMG = new ImageView("file:src\\game\\staff.png");
                        this.price = 5;
                        this.name = "staff";
                        this.state = state;
                        this.label = new Label(String.valueOf(price));
                        this.coinIcon = new ImageView("file:src\\game\\Coin Icon.png");

                        break;
                    case 1:
                        //map
                        this.imageViewIMG = new ImageView("file:src\\game\\MapItem.png");
                        this.price = 2;
                        this.name = "map";
                        this.state = state;
                        this.label = new Label(String.valueOf(price));
                        this.coinIcon = new ImageView("file:src\\game\\Coin Icon.png");

                        break;
                    case 2:
                        //attack boost
                        this.imageViewIMG = new ImageView("file:src\\game\\attack boost.png");
                        this.price = 5;
                        this.name = "attack boost";
                        this.state = state;
                        this.label = new Label(String.valueOf(price));
                        this.ID = 2;
                        this.coinIcon = new ImageView("file:src\\game\\Coin Icon.png");

                        break;
                }
                break;
        }
        this.roomX = roomX;
        this.roomY = roomY;
    }
}
