package com.company;

import javafx.scene.image.ImageView;

public class Inventory extends  Base_Class{

       public Inventory(int Id) {
        switch (Id){
            case 0:
                this.imageViewIMG = new ImageView("file:E:\\game\\attack boost.png");
                this.name = "Attack boost";
                this.itemType = "Consumable";
                //this.description = "You feel stronger when holding this item,\n just imagine how you'll feel when you eat it";
                this.description = "You feel stronger when\n holding this item, just\n imagine how you'll feel\n when you eat it.";
                this.effect = "+25% attack";
                break;
            case 1:
                this.imageViewIMG = new ImageView("file:E:\\game\\health potion.png");
                this.name = "Health potion";
                this.itemType = "Consumable";
                this.description = "A revitalizing drink to\n heal the wounds of\n weary travelers.";
                this.effect = "+2 Health";
                break;
        }
    }
}


