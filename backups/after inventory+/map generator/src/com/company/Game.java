package com.company;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.Pulse;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

//@SuppressWarnings("ALL")
//@SuppressWarnings("ALL")
public class Game extends Application {
    //||
    //smaller annoying enemies in rooms.
    //enemies drop items.
    //chests in rooms.
    //more items, more rooms.
//https://www.youtube.com/watch?v=iOFclU4zURE
    static int positionX = 9;
    static int positionY = 9;
    private static MediaPlayer MEDIAWalking;
    private static MediaPlayer MEDIABackground;
    private static MediaPlayer MEDIABoss;
    int musicVolume = 0;
    String animation = "b";
    String enemyAnimation = "b";
    boolean activeBoss = false;
    static boolean activestaff = false;
    static boolean activeAttackBoost = false;
    static boolean activeMap = false;
    static boolean defeatedBoss = false;
    String direction = "down";
    String attackDirection = "down";
    int ii = 0;
    int jj = 0;
    int scoretimer = 0;
    boolean attacking = false;
    int walking = 0;
    int grassCounter = 1;
    int i = 0;
    int j = 0;
    int l = 0;
    int k = 0;
    int q = 0;
    int b = 0;
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
    //int timepassed4 = 0;
    //int timepassed5 = 0;
    //int timepassed6 = 0;
    int timepassed7 = 0;
    int timepassed8 = 0;
    int defeatedEnemies = 0;
    int number = 0;
    String sound;
    Timeline walkSounds;
    Timeline EnemyMover;
    Timeline subEnemy;
    Timeline Bossroomtimeline;
    double attackmultiplier = 1;
    boolean up = false;
    boolean right = false;
    boolean left = false;
    boolean down = false;
    //health
    int hero_Health = 6;
    //dynamic arrays
    ArrayList<Inventory> inventory = new ArrayList<>();
    ArrayList<Fireballs> fireballs = new ArrayList<>();
    ArrayList<Magic> magic = new ArrayList<>();
    static ArrayList<Enemy> enemies = new ArrayList<>();
    static ArrayList<Items> items = new ArrayList<>();
    static ArrayList<Backgrounds> backgrounds = new ArrayList<>();
    static ArrayList<MapPieces> mapPieces = new ArrayList<>();
    int CoinNumber = 0;
    //
    Label itemName = new Label();
    Label itemType = new Label();
    Label itemDescription = new Label();
    Label itemEffect = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //incorrect username message.
        Label badUserName = new Label();
        badUserName.setText("Name cannot contain spaces, special characters and must have between 4 and 15 characters");
        badUserName.relocate(735, 1008);
        badUserName.setFont(Font.font("Upheaval", FontWeight.BOLD, 16));
        badUserName.setTextFill(Color.WHITE);
        badUserName.setVisible(false);
        //
        Group group = new Group();
//
        itemName.relocate(1075, 330);
        itemName.setTextFill(Color.WHITE);
        itemName.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));

        itemType.relocate(1030, 390);
        itemType.setTextFill(Color.ORANGE);
        itemType.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));

        itemDescription.relocate(1030, 430);
        itemDescription.setTextFill(Color.WHITE);
        itemDescription.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));

        itemEffect.relocate(1030, 570);
        itemEffect.setTextFill(Color.ORANGE);
        itemEffect.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));

        group.getChildren().addAll(itemName, itemType, itemDescription, itemEffect);
        //
        Scene scene = new Scene(group, 1920, 1080, Color.BLACK);
        TranslateTransition translate = new TranslateTransition();
        final String[] playerName = {""};
        final Group menuGroup = new Group();
        menuGroup.getChildren().add(badUserName);
        ImageView hero = new ImageView("file:E:\\game\\link_running_down_1.png");
        ImageView boss = new ImageView("file:E:\\game\\Wizard.png");
        Label enterName = new Label("Enter Name Here:");
        enterName.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        enterName.setTextFill(Color.WHITE);
        menuGroup.getChildren().add(enterName);
        enterName.relocate(915, 926);
        ImageView menu = new ImageView("file:E:\\game\\Menu.png");
        menuGroup.getChildren().add(menu);
        menu.toBack();
        ImageView title = new ImageView("file:E:\\game\\Title.png");
        menuGroup.getChildren().add(title);
        title.relocate(560, 100);
        title.toFront();
        final Scene menuScene = new Scene(menuGroup, 1920, 1080);
        menuGroup.getChildren().add(hero);
        hero.relocate(940, 760);
        TextField textField = new TextField();
        textField.setPrefSize(180, 32);
        textField.relocate(915, 958);
        textField.getStylesheets().add("com/company/Style.css");
        ImageView controls = new ImageView("file:E:\\game\\controls.png");
        controls.relocate(313, 368);
        menuScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!textField.getText().equalsIgnoreCase("") && textField.getText().length() >= 4 && textField.getText().length() <= 15 && textField.getText().matches("[a-zA-Z]+")) {
                    stage.setScene(scene);
                    stage.setFullScreen(true);
                    playerName[0] = textField.getText();
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
        //enter.setTooltip(new Tooltip("(Ctrl + S)"));
        enter.relocate(1100, 958);
        menuGroup.getChildren().addAll(textField, enter, controls);
        stage.setScene(menuScene);
        stage.setFullScreen(true);
        enter.setOnAction(event -> {
            if (!textField.getText().equalsIgnoreCase("") && textField.getText().length() >= 4 && textField.getText().length() <= 15 && textField.getText().matches("[a-zA-Z]+")) {
                stage.setScene(scene);
                stage.setFullScreen(true);
                playerName[0] = textField.getText();
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
        ImageView northDoor = new ImageView("file:E:\\game\\door_up.png");
        ImageView southDoor = new ImageView("file:E:\\game\\door_down.png");
        ImageView eastDoor = new ImageView("file:E:\\game\\door_right.png");
        ImageView westDoor = new ImageView("file:E:\\game\\door_left.png");
        ImageView bossDoor = new ImageView("file:E:\\game\\door_boss_closed.png");
        Image bossDoor_open = new Image("file:E:\\game\\door_boss_open.png");
        Image bossDoor_closed = new Image("file:E:\\game\\door_boss_closed.png");
        northDoor.relocate(879, 4);
        bossDoor.relocate(879, 5);
        southDoor.relocate(879, 1010);
        westDoor.relocate(0, 460);
        eastDoor.relocate(1850, 460);
        bossDoor.setVisible(false);
        group.getChildren().addAll(northDoor, southDoor, eastDoor, westDoor, bossDoor);
        //
        Random rand = new Random();
        int[][] map = new int[19][19];
        mapMaker(group, map, rand);
        //
        ImageView shadow = new ImageView("file:E:\\game\\floors\\shadow.png");
        group.getChildren().add(shadow);
        shadow.toBack();
        //
        stage.setFullScreen(true);
        //
        sound = "background";
        soundManager();
        //shop related things
        ImageView shopkeeper = new ImageView("file:E:\\game\\shopkeeper.png");
        shopkeeper.relocate(885, 200);
        shopkeeper.setVisible(false);
        group.getChildren().add(shopkeeper);
        ImageView staff = new ImageView("file:E:\\game\\staff.png");
        staff.relocate(750, 500);
        staff.setVisible(false);
        //Thread t1 = new Thread(new CoolThreads());
        //t1.start(); https://dzone.com/articles/java-thread-tutorial-creating-threads-and-multithr
        ImageView coinIcon_staff = new ImageView("file:E:\\game\\Coin Icon.png");
        Label coinNumber_staff;
        Label coinNumber_attackBoost;
        Label coinNumber_mapItem;
        coinNumber_staff = new Label();
        coinNumber_staff.relocate(750, 580);
        coinNumber_staff.setText("5");
        coinNumber_staff.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        coinNumber_staff.setTextFill(Color.WHITE);
        coinNumber_staff.setVisible(false);
        coinIcon_staff.relocate(765, 580);
        coinIcon_staff.setVisible(false);
        group.getChildren().addAll(staff, coinIcon_staff, coinNumber_staff);
        //
        ImageView mapItem = new ImageView("file:E:\\game\\MapItem.png");
        mapItem.relocate(900, 480);
        mapItem.setVisible(false);
        ImageView coinIcon_mapItem = new ImageView("file:E:\\game\\Coin Icon.png");
        coinIcon_mapItem.relocate(945, 580);
        coinIcon_mapItem.setVisible(false);
        coinNumber_mapItem = new Label();
        coinNumber_mapItem.relocate(915, 580);
        coinNumber_mapItem.setText("2");
        coinNumber_mapItem.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        coinNumber_mapItem.setTextFill(Color.WHITE);
        coinNumber_mapItem.setVisible(false);
        group.getChildren().addAll(coinNumber_mapItem, mapItem, coinIcon_mapItem);
        //
        ImageView attackBoost = new ImageView("file:E:\\game\\attack boost.png");
        attackBoost.relocate(1050, 500);
        attackBoost.setVisible(false);
        ImageView coinIcon_attackBoost = new ImageView("file:E:\\game\\Coin Icon.png");
        coinNumber_attackBoost = new Label();
        coinNumber_attackBoost.relocate(1050, 580);
        coinNumber_attackBoost.setText("5");
        coinNumber_attackBoost.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        coinNumber_attackBoost.setTextFill(Color.WHITE);
        coinNumber_attackBoost.setVisible(false);
        coinIcon_attackBoost.relocate(1065, 580);
        coinIcon_attackBoost.setVisible(false);
        group.getChildren().addAll(coinIcon_attackBoost, attackBoost, coinNumber_attackBoost);
        //
        //Makes the grass
        ImageView Grass = new ImageView("file:E:\\game\\Grass_01.png");
        //Grass.setOpacity(0.6);
        //group.getChildren().add(Grass);
        //Image Grass_01 = new Image("file:E:\\game\\Grass_01.png");
        //Image Grass_02 = new Image("file:E:\\game\\Grass_02.png");
        //Image Grass_03 = new Image("file:E:\\game\\Grass_03.png");
        //Grass_overlay(Grass, Grass_01, Grass_02, Grass_03);
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
        ImageView background = new ImageView("file:E:\\game\\floors\\floor1.png");
        group.getChildren().add(background);
        backgroundChanger(background);
        background.toBack();
        //chest
        ImageView chest = new ImageView("file:E:\\game\\chest_closed.png");
        Image chest_open = new Image("file:E:\\game\\chest_open.png");
        chest.relocate(885, 150);
        chest.setVisible(false);
        group.getChildren().add(chest);

        ImageView bossHealthBar = new ImageView("file:E:\\game\\HealthBarImage.png");
        Rectangle bossHealthBarREC = new Rectangle(616, 87, 658, 48);

        ImageView scoreIMG = new ImageView("file:E:\\game\\score.png");
        //hearts
        ImageView hearts = new ImageView("file:E:\\game\\hearts\\heart_3.png");
        hearts.relocate(0, 0);
        group.getChildren().addAll(hearts);
        //
        //Font nameFont = Font.loadFont("file:Block.ttf", 10);
        //score
        int Score = 1000;
        Label score = new Label();
        score.relocate(1000, 90);
        score.setText(String.valueOf(Score));
        score.setFont(Font.font("", FontWeight.BOLD, 21));
        score.setTextFill(Color.WHITE);
        score.setOpacity(0.3);
        ImageView scoreBoard = new ImageView("file:E:\\game\\ScoreBoard IMG.png");
        Label[][] Labels = new Label[10][3];
        ScoreDisplayer(group, scoreBoard, Labels);
        //roomCreater();
        //Coin Icon
        ImageView coinIcon = new ImageView("file:E:\\game\\Coin Icon.png");
        coinIcon.relocate(25, 100);
        //
        Label coinNumber = new Label(String.valueOf(CoinNumber));
        coinNumber.relocate(50, 98);
        coinNumber.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        coinNumber.setTextFill(Color.WHITE);
        group.getChildren().addAll(coinNumber, coinIcon);
        //Score
        scoreIMG.relocate(845, 90);
        scoreIMG.setOpacity(0.3);
        //BOSS
        boss.relocate(885, 459);//center 860 520
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
        ImageView inventoryPIC = new ImageView("file:E:\\game\\newInventory.png");
        inventoryPIC.relocate(603, 275);
        //inventoryPIC.relocate(752, 325);
        inventoryPIC.setVisible(false);
        ImageView selected = new ImageView("file:E:\\game\\selected.png");
        selected.relocate(643, 314);
        selected.setVisible(false);
        //
        start();//starts the timers
        //stage walls
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
        stage.getIcons().add(new Image("file:E:\\game\\Master Sword In Pedastol.png"));
        //
        group.getChildren().addAll(inventoryPIC, selected, scoreIMG, score);
        stage.setFullScreen(true);
        //swords
        Image link_sword_down = new Image("file:E:\\game\\link_sword_down.png");
        Image link_sword_left = new Image("file:E:\\game\\link_sword_left.png");
        Image link_sword_right = new Image("file:E:\\game\\link_sword_right.png");
        Image link_sword_up = new Image("file:E:\\game\\link_sword_up.png");
        //staff / magic
        Image link_staff_down = new Image("file:E:\\game\\link_staff_down.png");
        Image link_staff_left = new Image("file:E:\\game\\link_staff_left.png");
        Image link_staff_right = new Image("file:E:\\game\\link_staff_right.png");
        Image link_staff_up = new Image("file:E:\\game\\link_staff_up.png");
        //
        Image link_running_down_1 = new Image("file:E:\\game\\link_running_down_1.png");
        Image link_running_left_1 = new Image("file:E:\\game\\link_running_left_1.png");
        Image link_running_up_1 = new Image("file:E:\\game\\link_running_up_1.png");
        Image link_running_right_1 = new Image("file:E:\\game\\link_running_right_1.png");

        ImageView indicator = new ImageView("file:E:\\game\\Indicator.png");
        InputManager(Score, itemId, group, scene, translate, playerName[0], boss, hero, indicator, map, stage, northWall, eastWall, southWall, westWall, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, chest, chest_open, controls, bossHealthBar, bossHealthBarREC, scoreBoard, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, mapItem, coinIcon_mapItem, coinNumber_mapItem, hearts, inventoryPIC, selected, score, coinNumber_staff, coinNumber_attackBoost, coinNumber, Labels, northDoor, eastDoor, southDoor, westDoor, Grass, background, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, shopkeeper);
        swordMover(sword_down, sword_left, sword_right, sword_up, usedSword, hero);

        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.setTitle("The Lost Dungeon");
        //stage.setScene(scene);
        doorChanger(map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open);
        itemChanger();
        stage.show();
        int i = 0;
        for (MapPieces mappiece : mapPieces) {//makes the map appear in front of EVERYTHING
            mappiece.getImageViewIMG().toFront();
            mappiece.getImageViewIMG().setVisible(false);
            i++;
            if (i == 181) {//array location of starting room
                indicator.relocate(mappiece.getImageViewIMG().getLayoutX(), mappiece.getImageViewIMG().getLayoutY());
                group.getChildren().add(indicator);
                indicator.setOpacity(0.3);
                indicator.setVisible(false);
            }
        }
    }

    private void bossRoom(int Score, Group group, Scene scene, ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView hearts, Label score, ImageView hero, ImageView boss, Stage stage) {
        bossHealthBar.setVisible(true);
        bossHealthBarREC.setVisible(true);
        timePassed3 = 0;
        MEDIABackground.stop(); //stops the background music
        sound = "boss";
        soundManager();
        activeBoss = true;
        boss.setVisible(true);
        if (k == 0) {
            Bossroomtimeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
                try {
                    if (timePassed3 >= 5 && boss.isVisible()) {
                        k = 1;
                        new Pulse(boss).play();
                        TranslateTransition translateFireball = new TranslateTransition();
                        fireballs.add(new Fireballs());
                        fireballs.get(0).getImage().relocate(boss.getLayoutX(), boss.getLayoutY());
                        group.getChildren().add(fireballs.get(0).getImage());
                        translateFireball.setNode(fireballs.get(0).getImage());
                        translateFireball.setDuration(Duration.seconds(1.6));
                        translateFireball.setToX(-fireballs.get(0).getImage().getLayoutX() + hero.getLayoutX() + (hero.getImage().getWidth() / 2));
                        translateFireball.setToY(-fireballs.get(0).getImage().getLayoutY() + hero.getLayoutY() + (hero.getImage().getHeight() / 2));
                        translateFireball.setInterpolator(Interpolator.LINEAR);
                        translateFireball.setOnFinished(event1 -> {
                            try {
                                fireballs.get(0).getImage().toBack();
                                fireballs.get(0).getImage().setVisible(false);
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
                        healthChecker(hearts, group, scene, stage, killedBy);
                        fireballs.get(0).getImage().setVisible(false);
                        fireballs.remove(0);
                        group.getChildren().remove(fireballs.get(0).getImage());
                    }
                    if (y >= 8) {
                        bossPhase2(Score, group, scene, chest, bossHealthBar, bossHealthBarREC, hearts, score, hero, boss, stage);
                    }
                } catch (Exception e) {
                    //System.out.println("Boss room phase 1 error");
                }
            }));
            Bossroomtimeline.setCycleCount(Timeline.INDEFINITE);
            Bossroomtimeline.play();
        }
    }

    private void bossPhase2(int Score, Group group, Scene scene, ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView hearts, Label score, ImageView hero, ImageView boss, Stage stage) {//phase 2 of the boss fight
        final int[] i = {Score};
        Random rand = new Random();
        System.out.println("phase two");
        TranslateTransition[] fireBlast = new TranslateTransition[3];
        for (int m = 0; m < 3; m++) {
            fireBlast[m] = new TranslateTransition();
        }
        TranslateTransition[] fireCircle = new TranslateTransition[8];
        for (int m = 0; m < 8; m++) {
            fireCircle[m] = new TranslateTransition();
        }
        Bossroomtimeline.stop();//stops boos phase 1 timeline
        fireballs.get(0).getImage().setVisible(false);
        fireballs.remove(0);
        timePassed3 = 5;
        Timeline Bossroomtimeline2 = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            int constant = (int) ((hero.getLayoutY() - boss.getLayoutY()) / (hero.getLayoutX() - boss.getLayoutX()));
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
                        if ((hero.getLayoutX() > boss.getLayoutX() && (constant > -0.5 && constant < 0.5)) || hero.getLayoutX() < boss.getLayoutX() && (constant > -0.7 && constant < 0.6)) {//0.6 -0.7
                            fireBlast[m].setToX(-fireballs.get(m).getImage().getLayoutX() + hero.getLayoutX() + (hero.getImage().getWidth() / 2));
                            fireBlast[m].setToY(-fireballs.get(m).getImage().getLayoutY() + hero.getLayoutY() + (hero.getImage().getHeight() / 2) + (((Math.tan(0.35) * Math.abs(hero.getLayoutX() - boss.getLayoutX()))) * (m - 1)));
                        } else {    //same logic as the enemy animator procedure.
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
                                // System.out.println("fireblast finished error");
                            }
                        });
                        sound = "fire_ball";
                        fireBlast[m].play();
                    }
                    sound = "fire_ball";
                    soundManager();
                } else {
                    System.out.println("attack 2");//860 520
                    for (int m = 0; m < 8; m++) {
                        fireballs.add(new Fireballs());
                        fireballs.get(m).getImage().relocate(boss.getLayoutX() + 75, boss.getLayoutY() + 75);
                        group.getChildren().add(fireballs.get(m).getImage());
                        //
                        fireCircle[m].setNode(fireballs.get(m).getImage());
                        fireCircle[m].setDuration(Duration.seconds(2.5));
                        fireCircle[m].setToX(-fireballs.get(m).getImage().getLayoutX() + 860 + (175 * (Math.sin(Math.toRadians(45) * (m + 1)))));//creates the circle around the boss's center
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
                        healthChecker(hearts, group, scene, stage, killedBy);
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
                    i[0] = i[0] + 200;
                    score.setText(String.valueOf(i[0]));//converts to atomic to deal with effectively final variables
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
                        File f = new File("E:\\game\\sound\\walkingSound.wav");
                        Media media = new Media(f.toURI().toString());
                        MEDIAWalking = new MediaPlayer(media);
                        MEDIAWalking.setVolume(0.10 * musicVolume);
                        MEDIAWalking.setAutoPlay(true);
                    }));
                    walkSounds.setCycleCount(Timeline.INDEFINITE);
                    walkSounds.play();
                }
                break;
            case "swordMiss":
                File f = new File("E:\\game\\sound\\sword_miss.mp3");
                Media media = new Media(f.toURI().toString());
                MediaPlayer MEDIASwordMiss = new MediaPlayer(media);
                MEDIASwordMiss.setVolume(0.05 * musicVolume);
                MEDIASwordMiss.play();
                break;
            case "swordHit":
                File f1 = new File("E:\\game\\sound\\sword_hit.mp3");
                Media media1 = new Media(f1.toURI().toString());
                MediaPlayer MEDIASwordHit = new MediaPlayer(media1);
                MEDIASwordHit.setVolume(0.05 * musicVolume);
                MEDIASwordHit.play();
                break;
            case "boss":
                File f2 = new File("E:\\game\\sound\\bossMusic.mp3");
                Media media2 = new Media(f2.toURI().toString());
                MEDIABoss = new MediaPlayer(media2);
                MEDIABoss.setVolume(0.04 * musicVolume);
                MEDIABoss.play();
                MEDIABoss.setOnEndOfMedia(() -> {
                    MEDIABoss.seek(Duration.ZERO);
                    MEDIABoss.play();
                });
                break;
            case "background":
                File f3 = new File("E:\\game\\sound\\background.mp3");
                Media media3 = new Media(f3.toURI().toString());
                MEDIABackground = new MediaPlayer(media3);
                MEDIABackground.setVolume(0.04 * musicVolume);
                MEDIABackground.play();
                MEDIABackground.setOnEndOfMedia(() -> {
                    MEDIABackground.seek(Duration.ZERO);
                    MEDIABackground.play();
                });
                break;
            case "triforce":
                File f4 = new File("E:\\game\\sound\\Triforce.mp3");
                Media media4 = new Media(f4.toURI().toString());
                MediaPlayer MEDIATriforce = new MediaPlayer(media4);
                MEDIATriforce.setVolume(0.2 * musicVolume);
                MEDIATriforce.play();
                break;
            case "magic_shot":
                File f5 = new File("E:\\game\\sound\\magic_shot.mp3");
                Media media5 = new Media(f5.toURI().toString());
                MediaPlayer MEDIAMagic_Shot = new MediaPlayer(media5);
                MEDIAMagic_Shot.setVolume(0.10 * musicVolume);
                MEDIAMagic_Shot.play();
                break;
            case "fire_ball":
                File f6 = new File("E:\\game\\sound\\Fireball.wav");
                Media media6 = new Media(f6.toURI().toString());
                MediaPlayer MEDIAFire_Ball = new MediaPlayer(media6);
                MEDIAFire_Ball.setVolume(0.15 * musicVolume);
                MEDIAFire_Ball.play();
                break;
            case "Explosion":
                File f7 = new File("E:\\game\\sound\\Explosion.mp3");
                Media media7 = new Media(f7.toURI().toString());
                MediaPlayer MEDIAExplosion = new MediaPlayer(media7);
                MEDIAExplosion.setVolume(0.15 * musicVolume);
                MEDIAExplosion.play();
                break;
            case "Death":
                File f8 = new File("E:\\game\\sound\\Death.mp4");
                Media media8 = new Media(f8.toURI().toString());
                MediaPlayer MEDIADeath = new MediaPlayer(media8);
                MEDIADeath.setVolume(0.15 * musicVolume);
                MEDIADeath.play();
                break;
        }
    }

    /*private void Grass_overlay(ImageView Grass, Image Grass_01, Image Grass_02, Image Grass_03) {

        Timeline grassTimeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
            Grass.relocate(500, 500);
            //Grass.setVisible(true);
            switch (grassCounter) {
                case 1:
                    Grass.setImage(Grass_01);
                    grassCounter = 2;
                    break;
                case 2:
                    Grass.setImage(Grass_02);
                    grassCounter = 3;
                    break;
                case 3:
                    Grass.setImage(Grass_03);
                    grassCounter = 4;
                    break;
                case 4:
                    Grass.setImage(Grass_02);
                    grassCounter = 1;
                    break;
                default:
            }
        }));
        grassTimeline.setCycleCount(Timeline.INDEFINITE);
        grassTimeline.play();
    }*/

    private void ScoreDisplayer(Group group, ImageView scoreBoard, Label[][] Labels) {
        scoreBoard.toFront();
        for (int m = 0; m < 10; m++) {
            for (int n = 0; n < 3; n++) {
                Labels[m][n] = new Label();
                Labels[m][n].toFront();
                Labels[m][n].setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
                Labels[m][n].setTextFill(Color.WHITE);
                Labels[m][n].setVisible(true);
                Labels[m][n].setOpacity(0.8);
            }
        }
        int j = 0;
        scoreBoard.relocate(590 - 100, 125);
        scoreBoard.setOpacity(0.7);
        group.getChildren().addAll(scoreBoard);
        //This reads from the database
        String connectionUrl = "jdbc:sqlserver://ks-sql-02:1433;" + "databaseName=db10;integratedSecurity=true";
        //Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "select Name, Score from ScoreTable order by Score DESC";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next() && j < 10) {
                String NameFromDatabase = rs.getString("Name");
                int ScoreFromDatabase = rs.getInt("Score");
                Labels[j][0].relocate(504 + 74, 208 + (49.5 * (j + 1)));
                Labels[j][0].setText((j + 1) + " .");
                Labels[j][1].relocate(675 + 74, 208 + (49.5 * (j + 1)));
                Labels[j][1].setText(NameFromDatabase);
                Labels[j][2].relocate(1090 + 74, 208 + (49.5 * (j + 1)));
                Labels[j][2].setText(String.valueOf(ScoreFromDatabase));
                group.getChildren().addAll(Labels[j][0], Labels[j][1], Labels[j][2]);
                j++;
            }
        }
        //Handle any errors that may have occurred.
        catch (Exception e) {
            // System.out.println("Error showing scores");
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

    private void swordMover(Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, ImageView hero) {
        sword_up.relocate(hero.getLayoutX() + 46, hero.getLayoutY() - 78);
        sword_down.relocate(hero.getLayoutX() + 67, hero.getLayoutY() + 128);
        sword_left.relocate(hero.getLayoutX() - 85, hero.getLayoutY() + 78);
        sword_right.relocate(hero.getLayoutX() + 142, hero.getLayoutY() + 75);
    }

    private final TimerTask task = new TimerTask() {//can turn to a thread
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
            //System.out.println("time passed: " + timePassed);
        }
    };

    private void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 1000, 1000);//updates every second
    }

    private void scoreCountdown(Label score, int Score) {
        if (scoretimer == 0) {
            scoreChanger(score, Score);
            scoretimer = 1;
        }
    }

    private void scoreChanger(Label score, int Score) {
        //Score goes down 1 every second
        if (q == 0) {
            final int[] i = {Score};
            Timeline scoreTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                q = 1;
                if (!defeatedBoss) {//when boss is defeated, the score stops going down.
                    i[0]--;
                }                   //converts to atomic here to deal with effectively final variable
                if (i[0] <= 0) {
                    i[0] = 0;
                }
                score.setText(String.valueOf(i[0]));
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

    private void AnimationManager(ImageView hero) {
        if (i == 0) {
            //when holding an arrow key it swaps the image2 every 0.35 seconds
            Timeline animationTimeline = new Timeline(new KeyFrame(Duration.seconds(0.35), event -> {
                i = 1;
                if (!(direction.equals(""))) {
                    if (animation.equals("b")) {
                        Image image1 = new Image("file:E:\\game\\link_running_" + direction + "_1.png");
                        hero.setImage(image1);
                        animation = "a";
                    } else {
                        Image image2 = new Image("file:E:\\game\\link_running_" + direction + "_2.png");
                        hero.setImage(image2);
                        animation = "b";
                    }
                    direction = "";
                }
            }));
            animationTimeline.setCycleCount(Timeline.INDEFINITE);
            animationTimeline.play();
        }
    }

    private void inventoryHider(ImageView controls, ImageView inventoryPIC, ImageView selected) {
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

    private void Checkcollision(Rectangle northWall, Rectangle eastWall, Rectangle southWall, Rectangle westWall, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, ImageView hero) {
        //pushes back from big box around the wall to stop from leaving the screen
        if (hero.getBoundsInParent().intersects(northWall.getBoundsInParent())) {
            hero.setLayoutY(hero.getLayoutY() + 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword, hero);
            Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, hero);
        }
        if (hero.getBoundsInParent().intersects(southWall.getBoundsInParent())) {
            hero.setLayoutY(hero.getLayoutY() - 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword, hero);
            Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, hero);
        }
        if (hero.getBoundsInParent().intersects(eastWall.getBoundsInParent())) {
            hero.setLayoutX(hero.getLayoutX() + 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword, hero);
            Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, hero);
        }
        if (hero.getBoundsInParent().intersects(westWall.getBoundsInParent())) {
            hero.setLayoutX(hero.getLayoutX() - 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword, hero);
            Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, hero);
        }
    }

    private void inventoryManager(ImageView inventoryPIC, ImageView selected) {
        if (!inventoryPIC.isVisible()) {
            itemInformation(selected);
            inventoryPIC.setVisible(true);
            inventoryPIC.toFront();
            selected.setVisible(true);
            selected.toFront();
            new FadeIn(inventoryPIC).play();
            new FadeIn(selected).play();
            new FadeIn(itemName).play();
            itemName.toFront();
            new FadeIn(itemType).play();
            itemType.toFront();
            new FadeIn(itemDescription).play();
            itemDescription.toFront();
            new FadeIn(itemEffect).play();
            itemEffect.toFront();
            for (Inventory value : inventory) {
                value.getImage().setVisible(true);
                new FadeIn(value.getImage()).play();
                value.getImage().toFront();
            }
        } else {
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

    private void Base_Procedure(int Score, Group group, Scene scene, TranslateTransition translate, ImageView boss, ImageView hero, ImageView indicator, Rectangle northWall, Rectangle eastWall, Rectangle southWall, Rectangle westWall, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, ImageView controls, ImageView scoreBoard, ImageView inventoryPIC, ImageView selected, Label score, Label[][] Labels, int[][] map, ImageView hearts, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView Grass, ImageView background, ImageView shopkeeper, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, Label coinNumber_attackBoost, Label coinNumber_staff, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, Stage stage) {
        heroMover(hero);
        sound = "walking";
        scoreCountdown(score, Score);
        swordMover(sword_down, sword_left, sword_right, sword_up, usedSword, hero);
        AnimationManager(hero);
        walking++;
        soundManager();
        Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, hero);
        inventoryHider(controls, inventoryPIC, selected);
        ScoreBoardHider(scoreBoard, Labels);
        roomChanger(Score, group, scene, translate, boss, indicator, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, score, Grass, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, hero, stage);
    }

    private void InputManager(int Score, int itemId, Group group, Scene scene, TranslateTransition translate, String playerName, ImageView boss, ImageView hero, ImageView indicator, int[][] map, Stage stage, Rectangle northWall, Rectangle eastWall, Rectangle southWall, Rectangle westWall, Image link_sword_down, Image link_sword_left, Image link_sword_right, Image link_sword_up, Image link_staff_down, Image link_staff_left, Image link_staff_right, Image link_staff_up, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open, ImageView bossDoor, ImageView chest, Image chest_open, ImageView controls, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView scoreBoard, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, ImageView hearts, ImageView inventoryPic, ImageView selected, Label score, Label coinNumber_staff, Label coinNumber_attackBoost, Label coinNumber, Label[][] Labels, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView Grass, ImageView background, Image link_running_down_1, Image link_running_left_1, Image link_running_right_1, Image link_running_up_1, ImageView shopkeeper) {
        final String[] killedBy = new String[1];
        try {
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case UP:
                        if (hero.getImage() == link_staff_up || hero.getImage() == link_sword_up) {
                            hero.setLayoutY(hero.getLayoutY() + 100);
                            hero.setImage(link_running_up_1);
                            attacking = false;
                        }
                        up = true;
                        direction = "up";
                        attackDirection = "up";
                        Base_Procedure(Score, group, scene, translate, boss, hero, indicator, northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard, inventoryPic, selected, score, Labels, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, Grass, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage);
                        break;
                    case DOWN:
                        attacking = false;
                        down = true;
                        direction = "down";
                        attackDirection = "down";
                        Base_Procedure(Score, group, scene, translate, boss, hero, indicator, northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard, inventoryPic, selected, score, Labels, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, Grass, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage);
                        break;
                    case LEFT:
                        if ((hero.getImage() == link_staff_left) || (hero.getImage() == link_sword_left)) {
                            hero.setLayoutX(hero.getLayoutX() + 100);
                            hero.setImage(link_running_left_1);
                            attacking = false;
                        }
                        left = true;
                        direction = "left";
                        attackDirection = "left";
                        Base_Procedure(Score, group, scene, translate, boss, hero, indicator, northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard, inventoryPic, selected, score, Labels, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, Grass, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage);
                        break;
                    case RIGHT:
                        attacking = false;
                        right = true;
                        direction = "right";
                        attackDirection = "right";
                        Base_Procedure(Score, group, scene, translate, boss, hero, indicator, northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard, inventoryPic, selected, score, Labels, map, hearts, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, chest, bossHealthBar, bossHealthBarREC, Grass, background, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage);
                        break;
                    case SPACE:
                        swordMover(sword_down, sword_left, sword_right, sword_up, usedSword, hero);
                        if (up || right || left || down) {//this stops the walking sound when you attack
                            walking = 0;
                            walkSounds.stop();
                            MEDIAWalking.stop();
                            l = 0;
                        }
                        AttackHandler(Score, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero);
                        inventoryHider(controls, inventoryPic, selected);
                        ScoreBoardHider(scoreBoard, Labels);
                        break;
                    case TAB:
                        inventoryManager(inventoryPic, selected);
                        itemInformation(selected);
                        break;
                    case EQUALS:
                        if (musicVolume < 10) {
                            musicVolume++;
                            VolumeChanger();
                        }
                        break;
                    case MINUS:
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
                        healthChecker(hearts, group, scene, stage, killedBy[0]);
                        break;
                    case L:
                        hero_Health--;
                        if (hero_Health < 0) {
                            hero_Health = 0;
                        }
                        ScoreBoardHider(scoreBoard, Labels);
                        killedBy[0] = "cheats";

                        healthChecker(hearts, group, scene, stage, killedBy[0]);
                        break;
                    case F:
                        if (stage.isFullScreen()) {
                            stage.setFullScreen(false);
                        } else {
                            stage.setFullScreen(true);
                        }
                        break;
                    //inventory controls
                    case W:
                        if (!selected.isVisible()) {
                            if (activestaff) {
                                attackDirection = "up_staff";
                                AttackHandler(Score, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero);
                            }
                        } else {
                            selected.setLayoutY(selected.getLayoutY() - 67);
                            itemInformation(selected);
                            if (selected.getLayoutY() <= 314) {
                                selected.setLayoutY(314);
                            }
                        }
                        break;
                    case A:
                        if (!selected.isVisible()) {
                            if (activestaff) {
                                attackDirection = "left_staff";
                                AttackHandler(Score, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero);
                            }
                        } else {
                            selected.setLayoutX(selected.getLayoutX() - 68);
                            itemInformation(selected);
                            if (selected.getLayoutX() <= 643) {
                                selected.setLayoutX(643);
                            }
                        }
                        break;
                    case S:
                        if (!selected.isVisible()) {
                            if (activestaff) {
                                attackDirection = "down_staff";
                                AttackHandler(Score, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero);
                            }
                        } else {
                            selected.setLayoutY(selected.getLayoutY() + 67);
                            itemInformation(selected);
                            if (selected.getLayoutY() >= 649) {
                                selected.setLayoutY(649);
                            }
                        }
                        break;
                    case D:
                        if (!selected.isVisible()) {
                            if (activestaff) {
                                attackDirection = "right_staff";
                                AttackHandler(Score, score, group, translate, boss, link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber, link_running_down_1, link_running_left_1, link_running_right_1, link_running_up_1, hero);
                            }
                        } else {
                            selected.setLayoutX(selected.getLayoutX() + 68);
                            itemInformation(selected);
                            if (selected.getLayoutX() >= 915) {
                                selected.setLayoutX(915);
                            }
                        }
                        break;
                    case E:
                        itemInteraction(Score, itemId, group, scene, playerName, hero, indicator, chest, chest_open, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, hearts, inventoryPic, selected, coinNumber_staff, coinNumber_attackBoost, coinNumber, Labels, mapItem, coinIcon_mapItem, coinNumber_mapItem, stage);
                        break;
                    case I:
                        if (!controls.isVisible()) {
                            controls.setVisible(true);
                        } else {
                            controls.setVisible(false);
                        }
                        break;
                    case M:
                        MusicControls();
                        break;
                }
            });
            scene.setOnKeyReleased(event -> {
                try {
                    switch (event.getCode()) {
                        case UP:
                            up = false;
                            heroMover(hero);
                        case DOWN:
                            down = false;
                            heroMover(hero);
                        case LEFT:
                            left = false;
                            heroMover(hero);
                        case RIGHT:
                            right = false;
                            walking = 0;
                            walkSounds.stop(); //stops the weird sound glitch
                            MEDIAWalking.stop();
                            l = 0;
                            //
                            heroMover(hero);
                            break;
                    }
                } catch (Exception e) {
                    //System.out.println("");
                }
            });
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    private void itemInformation(ImageView selected) {
        for (int i1 = 0; i1 < inventory.size(); i1++) {//checks all items
            Inventory item = inventory.get(i1);
            if (selected.getBoundsInParent().intersects(item.getImageViewIMG().getBoundsInParent()) && item.getImageViewIMG().isVisible() && item.getImageViewIMG().getOpacity() > 0) {
                itemName.setText(item.getName());
                itemType.setText(item.getItemType());
                itemDescription.setText(item.getDescription());//sets text
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
        if (!activeBoss) {
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

    private void scoreSaver(Label[][] Labels, String playerName, int Score) {
        String name;
        int finalScore = Score + (CoinNumber * 10);// 1 coin = 10 Score when the boss is defeated.
        name = playerName; //for use when writing to database
        System.out.println(name);
        System.out.println(playerName);
        //this writes to the database
        String connectionUrl = "jdbc:sqlserver://ks-sql-02:1433;" + "databaseName=db10;integratedSecurity=true";
        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "insert into ScoreTable (Name, Score) values ('" + name + "'," + finalScore + ");";
            stmt = con.createStatement();
            int i = stmt.executeUpdate(SQL);
            if (i == 1) {
                System.out.println("New Score added");
                SQL = "delete from ScoreTable where score <" + Labels[9][2].getText();
                i = stmt.executeUpdate(SQL);
                if (i > 0) {
                    System.out.println(i + " Scores were deleted");
                }
            } else {
                System.out.println("New Score not added. Something went wrong!");
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

    private void itemInteraction(int Score, int itemId, Group group, Scene scene, String playerName, ImageView hero, ImageView indicator, ImageView chest, Image chest_open, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, ImageView hearts, ImageView inventoryPIC, ImageView selected, Label coinNumber_staff, Label coinNumber_attackBoost, Label coinNumber, Label Labels[][], ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, Stage stage) {

        if (hero.getBoundsInParent().intersects(mapItem.getBoundsInParent()) && mapItem.isVisible() && CoinNumber >= 2) {
            //buys the map from the shop
            activeMap = true;
            mapItem.setVisible(false);
            coinNumber_mapItem.setVisible(false);
            coinIcon_mapItem.setVisible(false);
            CoinNumber = CoinNumber - 2;
            coinNumber.setText(String.valueOf(CoinNumber));
            new Pulse(hero).play();
            for (int m = 0; m < 361; m++) {
                mapPieces.get(m).getImageViewIMG().setVisible(true);
            }
            indicator.setVisible(true);
        }
        if (hero.getBoundsInParent().intersects(staff.getBoundsInParent()) && staff.isVisible() && CoinNumber >= 5) {
            //buys the staff from the shop
            activestaff = true;
            staff.setVisible(false);
            coinNumber_staff.setVisible(false);
            coinIcon_staff.setVisible(false);
            CoinNumber = CoinNumber - 5;
            coinNumber.setText(String.valueOf(CoinNumber));
            new Pulse(hero).play();
        }
        if (hero.getBoundsInParent().intersects(attackBoost.getBoundsInParent()) && attackBoost.isVisible() && CoinNumber >= 5) {
            //buys the attack boost from the shop
            activeAttackBoost = true;
            attackBoost.setVisible(false);
            coinNumber_attackBoost.setVisible(false);
            coinIcon_attackBoost.setVisible(false);
            CoinNumber = CoinNumber - 5;
            coinNumber.setText(String.valueOf(CoinNumber));
            new Pulse(hero).play();
            itemId = 0;
            inventory.add(new Inventory(itemId));
            for (Inventory anInventory : inventory) {
                anInventory.getImage().setOnMouseClicked(event -> usingItems(Score, group, scene, anInventory, hearts, stage));
            }
            if (ii == 5) {
                ii = 0; //puts an imageViewIMG onto the next row
                jj++; //next column
            }
            inventory.get(b).getImage().relocate((791 - 149) + (69 * ii), 314 + (69 * jj));
            inventory.get(b).getImage().setVisible(false);
            group.getChildren().add(inventory.get(b).getImage());
            ii++;
            b++;
        }
        if (chest.isVisible() && hero.getBoundsInParent().intersects(chest.getBoundsInParent())) {
            if (f == 0) {
                f = 1;
                //opens the chest after the boss fight
                chest.setImage(chest_open);
                Image link_holding_triforce = new Image("file:E:\\game\\link_holding_triforce.png");
                hero.setImage(link_holding_triforce);
                new Pulse(hero).play();
                sound = "triforce";
                soundManager();
                //triforce
                ImageView triforce = new ImageView("file:E:\\game\\triforce.png");
                triforce.relocate(1620, 0);
                group.getChildren().add(triforce);
                scoreSaver(Labels, playerName, Score);
            }
        }
        if (!inventoryPIC.isVisible()) {
            //picking up imageViewIMG
            for (Items image : items) {
                if (hero.getBoundsInParent().intersects(image.getImage().getBoundsInParent()) && image.getImage().getOpacity() > 0 && image.getImage().isVisible()) {//checks if you're touching any potions
                    image.getImage().setOpacity(0);
                    String item = image.getName();
                    System.out.println(item);
                    itemId = 1;
                    inventory.add(new Inventory(itemId));
                    if (ii == 5) {
                        ii = 0; //puts an imageViewIMG onto the next row
                        jj++; //next column
                    }
                    for (Inventory anInventory : inventory) {
                        anInventory.getImage().setOnMouseClicked(event -> usingItems(Score, group, scene, anInventory, hearts, stage));
                    }
                    inventory.get(b).getImage().relocate((791 - 149) + (69 * ii), 314 + (69 * jj));
                    inventory.get(b).getImage().setVisible(false);
                    if (ii == 5 && jj == 5) {
                        inventory.remove(inventory.get(b));
                    }
                    group.getChildren().add(inventory.get(b).getImage());
                    ii++;
                    b++;
                }
            }
        } else {
            //using an imageViewIMG
            for (Inventory anInventory : inventory) {
                if (selected.getBoundsInParent().intersects(anInventory.getImage().getBoundsInParent()) && anInventory.getImage().getOpacity() > 0 && anInventory.getImage().isVisible()) {
                    usingItems(Score, group, scene, anInventory, hearts, stage);
                }
            }
        }
    }


    private void usingItems(int Score, Group group, Scene scene, Inventory anInventory, ImageView hearts, Stage stage) {
        Score = Score - 50;
        anInventory.getImage().setOpacity(0);
        group.getChildren().remove(anInventory.getImage());
        String itemName = anInventory.getName();
        System.out.println(itemName + " used");
        switch (itemName) {
            case "Health potion":
                hero_Health = hero_Health + 2;
                String killedBy = "";
                healthChecker(hearts, group, scene, stage, killedBy);
                if (hero_Health >= 6) {
                    hero_Health = 6;
                    healthChecker(hearts, group, scene, stage, killedBy);
                }
                break;
            case "Attack boost":
                anInventory.getImage().setOpacity(0);
                group.getChildren().remove(anInventory.getImage());
                attackmultiplier = 1.25;
                System.out.println("attack is now " + attackmultiplier);
                break;
        }
    }

    private void VolumeChanger() {
        if (!activeBoss) {
            MEDIABackground.setVolume(0.05 * musicVolume);
        } else if (activeBoss) {
            MEDIABoss.setVolume(0.05 * musicVolume);
        }
        System.out.println("Volume is: " + musicVolume);
    }

    private void healthChecker(ImageView hearts, Group group, Scene scene, Stage stage, String killedBy) {
        switch (hero_Health) {
            case 0:
                Image heart_0 = new Image("file:E:\\game\\hearts\\heart_0.png");
                hearts.setImage(heart_0);
                deathScreen(group, scene, stage, killedBy);
                break;
            case 1:
                Image heart_05 = new Image("file:E:\\game\\hearts\\heart_05.png");
                hearts.setImage(heart_05);
                break;
            case 2:
                Image heart_1 = new Image("file:E:\\game\\hearts\\heart_1.png");
                hearts.setImage(heart_1);
                break;
            case 3:
                Image heart_15 = new Image("file:E:\\game\\hearts\\heart_15.png");
                hearts.setImage(heart_15);
                break;
            case 4:
                Image heart_2 = new Image("file:E:\\game\\hearts\\heart_2.png");
                hearts.setImage(heart_2);
                break;
            case 5:
                Image heart_25 = new Image("file:E:\\game\\hearts\\heart_25.png");
                hearts.setImage(heart_25);
                break;
            case 6:
                Image heart_3 = new Image("file:E:\\game\\hearts\\heart_3.png");
                hearts.setImage(heart_3);
                break;
        }
    }

    private void deathScreen(Group group, Scene scene, Stage stage, String killedBy) {
        if (activeBoss) {
            MEDIABoss.setMute(true);
        } else {                                        //stops the music
            MEDIABackground.setMute(true);
        }
        sound = "Death";
        soundManager();                                 //starts the Death music
        final int[] number = {0};
        new FadeOut(group).play();                      //gets rid of everything
        ImageView paper = new ImageView("file:E:\\game\\paper.png");
        paper.relocate(290, 89);
        scene.setOnKeyPressed(event1 -> {               //resets key inputs
            switch (event1.getCode()) {
                case ESCAPE:
                    System.exit(30);
                    break;                              //new key listener for the new buttons
                case SPACE:
                    //resets the game
                    break;
            }
        });
        Timeline death = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            number[0]++;
            System.out.println(number[0]);
            switch (number[0]) {
                case 4:                                   //2 seconds in the paper will appear
                    group.getChildren().clear();
                    group.getChildren().add(paper);
                    new FadeIn(group).play();
                    break;
                case 7:                                   //3.5 seconds in the 'you died' text will appear
                    ImageView deathText = new ImageView("file:E:\\game\\DeathText.png");
                    deathText.relocate(398, 150);
                    new FadeIn(deathText).play();
                    group.getChildren().add(deathText);
                    break;
                case 10:                                  //5 seconds in the 'killed by' message appears
                    ImageView deathText2 = new ImageView("file:E:\\game\\DeathText222.png");
                    deathText2.relocate(448, 414);
                    new FadeIn(deathText2).play();
                    group.getChildren().add(deathText2);
                    //
                    ImageView killedbyEnemy = new ImageView(); //gets the enemy that killed the player.
                    switch (killedBy) {
                        case "sword_enemy":
                            killedbyEnemy = new ImageView("file:E:\\game\\sword_enemy_down_1.png");
                            break;
                        case "troll":
                            killedbyEnemy = new ImageView("file:E:\\game\\troll_down_1.png");
                            break;
                        case "cheats":
                            killedbyEnemy = new ImageView("file:E:\\game\\missing.png");
                            break;
                        case "boss":
                            killedbyEnemy = new ImageView("file:E:\\game\\Wizard.png");
                            break;
                    }
                    killedbyEnemy.relocate(1289, 446);
                    new FadeIn(killedbyEnemy).play();
                    group.getChildren().add(killedbyEnemy);
                    break;
                case 16:                             //8 seconds in the buttons will appear
                    ImageView exit = new ImageView("file:E:\\game\\Exit button.png");
                    exit.relocate(0, 1080 - 338);
                    new FadeIn(exit).play();
                    group.getChildren().add(exit);
                    exit.setOnMouseClicked(event1 -> {
                        System.exit(12);
                    });//closes the program if the exit image is clicked
                    //
                    ImageView restart = new ImageView("file:E:\\game\\Restart button.png");
                    restart.relocate(1920 - 468, 1080 - 438);
                    new FadeIn(restart).play();
                    group.getChildren().add(restart);
                    restart.setOnMouseClicked(event1 -> {/* resets the game */});
            }
        }));
        death.setCycleCount(16);  //16 cycles of 0.5 seconds = 8 seconds for the death screen to complete
        death.play();
    }

    private void staffShooter(int Score, Label score, Group group, TranslateTransition translate, ImageView boss, Image bossDoor_open, ImageView bossDoor, Rectangle bossHealthBarREC, Label coinNumber, ImageView hero) {
        try {
            final int[] i = {Score};
            TranslateTransition translateMagic = new TranslateTransition();
            magic.add(new Magic());
            //c = 1;
            group.getChildren().add(magic.get(0).getImage());
            translateMagic.setDuration(Duration.seconds(2));
            translateMagic.setNode(magic.get(0).getImage());
            sound = "magic_shot";
            soundManager();
            switch (attackDirection) {
                case "up_staff":
                    magic.get(0).getImage().relocate(hero.getLayoutX() + 50, hero.getLayoutY());
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX() + hero.getLayoutX());
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY());
                    //timepassed6 = 0;
                    break;
                case "left_staff":
                    magic.get(0).getImage().relocate(hero.getLayoutX(), hero.getLayoutY() + 55);
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX());
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY() + hero.getLayoutY());
                    //timepassed6 = 0;
                    break;
                case "right_staff":
                    magic.get(0).getImage().relocate(hero.getLayoutX() + 235, hero.getLayoutY() + 90);
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX() + 1900);
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY() + (hero.getLayoutY() + 60));
                    //timepassed6 = 0;
                    break;
                case "down_staff":
                    magic.get(0).getImage().relocate(hero.getLayoutX() + 80, hero.getLayoutY() + 270);
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX() + (hero.getLayoutX() + 60));
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY() + 1060);
                    // timepassed6 = 0;
                    break;
            }
            translateMagic.setOnFinished(event -> {
                try {
                    magic.get(0).getImage().setVisible(false);
                    magic.remove(0);
                } catch (Exception e) {
                    // System.out.println("Error removing magic when finished");
                }
            });
            translateMagic.play();
            if (c == 0) {
                //removes the magic sprite when its flight duration is over
                Timeline magicShooterTimeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
                    c = 1;
                    try {
                        if (!activeBoss) {
                            for (int m = 0; m < enemies.size(); m++) {
                                Enemy enemy = enemies.get(m);
                                if (magic.get(0).getImage().getBoundsInParent().intersects(enemy.getImage().getBoundsInParent()) && enemy.getRoomX() == positionY && enemy.getRoomY() == positionX && enemy.getImage().isVisible() && enemy.getImage().getOpacity() > 0 && timePassed >= 1) {
                                    translate.stop();
                                    if (enemies.get(m).getImage().getOpacity() == 0.5) {
                                        enemies.get(m).getImage().setOpacity(0);
                                        magic.get(0).getImage().setVisible(false);
                                        magic.remove(0);
                                        i[0] = i[0] + 50;
                                        score.setText(String.valueOf(i[0]));//converts to atomic to deal with effectively final variable
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
                                System.out.println(y);
                            } else {//when the magic hits the fireball
                                for (int m = 0; m < 8; m++) {
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

    private void attackingEnemies(int Score, TranslateTransition translate, ImageView boss, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open, ImageView bossDoor, ImageView bossHealthBar, Rectangle bossHealthBarREC, Label coinNumber) {
        try {
            if (activeBoss) {
                if (timePassed >= 1 && usedSword.getBoundsInParent().intersects(boss.getBoundsInParent())) {
                    timePassed = 0;
                    y++;
                    System.out.println("hit boss");
                    bossHealthBarREC.setWidth(658 - (((65.8) / 2 * y)) * attackmultiplier);
                    System.out.println("The boss has " + Math.floor(bossHealthBarREC.getWidth()) + " Health left");
                    System.out.println(y);
                    //timepassed5 = 0;
                    sound = "swordHit";
                    soundManager();
                }
            } else if (!activeBoss) {
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy enemy = enemies.get(i);
                    if (enemy.getRoomX() == positionY && enemy.getRoomY() == positionX) {
                        if (usedSword.getBoundsInParent().intersects(enemy.getImage().getBoundsInParent()) && enemy.getImage().isVisible() && enemy.getImage().getOpacity() > 0 && timePassed >= 1) {
                            sound = "swordHit";
                            soundManager();
                            System.out.println("hit enemy in room " + positionY + " " + positionX);
                            translate.stop();
                            if (enemy.getImage().getOpacity() == 0.5) {
                                enemy.getImage().setOpacity(0);
                                Score = Score + 50;
                                CoinNumber = CoinNumber + 1;
                                coinNumber.setText(String.valueOf(CoinNumber));
                                defeatedEnemies++;
                                System.out.println(defeatedEnemies + " out of " + (enemies.size() - 1));
                                if (defeatedEnemies == enemies.size() - 1) {
                                    bossDoor.setImage(bossDoor_open);
                                    System.out.println("boos door open");
                                }
                            } else {
                                enemy.getImage().setOpacity(0.5);
                            }
                            timePassed = 0;
                        } else {
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

    private void enemyMover(int Score, Label score, TranslateTransition translate, ImageView hearts, int[][] map, ImageView hero, Group group, Scene scene, Stage stage) {
        translate.setDuration(Duration.seconds(3.3));//takes 3.3 seconds to move to its end cords
        if (s == 0) {
            for (Enemy enemy : enemies) {
                if (enemy.getRoomX() == positionY && enemy.getRoomY() == positionX) {
                    enemyAnimater(enemy, hero, translate);
                }
            }
            final int[] i = {Score};
            EnemyMover = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
                s = 1;
                if (map[positionY][positionX] == 1) {
                    for (Enemy enemy : enemies) {
                        if (enemy.getRoomX() == positionY && enemy.getRoomY() == positionX) {
                            translate.setNode(enemy.getImage());
                            translate.setToX(-enemy.getLayoutX() + hero.getLayoutX());
                            translate.setToY(-enemy.getLayoutY() + hero.getLayoutY());
                            translate.setInterpolator(Interpolator.LINEAR);
                            if (!(enemy.getImage().isVisible())) {
                                translate.stop();
                            }
                            translate.play();
                            translate.setOnFinished(event1 -> {
                                if (n == 1) {
                                    subEnemy.stop();
                                }
                                enemyAnimater(enemy, hero, translate);
                            });
                            if (hero.getBoundsInParent().intersects(enemy.getImage().getBoundsInParent()) && timePassed2 >= 2 && enemy.getImage().getOpacity() > 0 && enemy.getImage().isVisible() && (!attacking)) {
                                i[0] = i[0] - 10;
                                score.setText(String.valueOf(i[0]));//converts to atomic for effectively final variable
                                hero_Health = hero_Health - 1;
                                String killedBy = enemy.getName();
                                healthChecker(hearts, group, scene, stage, killedBy);
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
        double constant = (hero.getLayoutY() - ((enemy.getImage().getLayoutY() - 82) + enemy.getImage().getTranslateY())) / (hero.getLayoutX() - ((enemy.getImage().getLayoutX() - 75) + enemy.getImage().getTranslateX()));
        int horizontalDistance = (int) (hero.getLayoutX() - enemy.getImage().getTranslateX()) - 780 - 105;//accounts for a bug where h d is 105 when it should be 0
        int verticalDistance = (int) ((hero.getLayoutY() - enemy.getImage().getTranslateY())) - 540 + 75;//accounts for a bug where v d is -75 when it should be 0
        String name = enemy.getName();
        if (horizontalDistance > 0 && (constant < 0.51 && constant > -0.51) && num == 0) {
            Image right_1 = new Image("file:E:\\game\\" + name + "_right_1.png");
            Image right_2 = new Image("file:E:\\game\\" + name + "_right_2.png");
            enemy.setImage(right_2);
            num = 1;
            subEnemyAnimater(enemy, right_1, right_2, translate);
        }
        if (horizontalDistance < 0 && (constant > -0.51 && constant < 0.51) && num == 0) {
            Image left_1 = new Image("file:E:\\game\\" + name + "_left_1.png");
            Image left_2 = new Image("file:E:\\game\\" + name + "_left_2.png");
            enemy.setImage(left_2);
            num = 1;
            subEnemyAnimater(enemy, left_1, left_2, translate);
        }
        if (verticalDistance < 0 && (constant > -105 && constant < 105) && num == 0) {
            Image up_1 = new Image("file:E:\\game\\" + name + "_up_1.png");
            Image up_2 = new Image("file:E:\\game\\" + name + "_up_2.png");
            enemy.setImage(up_2);
            num = 1;
            subEnemyAnimater(enemy, up_1, up_2, translate);
        }
        if (verticalDistance > 0 && (constant > -105 && constant < 105) && num == 0) {
            Image down_1 = new Image("file:E:\\game\\" + name + "_down_1.png");
            Image down_2 = new Image("file:E:\\game\\" + name + "_down_2.png");
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

    private void AttackHandler(int Score, Label score, Group group, TranslateTransition translate, ImageView boss, Image link_sword_down, Image link_sword_left, Image link_sword_right, Image link_sword_up, Image link_staff_down, Image link_staff_left, Image link_staff_right, Image link_staff_up, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open, ImageView bossDoor, ImageView bossHealthBar, Rectangle bossHealthBarREC, Label coinNumber, Image link_running_down_1, Image link_running_left_1, Image link_running_right_1, Image link_running_up_1, ImageView hero) {
        switch (attackDirection) {
            case "up":
            case "right":
            case "left":
            case "down":
                if (hero.getImage() != link_sword_down || hero.getImage() != link_sword_left || hero.getImage() != link_sword_up || hero.getImage() != link_sword_right) {
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
                    attackingEnemies(Score, translate, boss, sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
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
                if (hero.getImage() != link_staff_down || hero.getImage() != link_staff_up || hero.getImage() != link_staff_left || hero.getImage() != link_staff_right) {
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
                        staffShooter(Score, score, group, translate, boss, bossDoor_open, bossDoor, bossHealthBarREC, coinNumber, hero);
                        timepassed7 = 0;
                        timepassed1 = 0;
                    }
                }
                break;
        }
        if (j == 0) {
            Rectangle finalUsedSword = usedSword;
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
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword, hero);
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
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword, hero);
                                attackDirection = attackDirection.substring(0, attackDirection.length() - 6);//this gets rid of the _staff, leaving just the direction
                            }
                    }
                }
            }));
            animationReverser.setCycleCount(Timeline.INDEFINITE);
            animationReverser.play();
        }
    }

    private void roomChanger(int Score, Group group, Scene scene, TranslateTransition translate, ImageView boss, ImageView indicator, int[][] map, ImageView hearts, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, Label score, ImageView grass, ImageView background, ImageView shopkeeper, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, Label coinNumber_attackBoost, Label coinNumber_staff, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, ImageView hero, Stage stage) {
        if (hero.getBoundsInParent().intersects(bossDoor.getBoundsInParent()) /*&& bossDoor.getImage()==bossDoor_open*/ && bossDoor.isVisible() && Objects.equals(direction, "up")) {
            System.out.println("entering boss room");
            positionY = positionY - 1;
            indicator.relocate(indicator.getLayoutX(), indicator.getLayoutY() - 64);
            backgroundChanger(background);
            EnemyMover.stop();
            hero.relocate((960) - 75, 750);
            grass.setVisible(false);
            northDoor.setVisible(false);
            bossDoor.setVisible(false);
            eastDoor.setVisible(false);
            southDoor.setVisible(false);
            westDoor.setVisible(false);
            if (v == 0) {
                activeBoss = true;
                bossRoom(Score, group, scene, chest, bossHealthBar, bossHealthBarREC, hearts, score, hero, boss, stage);
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
            Base_Procedure2(Score, score, translate, hero, map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, hearts, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, grass, mapItem, coinIcon_mapItem, coinNumber_mapItem, group, scene, stage);
        }
        if (hero.getBoundsInParent().intersects(southDoor.getBoundsInParent()) && southDoor.isVisible() && Objects.equals(direction, "down")) {
            System.out.println("south door");
            positionY = positionY + 1;
            indicator.relocate(indicator.getLayoutX(), indicator.getLayoutY() + 64);
            hero.relocate((960) - 75, 150);
            Base_Procedure2(Score, score, translate, hero, map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, hearts, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, grass, mapItem, coinIcon_mapItem, coinNumber_mapItem, group, scene, stage);
        }
        if (hero.getBoundsInParent().intersects(eastDoor.getBoundsInParent()) && eastDoor.isVisible() && Objects.equals(direction, "right")) {
            System.out.println("west door");
            positionX = positionX + 1;
            indicator.relocate(indicator.getLayoutX() + 72, indicator.getLayoutY());
            hero.relocate(150 - 75, 400 + 81);
            Base_Procedure2(Score, score, translate, hero, map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, hearts, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, grass, mapItem, coinIcon_mapItem, coinNumber_mapItem, group, scene, stage);
        }
        if (hero.getBoundsInParent().intersects(westDoor.getBoundsInParent()) && westDoor.isVisible() && Objects.equals(direction, "left")) {
            System.out.println("east door");
            positionX = positionX - 1;
            indicator.relocate(indicator.getLayoutX() - 72, indicator.getLayoutY());
            hero.relocate(1765 - 75, 400 + 81);
            Base_Procedure2(Score, score, translate, hero, map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open, background, hearts, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, grass, mapItem, coinIcon_mapItem, coinNumber_mapItem, group, scene, stage);
        }
    }

    private void Base_Procedure2(int Score, Label score, TranslateTransition translate, ImageView hero, int[][] map, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open, ImageView background, ImageView hearts, ImageView shopkeeper, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, Label coinNumber_attackBoost, Label coinNumber_staff, ImageView grass, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem, Group group, Scene scene, Stage stage) {
        doorChanger(map, northDoor, eastDoor, southDoor, westDoor, bossDoor, bossDoor_closed, bossDoor_open);
        enemyChanger();
        backgroundChanger(background);
        enemyMover(Score, score, translate, hearts, map, hero, group, scene, stage);
        itemChanger();
        shopChecker(map, shopkeeper, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, coinNumber_attackBoost, coinNumber_staff, grass, mapItem, coinIcon_mapItem, coinNumber_mapItem);
        System.out.println("now in room " + positionY + " " + positionX);
        System.out.println(map[positionY][positionX]);
    }

    private void doorChanger(int[][] map, ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, Image bossDoor_closed, Image bossDoor_open) {
        if (map[positionY - 1][positionX] == 1 || map[positionY - 1][positionX] == 2) {//checks above
            northDoor.setVisible(true);
        } else {
            northDoor.setVisible(false);
        }
        if (map[positionY - 1][positionX] == 3) {
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

    private static void enemyChanger() {
        for (Enemy enemy : enemies) {
            if (positionX == enemy.getRoomY() && positionY == enemy.getRoomX()) {
                enemy.getImage().setVisible(true);//changes the visible enemy
            } else {
                enemy.getImage().setVisible(false);
            }
        }
    }

    private static void shopChecker(int[][] map, ImageView shopkeeper, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost, Label coinNumber_attackBoost, Label coinNumber_staff, ImageView grass, ImageView mapItem, ImageView coinIcon_mapItem, Label coinNumber_mapItem) {
        if (map[positionY][positionX] == 2) {//checks if the current room is a shop
            System.out.println("Entered the shop");
            shopkeeper.setVisible(true);
            //grass.setVisible(false);
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
            //grass.setVisible(true);
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

    private static void itemChanger() {
        for (Items item : items) {
            if (positionX == item.getRoomY() && positionY == item.getRoomX()) {
                item.getImage().setVisible(true);//changes the visible imageViewIMG
            } else {
                item.getImage().setVisible(false);
            }
        }
    }

    private static void backgroundChanger(ImageView background) {
        for (Backgrounds value : backgrounds) {
            if (positionX == value.getRoomY() && positionY == value.getRoomX()) {
                background.setImage(value.getImage2());//changes the background image2
            }
        }
    }

    private static void mapMaker(Group group, int[][] map, Random rand) {
        int localRooms = 0;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {//sets all the positions to 0
                map[i][j] = 0;
            }
        }
        map[9][9] = 1;//starting room
        int distanceFromNodeX = -1;//ABOVE THE STARTING POINT
        int distanceFromNodeY = 0;
        int NoOfRooms = 1;
        mapGenerator(map, distanceFromNodeX, distanceFromNodeY, rand, NoOfRooms);
        int k = 0;
        //counts rooms and adds shop room
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (map[i][j] == 1) {//sets a 0 to a 1, therefore creates a room
                    enemies.add(new Enemy(i, j));
                    items.add(new Items(i, j));//creates an enemy and imageViewIMG
                    localRooms++;
                }
                if (map[i][j] == 1 && k == 0) {//creates a boss room
                    map[i - 1][j] = 3;
                    k = 1;
                    localRooms++;
                }
                //0 empty
                //1 room
                //2 SHOP
                //3 BOSS
                if (map[i][j] == 1 && k == 1 && map[i - 1][j] == 0 && localRooms > 3) {//creates a shop room, after the boss room is created.
                    map[i - 1][j] = 2;
                    k = 2;
                    localRooms++;
                }
            }

        }
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (map[i][j] == 1 || map[i][j] == 2 || map[i][j] == 3) {
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
            if (!(enemy.getRoomX() == 9 && enemy.getRoomY() == 9)) {//stops there being an enemy in the starting room.
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

        mapItemMaker(group, map, localRooms);//creates the map that appears in the top right corner
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

    private static void mapItemMaker(Group group, int[][] map, int NoOfRooms) {//this creates the physical map that appears in he corner
        int localRooms = 0;
        int l = 0;
        int ii = 0;
        int topI = 0;
        int rightJ = 0;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                mapPieces.add(new MapPieces(j, i, map[i][j]));//sends 19^2 to the MapPieces class
            }
        }
        for (MapPieces mappiece : mapPieces) {

            mappiece.getImageViewIMG().relocate(mappiece.getLayoutX(), mappiece.getLayoutY());//this creates the map
            mappiece.getImageViewIMG().setOpacity(0.6);
            group.getChildren().add(mappiece.getImageViewIMG());
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
                    System.out.println(j + " " + i);
                }
            }
        }
        for (int i = 0; i < 361; i++) {//moves the map
            ii++;
            if (ii == 19) {
                ii = 0;
            }
            mapPieces.get(i).getImageViewIMG().relocate(mapPieces.get(i).getImageViewIMG().getLayoutX() + (1920 - (rightJ * 72)) - 72, mapPieces.get(i).getImageViewIMG().getLayoutY() - (64 * topI));
            //moves all the map pieces to the top right corner
        }
    }
}