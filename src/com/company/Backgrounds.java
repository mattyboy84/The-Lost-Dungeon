package com.company;

import javafx.scene.image.Image;

public class Backgrounds extends Base_Class {

    public Backgrounds(int roomX, int roomY, int room) {
        //roomX and roomY are indicates for the 2D array
        //room will be 1,2 or 3
        switch (room) {
            case 1://normal room
                switch (rand.nextInt(7)) {
                    case 0:
                        imageIMG = new Image("file:src\\game\\floors\\floor3.png");//room with vines    14%
                        break;
                    case 1:
                        imageIMG = new Image("file:src\\game\\floors\\floor4.png");//room with wall carvings     14%
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        imageIMG = new Image("file:src\\game\\floors\\floor2.png");//basic room       72%
                        break;
                }
                break;
            case 2://shop
                imageIMG = new Image("file:src\\game\\floors\\floor7.png");//shop
                break;
            case 3://boss
                imageIMG = new Image("file:src\\game\\floors\\floor6.png");//boss
                break;
        }
        this.roomX = roomX;
        this.roomY = roomY;
    }
}
