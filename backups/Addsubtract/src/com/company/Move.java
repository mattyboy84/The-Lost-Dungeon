package com.company;

import animatefx.animation.*;
import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.Timer;

public class Move extends Application {
    //||
    private static MediaPlayer mplayer;
    private static MediaPlayer mplayer5;
    private static MediaPlayer mplayer4;
    int musicVolume = 1;
    String animation = "b";
    boolean activeBoss = false;
    boolean activestaff = false;
    boolean activeAttackBoost = false;
    boolean defeatedBoss = false;
    String direction = "down";
    String attackDirection = "down";
    int ii = 0;
    int jj = 0;
    int scoretimer = 0;
    boolean attacking = false;
    int walking = 0;
    int grassCounter = 1;
    int itemId;
    int i = 0;
    int j = 0;
    int l = 0;
    int k = 0;
    int q = 0;
    int b = 0;
    int v = 0;
    int y = 0;
    int c = 0;
    int h = 0;
    int s = 0;
    int f = 0;
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
    Timeline Bossroomtimeline;
    double attackmultiplier = 1;
    boolean up = false;
    boolean right = false;
    boolean left = false;
    boolean down = false;
    //character
    ImageView hero;
    //BOSS
    ImageView boss = new ImageView("file:E:\\game\\Wizard.png");
    TranslateTransition translate = new TranslateTransition();
    String currentFloor = "floor 1";
    //score
    int Score = 1000;
    //health
    int hero_Health = 6;
    //dynamic arrays
    ArrayList<Images> images = new ArrayList<>();
    ArrayList<Inventory> inventory = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Fireballs> fireballs = new ArrayList<>();
    ArrayList<Magic> magic = new ArrayList<>();
    //ArrayList<Coins> coins = new ArrayList<Coins>();
    int CoinNumber = 0;
    //
    final Group group = new Group();
    final Group menuGroup = new Group();
    String playerName = "";
    //Group[] groupRooms = new Group[8];
    //Scene[] rooms = new Scene[8];

    public static void main(String[] args) {
        launch(args);
    }

    //https://www.bing.com/images/search?q=binding+of+isaac+afterbirth+dungeon+floor+png&FORM=HDRSC2
    @Override
    public void start(Stage stage) {
        ImageView menu = new ImageView("file:E:\\game\\Menu.png");
        menuGroup.getChildren().add(menu);
        menu.toBack();
        ImageView title = new ImageView("file:E:\\game\\Title.png");
        menuGroup.getChildren().add(title);
        title.relocate(560,100);
        title.toFront();
        final Scene menuScene = new Scene(menuGroup, 1920, 1080);
        final Scene scene = new Scene(group, 1920, 1080);
        //
        hero = new ImageView("file:E:\\game\\link_running_down_1.png");
        menuGroup.getChildren().add(hero);
        hero.relocate(940, 760);
        //
        ImageView controls = new ImageView("file:E:\\game\\controls.png");
        controls.relocate(313, 368);
        //
        TextField textField = new TextField();
        textField.relocate(790, 600);
        menuScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!textField.getText().equalsIgnoreCase("")) {
                    stage.setScene(scene);
                    stage.setFullScreen(true);
                    playerName = textField.getText();
                    group.getChildren().addAll(hero);
                    hero.relocate(785, 739);
                    controls.setVisible(false);
                    group.getChildren().add(controls);
                    new FadeIn(group).play();
                }
            }
        });
        //
        Button enter = new Button();
        enter.setText("Enter");
        enter.relocate(900, 600);
        //
        menuGroup.getChildren().addAll(textField, enter, controls);
        //
        stage.setScene(menuScene);
        stage.setFullScreen(true);
        enter.setOnAction(event -> {
            if (!textField.getText().equalsIgnoreCase("")) {
                stage.setScene(scene);
                stage.setFullScreen(true);
                playerName = textField.getText();
                group.getChildren().addAll(hero);
                hero.relocate(785, 739);
                controls.setVisible(false);
                group.getChildren().add(controls);
                new FadeIn(group).play();
            }
        });
        //Glow glow = new Glow();
        //hero.setEffect(glow);
        //Reflection reflection = new Reflection();
        //hero.setEffect(reflection);
        //Lighting lighting = new Lighting();
