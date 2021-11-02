package com.company;

import javafx.scene.image.Image;

public class Backgrounds extends  Base_Class{

    public Backgrounds(int roomX, int roomY, int room) {
        switch (room) {
            case 1:
                switch (rand.nextInt(10)) {
                    case 0:
                        imageIMG = new Image("file:E:\\game\\floors\\floor3.png");//room with vines    10%
                        break;
                    case 1:
                        imageIMG = new Image("file:E:\\game\\floors\\floor4.png");//room with wall carvings     10%
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        imageIMG = new Image("file:E:\\game\\floors\\floor2.png");//basic room       80%
                        break;
                }
                break;
            case 2:
                imageIMG = new Image("file:E:\\game\\floors\\floor7.png");//shop
                break;
            case 3:
                imageIMG = new Image("file:E:\\game\\floors\\floor6.png");//boss
                break;
        }
        this.roomX = roomX;
        this.roomY = roomY;
    }
}
