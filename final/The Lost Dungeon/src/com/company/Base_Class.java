package com.company;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Base_Class {
    int layoutX;
    int layoutY;
    String name;
    String itemType;
    String description;
    String effect;



    int ID;
    ImageView imageViewIMG = new ImageView();
    Image imageIMG;
    int roomX;
    int roomY;
    int room;
    Random rand = new Random();


    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public String getEffect() {
        return effect;
    }
    public void setEffect(String effect) {
        this.effect = effect;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getRoomY() {
        return roomX;
    }
    public int getRoomX() {
        return roomY;
    }
    public int getLayoutX() {
        return layoutX;
    }
    public void setLayoutX(int layoutX) {
        this.layoutX = layoutX;
    }
    public int getLayoutY() {
        return layoutY;
    }
    public void setLayoutY(int layoutY) {
        this.layoutY = layoutY;
    }
    public Image getImage2() {
        return imageIMG;
    }
    public ImageView getImage() {
        return imageViewIMG;
    }
    public void setImage(Image item) {
        this.imageViewIMG.setImage(item);
    }

}