/*
        Light.Spot light = new Light.Spot();
        light.setX(500);
        light.setY(500);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        hero.setEffect(lighting);
  */
        //Map
        ImageView mapItem = new ImageView("file:E:\\game\\MapItem.png");
        mapItem.relocate(100, 800);
        mapItem.setVisible(false);
        ImageView map = new ImageView("file:E:\\game\\Map.png");
        group.getChildren().addAll(mapItem, map);
        map.relocate(1624, 0);
        map.setVisible(false);
        //doors
        ImageView northDoor = new ImageView("file:E:\\game\\door_up.png");
        ImageView southDoor = new ImageView("file:E:\\game\\door_down.png");
        ImageView eastDoor = new ImageView("file:E:\\game\\door_right.png");
        ImageView westDoor = new ImageView("file:E:\\game\\door_left.png");
        ImageView bossDoor = new ImageView("file:E:\\game\\door_boss_closed.png");
        Image bossDoor_closed = new Image("file:E:\\game\\door_boss_closed.png");
        Image bossDoor_open = new Image("file:E:\\game\\door_boss_open.png");

        northDoor.relocate(779.5, 5);
        bossDoor.relocate(779.5, 5);
        southDoor.relocate(779.5, 1010);
        westDoor.relocate(0, 500);
        eastDoor.relocate(1850, 500);
        bossDoor.setVisible(false);
        group.getChildren().addAll(northDoor, southDoor, eastDoor, westDoor, bossDoor);
        //
        ImageView shadow = new ImageView("file:E:\\game\\shadow.png");
        group.getChildren().add(shadow);
        //
        stage.setFullScreen(true);
        //
        sound = "background";
        soundManager();
        itemMaker();
        //shop related things
        ImageView shopkeeper = new ImageView("file:E:\\game\\shopkeeper.png");
        shopkeeper.relocate(885, 200);
        shopkeeper.setVisible(false);
        group.getChildren().add(shopkeeper);
        ImageView staff = new ImageView("file:E:\\game\\staff.png");
        staff.relocate(950, 500);
        staff.setVisible(false);
        group.getChildren().add(staff);
        //
        //Thread t1 = new Thread(new CoolThreads());
        //t1.start(); https://dzone.com/articles/java-thread-tutorial-creating-threads-and-multithr
        ImageView coinIcon_staff = new ImageView("file:E:\\game\\Coin Icon.png");
        ImageView coinIcon_attackBoost = new ImageView("file:E:\\game\\Coin Icon.png");
        Label coinNumber_staff;
        Label coinNumber_attackBoost;
        coinNumber_staff = new Label();
        coinNumber_staff.relocate(950, 580);
        coinNumber_staff.setText("5");
        coinNumber_staff.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        coinNumber_staff.setTextFill(Color.WHITE);
        coinNumber_staff.setVisible(false);
        group.getChildren().add(coinNumber_staff);
        coinIcon_staff.relocate(965, 580);
        coinIcon_staff.setVisible(false);
        group.getChildren().add(coinIcon_staff);
        //
        ImageView attackBoost = new ImageView("file:E:\\game\\attack boost.png");
        attackBoost.relocate(1050, 500);
        attackBoost.setVisible(false);
        group.getChildren().add(attackBoost);

        coinNumber_attackBoost = new Label();
        coinNumber_attackBoost.relocate(1050, 580);
        coinNumber_attackBoost.setText("5");
        coinNumber_attackBoost.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        coinNumber_attackBoost.setTextFill(Color.WHITE);
        coinNumber_attackBoost.setVisible(false);
        group.getChildren().add(coinNumber_attackBoost);
        coinIcon_attackBoost.relocate(1065, 580);
        coinIcon_attackBoost.setVisible(false);
        group.getChildren().add(coinIcon_attackBoost);
        //Makes the grass
        //
        ImageView Grass = new ImageView("file:E:\\game\\Grass_01.png");
        Grass.setOpacity(0.6);
        group.getChildren().add(Grass);
        Image Grass_01 = new Image("file:E:\\game\\Grass_01.png");
        Image Grass_02 = new Image("file:E:\\game\\Grass_02.png");
        Image Grass_03 = new Image("file:E:\\game\\Grass_03.png");
        Grass_overlay(Grass, Grass_01, Grass_02, Grass_03);

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
        ImageView background = new ImageView("file:E:\\game\\floor1.png");
        Image floor1 = new Image("file:E:\\game\\floor0.png");
        Image floor2 = new Image("file:E:\\game\\floor1.png");
        Image floor3 = new Image("file:E:\\game\\floor2.png");
        Image floor4 = new Image("file:E:\\game\\floor3.png");
        Image floor5 = new Image("file:E:\\game\\floor4.png");
        Image floor6 = new Image("file:E:\\game\\floor5.png");
        Image floor7 = new Image("file:E:\\game\\floor6.png"); //boss room
        Image floor8 = new Image("file:E:\\game\\floor7.png"); //shop
        group.getChildren().add(background);
        background.toBack();

        //chest
        ImageView chest = new ImageView("file:E:\\game\\chest_closed.png");
        Image chest_open = new Image("file:E:\\game\\chest_open.png");
        chest.relocate(785, 150);
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
        //
        Label score = new Label();
        score.relocate(910, 90);
        score.setText(String.valueOf(Score));
        score.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        score.setTextFill(Color.WHITE);
        score.setOpacity(0.3);


        ImageView scoreBoard = new ImageView("file:E:\\game\\ScoreBoard IMG.png");
        Label[][] Labels = new Label[10][3];
        ScoreDisplayer(scoreBoard, Labels);
        mapGenerator(Grass, sword_down, sword_left, sword_right, sword_up, usedSword, floor1, floor2, floor3,
                floor4, floor5, floor6, floor7, floor8, background, northDoor, eastDoor, southDoor, westDoor, bossDoor, chest,
                mapItem, bossHealthBar, bossHealthBarREC, shopkeeper, scoreIMG, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, hearts,
                score, coinNumber_attackBoost, coinNumber_staff);
        //
        enemyMaker(); //puts the enemies over the doors
        //roomCreater();
        //Coin Icon
        ImageView coinIcon = new ImageView("file:E:\\game\\Coin Icon.png");
        coinIcon.relocate(25, 100);
        //
        Label coinNumber = new Label();
        coinNumber.relocate(50, 98);
        coinNumber.setText(String.valueOf(CoinNumber));
        coinNumber.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        coinNumber.setTextFill(Color.WHITE);
        group.getChildren().addAll(coinNumber, coinIcon);
        //Score
        scoreIMG.relocate(750, 90);
        scoreIMG.setOpacity(0.3);

        //
        //BOSS
        //
        boss.relocate(785, 439);//center 860 520
        boss.setVisible(false);
        group.getChildren().add(boss);
        //boss health
        bossHealthBar.relocate(500, 50);
        bossHealthBar.setVisible(false);
        bossHealthBarREC.setVisible(false);
        bossHealthBarREC.setFill(Color.RED);
        group.getChildren().addAll(bossHealthBar, bossHealthBarREC);
        //items
        images.get(0).getImage().setVisible(true); //sets the item in the starting room to visible
        //inventory
        ImageView inventoryPIC = new ImageView("file:E:\\game\\inventory.png");
        inventoryPIC.relocate(752, 325);
        inventoryPIC.setVisible(false);
        ImageView selected = new ImageView("file:E:\\game\\selected.png");
        selected.relocate(791, 364);
        selected.setVisible(false);

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
        //
        //
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
        InputManager(scene, stage, northWall, eastWall, southWall, westWall, link_sword_down, link_sword_left, link_sword_right,
                link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right,
                sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, chest, chest_open, mapItem, map, controls, bossHealthBar,
                bossHealthBarREC, scoreBoard, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, hearts, inventoryPIC, selected,
                score, coinNumber_staff, coinNumber_attackBoost, coinNumber,
                Labels);

        swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);

        stage.setFullScreenExitHint("");
        stage.setTitle("Cool game");
        //new FadeIn(menuGroup);
        stage.show();
    }

    private void soundManager() {
        switch (sound) { //whenever soundManager is called, the String sound will be set to the sound that plays.
            case "walking":
                if (l == 0 && walking == 1) {
                    walkSounds = new Timeline(new KeyFrame(Duration.seconds(0.4), event -> {
                        l = 1;
                        File f = new File("E:\\game\\sound\\walkingSound.wav");
                        Media media = new Media(f.toURI().toString());
                        mplayer = new MediaPlayer(media);
                        mplayer.setVolume(0.10 * musicVolume);
                        mplayer.setAutoPlay(true);
                    }));
                    walkSounds.setCycleCount(Timeline.INDEFINITE);
                    walkSounds.play();
                }
                break;
            case "swordMiss":
                File f = new File("E:\\game\\sound\\sword_miss.mp3");
                Media media = new Media(f.toURI().toString());
                MediaPlayer mplayer2 = new MediaPlayer(media);
                mplayer2.setVolume(0.05 * musicVolume);
                mplayer2.play();
                break;
            case "swordHit":
                File f1 = new File("E:\\game\\sound\\sword_hit.mp3");
                Media media1 = new Media(f1.toURI().toString());
                MediaPlayer mplayer3 = new MediaPlayer(media1);
                mplayer3.setVolume(0.05 * musicVolume);
                mplayer3.play();
                break;
            case "boss":
                File f2 = new File("E:\\game\\sound\\bossMusic.mp3");
                Media media2 = new Media(f2.toURI().toString());
                mplayer4 = new MediaPlayer(media2);
                mplayer4.setVolume(0.04 * musicVolume);
                mplayer4.play();
                mplayer4.setOnEndOfMedia(() -> {
                    mplayer4.seek(Duration.ZERO);
                    mplayer4.play();
                });
                break;
            case "background":
                File f3 = new File("E:\\game\\sound\\background.mp3");
                Media media3 = new Media(f3.toURI().toString());
                mplayer5 = new MediaPlayer(media3);
                mplayer5.setVolume(0.04 * musicVolume);
                mplayer5.play();
                mplayer5.setOnEndOfMedia(() -> {
                    mplayer5.seek(Duration.ZERO);
                    mplayer5.play();
                });
                break;
            case "triforce":
                File f4 = new File("E:\\game\\sound\\Triforce.mp3");
                Media media4 = new Media(f4.toURI().toString());
                MediaPlayer mplayer6 = new MediaPlayer(media4);
                mplayer6.setVolume(0.2 * musicVolume);
                mplayer6.play();
                break;
            case "magic_shot":
                File f5 = new File("E:\\game\\sound\\magic_shot.mp3");
                Media media5 = new Media(f5.toURI().toString());
                MediaPlayer mplayer7 = new MediaPlayer(media5);
                mplayer7.setVolume(0.10 * musicVolume);
                mplayer7.play();
                break;
            case "fire_ball":
                File f6 = new File("E:\\game\\sound\\Fireball.wav");
                Media media6 = new Media(f6.toURI().toString());
                MediaPlayer mplayer8 = new MediaPlayer(media6);
                mplayer8.setVolume(0.15 * musicVolume);
                mplayer8.play();
                break;
            case "Explosion":
                File f7 = new File("E:\\game\\sound\\Explosion.mp3");
                Media media7 = new Media(f7.toURI().toString());
                MediaPlayer mplayer9 = new MediaPlayer(media7);
                mplayer9.setVolume(0.15 * musicVolume);
                mplayer9.play();
                break;

        }
    }

    private void scoreChanger(Label score) {
        //Score goes down 1 every second
        if (q == 0) {
            Timeline scoreTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                q = 1;
                if (!defeatedBoss) {//when boss is defeated, the score stops going down.
                    Score--;
                }
                if (Score <= 0) {
                    Score = 0;
                }
                score.setText(String.valueOf(Score));
            }));
            scoreTimeline.setCycleCount(Timeline.INDEFINITE);
            scoreTimeline.play();
        }
    }

    private void itemMaker() {

        for (int m = 0; m < 6; m++) {
            images.add(new Images());
        }//makes 6 items
        for (Images image : images) {
            (image.getImage()).setLayoutY(image.getLayoutY());
            (image.getImage()).setLayoutX(image.getLayoutX());//CAN BE CHANGED TO RELOCATE
            image.getImage().setVisible(false);
            group.getChildren().add((image.getImage()));
        }

    }

    private void enemyMaker() {
        for (int m = 0; m < 6; m++) {
            enemies.add(new Enemy());
        }//makes 6 enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.getImage().setLayoutX(enemy.getLayoutX());
            enemy.getImage().setLayoutY(enemy.getLayoutY()); //CAN BE CHANGED TO RELOCATE
            enemy.getImage().setVisible(false);
            group.getChildren().add(enemy.getImage());
        }
    }

    private void itemChanger() {
        int currentFloorSUB = Integer.parseInt(currentFloor.substring(6));
        currentFloorSUB--;
        if (!(currentFloorSUB == 6)) {
            for (Images image : images) {
                image.getImage().setVisible(false);
            }//makes the item in the current room visable
            images.get(currentFloorSUB).getImage().setVisible(true);
        }
    }

    private void enemyChanger() {
        int currentFloorSUB = Integer.parseInt(currentFloor.substring(6));
        currentFloorSUB--;
        for (Enemy enemy : enemies) {
            enemy.getImage().setVisible(false);
        }//makes the enemy in the current room visable
        if (!(currentFloorSUB == 0)) {
            enemies.get(currentFloorSUB).getImage().setVisible(true);
        }
    }

    private void enemyMover(ImageView hearts) {
        translate.setDuration(Duration.seconds(3.3));//3.3secs
        if (s == 0) {
            EnemyMover = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
                s = 1;
                number++;
                int currentFloorSUB1 = Integer.parseInt(currentFloor.substring(6));
                currentFloorSUB1--;
                int finalCurrentFloorSUB = currentFloorSUB1;
                if (!(currentFloorSUB1 == 7 || currentFloorSUB1 == 6 || currentFloorSUB1 == 8) && enemies.get(currentFloorSUB1).getImage().isVisible()) {
                    translate.setNode(enemies.get(finalCurrentFloorSUB).getImage());
                    translate.setToX(-enemies.get(finalCurrentFloorSUB).getImage().getLayoutX() + (hero.getLayoutX()));
                    translate.setToY(-enemies.get(finalCurrentFloorSUB).getImage().getLayoutY() + (hero.getLayoutY()));
                    translate.setInterpolator(Interpolator.LINEAR);
                    //System.out.println(enemies.get(currentFloorSUB).getImage().getTranslateX() + " , " + enemies.get(currentFloorSUB).getImage().getTranslateY());
                    if (!(enemies.get(currentFloorSUB1).getImage().isVisible())) {
                        translate.stop();//checks if you've left the room.
                    }
                    translate.play();
                    if ((enemies.get(currentFloorSUB1).getImage().getTranslateX() - Math.floor(enemies.get(currentFloorSUB1).getImage().getTranslateX()) == 0) && (enemies.get(currentFloorSUB1).getImage().getTranslateY() - Math.floor(enemies.get(currentFloorSUB1).getImage().getTranslateY()) == 0)) {
                        enemyAnimater(); //checks if the enemy is standing still
                    }
                    if (hero.getBoundsInParent().intersects(enemies.get(finalCurrentFloorSUB).getImage().getBoundsInParent()) && timePassed2 >= 2 && enemies.get(finalCurrentFloorSUB).getImage().getOpacity() > 0 && enemies.get(finalCurrentFloorSUB).getImage().isVisible() && !attacking) {
                        Score = Score - 10;
                        hero_Health = hero_Health - 1;
                        healthChecker(hearts);
                        timePassed2 = 0;
                    }
                }
            }));
            EnemyMover.setCycleCount(Timeline.INDEFINITE);
            EnemyMover.play();
        }
    }

    private void enemyAnimater() {
        int currentFloorSUB = Integer.parseInt(currentFloor.substring(6));
        currentFloorSUB--;
        int i = currentFloorSUB;
        double constant = (hero.getLayoutY() - ((enemies.get(i).getImage().getLayoutY() - 82) + enemies.get(i).getImage().getTranslateY())) / (hero.getLayoutX() - ((enemies.get(i).getImage().getLayoutX() - 75) + enemies.get(i).getImage().getTranslateX()));
        int horizontalDistance = (int) (hero.getLayoutX() - enemies.get(i).getImage().getTranslateX()) - 780;
        int verticalDistance = (int) (hero.getLayoutY() - enemies.get(i).getImage().getTranslateY()) - 540;
        //System.out.println("HERO: " + hero.getLayoutX() + " , " + hero.getLayoutY());
        //System.out.println("ENEMY: " + enemies.get(i).getImage().getTranslateX() + " , " + enemies.get(i).getImage().getTranslateY());
        // System.out.println("horizontal Distance: " + horizontalDistance + " , vertical Distance: " + verticleDistance);
        //System.out.println(constant);
        String name = enemies.get(i).getName();
        //-0.42 <  constant < 0.43
        if (verticalDistance < 0 && (constant > -100 && constant < 100)) {
            Image up = new Image("file:E:\\game\\" + name + "_up_1.png");
            enemies.get(i).setImage(up);
            //System.out.println("up " + verticleDistance + " " + constant);
        }
        if (horizontalDistance > 0 && (constant < 0.43 && constant > -0.42)) {
            Image right = new Image("file:E:\\game\\" + name + "_right_1.png");
            enemies.get(i).setImage(right);
            //System.out.println("right " + horizontalDistance + " " + constant);
        }
        if (horizontalDistance < 0 && (constant > -0.64 && constant < 0.65)) {
            Image left = new Image("file:E:\\game\\" + name + "_left_1.png");
            enemies.get(i).setImage(left);
            //System.out.println("left " + horizontalDistance + " " + constant);
        }
        if (verticalDistance > 0 && (constant > -100 && constant < 100)) {
            Image down = new Image("file:E:\\game\\" + name + "_down_1.png");
            enemies.get(i).setImage(down);
            //System.out.println("down " + verticleDistance + " " + constant);
        }
    }

    private void swordMover(Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword) {
        sword_up.relocate(hero.getLayoutX() + 46, hero.getLayoutY() - 78);
        sword_down.relocate(hero.getLayoutX() + 67, hero.getLayoutY() + 128);
        sword_left.relocate(hero.getLayoutX() - 85, hero.getLayoutY() + 78);
        sword_right.relocate(hero.getLayoutX() + 142, hero.getLayoutY() + 75);
    }

    private void mapGenerator(ImageView Grass, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword,
                              Image floor1, Image floor2, Image floor3, Image floor4, Image floor5, Image floor6, Image floor7, Image floor8, ImageView background,
                              ImageView northDoor, ImageView eastDoor, ImageView southDoor, ImageView westDoor, ImageView bossDoor, ImageView chest, ImageView mapItem,
                              ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView shopkeeper, ImageView scoreIMG, ImageView staff, ImageView attackBoost,
                              ImageView coinIcon_staff, ImageView coinIcon_attackBoost, ImageView hearts, Label score, Label coinNumber_attackBoost, Label coinNumber_staff) {
        if (h == 0) {
            Timeline mapTimeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
                h = 1;
                switch (currentFloor) {

                    case "floor 1":
                        for (Enemy enemy1 : enemies) {
                            enemy1.getImage().setVisible(false);
                        }
                        northDoor.setVisible(true);
                        if (hero.getBoundsInParent().intersects(northDoor.getBoundsInParent()) && Objects.equals(direction, "up")) {
                            currentFloor = "floor 2";
                            background.setImage(floor2);
                            hero.relocate(810, 810);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);

                        }
                        eastDoor.setVisible(true);
                        if (hero.getBoundsInParent().intersects(eastDoor.getBoundsInParent()) && Objects.equals(direction, "right")) {
                            currentFloor = "floor 3";
                            background.setImage(floor3);
                            hero.relocate(150, 430);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);
                        }
                        southDoor.setVisible(true);

                        if (hero.getBoundsInParent().intersects(southDoor.getBoundsInParent()) && Objects.equals(direction, "down")) {
                            currentFloor = "floor 4";
                            background.setImage(floor4);
                            hero.relocate(810, 130);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);

                        }
                        westDoor.setVisible(true);

                        if (hero.getBoundsInParent().intersects(westDoor.getBoundsInParent()) && Objects.equals(direction, "left")) {
                            currentFloor = "floor 5";
                            background.setImage(floor5);
                            hero.relocate(1600, 430);
                            mapItem.setVisible(true);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);
                        }
                        break;
                    case "floor 2":
                        northDoor.setVisible(true);
                        eastDoor.setVisible(false);
                        southDoor.setVisible(true);
                        bossDoor.setVisible(false);
                        westDoor.setVisible(false);
                        if (hero.getBoundsInParent().intersects(southDoor.getBoundsInParent()) && Objects.equals(direction, "down")) {
                            currentFloor = "floor 1";
                            background.setImage(floor1);
                            hero.relocate(810, 130);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);
                        }

                        if (hero.getBoundsInParent().intersects(northDoor.getBoundsInParent()) && Objects.equals(direction, "up")) {
                            currentFloor = "floor 8";//shop
                            background.setImage(floor8);
                            Grass.setVisible(false);
                            hero.relocate(810, 910);
                            southDoor.setLayoutX(southDoor.getLayoutX() + 110);
                            scoreIMG.setLayoutX(scoreIMG.getLayoutX() + 110);
                            score.setLayoutX(score.getLayoutX() + 110);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            images.get(1).getImage().setVisible(false);
                        }
                        break;
                    case "floor 3":
                        northDoor.setVisible(false);
                        eastDoor.setVisible(true);
                        southDoor.setVisible(false);
                        westDoor.setVisible(true);
                        bossDoor.setVisible(false);

                        if (hero.getBoundsInParent().intersects(westDoor.getBoundsInParent()) && Objects.equals(direction, "left")) {
                            currentFloor = "floor 1";
                            background.setImage(floor1);
                            hero.relocate(1600, 430);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);
                        }
                        if (hero.getBoundsInParent().intersects(eastDoor.getBoundsInParent()) && Objects.equals(direction, "right")) {
                            currentFloor = "floor 6";
                            background.setImage(floor6);
                            hero.relocate(150, 430);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);
                        }
                        break;
                    case "floor 4":
                        northDoor.setVisible(true);
                        bossDoor.setVisible(false);
                        eastDoor.setVisible(false);
                        southDoor.setVisible(false);
                        westDoor.setVisible(false);
                        if (hero.getBoundsInParent().intersects(northDoor.getBoundsInParent()) && Objects.equals(direction, "up")) {
                            currentFloor = "floor 1";
                            background.setImage(floor1);
                            hero.relocate(810, 810);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);
                        }

                        break;
                    case "floor 5":
                        northDoor.setVisible(false);
                        eastDoor.setVisible(true);
                        bossDoor.setVisible(false);
                        southDoor.setVisible(false);
                        westDoor.setVisible(false);
                        if (hero.getBoundsInParent().intersects(eastDoor.getBoundsInParent()) && Objects.equals(direction, "right")) {
                            currentFloor = "floor 1";
                            background.setImage(floor1);
                            hero.relocate(150, 430);
                            mapItem.setVisible(false);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);
                        }

                        break;
                    case "floor 6":
                        northDoor.setVisible(false);
                        bossDoor.setVisible(true);
                        eastDoor.setVisible(false);
                        southDoor.setVisible(false);
                        westDoor.setVisible(true);
                        if (hero.getBoundsInParent().intersects(westDoor.getBoundsInParent()) && Objects.equals(direction, "left")) {
                            currentFloor = "floor 3";
                            background.setImage(floor3);
                            hero.relocate(1600, 430);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);
                        }
                        if (hero.getBoundsInParent().intersects(bossDoor.getBoundsInParent()) /*&& bossDoor.getImage() == bossDoor_open*/ && Objects.equals(direction, "up")) {
                            currentFloor = "floor 7";
                            Grass.setVisible(false);
                            background.setImage(floor7);
                            hero.relocate(810, 810);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                        }
                        break;
                    case "floor 7":
                        EnemyMover.stop();
                        //boss room
                        northDoor.setVisible(false);
                        bossDoor.setVisible(false);
                        eastDoor.setVisible(false);
                        southDoor.setVisible(false);
                        westDoor.setVisible(false);
                        if (v == 0) {
                            activeBoss = true;
                            bossRoom(chest, bossHealthBar, bossHealthBarREC, hearts, score);
                            v = 1;
                        }

                        for (Images image : images) {
                            image.getImage().setVisible(false);
                        }
                        for (Enemy enemy : enemies) {
                            enemy.getImage().setVisible(false);
                        }
                        break;
                    case "floor 8":
                        //shop room
                        northDoor.setVisible(false);
                        southDoor.setVisible(true);
                        eastDoor.setVisible(false);
                        westDoor.setVisible(false);
                        //
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
                        for (Enemy enemy : enemies) {
                            enemy.getImage().setVisible(false);
                        }
                        if (hero.getBoundsInParent().intersects(southDoor.getBoundsInParent()) && Objects.equals(direction, "down")) {
                            currentFloor = "floor 2";
                            background.setImage(floor2);
                            Grass.setVisible(true);
                            hero.setLayoutX(810);
                            hero.setLayoutY(130);
                            southDoor.setLayoutX(southDoor.getLayoutX() - 110);
                            scoreIMG.setLayoutX(scoreIMG.getLayoutX() - 110);
                            score.setLayoutX(score.getLayoutX() - 110);
                            shopkeeper.setVisible(false);
                            staff.setVisible(false);
                            attackBoost.setVisible(false);
                            coinNumber_staff.setVisible(false);
                            coinIcon_staff.setVisible(false);
                            coinNumber_attackBoost.setVisible(false);
                            coinIcon_attackBoost.setVisible(false);
                            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                            itemChanger();
                            enemyChanger();
                            enemyMover(hearts);
                        }
                        break;
                    //boss room
                }
            }));
            mapTimeline.setCycleCount(Timeline.INDEFINITE);
            mapTimeline.play();
        }
    }

    private void Grass_overlay(ImageView Grass, Image Grass_01, Image Grass_02, Image Grass_03) {

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
    }

    private void bossRoom(ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView hearts, Label score) {
        bossHealthBar.setVisible(true);
        bossHealthBarREC.setVisible(true);
        timePassed3 = 0;
        mplayer5.stop(); //stops the background music
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
                        fireballs.get(0).getImage().setLayoutX(boss.getLayoutX());
                        fireballs.get(0).getImage().setLayoutY(boss.getLayoutY());
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
                        healthChecker(hearts);
                        fireballs.get(0).getImage().setVisible(false);
                        fireballs.remove(0);
                        group.getChildren().remove(fireballs.get(0).getImage());
                    }
                    if (y >= 8) {
                        bossPhase2(chest, bossHealthBar, bossHealthBarREC, hearts, score);
                    }
                } catch (Exception e) {
                    //System.out.println("Boss room phase 1 error");
                }
            }));
            Bossroomtimeline.setCycleCount(Timeline.INDEFINITE);
            Bossroomtimeline.play();
        }
    }

    private void bossPhase2(ImageView chest, ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView hearts, Label score) {//phase 2 of the boss fight
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
                        fireballs.get(m).getImage().setLayoutX(boss.getLayoutX());
                        fireballs.get(m).getImage().setLayoutY(boss.getLayoutY());//sets the starting point to the bosses hand.
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
                        //System.out.println((Math.tan(radians) * Math.abs(hero.getLayoutX() - boss.getLayoutX())));
                    }
                    sound = "fire_ball";
                    soundManager();
                } else {
                    System.out.println("attack 2");//860 520
                    for (int m = 0; m < 8; m++) {
                        fireballs.add(new Fireballs());
                        fireballs.get(m).getImage().setLayoutX(boss.getLayoutX() + 75);
                        fireballs.get(m).getImage().setLayoutY(boss.getLayoutY() + 75);
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
                        healthChecker(hearts);
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
                    score.setText(String.valueOf(Score));
                    mplayer4.stop();
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

    public void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 1000, 1000);//updates every second
    }

    private void scoreCountdown(Label score) {
        if (scoretimer == 0) {
            scoreChanger(score);
            scoretimer = 1;
        }
    }

    private void heroMover() {
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

    private void InputManager(Scene scene, Stage stage, Rectangle northWall, Rectangle eastWall, Rectangle southWall, Rectangle westWall,
                              Image link_sword_down, Image link_sword_left, Image link_sword_right, Image link_sword_up, Image link_staff_down,
                              Image link_staff_left, Image link_staff_right, Image link_staff_up, Rectangle sword_down, Rectangle sword_left,
                              Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open,
                              ImageView bossDoor, ImageView chest, Image chest_open, ImageView mapItem, ImageView map, ImageView controls,
                              ImageView bossHealthBar, Rectangle bossHealthBarREC, ImageView scoreBoard, ImageView staff, ImageView attackBoost,
                              ImageView coinIcon_staff, ImageView coinIcon_attackBoost, ImageView hearts, ImageView inventoryPic, ImageView selected,
                              Label score, Label coinNumber_staff, Label coinNumber_attackBoost, Label coinNumber, Label[][] Labels) {
        try {
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case UP:
                        if (hero.getImage() == link_staff_up || hero.getImage() == link_sword_up) {
                            hero.setLayoutY(hero.getLayoutY() + 100);
                            Image link_running_up_1 = new Image("file:E:\\game\\link_running_up_1.png");
                            hero.setImage(link_running_up_1);
                            attacking = false;
                        }
                        up = true;
                        heroMover();
                        direction = "up";
                        attackDirection = "up";
                        Base_Procedure(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard
                                , inventoryPic, selected, score, Labels);
                        break;
                    case RIGHT:
                        attacking = false;
                        right = true;
                        heroMover();
                        direction = "right";
                        attackDirection = "right";
                        Base_Procedure(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard
                                , inventoryPic, selected, score, Labels);
                        break;
                    case DOWN:
                        attacking = false;
                        down = true;
                        heroMover();
                        direction = "down";
                        attackDirection = "down";
                        Base_Procedure(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard
                                , inventoryPic, selected, score, Labels);
                        break;
                    case LEFT:
                        if ((hero.getImage() == link_staff_left) || (hero.getImage() == link_sword_left)) {
                            hero.setLayoutX(hero.getLayoutX() + 100);
                            Image link_running_left_1 = new Image("file:E:\\game\\link_running_left_1.png");
                            hero.setImage(link_running_left_1);
                            attacking = false;
                        }
                        left = true;
                        heroMover();
                        direction = "left";
                        attackDirection = "left";
                        Base_Procedure(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword, controls, scoreBoard
                                , inventoryPic, selected, score, Labels);
                        break;
                    case SPACE:
                        swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
                        if (up || right || left || down) {//this stops the walking sound when you attack
                            walking = 0;
                            walkSounds.stop();
                            mplayer.stop();
                            l = 0;
                        }
                        AttackHandler(link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up,
                                usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                        inventoryHider(controls, inventoryPic, selected);
                        ScoreBoardHider(scoreBoard, Labels);
                        break;
                    case TAB:
                        inventoryManager(inventoryPic, selected);
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
                        healthChecker(hearts);
                        break;
                    case L:
                        hero_Health--;
                        if (hero_Health < 0) {
                            hero_Health = 0;
                        }
                        ScoreBoardHider(scoreBoard, Labels);
                        healthChecker(hearts);
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
                                AttackHandler(link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword
                                        , bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                            }
                        } else {
                            selected.setLayoutY(selected.getLayoutY() - 67);
                            if (selected.getLayoutY() <= 364) {
                                selected.setLayoutY(364);
                            }
                        }
                        break;
                    case A:
                        if (!selected.isVisible()) {
                            if (activestaff) {

                                attackDirection = "left_staff";
                                AttackHandler(link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword
                                        , bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                            }
                        } else {
                            selected.setLayoutX(selected.getLayoutX() - 68);
                            if (selected.getLayoutX() <= 791) {
                                selected.setLayoutX(791);
                            }
                        }
                        break;
                    case S:
                        if (!selected.isVisible()) {
                            if (activestaff) {

                                attackDirection = "down_staff";
                                AttackHandler(link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword
                                        , bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                            }
                        } else {
                            selected.setLayoutY(selected.getLayoutY() + 67);
                            if (selected.getLayoutY() >= 699) {
                                selected.setLayoutY(699);
                            }
                        }
                        break;
                    case D:
                        if (!selected.isVisible()) {
                            if (activestaff) {

                                attackDirection = "right_staff";
                                AttackHandler(link_sword_down, link_sword_left, link_sword_right, link_sword_up, link_staff_down, link_staff_left, link_staff_right, link_staff_up, sword_down, sword_left, sword_right, sword_up, usedSword
                                        , bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                            }
                        } else {
                            selected.setLayoutX(selected.getLayoutX() + 68);
                            if (selected.getLayoutX() >= 1063) {
                                selected.setLayoutX(1063);
                            }
                        }
                        break;
                    case E:
                        itemInteraction(chest, chest_open, mapItem, map, staff, attackBoost, coinIcon_staff, coinIcon_attackBoost, hearts, inventoryPic, selected, coinNumber_staff, coinNumber_attackBoost,
                                coinNumber, Labels);
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
                switch (event.getCode()) {
                    case UP:
                        up = false;
                        heroMover();

                    case DOWN:
                        down = false;
                        heroMover();

                    case LEFT:
                        left = false;
                        heroMover();

                    case RIGHT:
                        right = false;
                        //
                        walking = 0;
                        walkSounds.stop(); //stops the weird sound glitch
                        mplayer.stop();
                        l = 0;
                        //
                        heroMover();
                        break;
                }
            });
        } catch (Exception e) {
            // System.out.println("error in input manager");
            System.out.println();
        }
    }

    private void VolumeChanger() {
        if (!activeBoss) {
            mplayer5.setVolume(0.05 * musicVolume);
        } else if (activeBoss) {
            mplayer4.setVolume(0.05 * musicVolume);
        }
        System.out.println("Volume is: " + musicVolume);
    }

    private void MusicControls() {
        if (!activeBoss) {
            if (!mplayer5.isMute()) {
                mplayer5.setMute(true);
            } else {
                mplayer5.setMute(false);
            }
        } else {
            if (!mplayer4.isMute()) {
                mplayer4.setMute(true);
            } else {
                mplayer4.setMute(false);
            }
        }
    }

    private void Base_Procedure(Rectangle northWall, Rectangle eastWall, Rectangle southWall, Rectangle westWall, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right,
                                Rectangle sword_up, Rectangle usedSword, ImageView controls, ImageView scoreBoard, ImageView inventoryPIC, ImageView selected, Label score, Label[][] Labels) {
        sound = "walking";
        scoreCountdown(score);
        swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
        AnimationManager();
        walking++;
        soundManager();
        Checkcollision(northWall, eastWall, southWall, westWall, sword_down, sword_left, sword_right, sword_up, usedSword);
        inventoryHider(controls, inventoryPIC, selected);
        ScoreBoardHider(scoreBoard, Labels);
    }

    private void itemInteraction(ImageView chest, Image chest_open, ImageView mapItem, ImageView map, ImageView staff, ImageView attackBoost, ImageView coinIcon_staff, ImageView coinIcon_attackBoost,
                                 ImageView hearts, ImageView inventoryPIC, ImageView selected, Label coinNumber_staff, Label coinNumber_attackBoost, Label coinNumber, Label[][] Labels) {
        if (hero.getBoundsInParent().intersects(mapItem.getBoundsInParent()) && mapItem.isVisible() && mapItem.getOpacity() > 0) {
            System.out.println("map picked up");
            mapItem.setOpacity(0);
            map.setVisible(true);
            map.setOpacity(0.5);
            map.toFront();
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
            if (ii == 5) {
                ii = 0; //puts an item onto the next row
                jj++; //next column
            }
            inventory.get(b).getImage().setLayoutX(791 + 69 * ii);
            inventory.get(b).getImage().setLayoutY(364 + 69 * jj);
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
                scoreSaver(Labels);
            }
        }
        if (!inventoryPIC.isVisible()) {
            //picking up item
            for (Images image : images) {
                if (hero.getBoundsInParent().intersects(image.getImage().getBoundsInParent()) && image.getImage().getOpacity() > 0 && image.getImage().isVisible()) {//checks if you're touching any potions
                    image.getImage().setOpacity(0);
                    String item = image.getName();
                    System.out.println(item);
                    itemId = 1;
                    inventory.add(new Inventory(itemId));
                    if (ii == 5) {
                        ii = 0; //puts an item onto the next row
                        jj++; //next column
                    }
                    inventory.get(b).getImage().setLayoutX(791 + 69 * ii);
                    inventory.get(b).getImage().setLayoutY(364 + 69 * jj);
                    inventory.get(b).getImage().setVisible(false);
                    if (ii == 5 && jj == 5) {
                        inventory.remove(inventory.get(b));
                    }
                    group.getChildren().add(inventory.get(b).getImage());
                    ii++;
                    b++;
                }
            }
        } else if (inventoryPIC.isVisible()) {
            //using an item
            for (Inventory anInventory : inventory) {
                if (selected.getBoundsInParent().intersects(anInventory.getImage().getBoundsInParent()) && anInventory.getImage().getOpacity() > 0 && anInventory.getImage().isVisible()) {
                    Score = Score - 50;
                    anInventory.getImage().setOpacity(0);
                    group.getChildren().remove(anInventory.getImage());
                    String itemName = anInventory.getName();
                    switch (itemName) {
                        case "health potion":
                            hero_Health = hero_Health + 2;
                            healthChecker(hearts);
                            if (hero_Health >= 6) {
                                hero_Health = 6;
                                healthChecker(hearts);
                            }
                            break;
                        case "attack boost":
                            anInventory.getImage().setOpacity(0);
                            group.getChildren().remove(anInventory.getImage());
                            attackmultiplier = 1.25;
                            System.out.println("attack is now " + attackmultiplier);
                            break;
                    }
                }
            }
        }
    }

    private void inventoryHider(ImageView controls, ImageView inventoryPIC, ImageView selected) {
        //hides the inventory if you move while its open
        if (selected.isVisible()) {
            selected.setVisible(false);
            inventoryPIC.setVisible(false);
            for (Inventory value : inventory) {
                value.getImage().setVisible(false);
            }
        }
        if (controls.isVisible()) {
            controls.setVisible(false);
        }
    }

    private void inventoryManager(ImageView inventoryPIC, ImageView selected) {
        if (!inventoryPIC.isVisible()) {
            inventoryPIC.setVisible(true);
            inventoryPIC.toFront();
            selected.setVisible(true);
            selected.toFront();
            new FadeIn(inventoryPIC).play();
            new FadeIn(selected).play();
            for (Inventory value : inventory) {
                value.getImage().setVisible(true);
                new FadeIn(value.getImage()).play();
                value.getImage().toFront();
            }
        } else {
            inventoryPIC.setVisible(false);
            selected.setVisible(false);
            selected.relocate(752 + 39, 325 + 39);
            for (Inventory value : inventory) {
                value.getImage().setVisible(false);
            }
        }
    }

    private void healthChecker(ImageView hearts) {
        switch (hero_Health) {
            case 0:
                Image heart_0 = new Image("file:E:\\game\\hearts\\heart_0.png");
                hearts.setImage(heart_0);
                //new FadeOut(hero).play();
                System.exit(12);
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

    private void attackingEnemies(Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open,
                                  ImageView bossDoor, ImageView bossHealthBar, Rectangle bossHealthBarREC, Label coinNumber) {
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
                int currentFloorSUB = Integer.parseInt(currentFloor.substring(6));
                currentFloorSUB--;
                if (usedSword.getBoundsInParent().intersects(enemies.get(currentFloorSUB).getImage().getBoundsInParent()) && enemies.get(currentFloorSUB).getImage().isVisible() && enemies.get(currentFloorSUB).getImage().getOpacity() > 0 && timePassed >= 1) {
                    sound = "swordHit";
                    soundManager();
                    System.out.println("hit enemy in room " + (currentFloorSUB + 1));
                    translate.stop();

                    if (enemies.get(currentFloorSUB).getImage().getOpacity() == 0.5) {
                        enemies.get(currentFloorSUB).getImage().setOpacity(0);
                        Score = Score + 50;
                        CoinNumber = CoinNumber + 1;
                        coinNumber.setText(String.valueOf(CoinNumber));
                        defeatedEnemies++;
                        if (defeatedEnemies == enemies.size() - 1) {
                            bossDoor.setImage(bossDoor_open);
                        }
                    } else {
                        enemies.get(currentFloorSUB).getImage().setOpacity(0.5);
                    }
                    timePassed = 0;
                } else {
                    sound = "swordMiss";
                    soundManager();
                }
            }
        } catch (Exception e) {
            // System.out.println("Difficulty attacking enemies");
            sound = "swordMiss";
            soundManager();
        }
    }

    private void AttackHandler(Image link_sword_down, Image link_sword_left, Image link_sword_right, Image link_sword_up, Image link_staff_down, Image link_staff_left, Image link_staff_right, Image link_staff_up, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open, ImageView bossDoor, ImageView bossHealthBar, Rectangle bossHealthBarREC, Label coinNumber) {
        Image link_running_down_1 = new Image("file:E:\\game\\link_running_down_1.png");
        Image link_running_left_1 = new Image("file:E:\\game\\link_running_left_1.png");
        Image link_running_up_1 = new Image("file:E:\\game\\link_running_up_1.png");
        Image link_running_right_1 = new Image("file:E:\\game\\link_running_right_1.png");
        switch (attackDirection) {
            case "up":
                if (hero.getImage() != link_sword_up) {
                    hero.setImage(link_sword_up);
                    attacking = true;
                    hero.setLayoutY(hero.getLayoutY() - 100);
                    usedSword = sword_up;
                    attackingEnemies(sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                    timepassed1 = 0;
                }
                break;
            case "right":
                if (hero.getImage() != link_sword_right) {//if you are already attacking nothing will happen
                    hero.setImage(link_sword_right);
                    attacking = true;
                    usedSword = sword_right;
                    attackingEnemies(sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                    timepassed1 = 0;
                }
                break;
            case "down":
                if (hero.getImage() != link_sword_down) {//if you are already attacking nothing will happen
                    hero.setImage(link_sword_down);
                    attacking = true;
                    usedSword = sword_down;
                    attackingEnemies(sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                    timepassed1 = 0;
                }
                break;
            case "left":
                if (hero.getImage() != link_sword_left) {//if you are already attacking nothing will happen
                    hero.setImage(link_sword_left);
                    attacking = true;
                    hero.setLayoutX(hero.getLayoutX() - 100);
                    usedSword = sword_left;
                    attackingEnemies(sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                    timepassed1 = 0;
                }
                break;
            //
            //
            //staff handler hero -> staff
            //
            //
            case "up_staff":
                if (hero.getImage() != link_staff_up) {
                    hero.setImage(link_staff_up);
                    hero.setLayoutY(hero.getLayoutY() - 100);
                    if (timepassed7 >= 2) {
                        staffShooter(sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                        timepassed7 = 0;
                        timepassed1 = 0;
                    }
                }
                break;
            case "down_staff":
                if (hero.getImage() != link_staff_down) {
                    hero.setImage(link_staff_down);
                    if (timepassed7 >= 2) {
                        staffShooter(sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                        timepassed7 = 0;
                        timepassed1 = 0;
                    }
                }
                break;
            case "left_staff":
                if (hero.getImage() != link_staff_left) {
                    hero.setImage(link_staff_left);
                    hero.setLayoutX(hero.getLayoutX() - 100);
                    if (timepassed7 >= 2) {
                        staffShooter(sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
                        timepassed7 = 0;
                        timepassed1 = 0;
                    }
                }
                break;
            case "right_staff":
                if (hero.getImage() != link_staff_right) {
                    hero.setImage(link_staff_right);
                    if (timepassed7 >= 2) {
                        staffShooter(sword_down, sword_left, sword_right, sword_up, usedSword, bossDoor_closed, bossDoor_open, bossDoor, bossHealthBar, bossHealthBarREC, coinNumber);
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
                            if (hero.getImage() == link_sword_up) {
                                attacking = false;
                                hero.setImage(link_running_up_1);
                                hero.setLayoutY(hero.getLayoutY() + 100);
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword);
                            }
                            break;
                        case "right":
                            if (hero.getImage() == link_sword_right) {
                                attacking = false;
                                hero.setImage(link_running_right_1);
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword);
                            }
                            break;
                        case "down":
                            if (hero.getImage() == link_sword_down) {
                                attacking = false;
                                hero.setImage(link_running_down_1);
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword);
                            }
                            break;
                        case "left":
                            if (hero.getImage() == link_sword_left) {
                                attacking = false;
                                hero.setImage(link_running_left_1);
                                hero.setLayoutX(hero.getLayoutX() + 100);
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword);
                            }
                            break;
                        //
                        //
                        //staff handler, staff -> hero
                        //
                        //
                        case "up_staff":
                            if (hero.getImage() == link_staff_up) {
                                hero.setImage(link_running_up_1);
                                hero.setLayoutY(hero.getLayoutY() + 100);
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword);
                                attackDirection = "up";
                            }
                            break;
                        case "right_staff":
                            if (hero.getImage() == link_staff_right) {
                                hero.setImage(link_running_right_1);
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword);
                                attackDirection = "right";
                            }
                            break;
                        case "down_staff":
                            if (hero.getImage() == link_staff_down) {
                                hero.setImage(link_running_down_1);
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword);
                                attackDirection = "down";
                            }
                            break;
                        case "left_staff":
                            if (hero.getImage() == link_staff_left) {
                                hero.setImage(link_running_left_1);
                                hero.setLayoutX(hero.getLayoutX() + 100);
                                swordMover(sword_down, sword_left, sword_right, sword_up, finalUsedSword);
                                attackDirection = "left";
                            }
                            break;
                    }
                }
            }));
            animationReverser.setCycleCount(Timeline.INDEFINITE);
            animationReverser.play();
        }
    }

    private void staffShooter(Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword, Image bossDoor_closed, Image bossDoor_open, ImageView bossDoor,
                              ImageView bossHealthBar, Rectangle bossHealthBarREC, Label coinNumber) {
        try {
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
                    magic.get(0).getImage().setLayoutX(hero.getLayoutX() + 50);
                    magic.get(0).getImage().setLayoutY(hero.getLayoutY());
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX() + hero.getLayoutX());
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY());
                    //timepassed6 = 0;
                    break;

                case "left_staff":
                    magic.get(0).getImage().setLayoutX(hero.getLayoutX());
                    magic.get(0).getImage().setLayoutY(hero.getLayoutY() + 55);
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX());
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY() + hero.getLayoutY());
                    //timepassed6 = 0;
                    break;

                case "right_staff":
                    magic.get(0).getImage().setLayoutX(hero.getLayoutX() + 235);
                    magic.get(0).getImage().setLayoutY(hero.getLayoutY() + 90);
                    translateMagic.setToX(-magic.get(0).getImage().getLayoutX() + 1900);
                    translateMagic.setToY(-magic.get(0).getImage().getLayoutY() + (hero.getLayoutY() + 60));
                    //timepassed6 = 0;
                    break;

                case "down_staff":
                    magic.get(0).getImage().setLayoutX(hero.getLayoutX() + 80);
                    magic.get(0).getImage().setLayoutY(hero.getLayoutY() + 270);
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
                    int currentFloorSUB = Integer.parseInt(currentFloor.substring(6));
                    currentFloorSUB--;
                    c = 1;
                    try {
                        if (!activeBoss) {
                            if (magic.get(0).getImage().getBoundsInParent().intersects(enemies.get(currentFloorSUB).getImage().getBoundsInParent()) && enemies.get(currentFloorSUB).getImage().isVisible() && (enemies.get(currentFloorSUB).getImage().getOpacity() > 0)) {
                                //translate.stop();
                                //detects a hit on an enemy
                                if (enemies.get(currentFloorSUB).getImage().getOpacity() == 0.5) {
                                    enemies.get(currentFloorSUB).getImage().setOpacity(0);
                                    magic.get(0).getImage().setVisible(false);
                                    magic.remove(0);
                                    Score = Score + 50;
                                    CoinNumber = CoinNumber + 1;
                                    coinNumber.setText(String.valueOf(CoinNumber));
                                    defeatedEnemies++;
                                    if (defeatedEnemies == enemies.size() - 1) {
                                        bossDoor.setImage(bossDoor_open);
                                    }
                                } else {
                                    enemies.get(currentFloorSUB).getImage().setOpacity(0.5);
                                    magic.get(0).getImage().setVisible(false);
                                    magic.remove(0);
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
                                        magic.remove(0);
                                        fireballs.get(m).getImage().setVisible(false);
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

    private void scoreSaver(Label[][] Labels) {
        String name;
        int finalScore = Score + (CoinNumber * 10);// 1 coin = 10 Score when the boss is defeated.
        name = playerName; //for use when writing to database
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
                    System.out.println(i + "Scores were deleted");
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

    private void ScoreDisplayer(ImageView scoreBoard, Label[][] Labels) {
        for (int m = 0; m < 10; m++) {
            for (int n = 0; n < 3; n++) {
                Labels[m][n] = new Label();
                Labels[m][n].setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
                Labels[m][n].setTextFill(Color.WHITE);
                Labels[m][n].setVisible(true);
                Labels[m][n].setOpacity(0.8);
            }
        }
        int j = 0;
        scoreBoard.relocate(416, 125);
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
                Labels[j][0].relocate(504, 208 + (49.5 * (j + 1)));
                Labels[j][0].setText((j + 1) + " .");
                Labels[j][1].relocate(675, 208 + (49.5 * (j + 1)));
                Labels[j][1].setText(NameFromDatabase);
                Labels[j][2].relocate(1090, 208 + (49.5 * (j + 1)));
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

    private void AnimationManager() {
        if (i == 0) {
            //when holding an arrow key it swaps the image every 0.35 seconds
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

    private void Checkcollision(Rectangle northWall, Rectangle eastWall, Rectangle southWall, Rectangle westWall, Rectangle sword_down, Rectangle sword_left, Rectangle sword_right, Rectangle sword_up, Rectangle usedSword) {
        //pushes back from big box around the wall to stop from leaving the screen
        if (hero.getBoundsInParent().intersects(northWall.getBoundsInParent())) {
            hero.setLayoutY(hero.getLayoutY() + 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
        }
        if (hero.getBoundsInParent().intersects(southWall.getBoundsInParent())) {
            hero.setLayoutY(hero.getLayoutY() - 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
        }
        if (hero.getBoundsInParent().intersects(eastWall.getBoundsInParent())) {
            hero.setLayoutX(hero.getLayoutX() + 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
        }
        if (hero.getBoundsInParent().intersects(westWall.getBoundsInParent())) {
            hero.setLayoutX(hero.getLayoutX() - 10);
            swordMover(sword_down, sword_left, sword_right, sword_up, usedSword);
        }
    }
}