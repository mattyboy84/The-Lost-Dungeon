package com.company;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.Pulse;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class The_Lost_Dungeon extends Application {
    //|| CTRL SHIFT H TODO
    //smaller annoying enemies in rooms.
    //enemies drop items.
    //chests in rooms.
    //more items, more rooms.
    //shop items should be generated in the items class, always keep at the end of array. pressing E branches into item name - more efficient.
    static int positionX = 9;//the representation of this variable breaks if non global
    static int positionY = 9;//the representation of this variable breaks if non global
    private static MediaPlayer MEDIAWalking;
    private static MediaPlayer MEDIABackground;
    private static MediaPlayer MEDIABoss;
    int musicVolume = 1;//the representation of this variable breaks if non global
    String enemyAnimation = "b";//the representation of this variable breaks if non global
    boolean activeBoss = false;
    static boolean activestaff = false;
    static boolean activeAttackBoost = false;
    static boolean activeMap = false;
    static boolean defeatedBoss = false;
    String direction = "down";//the representation of this variable breaks if non global
    String attackDirection = "down";//the representation of this variable breaks if non global
    int scoretimer = 0;
    boolean attacking = false;
    int walking = 0;
    int i = 0;
    int j = 0;
    int l = 0;
    int k = 0;
    int q = 0;
    int v = 0;
    int y = 0;
    int c = 0;
    int s = 0;
    int f = 0;
    int n = 0;
    int timePassed = 0;
    int timepassed1 = 0;
    int timePassed2 = 0;
    int timePassed3 = 0;
    int timepassed7 = 0;
    int timepassed8 = 0;
    int defeatedEnemies = 0;
    int number = 0;
    String sound;
    Timeline walkSounds;
    Timeline EnemyMover;
    Timeline subEnemy;
    Timeline Bossroomtimeline;
    Timeline Bossroomtimeline2;
    double attackmultiplier = 1;
    boolean up = false;
    boolean right = false;
    boolean left = false;
    boolean down = false;
    //health
    int hero_Health = 6;//the representation of this int breaks if non global
    //
    int CoinNumber = 0;//the representation of this int breaks if non global
    //
    int Score = 1000;//the representation of this int breaks if non global
    String playerName = "";//the representation of this String breaks if non global

    public static void main(String[] args) {
        launch(args);
    }

    //check if there are errors in physics engine
    @Override
    public void start(Stage stage) {
        Group group = new Group();
        Scene scene = new Scene(group, 1920, 1080, Color.BLACK);
        //scene.setCursor(Cursor.NONE);//cursor manipulation

        //
        ArrayList<Backgrounds> backgrounds = new ArrayList<>();
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Magic> magic = new ArrayList<>();
        ArrayList<Fireballs> fireballs = new ArrayList<>();//Dynamic arrays
        ArrayList<Inventory> inventory = new ArrayList<>();
        ArrayList<Items> items = new ArrayList<>();
        ArrayList<MapPieces> mapPieces = new ArrayList<>();
        //
        int[][] inventoryARR = new int[5][6];
        for (int m = 0; m < 5; m++) {
            for (int o = 0; o < 6; o++) {
                inventoryARR[m][o] = 0;
            }
        }
        System.out.println("monitor width: " + Toolkit.getDefaultToolkit().getScreenSize().width); //TODO
        System.out.println("monitor height: " + Toolkit.getDefaultToolkit().getScreenSize().height);//TODO
        //laptop - 1600,900
        //
        String animationHero = "b";
        //incorrect username message.
        Label badUserName = new Label();
        labelMaker(badUserName, "Name cannot contain spaces, special characters and must have between 4 and 15 characters", 745, 1008, 16, Color.WHITE, false, 1);
        //
        Label itemName = new Label();
        Label itemType = new Label();
        Label itemDescription = new Label();
        Label itemEffect = new Label();
        //
        labelMaker(itemName, "", 1075, 330, 21, Color.WHITE, true, 1);
        labelMaker(itemType, "", 1035, 390, 21, Color.ORANGE, true, 1);
        labelMaker(itemDescription, "", 1030, 430, 21, Color.WHITE, true, 1);
        labelMaker(itemEffect, "", 1030, 570, 21, Color.ORANGE, true, 1);
        //
        group.getChildren().addAll(itemName, itemType, itemDescription, itemEffect);
        //
        TranslateTransition translate = new TranslateTransition();
        final Group menuGroup = new Group();
        menuGroup.getChildren().add(badUserName);
        ImageView hero = new ImageView("file:src\\game\\link_running_down_1.png");
        //
        ImageView boss = new ImageView("file:src\\game\\Wizard.png");
        Label enterName = new Label();
        labelMaker(enterName, "Enter Name Here:", 915, 926, 21, Color.WHITE, true, 1);
        menuGroup.getChildren().add(enterName);
        ImageView menu = new ImageView("file:src\\game\\Menu.png");
        menuGroup.getChildren().add(menu);
        menu.toBack();
        ImageView title = new ImageView("file:src\\game\\Title.png");
        menuGroup.getChildren().add(title);
        title.relocate(560, 100);
        title.toFront();
        final Scene menuScene = new Scene(menuGroup, 1920, 1080);
        menuGroup.getChildren().add(hero);
        hero.relocate(940, 760);
        TextField textField = new TextField();
        textField.setPrefSize(180, 32);
        textField.relocate(915, 958);
        textField.getStylesheets().add("com/company/Style.css");//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html#typeeffect
        ImageView controls = new ImageView("file:src\\game\\controls.png");
        controls.relocate(313, 368);
        menuScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {//if Enter is pressed on the menu screen, this runs
                if (!textField.getText().equalsIgnoreCase("") && textField.getText().length() >= 4 && textField.getText().length() <= 15 && textField.getText().matches("[a-zA-Z]+")) {//more than 4, less than 15
                    stage.setScene(scene);
                    stage.setFullScreen(true);
                    playerName = textField.getText();
                    group.getChildren().addAll(hero);
                    hero.relocate(885, 540);
                    controls.setVisible(false);
                    group.getChildren().add(controls);
                    new FadeIn(group).play();
                } else {
                    badUserName.setVisible(true);
                }
            }
        });
        //
        Button enter = new Button("PLAY");
        enter.getStylesheets().add("com/company/Style.css");
        enter.relocate(1100, 958);
        menuGroup.getChildren().addAll(textField, enter, controls);
        stage.setScene(menuScene);
        stage.setFullScreen(true);
        enter.setOnAction(event -> {//if the enter button is hit, this runs
            if (!textField.getText().equalsIgnoreCase("") && textField.getText().length() >= 4 && textField.getText().length() <= 15 && textField.getText().matches("[a-zA-Z]+")) {//more than 4, less than 15
                stage.setScene(scene);
                stage.setFullScreen(true);
                playerName = textField.getText();
                group.getChildren().addAll(hero);
                hero.relocate(885, 540);
                controls.setVisible(false);
                group.getChildren().add(controls);
                new FadeIn(group).play();
            } else {
                badUserName.setVisible(true);
            }
        });
        //doors
        ImageView northDoor = new ImageView("file:src\\game\\door_up.png");
        ImageView southDoor = new ImageView("file:src\\game\\door_down.png");
        ImageView eastDoor = new ImageView("file:src\\game\\door_right.png");
        ImageView westDoor = new ImageView("file:src\\game\\door_left.png");
        ImageView bossDoor = new ImageView("file:src\\game\\door_boss_closed.png");
        Image bossDoor_open = new Image("file:src\\game\\door_boss_open.png");
        Image bossDoor_closed = new Image("file:src\\game\\door_boss_closed.png");
        northDoor.relocate(879, 4);
        bossDoor.relocate(879, 5);
        southDoor.relocate(879, 1010);
        westDoor.relocate(0, 460);
        eastDoor.relocate(1850, 460);
        bossDoor.setVisible(false);
        group.getChildren().addAll(northDoor, southDoor, eastDoor, westDoor, bossDoor);
        //
        int[][] map = new int[19][19];//
        mapMaker(mapPieces, backgrounds, items, enemies, group, map);//TODO
        //
        ImageView shadow = new ImageView("file:src\\game\\floors\\shadow.png");
        group.getChildren().add(shadow);
        shadow.toFront();
        //
        stage.setFullScreen(true);
        //
        sound = "background";
        soundManager();//TODO
        //shop related things
        ImageView shopkeeper = new ImageView("file:src\\game\\shopkeeper.png");
        shopkeeper.relocate(885, 200);
        shopkeeper.setVisible(false);
        group.getChildren().add(shopkeeper);
        ImageView staff = new ImageView("file:src\\game\\staff.png");
        Tooltip.install(staff, new Tooltip("A powerful ranged weapon, use W,A,S,D to use"));
        staff.relocate(750, 500);
        staff.setVisible(false);
        //t1.start(); https://dzone.com/articles/java-thread-tutorial-creating-threads-and-multithr
        ImageView coinIcon_staff = new ImageView("file:src\\game\\Coin Icon.png");
        Label coinNumber_staff;
        Label coinNumber_attackBoost;
        Label coinNumber_mapItem;
        coinNumber_staff = new Label();
        labelMaker(coinNumber_staff, "5", 745, 580, 21, Color.WHITE, false, 1);//TODO
        coinIcon_staff.relocate(763, 583);
        coinIcon_staff.setVisible(false);
        group.getChildren().addAll(staff, coinIcon_staff, coinNumber_staff);
        //
        ImageView mapItem = new ImageView("file:src\\game\\MapItem.png");
        //enter.setTooltip(new Tooltip("(Ctrl + S)"));
        Tooltip.install(mapItem, new Tooltip("reveals the dungeon layout"));
        mapItem.relocate(900, 480);
        mapItem.setVisible(false);
        ImageView coinIcon_mapItem = new ImageView("file:src\\game\\Coin Icon.png");
        coinIcon_mapItem.relocate(945, 580);
        coinIcon_mapItem.setVisible(false);
        coinNumber_mapItem = new Label();
        labelMaker(coinNumber_mapItem, "2", 925, 577, 21, Color.WHITE, false, 1);
        group.getChildren().addAll(coinNumber_mapItem, mapItem, coinIcon_mapItem);
        //
        ImageView attackBoost = new ImageView("file:src\\game\\attack boost.png");
        Tooltip.install(attackBoost, new Tooltip("Makes you more powerful when consumed"));
        attackBoost.relocate(1050, 500);
        attackBoost.setVisible(false);
        ImageView coinIcon_attackBoost = new ImageView("file:src\\game\\Coin Icon.png");
        coinNumber_attackBoost = new Label();
        labelMaker(coinNumber_attackBoost, "5", 1058, 578, 21, Color.WHITE, false, 1);
        coinIcon_attackBoost.relocate(1075, 580);
        coinIcon_attackBoost.setVisible(false);
        group.getChildren().addAll(coinIcon_attackBoost, attackBoost, coinNumber_attackBoost);
        //sword hit boxes
        Rectangle usedSword = new Rectangle();
        Rectangle sword_up = new Rectangle(0, 0, 25, 68);
        Rectangle sword_right = new Rectangle(0, 0, 85, 31);
        Rectangle sword_down = new Rectangle(0, 0, 26, 75);
        Rectangle sword_left = new Rectangle(0, 0, 84, 28);
        sword_up.setOpacity(0);
        sword_right.setOpacity(0);
        sword_down.setOpacity(0);
        sword_left.setOpacity(0);
        group.getChildren().addAll(sword_up, sword_right, sword_down, sword_left);
        //backgrounds
        ImageView background = new ImageView("file:src\\game\\floors\\floor1.png");
        group.getChildren().add(background);
        backgroundChanger(backgrounds, background);//TODO
        background.toBack();
        //chest
        ImageView chest = new ImageView("file:src\\game\\chest_closed.png");
        Image chest_open = new Image("file:src\\game\\chest_open.png");
        chest.relocate(885, 150);
        chest.setVisible(false);
        group.getChildren().add(chest);
        ImageView bossHealthBar = new ImageView("file:src\\game\\HealthBarImage.png");
        Rectangle bossHealthBarREC = new Rectangle(616, 87, 658, 48);
        ImageView scoreIMG = new ImageView("file:src\\game\\score.png");
        //hearts
        ImageView hearts = new ImageView("file:src\\game\\hearts\\heart_3.png");
        hearts.relocate(0, 0);
        group.getChildren().addAll(hearts);
        //score
        Label score = new Label();
        labelMaker(score, String.valueOf(Score), 1000, 90, 21, Color.WHITE, true, 0.3);
        ImageView scoreBoard = new ImageView("file:src\\game\\ScoreBoard IMG.png");
        Label[][] scoreboardLabels = new Label[10][3];
        ScoreDisplayer(group, scoreBoard, scoreboardLabels);//TODO
        //Coin Icon
        ImageView coinIcon = new ImageView("file:src\\game\\Coin Icon.png");
        coinIcon.relocate(25, 100);
        //
        Label coinNumber = new Label(String.valueOf(CoinNumber));
        labelMaker(coinNumber, String.valueOf(CoinNumber), 50, 98, 21, Color.WHITE, true, 1);
        group.getChildren().addAll(coinNumber, coinIcon);
        //Score
        scoreIMG.relocate(845, 90);
        scoreIMG.setOpacity(0.3);
        //BOSS
        boss.relocate(885, 459);
        boss.setVisible(false);
        group.getChildren().add(boss);
        //boss health
        bossHealthBar.relocate(500, 50);
        bossHealthBar.setVisible(false);
        bossHealthBarREC.setVisible(false);
        bossHealthBarREC.setFill(Color.RED);
        group.getChildren().addAll(bossHealthBar, bossHealthBarREC);
        //inventory
        int itemId = 0;
        ImageView inventoryPIC = new ImageView("file:src\\game\\newInventory.png");
        inventoryPIC.relocate(603, 275);
        //inventoryPIC.relocate(752, 325);
        inventoryPIC.setVisible(false);
        ImageView selected = new ImageView("file:src\\game\\selected.png");
        selected.relocate(643, 314);
        selected.setVisible(false);
        startTimers();//starts the timers TODO
        Rectangle northWall = new Rectangle(0, 0, 1920, 2);
        Rectangle southWall = new Rectangle(0, 1080, 1920, 2);
        Rectangle eastWall = new Rectangle(0, 0, 2, 1200);
        Rectangle westWall = new Rectangle(1920, 0, 2, 1920);
        northWall.setOpacity(0);
        southWall.setOpacity(0);
        eastWall.setOpacity(0);
        westWall.setOpacity(0);
        group.getChildren().addAll(northWall, southWall, eastWall, westWall);
        //logo
        stage.getIcons().add(new Image("file:src\\game\\Master Sword In Pedastol.png"));
        //
        group.getChildren().addAll(inventoryPIC, selected, scoreIMG, score);
        stage.setFullScreen(true);
        //swords
        Image link_sword_down = new Image("file:src\\game\\link_sword_down.png");
        Image link_sword_left = new Image("file:src\\game\\link_sword_left.png");
        Image link_sword_right = new Image("file:src\\game\\link_sword_right.png");
        Image link_sword_up = new Image("file:src\\game\\link_sword_up.png");
        //staff / magic
        Image link_staff_down = new Image("file:src\\game\\link_staff_down.png");
        Image link_staff_left = new Image("file:src\\game\\link_staff_left.png");
        Image link_staff_right = new Image("file:src\\game\\link_staff_right.png");
        Image link_staff_up = new Image("file:src\\game\\link_staff_up.png");
        //running 1
        Image link_running_down_1 = new Image("file:src\\game\\link_running_down_1.png");
        Image link_running_left_1 = new Image("file:src\\game\\link_running_left_1.png");
        Image link_running_up_1 = new Image("file:src\\game\\link_running_up_1.png");
        Image link_running_right_1 = new Image("file:src\\game\\link_running_right_1.png");
        //running 2
        Image link_running_down_2 = new Image("file:src\\game\\link_running_down_2.png");
        Image link_running_left_2 = new Image("file:src\\game\\link_running_left_2.png");
        Image link_running_up_2 = new Image("file:src\\game\\link_running_up_2.png");
        Image link_running_right_2 = new Image("file:src\\game\\link_running_right_2.png");
        //map indicator - shows what room the player is in
        ImageView indicator = new ImageView("file:src\\game\\Indicator.png");
        InputManager(mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, inventoryARR, animationHero, itemName, itemType, itemDescription, itemEffect, itemId, group, scene, translate, boss, hero, indicator, map, stage, northWall, eastWall, southWall, westWall, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, chest, chest_open, controls, bossHealthBar, bossHealthBarREC, scoreBoard, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, mapItem, coinIcon_mapItem, coinNumber_mapItem, hearts, inventoryPIC, selected, score, coinNumber_staff, coinNumber_attackBoost, coinNumber, scoreboardLabels, northDoor, eastDoor, southDoor, westDoor, background, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, shopkeeper, link_running_down_2, link_running_left_2, link_running_right_2, link_running_up_2);
        swordMover(sword_down, sword_left, sword_right, sword_up, hero);//TODO TODO

        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.setTitle("The Lost Dungeon");
        //stage.setScene(scene);
        doorChanger(map, northDoor, eastDoor, southDoor, westDoor, bossDoor);// makes sure doors are correct in the starting room TODO
        itemChanger(items);//shows the correct item TODO
        stage.show();
        int i = 0;
        for (MapPieces mappiece : mapPieces) {//makes the map appear in front of EVERYTHING
            mappiece.getImage().toFront();
            mappiece.getImage().setVisible(false);
            i++;
            if (i == 181) {//array location of starting room
                indicator.relocate(mappiece.getImage().getLayoutX(), mappiece.getImage().getLayoutY());
                group.getChildren().add(indicator);
                indicator.setOpacity(0.3);
                indicator.setVisible(false);
            }
        }
    }

    private void labelMaker(Label label, String text, int X, int Y, int fontSize, Color fontColor, boolean Visible, double opacity) {
        //        labelMaker(badUserName,"Name cannot contain spaces, special characters and must have between 4 and 15 characters",735, 1008,"Upheaval", FontWeight.BOLD, 16, "WHITE", "false",1 );
        //template ^
        label.setText(text);//text
        label.relocate(X, Y);//position
        label.setFont(javafx.scene.text.Font.font("Upheaval", FontWeight.BOLD, fontSize));//font and such
        label.setTextFill(fontColor);//color
        label.setVisible(Visible);//visibility
        label.setOpacity(opacity);//opacity


    }

    private void bossRoom(int[][] inventoryARR, ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, Group group, Scene scene, ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView hearts, Label score, ImageView hero, ImageView boss, Stage stage, int[][] map, ImageView indicator, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView background, TranslateTransition translate) {
        bossHealthBar.setVisible(true);
        bossHealthBarREC.setVisible(true);//shows the health bar
        bossHealthBarREC.setWidth(658);
        timePassed3 = 0;//fireball cooldown timer
        MEDIABackground.stop(); //stops the background music
        sound = "boss";
        soundManager();//boss music
        activeBoss = true;
        boss.setVisible(true);
        if (k == 0) {
            Bossroomtimeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {//checks the time every 0.05 seconds to see
                try {
                    if (timePassed3 >= 5 && boss.isVisible()) {
                        k = 1;
                        new Pulse(boss).play();//animatefx effect
                        TranslateTransition translateFireball = new TranslateTransition();
                        fireballs.add(new Fireballs());//new fireball
                        fireballs.get(0).getImage().relocate(boss.getLayoutX(), boss.getLayoutY());//puts it in the boss's hand
                        group.getChildren().add(fireballs.get(0).getImage());
                        translateFireball.setNode(fireballs.get(0).getImage());
                        translateFireball.setDuration(Duration.seconds(1.6));
                        translateFireball.setToX(-fireballs.get(0).getImage().getLayoutX() + hero.getLayoutX() + (hero.getImage().getWidth() / 2));
                        translateFireball.setToY(-fireballs.get(0).getImage().getLayoutY() + hero.getLayoutY() + (hero.getImage().getHeight() / 2));//go to location
                        translateFireball.setInterpolator(Interpolator.LINEAR);
                        translateFireball.setOnFinished(event1 -> {
                            try {
                                fireballs.get(0).getImage().toBack();
                                fireballs.get(0).getImage().setVisible(false);//when the fireball misses
                                fireballs.remove(0);
                                group.getChildren().remove(fireballs.get(0).getImage());
                            } catch (Exception e) {
                                //System.out.println("Boss room timeline error");
                            }
                        });
                        sound = "fire_ball";
                        soundManager();
                        translateFireball.play();
                        timePassed3 = 0;
                        //timepassed4 = 0;
                    }
                    if (hero.getBoundsInParent().intersects(fireballs.get(0).getImage().getBoundsInParent())) {//hit the target
                        hero_Health--;
                        String killedBy = "boss";
                        healthChecker(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                        fireballs.get(0).getImage().setVisible(false);              //collision detection for the player
                        fireballs.remove(0);
                        group.getChildren().remove(fireballs.get(0).getImage());
                    }
                    if (y >= 8) {
                        System.out.println("Boss phase 2");
                        bossPhase2(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, group, scene, chest, bossHealthBar, bossHealthBarREC, hearts, score, hero, boss, stage, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, translate);
                    }
                } catch (Exception e) {
                    //System.out.println("Boss room phase 1 error");
                }
            }));
            Bossroomtimeline.setCycleCount(Timeline.INDEFINITE);
            Bossroomtimeline.play();
        }
    }

    private void bossPhase2(int[][] inventoryARR, ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, Group group, Scene scene, ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView hearts, Label score, ImageView hero, ImageView boss, Stage stage, int[][] map, ImageView indicator, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView background, TranslateTransition translate) {//phase 2 of the boss fight
        Random rand = new Random();
        System.out.println("phase two");
        TranslateTransition[] fireBlast = new TranslateTransition[3];//
        for (int m = 0; m < 3; m++) {
            fireBlast[m] = new TranslateTransition();
        }
        TranslateTransition[] fireCircle = new TranslateTransition[8];//
        for (int m = 0; m < 8; m++) {
            fireCircle[m] = new TranslateTransition();
        }
        Bossroomtimeline.stop();//stops boos phase 1 timeline
        fireballs.get(0).getImage().setVisible(false);
        fireballs.remove(0);
        timePassed3 = 5;
        Bossroomtimeline2 = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            int constant = (int) ((hero.getLayoutY() - boss.getLayoutY()) / (hero.getLayoutX() - boss.getLayoutX()));//gradient between the two
            if (timePassed3 >= 5 && boss.isVisible()) {
                timePassed3 = 0;
                //timepassed4 = 0;
                if (rand.nextInt(5) < 3) {//chooses between two attacks. (0,1,2) 3 fireball blast. (3,4) circle of fireballs.
                    System.out.println("attack 1");
                    for (int m = 0; m < 3; m++) {
                        fireballs.add(new Fireballs());
                        fireballs.get(m).getImage().relocate(boss.getLayoutX(), boss.getLayoutY());//sets the starting point to the bosses hand.
                        group.getChildren().add(fireballs.get(m).getImage());
                        //
                        fireBlast[m].setNode(fireballs.get(m).getImage());
                        fireBlast[m].setDuration(Duration.seconds(2));
                        //determines if the player is above or below the boss
                        if ((hero.getLayoutX() > boss.getLayoutX() && (constant > -0.5 && constant < 0.5)) || hero.getLayoutX() < boss.getLayoutX() && (constant > -0.7 && constant < 0.6)) {//left or right of the boss
                            //positioning for the fireBlast attack
                            fireBlast[m].setToX(-fireballs.get(m).getImage().getLayoutX() + hero.getLayoutX() + (hero.getImage().getWidth() / 2));
                            fireBlast[m].setToY(-fireballs.get(m).getImage().getLayoutY() + hero.getLayoutY() + (hero.getImage().getHeight() / 2) + (((Math.tan(0.35) * Math.abs(hero.getLayoutX() - boss.getLayoutX()))) * (m - 1)));
                        } else {//left or right of the boss,    same logic as the enemy animator procedure.
                            //positioning for the fireBlast attack
                            fireBlast[m].setToX(-fireballs.get(m).getImage().getLayoutX() + hero.getLayoutX() + (hero.getImage().getWidth() / 2) + (((Math.tan(0.35) * Math.abs(hero.getLayoutY() - boss.getLayoutY()))) * (m - 1)));
                            fireBlast[m].setToY(-fireballs.get(m).getImage().getLayoutY() + hero.getLayoutY() + (hero.getImage().getHeight() / 2));
                        }
                        fireBlast[m].setOnFinished(event1 -> {//this removes the fireballs when they're done moving.
                            try {
                                for (int n = 0; n < 3; n++) {
                                    fireballs.get(n).getImage().toBack();
                                    fireballs.get(n).getImage().setVisible(false);
                                    group.getChildren().remove(fireballs.get(n).getImage());
                                    fireballs.remove(n);
                                }
                            } catch (Exception e) {
                                // System.out.println("fireBlast finished error");
                            }
                        });
                        sound = "fire_ball";
                        fireBlast[m].play();
                    }
                    sound = "fire_ball";
                    soundManager();
                } else {
                    System.out.println("attack 2");
                    for (int m = 0; m < 8; m++) {
                        fireballs.add(new Fireballs());
                        fireballs.get(m).getImage().relocate(boss.getLayoutX() + 75, boss.getLayoutY() + 75);
                        group.getChildren().add(fireballs.get(m).getImage());
                        //
                        fireCircle[m].setNode(fireballs.get(m).getImage());
                        fireCircle[m].setDuration(Duration.seconds(2.5));
                        fireCircle[m].setToX(-fireballs.get(m).getImage().getLayoutX() + 935 + (175 * (Math.sin(Math.toRadians(45) * (m + 1)))));//creates the circle around the boss's center
                        fireCircle[m].setToY(-fireballs.get(m).getImage().getLayoutY() + 520 + (175 * (Math.cos(Math.toRadians(45) * (m + 1)))));
                        fireCircle[m].setOnFinished(event1 -> {
                            try {
                                for (int n = 0; n < 8; n++) {
                                    fireballs.get(n).getImage().toBack();
                                    fireballs.get(n).getImage().setVisible(false);//removes all 8 of the fireballs.
                                    group.getChildren().remove(fireballs.get(n).getImage());
                                    fireballs.remove(n);
                                }
                            } catch (Exception e) {
                                // System.out.println("Firecircle finished error");
                            }
                        });
                        new Pulse(boss).play();
                        fireCircle[m].play();

                    }
                    sound = "fire_ball";
                    soundManager();
                }
            }
            try {
                for (int m = 0; m < 8; m++) {//collision detection
                    if (hero.getBoundsInParent().intersects(fireballs.get(m).getImage().getBoundsInParent()) && fireballs.get(m).getImage().isVisible()) {
                        hero_Health--;
                        String killedBy = "boss";
                        healthChecker(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                        fireballs.get(m).getImage().setVisible(false);
                        group.getChildren().remove(fireballs.get(m).getImage());
                        fireballs.remove(m);
                    }
                }
            } catch (Exception e) {
                //System.out.println("Error removing fireballs, phase 2");
            }
            if (bossHealthBarREC.getWidth() <= 0) {
                try {
                    System.out.println("defeated");
                    bossHealthBarREC.setWidth(1);
                    bossHealthBarREC.setVisible(false);
                    Score = Score + 200;
                    score.setText(String.valueOf(Score));//converts to atomic to deal with effectively final variables
                    MEDIABoss.stop();
                    defeatedBoss = true;
                    boss.setVisible(false);
                    bossHealthBar.setVisible(false);
                    chest.setVisible(true);
                    for (int m = 0; m < 8; m++) {
                        fireballs.get(m).getImage().setVisible(false);
                    }
                } catch (Exception e) {
                    // System.out.println("Error updating boss health");
                }
            }
        }));
        Bossroomtimeline2.setCycleCount(Timeline.INDEFINITE);
        Bossroomtimeline2.play();
    }

    private void soundManager() {
        switch (sound) { //whenever soundManager is called, the String sound will be set to the sound that plays.
            case "walking":
                if (l == 0 && walking == 1) {
                    walkSounds = new Timeline(new KeyFrame(Duration.seconds(0.4), event -> {
                        l = 1;
                        File walkingFile = new File("src\\game\\sound\\walkingSound.wav");
                        Media walkingMedia = new Media(walkingFile.toURI().toString());
                        MEDIAWalking = new MediaPlayer(walkingMedia);
                        MEDIAWalking.setVolume(0.10 * musicVolume);
                        MEDIAWalking.setAutoPlay(true);
                    }));
                    walkSounds.setCycleCount(Timeline.INDEFINITE);
                    walkSounds.play();
                }
                break;
            case "swordMiss"://sound effect for when the sword misses
                File f = new File("src\\game\\sound\\sword_miss.mp3");
                Media media = new Media(f.toURI().toString());
                MediaPlayer MEDIASwordMiss = new MediaPlayer(media);
                MEDIASwordMiss.setVolume(0.05 * musicVolume);
                MEDIASwordMiss.play();
                break;
            case "swordHit"://sound effect for when sword hits
                File f1 = new File("src\\game\\sound\\sword_hit.mp3");
                Media media1 = new Media(f1.toURI().toString());
                MediaPlayer MEDIASwordHit = new MediaPlayer(media1);
                MEDIASwordHit.setVolume(0.05 * musicVolume);
                MEDIASwordHit.play();
                break;
            case "boss"://boss music
                File f2 = new File("src\\game\\sound\\bossMusic.mp3");
                Media media2 = new Media(f2.toURI().toString());
                MEDIABoss = new MediaPlayer(media2);
                MEDIABoss.setVolume(0.04 * musicVolume);
                MEDIABoss.play();
                MEDIABoss.setOnEndOfMedia(() -> {
                    MEDIABoss.seek(Duration.ZERO);
                    MEDIABoss.play();
                });
                break;
            case "background"://background music
                File f3 = new File("src\\game\\sound\\background.mp3");
                Media media3 = new Media(f3.toURI().toString());
                MEDIABackground = new MediaPlayer(media3);
                MEDIABackground.setVolume(0.04 * musicVolume);
                MEDIABackground.play();
                MEDIABackground.setOnEndOfMedia(() -> {
                    MEDIABackground.seek(Duration.ZERO);
                    MEDIABackground.play();
                });
                break;
            case "triforce"://for when the end game chest is opened
                File f4 = new File("src\\game\\sound\\Triforce.mp3");
                Media media4 = new Media(f4.toURI().toString());
                MediaPlayer MEDIATriforce = new MediaPlayer(media4);
                MEDIATriforce.setVolume(0.2 * musicVolume);
                MEDIATriforce.play();
                break;
            case "magic_shot"://sound effect for when the staff is used
                File f5 = new File("src\\game\\sound\\magic_shot.mp3");
                Media media5 = new Media(f5.toURI().toString());
                MediaPlayer MEDIAMagic_Shot = new MediaPlayer(media5);
                MEDIAMagic_Shot.setVolume(0.10 * musicVolume);
                MEDIAMagic_Shot.play();
                break;
            case "fire_ball"://sound effect for when a fireball is shot
                File f6 = new File("src\\game\\sound\\Fireball.wav");
                Media media6 = new Media(f6.toURI().toString());
                MediaPlayer MEDIAFire_Ball = new MediaPlayer(media6);
                MEDIAFire_Ball.setVolume(0.15 * musicVolume);
                MEDIAFire_Ball.play();
                break;
            case "Explosion"://sound effect when a fireball meets a magic projectile
                File f7 = new File("src\\game\\sound\\Explosion.mp3");
                Media media7 = new Media(f7.toURI().toString());
                MediaPlayer MEDIAExplosion = new MediaPlayer(media7);
                MEDIAExplosion.setVolume(0.15 * musicVolume);
                MEDIAExplosion.play();
                break;
            case "Death"://sound effect for when the player dies
                File f8 = new File("src\\game\\sound\\death.mp4");
                Media media8 = new Media(f8.toURI().toString());
                MediaPlayer MEDIADeath = new MediaPlayer(media8);
                MEDIADeath.setVolume(0.15 * musicVolume);
                //System.out.println("asdasd");
                MEDIADeath.play();
                break;
        }
    }

    private void ScoreDisplayer(Group group, ImageView scoreBoard, Label[][] ScoreBoard) {
        scoreBoard.toFront();
        for (int m = 0; m < 10; m++) {
            for (int n = 0; n < 3; n++) {//makes the 30 labels needed
                ScoreBoard[m][n] = new Label();
                ScoreBoard[m][n].toFront();
                labelMaker(ScoreBoard[m][n], "", 0, 0, 21, Color.WHITE, true, 0.8);//sets up the labels
            }
        }
        int j = 0;
        scoreBoard.relocate(590 - 100, 125);
        scoreBoard.setOpacity(0.7);
        group.getChildren().addAll(scoreBoard);
        //This reads from the database
        //String connectionUrl = "jdbc:sqlserver://ks-sql-02:1433;" + "databaseName=db10;integratedSecurity=true";//school
        String connectionUrl = "jdbc:sqlserver://DESKTOP-38akage:1433;" + "databaseName=master;integratedSecurity=true";//home
        //Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "use master; select Name, Score from ScoreTable order by Score DESC";//THIS GETS THEN ORDERS THE INFORMATION BY SCORE
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next() && j < 10) {//reads top 10 scores needed
                String NameFromDatabase = rs.getString("Name");
                int ScoreFromDatabase = rs.getInt("Score");
                ScoreBoard[j][0].relocate(504 + 74, 208 + (49.5 * (j + 1)));          //rank position
                ScoreBoard[j][0].setText((j + 1) + " .");                                   //rank
                ScoreBoard[j][1].relocate(675 + 74, 208 + (49.5 * (j + 1)));          //name position
                ScoreBoard[j][1].setText(NameFromDatabase);                                 //name
                ScoreBoard[j][2].relocate(1090 + 74, 208 + (49.5 * (j + 1)));         //score position
                ScoreBoard[j][2].setText(String.valueOf(ScoreFromDatabase));                //score
                group.getChildren().addAll(ScoreBoard[j][0], ScoreBoard[j][1], ScoreBoard[j][2]);   //adds them to the group
                j++;
            }
        }
        //Handle any errors that may have occurred.
        catch (Exception e) {
            System.out.println("Error showing scores");
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }
    }

    private void swordMover(Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, ImageView hero) {
        sword_up.relocate(hero.getLayoutX() + 46, hero.getLayoutY() - 78);
        sword_down.relocate(hero.getLayoutX() + 67, hero.getLayoutY() + 128);
        sword_left.relocate(hero.getLayoutX() - 85, hero.getLayoutY() + 78);    //moves swords relative to the player
        sword_right.relocate(hero.getLayoutX() + 142, hero.getLayoutY() + 75);
    }

    private final TimerTask timerIncrement = new TimerTask() {//can turn to a thread
        @Override
        public void run() {
            timePassed++;  //enemy invincible phase 0.75 sec / attack cool down
            timepassed1++; //animation reset timer. 1 sec
            timePassed2++; //hero invincible phase 2 secs
            timePassed3++; //shooting fireball cool down 5 secs
            //timepassed4++; //fireball flight time 2 secs
            //timepassed5++; //boss cooldown hit 1 sec
            //timepassed6++; //magic flight time
            timepassed7++; //wand cooldown 2 secs
            timepassed8++;
        }
    };

    private void startTimers() {
        Timer timer = new Timer();//starts the timers
        timer.scheduleAtFixedRate(timerIncrement, 1000, 1000);//updates every second
    }

    private void scoreCountdown(Label score, Label coinNumber) {
        if (scoretimer == 0) {
            scoreChanger(score, coinNumber);
            scoretimer = 1;
        }
    }

    private void scoreChanger(Label score, Label coinNumber) {
        //Score goes down 1 every second
        if (q == 0) {
            Timeline scoreTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                coinNumber.setText(String.valueOf(Integer.parseInt(String.valueOf(CoinNumber))));
                q = 1;
                if (!defeatedBoss && Score > 0) {//when boss is defeated, the score stops going down.
                    Score--;
                }
                score.setText(String.valueOf(Score));//updates the label
            }));
            scoreTimeline.setCycleCount(Timeline.INDEFINITE);
            scoreTimeline.play();
        }
    }

    private void heroMover(ImageView hero) {

        if (up & right) {
            hero.relocate(hero.getLayoutX() + 6, hero.getLayoutY() - 6);//up and right
        }
        if (up & !right & !left & !down) {
            hero.relocate(hero.getLayoutX(), hero.getLayoutY() - 10);//just up
        }
        if (up & left) {
            hero.relocate(hero.getLayoutX() - 6, hero.getLayoutY() - 6);//up and left
        }
        if (left & !right & !down & !up) {
            hero.relocate(hero.getLayoutX() - 10, hero.getLayoutY());//just left
        }
        if (down & right) {
            hero.relocate(hero.getLayoutX() + 6, hero.getLayoutY() + 6);//down and right
        }
        if (right & !left & !down & !up) {
            hero.relocate(hero.getLayoutX() + 10, hero.getLayoutY());//just right
        }
        if (down & left) {
            hero.relocate(hero.getLayoutX() - 6, hero.getLayoutY() + 6);//down and left
        }
        if (down & !up & !left & !right) {
            hero.relocate(hero.getLayoutX(), hero.getLayoutY() + 10);//just down
        }
    }

    private void AnimationManager(ImageView hero, String animation) {
        final String[] anim = new String[1];//deal with the effectively final issue
        anim[0] = animation;
        if (i == 0) {
            //when holding an arrow key it swaps the image2 every 0.35 seconds
            Timeline animationTimeline = new Timeline(new KeyFrame(Duration.seconds(0.35), event -> {
                i = 1;
                if (!(direction.equals(""))) {
                    if (anim[0].equals("b")) {
                        Image image1 = new Image("file:src\\game\\link_running_" + direction + "_1.png");
                        hero.setImage(image1);
                        anim[0] = "a";
                    } else {
                        Image image2 = new Image("file:src\\game\\link_running_" + direction + "_2.png");
                        hero.setImage(image2);
                        anim[0] = "b";
                    }
                    direction = "";
                }
            }));
            animationTimeline.setCycleCount(Timeline.INDEFINITE);
            animationTimeline.play();
        }
    }

    private void inventoryHider(ArrayList<Inventory> inventory, ImageView controls, ImageView inventoryPIC, ImageView selected, Label itemName, Label itemType, Label itemDescription, Label itemEffect) {
        //hides the inventory if you move while its open
        if (selected.isVisible()) {
            selected.setVisible(false);
            inventoryPIC.setVisible(false);
            itemName.setVisible(false);
            itemType.setVisible(false);
            itemDescription.setVisible(false);
            itemEffect.setVisible(false);
            for (Inventory value : inventory) {
                value.getImage().setVisible(false);
            }
        }
        if (controls.isVisible()) {
            controls.setVisible(false);
        }
    }

    private void ScoreBoardHider(ImageView scoreBoard, Label[][] Labels) {
        try {
            if (Labels[0][0].isVisible()) {
                for (int m = 0; m < 10; m++) {
                    for (int n = 0; n < 3; n++) {
                        Labels[m][n].setVisible(false);
                    }
                }
                scoreBoard.setVisible(false);
            }
        } catch (Exception e) {
            //System.out.println("Error when hiding score board");
        }
    }

    private void Checkcollision(Rectangle northWall, Rectangle eastWall, Rectangle southWall, Rectangle westWall, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, ImageView hero) {
        //pushes back from big box around the wall to stop from leaving the screen
        if (hero.getBoundsInParent().intersects(northWall.getBoundsInParent())) {
            hero.setLayoutY(hero.getLayoutY() + 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, hero);
            Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, hero);
        }
        if (hero.getBoundsInParent().intersects(southWall.getBoundsInParent())) {
            hero.setLayoutY(hero.getLayoutY() - 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, hero);
            Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, hero);
        }
        if (hero.getBoundsInParent().intersects(eastWall.getBoundsInParent())) {
            hero.setLayoutX(hero.getLayoutX() + 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, hero);
            Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, hero);
        }
        if (hero.getBoundsInParent().intersects(westWall.getBoundsInParent())) {
            hero.setLayoutX(hero.getLayoutX() - 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, hero);
            Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, hero);
        }
    }

    private void inventoryManager(Scene scene, ArrayList<Inventory> inventory, ImageView inventoryPIC, ImageView selected, Label itemName, Label itemType, Label itemDescription, Label itemEffect) {
        if (!inventoryPIC.isVisible()) {
            //scene.setCursor(Cursor.DEFAULT);
            itemInformation(inventory, selected, itemName, itemType, itemDescription, itemEffect);
            inventoryPIC.setVisible(true);
            inventoryPIC.toFront();
            selected.setVisible(true);
            selected.toFront();

            new FadeIn(inventoryPIC).play();
            new FadeIn(selected).play();
            new FadeIn(itemName).play();
            //
            itemName.toFront();
            new FadeIn(itemType).play();
            itemType.toFront();
            new FadeIn(itemDescription).play();
            itemDescription.toFront();
            new FadeIn(itemEffect).play();
            itemEffect.toFront();
            for (Inventory value : inventory) {
                value.getImage().setVisible(true);
                if (value.getImage().getOpacity() > 0) {
                    new FadeIn(value.getImage()).play();
                }
                value.getImage().toFront();
            }
        } else {
            //scene.setCursor(Cursor.NONE);
            inventoryPIC.setVisible(false);
            selected.setVisible(false);
            selected.relocate(643, 314);
            for (Inventory value : inventory) {
                value.getImage().setVisible(false);
            }
            itemName.setVisible(false);
            itemType.setVisible(false);
            itemDescription.setVisible(false);
            itemEffect.setVisible(false);
        }
    }

    private void Base_Procedure(int[][] inventoryARR, ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, Label coinNumber, String animation, Label itemName, Label itemType, Label itemDescription, Label itemEffect, Group group, Scene scene, TranslateTransition translate, ImageView boss, ImageView hero, ImageView indicator, Rectangle northWall, Rectangle eastWall, Rectangle southWall, Rectangle westWall, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, ImageView controls, ImageView scoreBoard, ImageView inventoryPIC, ImageView selected, Label score, Label[][] Labels, int[][] map, ImageView hearts, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView background, ImageView shopkeeper, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, Label coinNumber_attackBoost, Label coinNumber_staff, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, Stage stage) {
        heroMover(hero);
        sound = "walking";
        scoreCountdown(score, coinNumber);
        swordMover(sword_down, sword_left, sword_right, sword_up, hero);
        AnimationManager(hero, animation);
        walking++;
        soundManager();
        Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, hero);
        inventoryHider(inventory, controls, inventoryPIC, selected, itemName, itemType, itemDescription, itemEffect);
        ScoreBoardHider(scoreBoard, Labels);
        roomChanger(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, group, scene, translate, boss, indicator, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, score, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, hero, stage);
    }

    private void InputManager(ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, int[][] inventoryARR, String animation, Label itemName, Label itemType, Label itemDescription, Label itemEffect, int itemId, Group group, Scene scene, TranslateTransition translate, ImageView boss, ImageView hero, ImageView indicator, int[][] map, Stage stage, Rectangle northWall, Rectangle eastWall, Rectangle southWall, Rectangle westWall, Image link_sword_down, Image link_sword_left, Image link_sword_right, Image link_sword_up, Image link_staff_down, Image link_staff_left, Image link_staff_right, Image link_staff_up, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open, ImageView bossDoor, ImageView chest, Image chest_open, ImageView controls, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView scoreBoard, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, ImageView hearts, ImageView inventoryPic, ImageView selected, Label score, Label coinNumber_staff, Label coinNumber_attackBoost, Label coinNumber, Label[][] Labels, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView background, Image link_running_down_1, Image link_running_left_1, Image link_running_right_1, Image link_running_up_1, ImageView shopkeeper, Image link_running_down_2, Image link_running_left_2, Image link_running_right_2, Image link_running_up_2) {
        final String[] killedBy = new String[1];
        try {
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case UP://moves up
                        if (hero.getImage() == link_staff_up || hero.getImage() == link_sword_up) {
                            hero.setLayoutY(hero.getLayoutY() + 100);
                            hero.setImage(link_running_up_1);
                            attacking = false;
                        }
                        up = true;
                        direction = "up";
                        attackDirection = "up";
                        Base_Procedure(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, coinNumber, animation, itemName, itemType, itemDescription, itemEffect, group, scene, translate, boss, hero, indicator, northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard, inventoryPic, selected, score, Labels, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage);
                        break;
                    case DOWN://moves down
                        attacking = false;
                        down = true;
                        direction = "down";
                        attackDirection = "down";
                        Base_Procedure(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, coinNumber, animation, itemName, itemType, itemDescription, itemEffect, group, scene, translate, boss, hero, indicator, northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard, inventoryPic, selected, score, Labels, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage);
                        break;
                    case LEFT://moves left
                        if ((hero.getImage() == link_staff_left) || (hero.getImage() == link_sword_left)) {
                            hero.setLayoutX(hero.getLayoutX() + 100);
                            hero.setImage(link_running_left_1);
                            attacking = false;
                        }
                        left = true;
                        direction = "left";
                        attackDirection = "left";
                        Base_Procedure(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, coinNumber, animation, itemName, itemType, itemDescription, itemEffect, group, scene, translate, boss, hero, indicator, northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard, inventoryPic, selected, score, Labels, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage);
                        break;
                    case RIGHT://moves right
                        attacking = false;
                        right = true;
                        direction = "right";
                        attackDirection = "right";
                        Base_Procedure(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, coinNumber, animation, itemName, itemType, itemDescription, itemEffect, group, scene, translate, boss, hero, indicator, northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard, inventoryPic, selected, score, Labels, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage);
                        break;
                    case SPACE://attacks - sword
                        swordMover(sword_down, sword_left, sword_right, sword_up, hero);
                        if (up || right || left || down) {//this stops the walking sound when you attack
                            walking = 0;
                            walkSounds.stop();
                            MEDIAWalking.stop();
                            l = 0;
                        }
                        AttackHandler(enemies, magic, fireballs, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero, link_running_down_2, link_running_left_2, link_running_right_2, link_running_up_2);
                        inventoryHider(inventory, controls, inventoryPic, selected, itemName, itemType, itemDescription, itemEffect);
                        ScoreBoardHider(scoreBoard, Labels);
                        break;
                    case TAB://opens inventory
                        inventoryManager(scene, inventory, inventoryPic, selected, itemName, itemType, itemDescription, itemEffect);
                        itemInformation(inventory, selected, itemName, itemType, itemDescription, itemEffect);
                        break;
                    case EQUALS://raises volume
                        if (musicVolume < 10) {
                            musicVolume++;
                            VolumeChanger();
                        }
                        break;
                    case MINUS://lowers volume
                        if (musicVolume > 0) {
                            musicVolume--;
                            VolumeChanger();
                        }
                        break;
                    case O:
                        CoinNumber++;
                        coinNumber.setText(String.valueOf(CoinNumber));
                        hero_Health++;
                        if (hero_Health > 6) {
                            hero_Health = 6;
                        }
                        ScoreBoardHider(scoreBoard, Labels);
                        killedBy[0] = "cheats";
                        healthChecker(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy[0], map, indicator
                                , northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                        break;
                    case L:
                        hero_Health--;
                        if (hero_Health < 0) {
                            hero_Health = 0;
                        }
                        ScoreBoardHider(scoreBoard, Labels);
                        killedBy[0] = "cheats";
                        healthChecker(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy[0], map, indicator
                                , northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                        break;
                    case F://full screen
                        if (stage.isFullScreen()) {
                            stage.setFullScreen(false);
                        } else {
                            stage.setFullScreen(true);
                        }
                        break;
                    //inventory controls
                    case W:
                        //System.out.println(selected.getLayoutX() + "   " + selected.getLayoutY());
                        if (!selected.isVisible()) {//shoots a projectile up
                            if (activestaff) {
                                attackDirection = "up_staff";
                                AttackHandler(enemies, magic, fireballs, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero, link_running_down_2, link_running_left_2, link_running_right_2, link_running_up_2);
                            }
                        } else {//moves the selected up, in the inventory
                            selected.setLayoutY(selected.getLayoutY() - 67);
                            if (selected.getLayoutY() <= 314) {
                                selected.setLayoutY(314);
                            }
                            itemInformation(inventory, selected, itemName, itemType, itemDescription, itemEffect);
                        }
                        break;
                    case A:
                        //System.out.println(selected.getLayoutX() + "   " + selected.getLayoutY());
                        if (!selected.isVisible()) {//shoots a projectile left
                            if (activestaff) {
                                attackDirection = "left_staff";
                                AttackHandler(enemies, magic, fireballs, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero, link_running_down_2, link_running_left_2, link_running_right_2, link_running_up_2);
                            }
                        } else {//moves the selected left, in the inventory
                            selected.setLayoutX(selected.getLayoutX() - 68);
                            if (selected.getLayoutX() <= 643) {
                                selected.setLayoutX(643);
                            }
                            itemInformation(inventory, selected, itemName, itemType, itemDescription, itemEffect);
                        }
                        break;
                    case S:
                        //System.out.println(selected.getLayoutX() + "   " + selected.getLayoutY());
                        if (!selected.isVisible()) {//shoots a projectile down
                            if (activestaff) {
                                attackDirection = "down_staff";
                                AttackHandler(enemies, magic, fireballs, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero, link_running_down_2, link_running_left_2, link_running_right_2, link_running_up_2);
                            }
                        } else {//moves the selected down, in the inventory
                            selected.setLayoutY(selected.getLayoutY() + 67);
                            if (selected.getLayoutY() >= 649) {
                                selected.setLayoutY(649);
                            }
                            itemInformation(inventory, selected, itemName, itemType, itemDescription, itemEffect);
                        }
                        break;
                    case D:
                        //System.out.println(selected.getLayoutX() + "   " + selected.getLayoutY());
                        if (!selected.isVisible()) {//shoots a projectile right
                            if (activestaff) {
                                attackDirection = "right_staff";
                                AttackHandler(enemies, magic, fireballs, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero, link_running_down_2, link_running_left_2, link_running_right_2, link_running_up_2);
                            }
                        } else {//moves the selected right, in the inventory
                            selected.setLayoutX(selected.getLayoutX() + 68);
                            if (selected.getLayoutX() >= 915) {
                                selected.setLayoutX(915);
                            }
                            itemInformation(inventory, selected, itemName, itemType, itemDescription, itemEffect);
                        }
                        break;
                    case E:
                        itemInteraction(mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, inventoryARR, itemId, group, scene, hero, indicator, chest, chest_open, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, hearts, inventoryPic, selected, coinNumber_staff, coinNumber_attackBoost, coinNumber, Labels, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage, score, itemName, itemType, itemDescription, itemEffect, map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, translate, bossHealthBar, bossHealthBarREC, boss);
                        //for picking up and using items
                        break;
                    case I:
                        /*
                        for (int m = 0; m < 6; m++) {
                            for (int o = 0; o < 5; o++) {
                                System.out.print(inventoryARR[o][m]);
                                System.out.print(" ");
                            }
                            System.out.println("");
                        }
                         */
                        if (!controls.isVisible()) {
                            controls.setVisible(true);//shows the inventory controls
                        } else {
                            controls.setVisible(false);//hides the inventory controls
                        }
                        break;
                    case M:
                        MusicControls();//mutes or un-mutes the music
                        break;
                }
            });
            scene.setOnKeyReleased(event -> {
                try {
                    switch (event.getCode()) {
                        case UP:
                            up = false;
                        case DOWN:
                            down = false;
                        case LEFT:
                            left = false;
                        case RIGHT:
                            right = false;
                            walking = 0;
                            walkSounds.stop(); //stops the weird sound glitch
                            MEDIAWalking.stop();
                            l = 0;
                            //
                            heroMover(hero);
                            Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, hero);
                            break;
                    }
                } catch (Exception e) {
                    //System.out.println("");
                }
            });
            /*
            scene.setOnMouseMoved(event -> {
                if (scene.getCursor() == Cursor.DEFAULT) {
                }
            });
             */
        } catch (Exception e) {
            System.out.println("input manager error");
        }
    }

    private void itemInformation(ArrayList<Inventory> inventory, ImageView selected, Label itemName, Label itemType, Label itemDescription, Label itemEffect) {
        for (int i1 = 0; i1 < inventory.size(); i1++) {//checks the items until it finds the correct one
            Inventory item = inventory.get(i1);
            if (selected.getBoundsInParent().intersects(item.getImage().getBoundsInParent()) && item.getImage().isVisible() && item.getImage().getOpacity() > 0) {

                itemName.setText(item.getName());

                itemType.setText(item.getItemType());

                itemDescription.setText(item.getDescription());     //sets text for the item

                itemEffect.setText(item.getEffect());

                itemName.setVisible(true);
                itemType.setVisible(true);
                itemDescription.setVisible(true);//fades in
                itemEffect.setVisible(true);

                i1 = inventory.size();//stops the for when it finds the item
            } else {
                itemName.setText("");
                itemType.setText("");
                itemDescription.setText("");//clears text
                itemEffect.setText("");
            }
        }
    }

    private void MusicControls() {
        if (!activeBoss) {                        //decides if the background music or boss music is playing
            if (!MEDIABackground.isMute()) {
                MEDIABackground.setMute(true);
            } else {
                MEDIABackground.setMute(false);
            }
        } else {
            if (!MEDIABoss.isMute()) {
                MEDIABoss.setMute(true);
            } else {
                MEDIABoss.setMute(false);
            }
        }
    }

    private void scoreSaver(Label[][] Labels) {
        boolean updated = false;
        int finalScore = Score + (CoinNumber * 10);// 1 coin = 10 Score when the boss is defeated.
        System.out.println(playerName);
        String SQL;
        //this writes to the database
        //String connectionUrl = "jdbc:sqlserver://ks-sql-02:1433;" + "databaseName=db10;integratedSecurity=true";//connection
        String connectionUrl = "jdbc:sqlserver://DESKTOP-38akage:1433;" + "databaseName=master;integratedSecurity=true";//home

        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            for (int m = 0; m < 10; m++) {
                if (Labels[m][1].getText().equals(playerName)) {//this checks if that player is already in the leader board
                    if (Integer.parseInt(Labels[m][2].getText()) < finalScore) {
                        //update the score
                        SQL = "use master; UPDATE ScoreTable SET Score =" + finalScore + "WHERE Name ='" + playerName + "'";         //updates score
                        stmt = con.createStatement();
                        int i = stmt.executeUpdate(SQL);
                        if (i == 1) {
                            System.out.println("a score has been updated");
                        }
                        updated = true;
                    }
                }
            }
            if (!updated) {
                SQL = "use master; insert into ScoreTable (Name, Score) values ('" + playerName + "'," + finalScore + ");";          //enters new score
                stmt = con.createStatement();//puts your name (from the menu) and score into the database ^
                int i = stmt.executeUpdate(SQL);
                if (i == 1) {
                    System.out.println("New Score added");
                    SQL = "use master; delete from ScoreTable where score <" + Labels[9][2].getText();                               //deletes new lowest score
                    i = stmt.executeUpdate(SQL);//deletes the lowest score ^
                    if (i > 0) {
                        System.out.println(i + " Scores were deleted");
                    }
                } else {
                    System.out.println("New Score not added. Something went wrong!");
                }
            }
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            //System.out.println("Error saving scores");
            //e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }
    }

    private void itemInteraction(ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, int[][] inventoryARR, int itemId, Group group, Scene scene, ImageView hero, ImageView indicator, ImageView chest, Image chest_open, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, ImageView hearts, ImageView inventoryPIC, ImageView selected, Label coinNumber_staff, Label coinNumber_attackBoost, Label coinNumber, Label Labels[][], ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, Stage stage, Label score, Label itemName, Label itemType, Label itemDescription, Label itemEffect, int[][] map, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView background, TranslateTransition translate, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView boss) {

        if (hero.getBoundsInParent().intersects(mapItem.getBoundsInParent()) && mapItem.isVisible() && CoinNumber >= 2) {//map
            //buys the map from the shop
            activeMap = true;
            mapItem.setVisible(false);
            coinNumber_mapItem.setVisible(false);
            coinIcon_mapItem.setVisible(false);
            CoinNumber = CoinNumber - 2;
            coinNumber.setText(String.valueOf(CoinNumber));
            new Pulse(hero).play();
            for (int m = 0; m < 361; m++) {
                mapPieces.get(m).getImage().setVisible(true);
            }
            indicator.setVisible(true);
        }
        if (hero.getBoundsInParent().intersects(staff.getBoundsInParent()) && staff.isVisible() && CoinNumber >= 5) {//staff
            //buys the staff from the shop
            activestaff = true;
            staff.setVisible(false);
            coinNumber_staff.setVisible(false);
            coinIcon_staff.setVisible(false);
            CoinNumber = CoinNumber - 5;
            coinNumber.setText(String.valueOf(CoinNumber));
            new Pulse(hero).play();
        }
        if (hero.getBoundsInParent().intersects(attackBoost.getBoundsInParent()) && attackBoost.isVisible() && CoinNumber >= 5) {//attack boost item
            //buys the attack boost from the shop
            activeAttackBoost = true;
            attackBoost.setVisible(false);
            coinNumber_attackBoost.setVisible(false);
            coinIcon_attackBoost.setVisible(false);
            CoinNumber = CoinNumber - 5;
            coinNumber.setText(String.valueOf(CoinNumber));
            new Pulse(hero).play();
            itemId = 2;
            inventory.add(new Inventory(itemId));
            for (int m = 0; m < 6; m++) {
                for (int o = 0; o < 5; o++) {
                    if (inventoryARR[o][m] == 0) {//finds the first free slot in the inventory
                        inventory.get(inventory.size() - 1).getImage().relocate((643 + (68 * (o))), 314 + (67 * (m)));
                        inventory.get(inventory.size() - 1).getImage().setVisible(false);
                        inventoryARR[o][m] = inventory.get(inventory.size() - 1).getID();
                        o = 5;
                        m = 6;
                    }
                }
            }
            for (Inventory anInventory : inventory) {
                anInventory.getImage().setOnMouseClicked(event -> usingItems(mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, inventoryARR, score, group, scene, anInventory, hearts, stage, selected, itemName, itemType, itemDescription, itemEffect, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, bossHealthBar, bossHealthBarREC, boss));
            }
            group.getChildren().add(inventory.get(inventory.size() - 1).getImage());
        }
        if (chest.isVisible() && hero.getBoundsInParent().intersects(chest.getBoundsInParent())) {
            if (f == 0) {
                f = 1;
                //opens the chest after the boss fight
                chest.setImage(chest_open);
                Image link_holding_triforce = new Image("file:src\\game\\link_holding_triforce.png");
                hero.setImage(link_holding_triforce);
                new Pulse(hero).play();
                sound = "triforce";
                soundManager();
                scoreSaver(Labels);
            }
        }
        if (!inventoryPIC.isVisible()) {
            //picking up an item/imageViewIMG
            for (Items image : items) {
                if (hero.getBoundsInParent().intersects(image.getImage().getBoundsInParent()) && image.getImage().getOpacity() > 0 && image.getImage().isVisible()) {//checks if you're touching any potions
                    image.getImage().setOpacity(0);
                    //the item is removed from the 'items' class and the same item is added to the 'inventory' class
                    String item = image.getName();
                    System.out.println(item);
                    //        Tooltip.install(staff,new Tooltip("A powerful ranged weapon"));

                    inventory.add(new Inventory(image.getID()));//adds an item to the inventory
                    for (Inventory anInventory : inventory) {
                        anInventory.getImage().setOnMouseClicked(event -> usingItems(mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, inventoryARR, score, group, scene, anInventory, hearts, stage, selected, itemName, itemType, itemDescription, itemEffect, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, bossHealthBar, bossHealthBarREC, boss));
                    }
                    for (int m = 0; m < 6; m++) {
                        for (int o = 0; o < 5; o++) {
                            if (inventoryARR[o][m] == 0) {//finds the first free slot in the inventory
                                inventory.get(inventory.size() - 1).getImage().relocate((643 + (68 * (o))), 314 + (67 * (m)));
                                //positions it relative the first 0 that it found
                                inventory.get(inventory.size() - 1).getImage().setVisible(false);
                                inventoryARR[o][m] = inventory.get(inventory.size() - 1).getID();//updates the array
                                o = 5;
                                m = 6;//stops the for when it finds its first zero
                            }
                        }
                    }
                    group.getChildren().add(inventory.get(inventory.size() - 1).getImage());
                }
            }
        } else {
            //using an item/imageViewIMG
            for (Inventory anInventory : inventory) {//
                if (selected.getBoundsInParent().intersects(anInventory.getImage().getBoundsInParent()) && anInventory.getImage().getOpacity() > 0 && anInventory.getImage().isVisible()) {
                    usingItems(mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, inventoryARR, score, group, scene, anInventory, hearts, stage, selected, itemName, itemType, itemDescription, itemEffect, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, bossHealthBar, bossHealthBarREC, boss);
                }
            }
        }
    }

    private void usingItems(ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, int[][] inventoryARR, Label score, Group group, Scene scene, Inventory anInventory, ImageView hearts, Stage stage, ImageView selected, Label itemName, Label itemType, Label itemDescription, Label itemEffect, int[][] map, ImageView indicator, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView hero, ImageView background, TranslateTransition translate, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView boss) {
        if (anInventory.getImage().getOpacity() > 0 && anInventory.getImage().isVisible()) {
            Score = Score - 25;
            int i = 0;
            int j = 0;

            System.out.println(anInventory.getImage().getLayoutX() + " " + anInventory.getImage().getLayoutY());
            System.out.println(selected.getLayoutX() + " " + selected.getLayoutY());

            while (anInventory.getImage().getLayoutX() > 643) {
                anInventory.getImage().setLayoutX(anInventory.getImage().getLayoutX() - 68);
                i++;
            }
            while (anInventory.getImage().getLayoutY() > 314) {
                anInventory.getImage().setLayoutY(anInventory.getImage().getLayoutY() - 67);
                j++;
            }
            inventoryARR[i][j] = 0;
            score.setText(String.valueOf(Score));
            anInventory.getImage().setOpacity(0);
            anInventory.getImage().setVisible(false);
            group.getChildren().remove(anInventory.getImage());
            String relativeItemName = anInventory.getName();
            System.out.println(relativeItemName + " used");
            switch (relativeItemName) {
                case "Health potion"://uses a health potion
                    hero_Health = hero_Health + 2;
                    String killedBy = "";
                    healthChecker(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                    if (hero_Health >= 6) {
                        hero_Health = 6;
                        healthChecker(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                    }
                    break;
                case "Attack boost"://uses an attack boost
                    anInventory.getImage().setOpacity(0);
                    group.getChildren().remove(anInventory.getImage());
                    attackmultiplier = 1.25;
                    System.out.println("attack is now " + attackmultiplier);
                    break;
            }
            itemInformation(inventory, selected, itemName, itemType, itemDescription, itemEffect);
        }
    }

    private void VolumeChanger() {
        if (!activeBoss) {
            MEDIABackground.setVolume(0.05 * musicVolume);
        } else {
            MEDIABoss.setVolume(0.05 * musicVolume);
        }
        System.out.println("Volume is: " + musicVolume);
    }

    private void healthChecker(int[][] inventoryARR, ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, ImageView hearts, Group group, Scene scene, Stage stage, String killedBy, int[][] map, ImageView indicator, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView hero, ImageView background, TranslateTransition translate, Label score, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView boss) {
        switch (hero_Health) {
            case 0:
                Image heart_0 = new Image("file:src\\game\\hearts\\heart_0.png");
                hearts.setImage(heart_0);
                System.out.println("Player has died");
                deathScreen(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy, map, indicator
                        , northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                break;
            case 1:
                Image heart_05 = new Image("file:src\\game\\hearts\\heart_05.png");
                hearts.setImage(heart_05);
                break;
            case 2:
                Image heart_1 = new Image("file:src\\game\\hearts\\heart_1.png");
                hearts.setImage(heart_1);
                break;
            case 3:
                Image heart_15 = new Image("file:src\\game\\hearts\\heart_15.png");
                hearts.setImage(heart_15);
                break;
            case 4:
                Image heart_2 = new Image("file:src\\game\\hearts\\heart_2.png");
                hearts.setImage(heart_2);
                break;
            case 5:
                Image heart_25 = new Image("file:src\\game\\hearts\\heart_25.png");
                hearts.setImage(heart_25);
                break;
            case 6:
                Image heart_3 = new Image("file:src\\game\\hearts\\heart_3.png");
                hearts.setImage(heart_3);
                break;
        }
    }

    private void deathScreen(int[][] inventoryARR, ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, ImageView hearts, Group group, Scene scene, Stage stage, String killedBy, int[][] map, ImageView indicator, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView hero, ImageView background, TranslateTransition translate, Label score, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView boss) {
        Group deathGroup = new Group();
        Scene deathSceen = new Scene(deathGroup, Color.BLACK);
        if (activeBoss) {
            MEDIABoss.stop();
        } else {                                        //stops the music
            MEDIABackground.stop();
        }
        sound = "Death";
        soundManager();                          //starts the Death music
        final int[] number = {0};
        new FadeOut(group).play();                      //fades everything out for a smooth transition
        ImageView paper = new ImageView("file:src\\game\\paper.png");
        paper.relocate(290, 89);
        deathSceen.setOnKeyPressed(event1 -> {               //resets key inputs
            switch (event1.getCode()) {
                case ESCAPE://closes game
                    System.exit(30);
                    break;                              //new key listener for the new buttons
                case SPACE://resets game
                    gameReset(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                    break;
            }
        });
        Timeline deathTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            number[0]++;
            switch (number[0]) {
                case 4:                                   //2 seconds in the paper will appear
                    stage.setScene(deathSceen);
                    stage.setFullScreen(true);
                    deathGroup.getChildren().add(paper);
                    new FadeIn(deathGroup).play();
                    break;
                case 7:                                   //3.5 seconds in the 'you died' text will appear
                    ImageView deathText = new ImageView("file:src\\game\\DeathText.png");
                    deathText.relocate(398, 150);
                    deathGroup.getChildren().add(deathText);
                    new FadeIn(deathText).play();
                    break;
                case 10:                                  //5 seconds in the 'killed by' message appears
                    ImageView deathText2 = new ImageView("file:src\\game\\DeathText222.png");
                    deathText2.relocate(448, 414);
                    deathGroup.getChildren().add(deathText2);
                    new FadeIn(deathText2).play();
                    //
                    ImageView killedbyEnemy = new ImageView(); //gets the enemy that killed the player.
                    switch (killedBy) {
                        case "sword_enemy":
                            killedbyEnemy = new ImageView("file:src\\game\\sword_enemy_down_1.png");
                            break;
                        case "troll":
                            killedbyEnemy = new ImageView("file:src\\game\\troll_down_1.png");
                            break;
                        case "cheats":
                            killedbyEnemy = new ImageView("file:src\\game\\missing.png");
                            break;
                        case "boss":
                            killedbyEnemy = new ImageView("file:src\\game\\Wizard.png");
                            break;
                    }
                    killedbyEnemy.relocate(1289, 446);
                    deathGroup.getChildren().add(killedbyEnemy);
                    new FadeIn(killedbyEnemy).play();
                    break;
                case 16:                                                //8 seconds in the buttons will appear
                    ImageView exit = new ImageView("file:src\\game\\Exit button.png");
                    exit.relocate(0, 1080 - 338);
                    deathGroup.getChildren().add(exit);
                    new FadeIn(exit).play();
                    exit.setOnMouseClicked(event1 -> System.exit(12));//closes the program if the exit image is clicked
                    //
                    ImageView restart = new ImageView("file:src\\game\\Restart button.png");
                    restart.relocate(1920 - 468, 1080 - 438);
                    deathGroup.getChildren().add(restart);
                    new FadeIn(restart).play();
                    restart.setOnMouseClicked(event1 -> {//restart button
                        gameReset(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                    });
            }
        }));
        deathTimeline.setCycleCount(17);  //16 cycles of 0.5 seconds = 8 seconds for the death screen to complete
        deathTimeline.play();

    }

    private void gameReset(int[][] inventoryARR, ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, ImageView hearts, Group group, Scene scene, Stage stage, String Killedby, int[][] map, ImageView indicator, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView hero, ImageView background, TranslateTransition translate, Label score, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView boss) {
        hero.relocate(885, 540);

        sound = "background";
        soundManager();

        if (activeBoss) {
            bossHealthBar.setVisible(false);
            bossHealthBarREC.setVisible(false);
            boss.setVisible(false);
        }
        {
            positionX = 9;
            positionY = 9;
            enemyAnimation = "b";
            activeBoss = false;
            activestaff = false;
            activeAttackBoost = false;
            activeMap = false;
            defeatedBoss = false;
            direction = "down";//the representation of this variable breaks if non global
            attackDirection = "down";//the representation of this variable breaks if non global
            scoretimer = 0;
            attacking = false;
            walking = 0;
            i = 0;
            j = 0;
            l = 0;
            k = 0;
            q = 0;
            v = 0;
            y = 0;
            c = 0;
            s = 0;
            f = 0;
            n = 0;
            timePassed = 0;
            timepassed1 = 0;
            timePassed2 = 0;
            timePassed3 = 0;
            timepassed7 = 0;
            timepassed8 = 0;
            defeatedEnemies = 0;
            number = 0;
            attackmultiplier = 1;
            up = false;
            right = false;
            left = false;
            down = false;
        }//resets global variables
        System.out.println("Game reset");
        for (Inventory value : inventory) {
            value.getImage().setVisible(false);                     //resets inventory array
            group.getChildren().remove(value.getImage());
        }
        inventory.clear();
        for (Fireballs fireball : fireballs) {
            fireball.getImage().setVisible(false);                  //resets fireballs array
            group.getChildren().remove(fireball.getImage());
        }
        fireballs.clear();
        for (Magic value : magic) {
            value.getImage().setVisible(false);                     //resets magic array
            group.getChildren().remove(value.getImage());
        }
        magic.clear();
        for (Enemy enemy : enemies) {
            enemy.getImage().setVisible(false);                     //resets enemies array
            group.getChildren().remove(enemy.getImage());
        }
        enemies.clear();
        for (Items item : items) {
            item.getImage().setVisible(false);                     //resets items array
            group.getChildren().remove(item.getImage());
        }
        items.clear();

        backgrounds.clear();       //resets backgrounds array
        //because the array doesn't add anything to the group, a for isn't needed

        for (MapPieces mapPiece : mapPieces) {
            mapPiece.getImage().setVisible(false);                     //resets mapPieces array
            group.getChildren().remove(mapPiece.getImage());
        }
        for (int m = 0; m < 5; m++) {
            for (int j = 0; j < 6; j++) {                              //resets the inventoryARR array
                inventoryARR[m][j] = 0;
            }
        }
        mapPieces.clear();
        CoinNumber = 0;//resets coins
        mapMaker(mapPieces, backgrounds, items, enemies, group, map);//makes a new map
        stage.setScene(scene);
        stage.setFullScreen(true);
        new FadeIn(group).play();
        hero_Health = 6;//resets health
        healthChecker(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, Killedby, map, indicator
                , northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
        Score = 1000;//resets score
        //sets up the starting room again
        doorChanger(map, northDoor, eastDoor, southDoor, westDoor, bossDoor);
        enemyChanger(enemies);
        backgroundChanger(backgrounds, background);
        enemyMover(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, score, translate, hearts, map, hero, group, scene, stage, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, bossHealthBar, bossHealthBarREC, boss);
        itemChanger(items);
        //sets up the starting room again

        int i = 0;
        for (MapPieces mappiece : mapPieces) {//makes the map appear in front of EVERYTHING
            mappiece.getImage().toFront();
            mappiece.getImage().setVisible(false);
            i++;                                                                //resets map position
            if (i == 181) {//array location of starting room
                indicator.relocate(mappiece.getImage().getLayoutX(), mappiece.getImage().getLayoutY());
                indicator.setOpacity(0.3);
                indicator.setVisible(false);
            }
        }
    }

    private void staffShooter(ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, Label score, Group group, TranslateTransition translate, ImageView boss, Image bossDoor_open, ImageView bossDoor, Rectangle bossHealthBarREC, Label coinNumber, ImageView hero) {
        try {
            TranslateTransition translateMagic = new TranslateTransition();
            magic.add(new Magic());
            //c = 1;
            group.getChildren().add(magic.get(0).getImage());
            translateMagic.setDuration(Duration.seconds(2));
            translateMagic.setNode(magic.get(0).getImage());
            sound = "magic_shot";
            soundManager();
            switch (attackDirection) {//sets the direction of the projectile
                case "up_staff":
                    magic.get(0).getImage().relocate(hero.getLayoutX() + 50, hero.getLayoutY());
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX() + hero.getLayoutX());
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY());
                    break;
                case "left_staff":
                    magic.get(0).getImage().relocate(hero.getLayoutX(), hero.getLayoutY() + 55);
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX());
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY() + hero.getLayoutY());
                    break;
                case "right_staff":
                    magic.get(0).getImage().relocate(hero.getLayoutX() + 235, hero.getLayoutY() + 90);
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX() + 1900);
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY() + (hero.getLayoutY() + 60));
                    break;
                case "down_staff":
                    magic.get(0).getImage().relocate(hero.getLayoutX() + 80, hero.getLayoutY() + 270);
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX() + (hero.getLayoutX() + 60));
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY() + 1060);
                    break;
            }
            translateMagic.setOnFinished(event -> {                //removes the magic sprite when its flight duration is over
                try {
                    magic.get(0).getImage().setVisible(false);
                    group.getChildren().remove(magic.get(0).getImage());
                    magic.remove(0);
                } catch (Exception e) {
                    // System.out.println("Error removing magic when finished");
                }
            });
            translateMagic.play();
            if (c == 0) {
                Timeline magicShooterTimeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
                    c = 1;
                    try {
                        if (!activeBoss) {
                            for (int m = 0; m < enemies.size(); m++) {
                                Enemy enemy = enemies.get(m);
                                if (magic.get(0).getImage().getBoundsInParent().intersects(enemy.getImage().getBoundsInParent()) && enemy.getRoomY() == positionY && enemy.getRoomX() == positionX && enemy.getImage().isVisible() && enemy.getImage().getOpacity() > 0 && timePassed >= 1) {
                                    translate.stop();
                                    if (enemies.get(m).getImage().getOpacity() == 0.5) {
                                        enemies.get(m).getImage().setOpacity(0);
                                        magic.get(0).getImage().setVisible(false);
                                        magic.remove(0);
                                        Score = Score + 50;
                                        score.setText(String.valueOf(Score));//converts to atomic to deal with effectively final variable
                                        CoinNumber = CoinNumber + 1;
                                        coinNumber.setText(String.valueOf(CoinNumber));
                                        defeatedEnemies++;
                                        System.out.println(defeatedEnemies + " out of " + (enemies.size() - 1));
                                        if (defeatedEnemies == enemies.size() - 1) {
                                            bossDoor.setImage(bossDoor_open);
                                            System.out.println("Boss door open");
                                        }
                                    } else {
                                        enemies.get(m).getImage().setOpacity(0.5);
                                        magic.get(0).getImage().setVisible(false);
                                        magic.remove(0);
                                    }
                                }
                            }
                        } else {//boss is active.
                            if (magic.get(0).getImage().getBoundsInParent().intersects(boss.getBoundsInParent()) && boss.isVisible()) {//when you hit the boss
                                magic.get(0).getImage().setVisible(false);
                                magic.remove(0);
                                y++;
                                System.out.println("hit boss");
                                bossHealthBarREC.setWidth(658 - (((65.8) / 2 * y)) * attackmultiplier);
                                System.out.println("The boss has " + Math.floor(bossHealthBarREC.getWidth()) + " Health left");
                                System.out.println("hit boss " + y + " times");
                            } else {//when the magic hits the fireball
                                for (int m = 0; m < fireballs.size(); m++) {
                                    if (magic.get(0).getImage().getBoundsInParent().intersects(fireballs.get(m).getImage().getBoundsInParent()) && fireballs.get(m).getImage().isVisible() && fireballs.get(m).getImage().getOpacity() > 0) {
                                        timepassed7 = 2;
                                        System.out.println("collision with fireball");
                                        sound = "Explosion";
                                        soundManager();
                                        magic.get(0).getImage().setVisible(false);
                                        magic.get(0).getImage().toBack();
                                        group.getChildren().remove(magic.get(0).getImage());
                                        magic.remove(0);
                                        fireballs.get(m).getImage().setVisible(false);
                                        fireballs.get(m).getImage().toBack();
                                        group.getChildren().remove(fireballs.get(m).getImage());
                                        fireballs.remove(m);
                                        //timepassed7=2;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        //System.out.println("Error in magic timeline");
                    }
                }));
                magicShooterTimeline.setCycleCount(Timeline.INDEFINITE);
                magicShooterTimeline.play();
            }
        } catch (Exception e) {
            //System.out.println("Error in staff shooter timeline");
        }
    }

    private void attackingEnemies(ArrayList<Enemy> enemies, Label score, TranslateTransition translate, ImageView boss, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open, ImageView bossDoor, ImageView bossHealthBar, Rectangle bossHealthBarREC, Label coinNumber) {
        try {
            if (activeBoss) {//attacking the boss
                if (timePassed >= 1 && usedSword.getBoundsInParent().intersects(boss.getBoundsInParent())) {
                    timePassed = 0;
                    y++;
                    System.out.println("hit boss");
                    bossHealthBarREC.setWidth(658 - (((65.8) / 2 * y)) * attackmultiplier);//updates boss health bar
                    System.out.println("The boss has " + Math.floor(bossHealthBarREC.getWidth()) + " Health left");
                    System.out.println("Boss has been hit " + y + " times");
                    //timepassed5 = 0;
                    sound = "swordHit";//hit the boss
                    soundManager();
                }
            } else if (!activeBoss) {//attacking enemies
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy enemy = enemies.get(i);
                    if (enemy.getRoomY() == positionY && enemy.getRoomX() == positionX) {//finds the enemy in your room
                        if (usedSword.getBoundsInParent().intersects(enemy.getImage().getBoundsInParent()) && enemy.getImage().isVisible() && enemy.getImage().getOpacity() > 0 && timePassed >= 1) {
                            //^^^detects if the sword is intersecting the enemy
                            sound = "swordHit";
                            soundManager();//hit the enemy
                            System.out.println("hit enemy in room " + positionY + " " + positionX);
                            translate.stop();//stops the enemy if its moving
                            if (enemy.getImage().getOpacity() == 0.5) {//enemy hit twice therefore defeated
                                enemy.getImage().setOpacity(0);
                                Score = Score + 50;
                                score.setText(String.valueOf(Score));//updates score
                                CoinNumber = CoinNumber + 1;
                                coinNumber.setText(String.valueOf(CoinNumber));//updates coin number
                                defeatedEnemies++;
                                System.out.println(defeatedEnemies + " out of " + (enemies.size() - 1));
                                if (defeatedEnemies == enemies.size() - 1) {
                                    //boss door opens if all enemies are defeated
                                    bossDoor.setImage(bossDoor_open);
                                    System.out.println("boos door open");
                                }
                            } else {//enemy hit once
                                enemy.getImage().setOpacity(0.5);
                            }
                            timePassed = 0;
                        } else {
                            //not hitting an enemy
                            sound = "swordMiss";
                            soundManager();
                        }
                    }
                }
            }
        } catch (Exception e) {
            // System.out.println("Difficulty attacking enemies");
            sound = "swordMiss";
            soundManager();
        }
    }

    private void enemyMover(int[][] inventoryARR, ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, Label score, TranslateTransition translate, ImageView hearts, int[][] map, ImageView hero, Group group, Scene scene, Stage stage, ImageView indicator, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView background, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView boss) {
        translate.setDuration(Duration.seconds(3.3));//takes 3.3 seconds to move to its end cords
        if (s == 0) {
            for (Enemy enemy : enemies) {
                if (enemy.getRoomY() == positionY && enemy.getRoomX() == positionX) {
                    enemyAnimater(enemy, hero, translate);//starts the enemy's animation
                }
            }
            EnemyMover = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
                s = 1;
                if (map[positionY][positionX] == 1) {
                    for (Enemy enemy : enemies) {
                        if (enemy.getRoomY() == positionY && enemy.getRoomX() == positionX) {
                            translate.setNode(enemy.getImage());
                            translate.setToX(-enemy.getLayoutX() + hero.getLayoutX());
                            translate.setToY(-enemy.getLayoutY() + hero.getLayoutY());//tells the enemy where to go
                            translate.setInterpolator(Interpolator.LINEAR);
                            if (!(enemy.getImage().isVisible())) {
                                translate.stop();
                            }
                            translate.play();
                            translate.setOnFinished(event1 -> {
                                if (n == 1) {
                                    subEnemy.stop();
                                }
                                enemyAnimater(enemy, hero, translate);//starts animating the enemy again when it stops
                            });
                            if (hero.getBoundsInParent().intersects(enemy.getImage().getBoundsInParent()) && timePassed2 >= 2 && enemy.getImage().getOpacity() > 0 && enemy.getImage().isVisible() && (!attacking)) {
                                hero_Health = hero_Health - 1;
                                String killedBy = enemy.getName();
                                healthChecker(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, hearts, group, scene, stage, killedBy, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, hero, background, translate, score, bossHealthBar, bossHealthBarREC, boss);
                                timePassed2 = 0;
                            }
                        }
                    }
                }
            }));
            EnemyMover.setCycleCount(Timeline.INDEFINITE);
            EnemyMover.play();
        }
    }

    private void enemyAnimater(Enemy enemy, ImageView hero, TranslateTransition translate) {
        int num = 0;
        double Gradient = (hero.getLayoutY() - ((enemy.getImage().getLayoutY() - 82) + enemy.getImage().getTranslateY())) / (hero.getLayoutX() - ((enemy.getImage().getLayoutX() - 75) + enemy.getImage().getTranslateX()));
        int horizontalDistance = (int) (hero.getLayoutX() - enemy.getImage().getTranslateX()) - 780 - 105;//accounts for a bug where h d is 105 when it should be 0
        int verticalDistance = (int) ((hero.getLayoutY() - enemy.getImage().getTranslateY())) - 540 + 75;//accounts for a bug where v d is -75 when it should be 0
        String name = enemy.getName();
        //Gradient = Math.floor(Gradient);
        if (horizontalDistance > 0 && (Gradient < 0.51 && Gradient > -0.51) && num == 0) {
            //System.out.println("horizontal distance: " + horizontalDistance + "             Vertical distance: " + verticalDistance + "             Gradient: " + Gradient + " Direction: right");
            Image right_1 = new Image("file:src\\game\\" + name + "_right_1.png");
            Image right_2 = new Image("file:src\\game\\" + name + "_right_2.png");
            enemy.setImage(right_2);
            num = 1;
            subEnemyAnimater(enemy, right_1, right_2, translate);
        }
        if (horizontalDistance < 0 && (Gradient > -0.51 && Gradient < 0.51) && num == 0) {
            //System.out.println("horizontal distance: " + horizontalDistance + "                Vertical distance: " + verticalDistance + "                Gradient: " + Gradient + " Direction: left");
            Image left_1 = new Image("file:src\\game\\" + name + "_left_1.png");
            Image left_2 = new Image("file:src\\game\\" + name + "_left_2.png");
            enemy.setImage(left_2);
            num = 1;
            subEnemyAnimater(enemy, left_1, left_2, translate);
        }
        if (verticalDistance < 0 && (Gradient > -105 && Gradient < 105) && num == 0) {
            //System.out.println("horizontal distance: " + horizontalDistance + "                  Vertical distance: " + verticalDistance + "             Gradient: " + Gradient + " Direction: up");
            Image up_1 = new Image("file:src\\game\\" + name + "_up_1.png");
            Image up_2 = new Image("file:src\\game\\" + name + "_up_2.png");
            enemy.setImage(up_2);
            num = 1;
            subEnemyAnimater(enemy, up_1, up_2, translate);
        }
        if (verticalDistance > 0 && (Gradient > -105 && Gradient < 105) && num == 0) {
            //System.out.println("horizontal distance: " + horizontalDistance + "                  Vertical distance: " + verticalDistance + "              Gradient: " + Gradient + " Direction: down");
            Image down_1 = new Image("file:src\\game\\" + name + "_down_1.png");
            Image down_2 = new Image("file:src\\game\\" + name + "_down_2.png");
            enemy.setImage(down_2);
            num = 1;
            subEnemyAnimater(enemy, down_1, down_2, translate);
        }
    }

    private void subEnemyAnimater(Enemy enemy, Image direction_1, Image direction_2, TranslateTransition translate) {
        subEnemy = new Timeline(new KeyFrame(Duration.seconds(0.4), event -> {//swaps images every 0.4 seconds
            n = 1;
            switch (enemyAnimation) {
                case "a":
                    enemy.setImage(direction_2);
                    enemyAnimation = "b";
                    break;//                       this will swap between two images
                case "b":
                    enemy.setImage(direction_1);
                    enemyAnimation = "a";
                    break;
            }
            translate.setOnFinished(event1 -> {
                subEnemy.stop();
                n = 0;
            });
        }));
        subEnemy.setCycleCount(Timeline.INDEFINITE);
        subEnemy.play();
    }

    private void AttackHandler(ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, Label score, Group group, TranslateTransition translate, ImageView boss, Image link_sword_down, Image link_sword_left, Image link_sword_right, Image link_sword_up, Image link_staff_down, Image link_staff_left, Image link_staff_right, Image link_staff_up, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open, ImageView bossDoor, ImageView bossHealthBar, Rectangle bossHealthBarREC, Label coinNumber, Image link_running_down_1, Image link_running_left_1, Image link_running_right_1, Image link_running_up_1, ImageView hero, Image link_running_down_2, Image link_running_left_2, Image link_running_right_2, Image link_running_up_2) {
        switch (attackDirection) {
            case "up":
            case "right":
            case "left":
            case "down":
                if ((hero.getImage() != link_sword_up) && (hero.getImage() != link_sword_left) && (hero.getImage() != link_sword_right) && (hero.getImage() != link_sword_down)) {
                    switch (attackDirection) {
                        case "up":
                            hero.setImage(link_sword_up);
                            hero.setLayoutY(hero.getLayoutY() - 100);//accounts for image skew
                            usedSword = sword_up;
                            break;
                        case "down":
                            hero.setImage(link_sword_down);
                            usedSword = sword_down;
                            break;
                        case "left":
                            hero.setImage(link_sword_left);
                            hero.setLayoutX(hero.getLayoutX() - 100);//accounts for image skew
                            usedSword = sword_left;
                            break;
                        case "right":
                            hero.setImage(link_sword_right);
                            usedSword = sword_right;
                            break;
                    }
                    attacking = true;
                    attackingEnemies(enemies, score, translate, boss, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                    timepassed1 = 0;
                }
                break;
            //
            //staff handler hero -> staff
            //
            case "left_staff":
            case "right_staff":
            case "up_staff":
            case "down_staff":
                if (hero.getImage() != link_staff_down && hero.getImage() != link_staff_up && hero.getImage() != link_staff_left && hero.getImage() != link_staff_right) {
                    switch (attackDirection) {
                        case "up_staff":
                            hero.setImage(link_staff_up);
                            hero.setLayoutY(hero.getLayoutY() - 100);
                            break;
                        case "left_staff":
                            hero.setImage(link_staff_left);
                            hero.setLayoutX(hero.getLayoutX() - 100);
                            break;
                        case "right_staff":
                            hero.setImage(link_staff_right);
                            break;
                        case "down_staff":
                            hero.setImage(link_staff_down);
                            break;
                    }
                    if (timepassed7 >= 2) {
                        staffShooter(enemies, magic, fireballs, score, group, translate, boss, bossDoor_open, bossDoor, bossHealthBarREC, coinNumber, hero);
                        timepassed7 = 0;
                        timepassed1 = 0;
                    }
                }
                break;
        }
        if (j == 0) {
            Timeline animationReverser = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
                j = 1;
                if (timepassed1 >= 1) {
                    switch (attackDirection) {
                        case "up":
                        case "down":
                        case "left":
                        case "right":
                            if (hero.getImage() == link_sword_down || hero.getImage() == link_sword_up || hero.getImage() == link_sword_left || hero.getImage() == link_sword_right) {
                                switch (attackDirection) {
                                    case "up":
                                        hero.setImage(link_running_up_1);
                                        hero.setLayoutY(hero.getLayoutY() + 100);
                                        break;
                                    case "down":
                                        hero.setImage(link_running_down_1);
                                        break;
                                    case "left":
                                        hero.setImage(link_running_left_1);
                                        hero.setLayoutX(hero.getLayoutX() + 100);
                                        break;
                                    case "right":
                                        hero.setImage(link_running_right_1);
                                        break;
                                }
                                attacking = false;
                                swordMover(sword_down, sword_left, sword_right, sword_up, hero);
                            }
                            //
                            //staff handler, staff -> hero
                            //
                        case "up_staff":
                        case "left_staff":
                        case "right_staff":
                        case "down_staff":
                            if (hero.getImage() == link_staff_down || hero.getImage() == link_staff_left || hero.getImage() == link_staff_up || hero.getImage() == link_staff_right) {
                                switch (attackDirection) {
                                    case "up_staff":
                                        hero.setImage(link_running_up_1);
                                        hero.setLayoutY(hero.getLayoutY() + 100);
                                        break;
                                    case "left_staff":
                                        hero.setImage(link_running_left_1);
                                        hero.setLayoutX(hero.getLayoutX() + 100);
                                        break;
                                    case "right_staff":
                                        hero.setImage(link_running_right_1);
                                        break;
                                    case "down_staff":
                                        hero.setImage(link_running_down_1);
                                        break;
                                }
                                swordMover(sword_down, sword_left, sword_right, sword_up, hero);
                                attackDirection = attackDirection.substring(0, attackDirection.length() - 6);//this gets rid of the _staff, leaving just the direction
                            }
                    }
                }
            }));
            animationReverser.setCycleCount(Timeline.INDEFINITE);
            animationReverser.play();
        }
    }

    private void roomChanger(int[][] inventoryARR, ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, Group group, Scene scene, TranslateTransition translate, ImageView boss, ImageView indicator, int[][] map, ImageView hearts, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, Label score, ImageView background, ImageView shopkeeper, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, Label coinNumber_attackBoost, Label coinNumber_staff, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, ImageView hero, Stage stage) {
        if (hero.getBoundsInParent().intersects(bossDoor.getBoundsInParent()) /*&& bossDoor.getImage()==bossDoor_open*/ && bossDoor.isVisible() && Objects.equals(direction, "up")) {
            System.out.println("entering boss room");
            positionY = positionY - 1;
            indicator.relocate(indicator.getLayoutX(), indicator.getLayoutY() - 64);
            backgroundChanger(backgrounds, background);
            EnemyMover.stop();
            hero.relocate((960) - 75, 750);
            northDoor.setVisible(false);
            bossDoor.setVisible(false);
            eastDoor.setVisible(false);
            southDoor.setVisible(false);
            westDoor.setVisible(false);
            if (v == 0) {
                activeBoss = true;
                bossRoom(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, group, scene, chest, bossHealthBar, bossHealthBarREC, hearts, score, hero, boss, stage, map, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, translate);
                v = 1;
            }
            for (Items image : items) {
                image.getImage().setVisible(false);
            }
            for (Enemy enemy : enemies) {
                enemy.getImage().setVisible(false);
            }
        }
        if (hero.getBoundsInParent().intersects(northDoor.getBoundsInParent()) && northDoor.isVisible() && Objects.equals(direction, "up")) {
            System.out.println("north door");
            positionY = positionY - 1;
            indicator.relocate(indicator.getLayoutX(), indicator.getLayoutY() - 64);
            hero.relocate((960) - 75, 750);
            Base_Procedure2(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, score, translate, hero, map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, hearts, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, group, scene, stage, indicator, bossHealthBar, bossHealthBarREC, boss);
        }
        if (hero.getBoundsInParent().intersects(southDoor.getBoundsInParent()) && southDoor.isVisible() && Objects.equals(direction, "down")) {
            System.out.println("south door");
            positionY = positionY + 1;
            indicator.relocate(indicator.getLayoutX(), indicator.getLayoutY() + 64);
            hero.relocate((960) - 75, 150);
            Base_Procedure2(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, score, translate, hero, map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, hearts, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, group, scene, stage, indicator, bossHealthBar, bossHealthBarREC, boss);
        }
        if (hero.getBoundsInParent().intersects(eastDoor.getBoundsInParent()) && eastDoor.isVisible() && Objects.equals(direction, "right")) {
            System.out.println("west door");
            positionX = positionX + 1;
            indicator.relocate(indicator.getLayoutX() + 72, indicator.getLayoutY());
            hero.relocate(150 - 75, 400 + 81);
            Base_Procedure2(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, score, translate, hero, map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, hearts, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, group, scene, stage, indicator, bossHealthBar, bossHealthBarREC, boss);
        }
        if (hero.getBoundsInParent().intersects(westDoor.getBoundsInParent()) && westDoor.isVisible() && Objects.equals(direction, "left")) {
            System.out.println("east door");
            positionX = positionX - 1;
            indicator.relocate(indicator.getLayoutX() - 72, indicator.getLayoutY());
            hero.relocate(1765 - 75, 400 + 81);
            Base_Procedure2(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, score, translate, hero, map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, hearts, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, group, scene, stage, indicator, bossHealthBar, bossHealthBarREC, boss);
        }
    }

    private void Base_Procedure2(int[][] inventoryARR, ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, ArrayList<Magic> magic, ArrayList<Fireballs> fireballs, ArrayList<Inventory> inventory, Label score, TranslateTransition translate, ImageView hero, int[][] map, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView background, ImageView hearts, ImageView shopkeeper, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, Label coinNumber_attackBoost, Label coinNumber_staff, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, Group group, Scene scene, Stage stage, ImageView indicator, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView boss) {
        doorChanger(map, northDoor, eastDoor, southDoor, westDoor, bossDoor);
        enemyChanger(enemies);
        backgroundChanger(backgrounds, background);
        enemyMover(inventoryARR, mapPieces, backgrounds, items, enemies, magic, fireballs, inventory, score, translate, hearts, map, hero, group, scene, stage, indicator, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, bossHealthBar, bossHealthBarREC, boss);
        itemChanger(items);
        shopChecker(enemies, map, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem);
        System.out.println("now in room " + positionY + " " + positionX);
        System.out.println(map[positionY][positionX]);
    }

    private void doorChanger(int[][] map, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor) {
        if (map[positionY - 1][positionX] == 1 || map[positionY - 1][positionX] == 2) {//checks above
            northDoor.setVisible(true);
        } else {
            northDoor.setVisible(false);
        }
        if (map[positionY - 1][positionX] == 3) {//checks above for the boss room
            bossDoor.setVisible(true);
        } else {
            bossDoor.setVisible(false);
        }
        if ((map[positionY + 1][positionX] == 1) || map[positionY + 1][positionX] == 2) {//checks below
            southDoor.setVisible(true);
        } else {
            southDoor.setVisible(false);
        }
        if ((map[positionY][positionX + 1] == 1) || map[positionY][positionX + 1] == 2) {//checks right
            eastDoor.setVisible(true);
        } else {
            eastDoor.setVisible(false);
        }
        if ((map[positionY][positionX - 1] == 1) || map[positionY][positionX - 1] == 2) {//checks left
            westDoor.setVisible(true);
        } else {
            westDoor.setVisible(false);
        }
    }

    private static void enemyChanger(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (positionX == enemy.getRoomX() && positionY == enemy.getRoomY()) {
                enemy.getImage().setVisible(true);//changes the visible enemy
            } else {
                enemy.getImage().setVisible(false);
            }
        }
    }

    private static void shopChecker(ArrayList<Enemy> enemies, int[][] map, ImageView shopkeeper, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, Label coinNumber_attackBoost, Label coinNumber_staff, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem) {
        if (map[positionY][positionX] == 2) {//checks if the current room is a shop
            System.out.println("Entered the shop");
            shopkeeper.setVisible(true);
            if (!activestaff) {
                staff.setVisible(true);
                coinIcon_staff.setVisible(true);
                coinNumber_staff.setVisible(true);
            }
            if (!activeAttackBoost) {
                attackBoost.setVisible(true);
                coinIcon_attackBoost.setVisible(true);
                coinNumber_attackBoost.setVisible(true);
            }
            if (!activeMap) {
                mapItem.setVisible(true);
                coinIcon_mapItem.setVisible(true);
                coinNumber_mapItem.setVisible(true);
            }
            for (Enemy enemy : enemies) {
                enemy.getImage().setVisible(false);
            }
        } else { //if its not a shop it does this

            shopkeeper.setVisible(false);
            //
            staff.setVisible(false);
            coinIcon_staff.setVisible(false);
            coinNumber_staff.setVisible(false);
            //
            attackBoost.setVisible(false);
            coinIcon_attackBoost.setVisible(false);
            coinNumber_attackBoost.setVisible(false);
            //
            mapItem.setVisible(false);
            coinIcon_mapItem.setVisible(false);
            coinNumber_mapItem.setVisible(false);
        }
    }

    private static void itemChanger(ArrayList<Items> items) {
        for (Items item : items) {
            if (positionX == item.getRoomX() && positionY == item.getRoomY()) {
                item.getImage().setVisible(true);//changes the visible imageViewIMG
            } else {
                item.getImage().setVisible(false);
            }
        }
    }

    private static void backgroundChanger(ArrayList<Backgrounds> backgrounds, ImageView background) {
        for (Backgrounds value : backgrounds) {
            if (positionX == value.getRoomX() && positionY == value.getRoomY()) {//finds the correct background
                background.setImage(value.getImage2());//changes the background image2

            } else {
            }
        }
    }

    private static void mapMaker(ArrayList<MapPieces> mapPieces, ArrayList<Backgrounds> backgrounds, ArrayList<Items> items, ArrayList<Enemy> enemies, Group group, int[][] map) {
        Random rand = new Random();
        boolean boss = false;//set true when a boss room is added
        boolean shop = false;//set true when a shop room is added
        do {//do while repeats if a map is made without shop or boss room
            for (Enemy enemy : enemies) {
                enemy.getImage().setVisible(false);
                group.getChildren().remove(enemy.getImage());
            }
            enemies.clear();
            for (Items item : items) {                             //this empties the enemy and item array if there is something in it.
                item.getImage().setVisible(false);
                group.getChildren().remove(item.getImage());
            }
            items.clear();
            backgrounds.clear();
            int localRooms = 0;
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {//sets all the positions to 0
                    map[i][j] = 0;
                }
            }
            map[9][9] = 1;//starting room
            int distanceFromNodeX = -1;//First room is always above the starting point
            int distanceFromNodeY = 0;
            int NoOfRooms = 1;
            mapGenerator(map, distanceFromNodeX, distanceFromNodeY, rand, NoOfRooms);
            //^^^^^^ recursive procedure that creates the map
            int k = 0;
            //counts rooms and adds shop room
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {
                    if (map[i][j] == 1) {
                        enemies.add(new Enemy(i, j));//creates a new instance of an enemy
                        items.add(new Items(i, j));//creates a new instance of an item
                        localRooms++;
                    }
                    if (map[i][j] == 1 && k == 0) {//creates a boss room
                        map[i - 1][j] = 3;
                        boss = true;
                        k = 1;
                        localRooms++;
                    }
                    //0 empty
                    //1 room
                    //2 SHOP
                    //3 BOSS
                    if (map[i][j] == 1 && k == 1 && map[i - 1][j] == 0 && localRooms > 3) {//creates a shop room, after the boss room is created.
                        map[i - 1][j] = 2;
                        shop = true;
                        k = 2;
                        localRooms++;
                    }
                }
            }
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {
                    if (map[i][j] == 1 || map[i][j] == 2 || map[i][j] == 3) {//this if cannot be in the for above, boss and shop backgrounds will not work
                        backgrounds.add(new Backgrounds(i, j, map[i][j]));//creates the backgrounds for each room
                    }
                }
            }
            for (Items item : items) {
                item.getImage().relocate(item.getLayoutX(), item.getLayoutY());//creates the items
                item.getImage().setVisible(false);
                group.getChildren().add(item.getImage());
            }
            for (Enemy enemy : enemies) {
                if (!(enemy.getRoomY() == 9 && enemy.getRoomX() == 9)) {//stops there being an enemy in the starting room.
                    enemy.getImage().relocate(enemy.getLayoutX(), enemy.getLayoutY());//creates the enemies
                    enemy.getImage().setVisible(false);
                    group.getChildren().add(enemy.getImage());
                }
            }
            System.out.println("O  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 COLUMN/ J");//outputs the map
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {
                    System.out.print(map[i][j] + "  ");
                }
                System.out.println("");
            }
            System.out.println(localRooms + " Rooms");

            mapItemMaker(mapPieces, group, map, localRooms);//creates the map that appears in the top right corner
        } while (!boss || !shop);
    }

    private static void mapGenerator(int[][] map, int distanceFromNodeX, int distanceFromNodeY, Random rand, int NoOfRooms) {//creates the map
        if (NoOfRooms < 12) {//when this is false the recursion ends therefore the map is generated.
            int direction = rand.nextInt(4);
            map[9 + distanceFromNodeX][9 + distanceFromNodeY] = 1;
            NoOfRooms++;
            switch (direction) {//randomly chooses a direction
                case 0:
                    distanceFromNodeX = distanceFromNodeX - 1; //left
                    break;
                case 1:
                    distanceFromNodeX = distanceFromNodeX + 1; //right
                    break;
                case 2:
                    distanceFromNodeY = distanceFromNodeY - 1; //down
                    break;
                case 3:
                    distanceFromNodeY = distanceFromNodeY + 1; //up
                    break;
            }
            mapGenerator(map, distanceFromNodeX, distanceFromNodeY, rand, NoOfRooms);
        }
    }

    private static void mapItemMaker(ArrayList<MapPieces> mapPieces, Group group, int[][] map, int NoOfRooms) {//this creates the physical map that appears in he corner
        int localRooms = 0;
        int l = 0;
        int ii = 0;
        int topI = 0;
        int rightJ = 0;
        for (MapPieces piece : mapPieces) {
            piece.getImage().setVisible(false);
            group.getChildren().remove(piece.getImage());
        }
        mapPieces.clear();
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                mapPieces.add(new MapPieces(j, i, map[i][j]));//sends 19^2 to the MapPieces class
            }
        }
        for (MapPieces mappiece : mapPieces) {
            mappiece.getImage().relocate(mappiece.getLayoutX(), mappiece.getLayoutY());//this creates the map
            mappiece.getImage().setOpacity(0.6);
            group.getChildren().add(mappiece.getImage());
        }
        for (int i = 0; i < 19; i++) {//this gets the top and right most room into order to move the map to the top right corner
            for (int j = 0; j < 19; j++) {
                if (!(map[i][j] == 0) && l == 0) {
                    topI = i;//highest room
                    l = 1;
                }
                if (!(map[j][i] == 0)) {
                    localRooms++;
                }
                if (localRooms == NoOfRooms) {
                    localRooms++;
                    rightJ = i;//right most room
                }
            }
        }
        for (int i = 0; i < 361; i++) {//moves the map
            ii++;
            if (ii == 19) {
                ii = 0;
            }
            mapPieces.get(i).getImage().relocate(mapPieces.get(i).getImage().getLayoutX() + (1920 - (rightJ * 72)) - 72, mapPieces.get(i).getImage().getLayoutY() - (64 * topI));
            //moves all the map pieces to the top right corner
        }
    }
}