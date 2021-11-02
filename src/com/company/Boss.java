package com.company;

import animatefx.animation.Pulse;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Boss {
    Timeline Bossroomtimeline;
    Timeline Bossroomtimeline2;
    Random rand = new Random();
    Boolean activeBoss;
    String name;
    ImageView imageViewIMG;
    ImageView bossHealthBar;
    Rectangle bossHealthBarREC;
    String path;
    Double health;
    Double maxHealth;
    int rectWidth = 658;
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    //
    private static MediaPlayer MEDIAWalking;
    private static MediaPlayer MEDIABackground;
    private static MediaPlayer MEDIABoss;
    int musicVolume = 1;//the representation of this variable breaks if non global
    int walking = 0;
    int l = 0;
    String sound;
    Timeline walkSounds;

    //
    public Boss(Group group, ArrayList<Fireballs> fireballs, ImageView hero, int hero_Health,
                ImageView chest, ArrayList<Boss> bossARR, Timeline fireballDetector) {
        sound = "boss";
        soundManager();//boss music
        activeBoss = true;
        switch (rand.nextInt(1)) {

            case 0:
                this.path = "file:src\\game\\Wizard.png";
                this.imageViewIMG = new ImageView(path);
                this.name = path.substring(path.length() - 10, path.length() - 4);
                this.health = 100.0;
                this.maxHealth = 100.0;


                break;
            case 1:

                break;
        }
        if (imageViewIMG != null) {
            imageViewIMG.relocate((int) (width / 2) - (imageViewIMG.getBoundsInLocal().getWidth() / 2), (int) (height / 2) - (imageViewIMG.getBoundsInLocal().getHeight() / 2));
        }


        this.bossHealthBar = new ImageView("file:src\\game\\HealthBarImage.png");
        bossHealthBar.relocate(500, 50);
        //bossHealthBar.setVisible(false);
        this.bossHealthBarREC = new Rectangle(616, 87, rectWidth, 48);
        bossHealthBarREC.setFill(Color.RED);
        //bossHealthBarREC.setVisible(false);
        group.getChildren().addAll(imageViewIMG, bossHealthBar, bossHealthBarREC);
        control(group, fireballs, hero, hero_Health, chest,bossARR,fireballDetector);
    }

    private void control(Group group, ArrayList<Fireballs> fireballs, ImageView hero, int hero_Health, ImageView chest, ArrayList<Boss> bossARR, Timeline fireballDetector) {
        switch (name.toLowerCase()) {
            case "wizard":
                wizardPhaseOne(group, fireballs, hero, hero_Health, chest,bossARR,fireballDetector);
                break;
            case "":
                System.out.println("crease in space-time fabric");
                break;

        }
    }

    private void wizardPhaseOne(Group group, ArrayList<Fireballs> fireballs, ImageView hero, int hero_Health, ImageView chest, ArrayList<Boss> bossARR, Timeline fireballDetector) {
        Bossroomtimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {//checks the time every 5 seconds to see
            try {
                new Pulse(imageViewIMG).play();//animatefx effect
                TranslateTransition translateFireball = new TranslateTransition();
                fireballs.add(new Fireballs());//new fireball
                fireballs.get(0).getImage().relocate(imageViewIMG.getLayoutX(), imageViewIMG.getLayoutY());//puts it in the boss's hand
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
                if (health / maxHealth <= 0.7) {
                    wizardPhaseTwo(group, fireballs, hero, chest,bossARR,fireballDetector);
                }
            } catch (Exception e) {
                //System.out.println("Boss room phase 1 error");
            }
        }));
        Bossroomtimeline.setCycleCount(Timeline.INDEFINITE);
        Bossroomtimeline.play();
    }

    private void wizardPhaseTwo(Group group, ArrayList<Fireballs> fireballs, ImageView hero, ImageView chest, ArrayList<Boss> bossARR, Timeline fireballDetector) {
        Bossroomtimeline.stop();

        System.out.println("phase 2");
        TranslateTransition[] fireBlast = new TranslateTransition[3];//
        for (int m = 0; m < 3; m++) {
            fireBlast[m] = new TranslateTransition();
        }
        TranslateTransition[] fireCircle = new TranslateTransition[8];//
        for (int m = 0; m < 8; m++) {
            fireCircle[m] = new TranslateTransition();
        }

        Bossroomtimeline2 = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            int constant = (int) ((hero.getLayoutY() - imageViewIMG.getLayoutY()) / (hero.getLayoutX() - imageViewIMG.getLayoutX()));//gradient between the two

            if (rand.nextInt(5) < 3) {//chooses between two attacks. (0,1,2) 3 fireball blast. (3,4) circle of fireballs.
                System.out.println("attack 1");
                for (int m = 0; m < 3; m++) {
                    fireballs.add(new Fireballs());
                    fireballs.get(m).getImage().relocate(imageViewIMG.getLayoutX(), imageViewIMG.getLayoutY());//sets the starting point to the bosses hand.
                    group.getChildren().add(fireballs.get(m).getImage());
                    //
                    fireBlast[m].setNode(fireballs.get(m).getImage());
                    fireBlast[m].setDuration(Duration.seconds(2));
                    //determines if the player is above or below the boss
                    if ((hero.getLayoutX() > imageViewIMG.getLayoutX() && (constant > -0.5 && constant < 0.5)) || hero.getLayoutX() < imageViewIMG.getLayoutX() && (constant > -0.7 && constant < 0.6)) {//left or right of the boss
                        //positioning for the fireBlast attack
                        fireBlast[m].setToX(-fireballs.get(m).getImage().getLayoutX() + hero.getLayoutX() + (hero.getImage().getWidth() / 2));
                        fireBlast[m].setToY(-fireballs.get(m).getImage().getLayoutY() + hero.getLayoutY() + (hero.getImage().getHeight() / 2) + (((Math.tan(0.35) * Math.abs(hero.getLayoutX() - imageViewIMG.getLayoutX()))) * (m - 1)));
                    } else {//left or right of the boss,    same logic as the enemy animator procedure.
                        //positioning for the fireBlast attack
                        fireBlast[m].setToX(-fireballs.get(m).getImage().getLayoutX() + hero.getLayoutX() + (hero.getImage().getWidth() / 2) + (((Math.tan(0.35) * Math.abs(hero.getLayoutY() - imageViewIMG.getLayoutY()))) * (m - 1)));
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
                    fireballs.get(m).getImage().relocate(imageViewIMG.getLayoutX() + 75, imageViewIMG.getLayoutY() + 75);
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
                    new Pulse(imageViewIMG).play();
                    fireCircle[m].play();

                }
                sound = "fire_ball";
                soundManager();
            }


            try {
            } catch (Exception e) {
                //System.out.println("Error removing fireballs, phase 2");
            }
            //
            if (health == 0) {
                try {
                    Bossroomtimeline.stop();
                    Bossroomtimeline2.stop();
                } catch (Exception e) {

                }
                System.out.println(name + " Defeated");
                try {
                    fireballDetector.stop();
                }catch (Exception e){
                }
                MEDIABoss.stop();
                imageViewIMG.setVisible(false);
                bossHealthBarREC.setVisible(false);
                bossHealthBar.setVisible(false);
                chest.setVisible(true);
                bossARR.clear();

            }
            //
        }));
        Bossroomtimeline2.setCycleCount(Timeline.INDEFINITE);
        Bossroomtimeline2.play();
    }

    public ImageView getImageViewIMG() {
        return imageViewIMG;
    }

    public ImageView getBossHealthBar() {
        return bossHealthBar;
    }

    public Rectangle getBossHealthBarREC() {
        return bossHealthBarREC;
    }

    public Double getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public void setHealth(Double health) {
        if (health < 0) {
            health = 0.0;
        }
        double percent = health / maxHealth;
        bossHealthBarREC.setWidth(rectWidth * percent);
        this.health = health;

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

}

