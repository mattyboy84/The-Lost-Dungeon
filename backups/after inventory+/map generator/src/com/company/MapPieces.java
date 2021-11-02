package com.company;


import javafx.scene.image.ImageView;

public class MapPieces extends Base_Class{//WIDTH: 72 HEIGHT: 64

    public MapPieces(int i, int j, int room) {
        this.layoutX = (72 * i);
        this.layoutY = (64 * j);
        this.room = room;
        switch (room) {
            case 1:
                this.imageViewIMG = new ImageView("file:E:\\game\\Room.png");
                break;
            case 2:
                this.imageViewIMG = new ImageView("file:E:\\game\\ShopRoom.png");
                break;
            case 3:
                this.imageViewIMG = new ImageView("file:E:\\game\\BossRoom.png");
                break;
        }
    }
}