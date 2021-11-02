package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.sun.org.apache.xpath.internal.FoundIndex;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.AnimationTimer;
import javafx.scene.media.*;

import javax.sound.sampled.*;
import javax.swing.text.StyledEditorKit;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class CharacterMovement2 extends Application {
    private static final int KEYBOARD_MOVEMENT_DELTA = 10;
    //Rectangle rectangle1 = new Rectangle(400, 0, 160, 160);
    Label info;
    Timer timer = new Timer();
    MediaPlayer mplayer;
    int delay = 0;
    Label touching;
    String animation = "b";
    boolean running, goNorth, goSouth, goEast, goWest;
    String direction = "";
    String attackDirection = "";
    int i = 0;
    int j = 0;
    int l = 0;
    int k = 0;
    int q = 0;
    int b = 0;
    boolean stop = false;
    int timePassed = 0;
    int h = 0;
    int s = 0;
    String sound;
    Timeline walkSounds;
    Timeline swordSounds;
    //character
    ImageView hero;
    String currentFloor = "floor 1";
    Image link_running_down_1 = new Image("file:C:\\Users\\user\\Pictures\\game\\link_running_down_1.png");
    Image link_running_down_2 = new Image("file:C:\\Users\\user\\Pictures\\game\\link_running_down_2.png");
    Image link_running_left_1 = new Image("file:C:\\Users\\user\\Pictures\\game\\link_running_left_1.png");
    Image link_running_left_2 = new Image("file:C:\\Users\\user\\Pictures\\game\\link_running_left_2.png");
    Image link_running_right_1 = new Image("file:C:\\Users\\user\\Pictures\\game\\link_running_right_1.png");
    Image link_running_right_2 = new Image("file:C:\\Users\\user\\Pictures\\game\\link_running_right_2.png");
    Image link_running_up_1 = new Image("file:C:\\Users\\user\\Pictures\\game\\link_running_up_1.png");
    Image link_running_up_2 = new Image("file:C:\\Users\\user\\Pictures\\game\\link_running_up_2.png");
    //swords
    Image link_sword_down = new Image("file:C:\\Users\\user\\Pictures\\game\\link_sword_down.png");
    Image link_sword_left = new Image("file:C:\\Users\\user\\Pictures\\game\\link_sword_left.png");
    Image link_sword_right = new Image("file:C:\\Users\\user\\Pictures\\game\\link_sword_right.png");
    Image link_sword_up = new Image("file:C:\\Users\\user\\Pictures\\game\\link_sword_up.png");
    //backgrounds
    Image floor1 = new Image("file:C:\\Users\\user\\Pictures\\game\\floor1.png");
    Image floor2 = new Image("file:C:\\Users\\user\\Pictures\\game\\floor2.png");
    Image floor3 = new Image("file:C:\\Users\\user\\Pictures\\game\\floor3.png");
    Image floor4 = new Image("file:C:\\Users\\user\\Pictures\\game\\floor4.png");
    Image floor5 = new Image("file:C:\\Users\\user\\Pictures\\game\\floor5.png");
    Image floor6 = new Image("file:C:\\Users\\user\\Pictures\\game\\floor6.png");
    Image floor7 = new Image("file:C:\\Users\\user\\Pictures\\game\\floor7.png");
    ImageView background = new ImageView("file:C:\\Users\\user\\Pictures\\game\\floor1.png");
    //doors
    ImageView northDoor = new ImageView("file:C:\\Users\\user\\Pictures\\game\\door_up.png");
    ImageView southDoor = new ImageView("file:C:\\Users\\user\\Pictures\\game\\door_down.png");
    ImageView eastDoor = new ImageView("file:C:\\Users\\user\\Pictures\\game\\door_right.png");
    ImageView westDoor = new ImageView("file:C:\\Users\\user\\Pictures\\game\\door_left.png");
    ImageView bossDoor = new ImageView("file:C:\\Users\\user\\Pictures\\game\\door_boss_closed.png");
    Image bossDoor_closed = new Image("file:C:\\Users\\user\\Pictures\\game\\door_boss_closed.png");
    Image bossDoor_open = new Image("file:C:\\Users\\user\\Pictures\\game\\door_boss_open.png");

    //inventory
    ImageView inventory;
    ImageView selected;
    int selectedX = 0;
    int selectedY = 0;
    //score
    ImageView scoreIMG = new ImageView("file:C:\\Users\\user\\Pictures\\game\\score.png");
    int Score = 1000;
    Label score;
    //hearts
    ImageView hearts;
    int hero_Health = 6;
    Image heart_0 = new Image("file:C:\\Users\\user\\Pictures\\game\\hearts\\heart_0.png");
    Image heart_05 = new Image("file:C:\\Users\\user\\Pictures\\game\\hearts\\heart_05.png");
    Image heart_1 = new Image("file:C:\\Users\\user\\Pictures\\game\\hearts\\heart_1.png");
    Image heart_15 = new Image("file:C:\\Users\\user\\Pictures\\game\\hearts\\heart_15.png");
    Image heart_2 = new Image("file:C:\\Users\\user\\Pictures\\game\\hearts\\heart_2.png");
    Image heart_25 = new Image("file:C:\\Users\\user\\Pictures\\game\\hearts\\heart_25.png");
    Image heart_3 = new Image("file:C:\\Users\\user\\Pictures\\game\\hearts\\heart_3.png");
    //items
    ArrayList<Images> images = new ArrayList<Images>();
    ImageView item = new ImageView("file:C:\\Users\\user\\Pictures\\game\\health potion.png");
    Image healthPotion = new Image("file:C:\\Users\\user\\Pictures\\game\\health potion.png");
    Image blank = new Image("file:C:\\Users\\user\\Pictures\\game\\blank.png");

    //sword hit boxes
    Rectangle sword_up = new Rectangle(0, 0, 25, 68);
    Rectangle sword_right = new Rectangle(0, 0, 85, 31);
    Rectangle sword_down = new Rectangle(0, 0, 26, 75);
    Rectangle sword_left = new Rectangle(0, 0, 84, 28);
    //stage walls
    Rectangle northWall = new Rectangle(0, 0, 1920, 2);
    Rectangle southWall = new Rectangle(0, 1100, 1920, 2);
    Rectangle eastWall = new Rectangle(0, 0, 2, 1200);
    Rectangle westWall = new Rectangle(1920, 0, 2, 1920);

    final Group group = new Group(background);
    ////
    //


    //
    private static final double W = 1960, H = 1200;

    @Override
    public void start(Stage stage) throws Exception {
        itemMaker();
        mapGenerator();
        scoreChanger();
        northDoor.setX(860 - 80.5);
        northDoor.setY(5);
        bossDoor.setX(860 - 80.5);
        bossDoor.setY(5);
        bossDoor.setVisible(false);
        southDoor.setX(860 - 80.5);
        southDoor.setY(1010);
        westDoor.setX(0);
        westDoor.setY(500);
        eastDoor.setX(1850);
        eastDoor.setY(500);
        group.getChildren().addAll(northDoor, southDoor, eastDoor, westDoor, bossDoor);
        //Score
        scoreIMG.setX(750);
        scoreIMG.setY(80);
        scoreIMG.setOpacity(0.3);
        //
        score = new Label();
        score.setLayoutX(910);
        score.setLayoutY(80);
        score.setText(String.valueOf(Score));
        score.setFont(Font.font("Upheaval", FontWeight.BOLD, 21));
        score.setTextFill(Color.WHITE);
        score.setOpacity(0.3);
        //
        hero = new ImageView("file:E\\\\game\\link_running_down_1.png");
        //hearts
        hearts = new ImageView("file:C:\\Users\\user\\Pictures\\game\\hearts\\heart_3.png");
        hearts.setLayoutX(0);
        hearts.setLayoutY(0);
        group.getChildren().addAll(hearts);
        HealthChecker();
        //items
        images.get(0).getImage().setVisible(true); //sets the item in the starting room to visible
        item.setImage(healthPotion);
        item.setLayoutX(800);
        item.setLayoutY(800);
        //inventory
        inventory = new ImageView("file:C:\\Users\\user\\Pictures\\game\\inventory.png");
        inventory.setTranslateX(752);
        inventory.setTranslateY(325);
        inventory.setVisible(false);
        selected = new ImageView("file:C:\\Users\\user\\Pictures\\game\\selected.png");
        selected.setLayoutX(752 + 39);
        selected.setLayoutY(325 + 39);
        selected.setVisible(false);

        //final Circle circle = new Circle(0, 0, 50, Color.FUCHSIA);
        //circle.setOpacity(0.7);
        //final Circle circle1 = new Circle(440, 220, 50, Color.PINK);
        start();
        //inventory
        sword_up.setOpacity(0);
        sword_right.setOpacity(0);
        sword_down.setOpacity(0);
        sword_left.setOpacity(0);
        group.getChildren().addAll(sword_up, sword_right, sword_down, sword_left);

        northWall.setOpacity(0);
        southWall.setOpacity(0);
        eastWall.setOpacity(0);
        westWall.setOpacity(0);
        group.getChildren().addAll(northWall, southWall, eastWall, westWall);
        hero.setLayoutX(300);
        hero.setLayoutY(300);


        ///
        //logo
        stage.getIcons().add(new Image("file:C:\\Users\\user\\Pictures\\game\\Master Sword In Pedastol.png"));
        ///
        info = new Label();
        info.setTranslateY(20);
        info.setTranslateX(20);
        touching = new Label();
        touching.setText("");
        touching.setTranslateY(20);
        touching.setTranslateX(130);
        group.getChildren().addAll(hero);
        group.getChildren().addAll(inventory);
        group.getChildren().addAll(selected);
        //group.getChildren().addAll(item);


        group.getChildren().addAll(touching);
        group.getChildren().addAll(info);
        group.getChildren().addAll(scoreIMG);
        group.getChildren().addAll(score);
        stage.setFullScreen(true);
        final Scene scene = new Scene(group, 1120, 700);

        moveHeroTo(W / 2, H / 2);


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        goNorth = true;
                        swordMover();
                        sound = "walking";
                        direction = "up";
                        attackDirection = "up";
                        AnimationManager();
                        soundManager();
                        Checkcollision();

                        break;
                    case DOWN:
                        goSouth = true;
                        swordMover();
                        sound = "walking";
                        direction = "down";
                        attackDirection = "down";
                        AnimationManager();
                        soundManager();
                        Checkcollision();

                        break;
                    case LEFT:
                        goWest = true;
                        swordMover();
                        sound = "walking";
                        direction = "left";
                        attackDirection = "left";
                        AnimationManager();
                        soundManager();
                        Checkcollision();

                        break;
                    case RIGHT:
                        goEast = true;
                        swordMover();
                        sound = "walking";
                        direction = "right";
                        attackDirection = "right";

                        AnimationManager();
                        soundManager();
                        Checkcollision();

                        break;
                    case SPACE:
                        AttackHandler();
                        sound = "sword";
                        soundManager();
                        break;
                    case TAB:
                        inventoryManager();
                        break;

                    case O:
                        hero_Health++;
                        if (hero_Health > 6) {
                            hero_Health = 6;
                        }
                        break;

                    case L:
                        hero_Health--;
                        if (hero_Health < 0) {
                            hero_Health = 0;
                        }
                        break;

                    case F:
                        stage.setFullScreen(true);
                        //inventory controls
                        break;

                    case W:
                        selected.setLayoutY(selected.getLayoutY() - 67);
                        if (selected.getLayoutY() <= 364) {
                            selected.setLayoutY(364);
                        }
                        break;
                    case A:
                        selected.setLayoutX(selected.getLayoutX() - 68);
                        if (selected.getLayoutX() <= 791) {
                            selected.setLayoutX(791);
                        }
                        break;

                    case S:
                        selected.setLayoutY(selected.getLayoutY() + 67);
                        if (selected.getLayoutY() >= 699) {
                            selected.setLayoutY(699);
                        }
                        break;

                    case D:
                        selected.setLayoutX(selected.getLayoutX() + 68);
                        if (selected.getLayoutX() >= 1063) {
                            selected.setLayoutX(1063);
                        }
                        break;

                    case E:
                        for (int i = 0; i < images.size(); i++) {
                            if (images.get(i).getImage().getBoundsInParent().intersects(hero.getBoundsInParent())) {


                            }
                        }
                        break;


                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        goNorth = false;walkSounds.stop();
                        l = 0;
                        break;
                    case DOWN:
                        goSouth = false;walkSounds.stop();
                        l = 0;
                        break;
                    case LEFT:
                        goWest = false;walkSounds.stop();
                        l = 0;
                        break;
                    case RIGHT:
                        goEast = false;walkSounds.stop();
                        l = 0;
                        break;
                    case SHIFT:
                        running = false;
                        break;
                }
            }
        });

        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;

                if (goNorth) dy -= 1;
                if (goSouth) dy += 1;
                if (goEast) dx += 1;
                if (goWest) dx -= 1;

                moveHeroBy(dx, dy);
            }
        };
        timer.start();
        swordMover();
        stage.setScene(scene);
        stage.show();
    }

    private void moveHeroBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = hero.getBoundsInLocal().getWidth() / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        double x = cx + hero.getLayoutX() + dx;
        double y = cy + hero.getLayoutY() + dy;

        moveHeroTo(x, y);
    }

    private void moveHeroTo(double x, double y) {
        final double cx = hero.getBoundsInLocal().getWidth() / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
                x + cx <= W &&
                y - cy >= 0 &&
                y + cy <= H) {
            hero.relocate(x - cx, y - cy);
        }
    }

    private void inventoryManager() {
        if (!inventory.isVisible()) {
            inventory.setVisible(true);
            selected.setVisible(true);
        } else {
            inventory.setVisible(false);
            selected.setVisible(false);
            selected.setLayoutX(752 + 39);
            selected.setLayoutY(325 + 39);
        }
    }

    private void HealthChecker() {
        if (k == 0) {
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
                @Override

                public void handle(ActionEvent event) {
                    k = 1;
                    switch (hero_Health) {
                        case 0:
                            hearts.setImage(heart_0);
                            System.exit(0);
                            break;
                        case 1:
                            hearts.setImage(heart_05);
                            break;
                        case 2:
                            hearts.setImage(heart_1);
                            break;
                        case 3:
                            hearts.setImage(heart_15);
                            break;
                        case 4:
                            hearts.setImage(heart_2);
                            break;
                        case 5:
                            hearts.setImage(heart_25);
                            break;
                        case 6:
                            hearts.setImage(heart_3);
                            break;
                    }
                }
            }));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
        }
    }

    private void AttackHandler() {
        //fix up and right attacking
        switch (attackDirection) {
            case "up":
                hero.setImage(link_sword_up);
                hero.setLayoutY(hero.getLayoutY() - 100);
                sword_up.setLayoutY(sword_up.getLayoutY() - 100);

                break;
            case "right":
                hero.setImage(link_sword_right);

                break;
            case "down":
                hero.setImage(link_sword_down);

                break;
            case "left":
                hero.setImage(link_sword_left);
                hero.setLayoutX(hero.getLayoutX() - 100);
                sword_left.setLayoutX(sword_left.getLayoutX() - 100);

                break;
        }
        if (j == 0) {
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override

                public void handle(ActionEvent event) {
                    j = 1;
                    switch (attackDirection) {
                        case "up":
                            if (hero.getImage() == link_sword_up) {
                                hero.setImage(link_running_up_1);
                                hero.setLayoutY(hero.getLayoutY() + 100);
                            }
                            break;
                        case "right":
                            if (hero.getImage() == link_sword_right) {
                                hero.setImage(link_running_right_1);
                            }
                            break;
                        case "down":
                            if (hero.getImage() == link_sword_down) {
                                hero.setImage(link_running_down_1);
                            }
                            break;
                        case "left":
                            if (hero.getImage() == link_sword_left) {
                                hero.setImage(link_running_left_1);
                                hero.setLayoutX(hero.getLayoutX() + 100);
                            }
                            break;
                    }
                }
            }));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
        }
    }

    private void AnimationManager() {
        if (i == 0) {
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.35), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    i = 1;
                    switch (direction) {
                        case "up":
                            if (animation.equals("b")) {
                                hero.setImage(link_running_up_1);
                                animation = "a";
                            } else {
                                hero.setImage(link_running_up_2);
                                animation = "b";
                            }
                            break;
                        case "right":
                            if (animation.equals("b")) {
                                hero.setImage(link_running_right_1);
                                animation = "a";
                            } else {
                                hero.setImage(link_running_right_2);
                                animation = "b";
                            }
                            break;
                        case "down":
                            if (animation.equals("b")) {
                                hero.setImage(link_running_down_1);
                                animation = "a";
                            } else {
                                hero.setImage(link_running_down_2);
                                animation = "b";
                            }
                            break;
                        case "left":
                            if (animation.equals("b")) {
                                hero.setImage(link_running_left_1);
                                animation = "a";
                            } else {
                                hero.setImage(link_running_left_2);
                                animation = "b";
                            }
                            break;
                    }
                    direction = "";
                }
            }));

            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
        }
    }

    private void Checkcollision() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                //pushes back from big box around the wall
                if (hero.getBoundsInParent().intersects(northWall.getBoundsInParent())) {
                    hero.setLayoutY(hero.getLayoutY() + KEYBOARD_MOVEMENT_DELTA);
                    sword_up.setLayoutY(sword_up.getLayoutY() + KEYBOARD_MOVEMENT_DELTA);
                    sword_down.setLayoutY(sword_down.getLayoutY() + KEYBOARD_MOVEMENT_DELTA);
                    sword_left.setLayoutY(sword_left.getLayoutY() + KEYBOARD_MOVEMENT_DELTA);
                    sword_right.setLayoutY(sword_right.getLayoutY() + KEYBOARD_MOVEMENT_DELTA);

                }
                if (hero.getBoundsInParent().intersects(southWall.getBoundsInParent())) {
                    hero.setLayoutY(hero.getLayoutY() - KEYBOARD_MOVEMENT_DELTA);
                    sword_up.setLayoutY(sword_up.getLayoutY() - KEYBOARD_MOVEMENT_DELTA);
                    sword_down.setLayoutY(sword_down.getLayoutY() - KEYBOARD_MOVEMENT_DELTA);
                    sword_left.setLayoutY(sword_left.getLayoutY() - KEYBOARD_MOVEMENT_DELTA);
                    sword_right.setLayoutY(sword_right.getLayoutY() - KEYBOARD_MOVEMENT_DELTA);
                }
                if (hero.getBoundsInParent().intersects(eastWall.getBoundsInParent())) {
                    hero.setLayoutX(hero.getLayoutX() + KEYBOARD_MOVEMENT_DELTA);
                    sword_up.setLayoutX(sword_up.getLayoutX() + KEYBOARD_MOVEMENT_DELTA);
                    sword_down.setLayoutX(sword_down.getLayoutX() + KEYBOARD_MOVEMENT_DELTA);
                    sword_left.setLayoutX(sword_left.getLayoutX() + KEYBOARD_MOVEMENT_DELTA);
                    sword_right.setLayoutX(sword_right.getLayoutX() + KEYBOARD_MOVEMENT_DELTA);
                }
                if (hero.getBoundsInParent().intersects(westWall.getBoundsInParent())) {
                    hero.setLayoutX(hero.getLayoutX() - KEYBOARD_MOVEMENT_DELTA);
                    sword_up.setLayoutX(sword_up.getLayoutX() - KEYBOARD_MOVEMENT_DELTA);
                    sword_down.setLayoutX(sword_down.getLayoutX() - KEYBOARD_MOVEMENT_DELTA);
                    sword_left.setLayoutX(sword_left.getLayoutX() - KEYBOARD_MOVEMENT_DELTA);
                    sword_right.setLayoutX(sword_right.getLayoutX() - KEYBOARD_MOVEMENT_DELTA);
                }

            }

        }.start();


    }

    private void soundManager() {
        switch (sound) {
            case "walking":
                if (l == 0) {
                    walkSounds = new Timeline(new KeyFrame(Duration.seconds(0.4), new EventHandler<ActionEvent>() {
                        @Override

                        public void handle(ActionEvent event) {
                            l = 1;
                            File f = new File("C:\\Users\\user\\Pictures\\game\\sound\\walkingSound.wav");
                            Media media = new Media(f.toURI().toString());
                            mplayer = new MediaPlayer(media);
                            mplayer.setVolume(0.05);
                            mplayer.setAutoPlay(true);
                        }
                    }));
                    walkSounds.setCycleCount(Timeline.INDEFINITE);
                    walkSounds.play();

                }


                break;
            case "sword":

                File f = new File("C:\\Users\\user\\Pictures\\game\\sound\\sword_miss.mp3");
                Media media = new Media(f.toURI().toString());
                MediaPlayer mplayer2 = new MediaPlayer(media);
                mplayer2.setVolume(0.05);
                mplayer2.play();

                break;


        }
    }

    private void scoreChanger() {
        if (q == 0) {
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override

                public void handle(ActionEvent event) {
                    q = 1;
                    Score--;
                    score.setText(String.valueOf(Score));
                }
            }));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
        }


    }

    private void itemMaker() {

        for (int m = 0; m < 6; m++) {
            images.add(new Images());

        }
        for (Images image : images) {
            (image.getImage()).setLayoutY(image.getLayoutY());
            (image.getImage()).setLayoutX(image.getLayoutX());
            image.getImage().setVisible(false);
            group.getChildren().add((image.getImage()));
        }


    }

    private void itemChanger() {
        int currentFloorSUB = Integer.parseInt(currentFloor.substring(6));
        currentFloorSUB--;
        for (Images image : images) {
            image.getImage().setVisible(false);
        }

        images.get(currentFloorSUB).getImage().setVisible(true);

    }


    private void swordMover() {

        sword_up.setLayoutX(hero.getLayoutX() + 46);
        sword_up.setLayoutY(hero.getLayoutY() + 22);
        //
        sword_down.setLayoutX(hero.getLayoutX() + 67);
        sword_down.setLayoutY(hero.getLayoutY() + 126);
        //
        sword_left.setLayoutX(hero.getLayoutX() + 15);
        sword_left.setLayoutY(hero.getLayoutY() + 78);
        //
        sword_right.setLayoutX(hero.getLayoutX() + 142);
        sword_right.setLayoutY(hero.getLayoutY() + 75);

    }

    private void mapGenerator() {
        if (h == 0) {
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
                @Override

                public void handle(ActionEvent event) {
                    h = 1;

                    switch (currentFloor) {
                        case "floor 1":
                            northDoor.setVisible(true);
                            bossDoor.setVisible(false);
                            if (hero.getBoundsInParent().intersects(northDoor.getBoundsInParent())) {
                                currentFloor = "floor 2";
                                background.setImage(floor2);
                                hero.setLayoutY(810);
                                hero.setLayoutX(810);
                                swordMover();
                                itemChanger();
                            }
                            eastDoor.setVisible(true);
                            bossDoor.setVisible(false);

                            if (hero.getBoundsInParent().intersects(eastDoor.getBoundsInParent())) {
                                currentFloor = "floor 3";
                                background.setImage(floor3);
                                hero.setLayoutX(150);
                                hero.setLayoutY(430);
                                swordMover();
                                itemChanger();
                            }
                            southDoor.setVisible(true);
                            bossDoor.setVisible(false);

                            if (hero.getBoundsInParent().intersects(southDoor.getBoundsInParent())) {
                                currentFloor = "floor 4";
                                background.setImage(floor4);
                                hero.setLayoutX(810);
                                hero.setLayoutY(130);
                                swordMover();
                                itemChanger();

                            }
                            westDoor.setVisible(true);
                            bossDoor.setVisible(false);

                            if (hero.getBoundsInParent().intersects(westDoor.getBoundsInParent())) {
                                currentFloor = "floor 5";
                                background.setImage(floor5);
                                hero.setLayoutX(1600);
                                hero.setLayoutY(430);
                                swordMover();
                                itemChanger();
                            }
                            break;
                        case "floor 2":
                            northDoor.setVisible(false);
                            eastDoor.setVisible(false);
                            southDoor.setVisible(true);
                            bossDoor.setVisible(false);

                            if (hero.getBoundsInParent().intersects(southDoor.getBoundsInParent())) {
                                currentFloor = "floor 1";
                                background.setImage(floor1);
                                hero.setLayoutX(810);
                                hero.setLayoutY(130);
                                swordMover();
                                itemChanger();
                            }
                            westDoor.setVisible(false);
                            break;
                        case "floor 3":
                            northDoor.setVisible(false);
                            eastDoor.setVisible(true);
                            southDoor.setVisible(false);
                            westDoor.setVisible(true);
                            bossDoor.setVisible(false);

                            if (hero.getBoundsInParent().intersects(westDoor.getBoundsInParent())) {
                                currentFloor = "floor 1";
                                background.setImage(floor1);
                                hero.setLayoutX(1600);
                                hero.setLayoutY(430);
                                swordMover();
                                itemChanger();
                            }
                            if (hero.getBoundsInParent().intersects(eastDoor.getBoundsInParent())) {
                                currentFloor = "floor 6";
                                background.setImage(floor6);
                                hero.setLayoutX(150);
                                hero.setLayoutY(430);
                                swordMover();
                                itemChanger();
                            }
                            break;
                        case "floor 4":
                            northDoor.setVisible(true);
                            bossDoor.setVisible(false);

                            if (hero.getBoundsInParent().intersects(northDoor.getBoundsInParent())) {
                                currentFloor = "floor 1";
                                background.setImage(floor1);
                                hero.setLayoutY(810);
                                hero.setLayoutX(810);
                                swordMover();
                                itemChanger();
                            }
                            eastDoor.setVisible(false);
                            southDoor.setVisible(false);
                            westDoor.setVisible(false);
                            break;
                        case "floor 5":
                            northDoor.setVisible(false);
                            eastDoor.setVisible(true);
                            bossDoor.setVisible(false);

                            if (hero.getBoundsInParent().intersects(eastDoor.getBoundsInParent())) {
                                currentFloor = "floor 1";
                                background.setImage(floor1);
                                hero.setLayoutX(150);
                                hero.setLayoutY(430);
                                swordMover();
                                itemChanger();
                            }
                            southDoor.setVisible(false);
                            westDoor.setVisible(false);
                            break;
                        case "floor 6":
                            northDoor.setVisible(false);
                            bossDoor.setVisible(true);
                            eastDoor.setVisible(false);
                            southDoor.setVisible(false);
                            westDoor.setVisible(true);
                            if (hero.getBoundsInParent().intersects(westDoor.getBoundsInParent())) {
                                currentFloor = "floor 3";
                                background.setImage(floor3);
                                hero.setLayoutX(1600);
                                hero.setLayoutY(430);
                                swordMover();
                                itemChanger();
                            }
                            if (hero.getBoundsInParent().intersects(bossDoor.getBoundsInParent()) && bossDoor.getImage() == bossDoor_open) {
                                currentFloor = "floor 7";
                                background.setImage(floor7);
                                hero.setLayoutY(810);
                                hero.setLayoutX(810);
                                swordMover();
                                itemChanger();
                            }
                            break;
                        case "floor 7":
                            northDoor.setVisible(false);
                            bossDoor.setVisible(false);
                            eastDoor.setVisible(false);
                            southDoor.setVisible(true);
                            westDoor.setVisible(false);
                            for (Images image : images) {
                                image.getImage().setVisible(false);
                            }
                            if (hero.getBoundsInParent().intersects(southDoor.getBoundsInParent())) {
                                currentFloor = "floor 6";
                                background.setImage(floor6);
                                hero.setLayoutX(810);
                                hero.setLayoutY(180);


                                swordMover();
                                itemChanger();
                            }


                            break;
                        //boss room

                    }
                }
            }));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
        }
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            timePassed++;
            //System.out.println("time passed: " + timePassed);

        }
    };

    public void start() {
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
