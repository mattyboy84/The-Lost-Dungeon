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
    ImageView imageViewIMG = new ImageView();
    Image imageIMG;
    int roomX;
    int roomY;
    int room;
    Random rand = new Random();

    public Base_Class(int layoutX, int layoutY, String name, ImageView item, int roomX, int roomY, Image item2, int room) {
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.name = name;
        this.imageViewIMG = item;
        this.roomX = roomX;
        this.roomY = roomY;
        this.imageIMG = item2;
        this.room = room;
    }

    public Base_Class() {
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

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getRoomX() {
        return roomX;
    }

    public void setRoomX(int roomX) {
        this.roomX = roomX;
    }

    public int getRoomY() {
        return roomY;
    }

    public void setRoomY(int roomY) {
        this.roomY = roomY;
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

    public void setImageViewIMG(ImageView imageViewIMG) {
        this.imageViewIMG = imageViewIMG;
    }

    public void setImage(Image item) {
        this.imageViewIMG.setImage(item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImageIMG() {
        return imageIMG;
    }

    public void setImageIMG(Image imageIMG) {
        this.imageIMG = imageIMG;
    }

    public ImageView getImageViewIMG() {
        return imageViewIMG;
    }
}