package com.company;

import javafx.scene.image.ImageView;

public class Inventory extends Base_Class {

    public Inventory(int Id) {
        switch (Id) {
            case 1:
                this.imageViewIMG = new ImageView("file:src\\game\\health potion.png");
                this.name = "Health potion";
                this.itemType = "Consumable";
                this.description = " A revitalizing drink to\n heal the wounds of\n weary travelers.";
                this.effect = "+2 Health";
                this.ID = 1;
                break;

            case 2:
                this.imageViewIMG = new ImageView("file:src\\game\\attack boost.png");
                this.name = "Attack boost";
                this.itemType = "Consumable";
                this.description = " You feel stronger when\n holding this item, just\n imagine how you'll feel\n when you eat it.";
                this.effect = "+25% attack";
                this.ID = 2;
                break;

        }
    }
}