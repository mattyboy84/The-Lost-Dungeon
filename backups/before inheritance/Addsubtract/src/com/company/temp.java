package com.company;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class temp {
/*
if (k == 0) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        if (timePassed3 >= 6 && boss.isVisible()) {
                            k = 1;
                            TranslateTransition translateFireball = new TranslateTransition();
                            fireballs.add(new Fireballs());
                            fireballs.get(0).getImage().setLayoutX(boss.getLayoutX());
                            fireballs.get(0).getImage().setLayoutY(boss.getLayoutY());
                            group.getChildren().add(fireballs.get(0).getImage());

                            translateFireball.setNode(fireballs.get(0).getImage());
                            translateFireball.setDuration(Duration.seconds(2));
                            translateFireball.setToX(-fireballs.get(0).getImage().getLayoutX() + hero.getLayoutX() + (hero.getImage().getWidth() / 2));
                            translateFireball.setToY(-fireballs.get(0).getImage().getLayoutY() + hero.getLayoutY() + (hero.getImage().getHeight() / 2));
                            translateFireball.setInterpolator(Interpolator.LINEAR);
                            translateFireball.play();
                            timePassed3 = 0;
                            timepassed4 = 0;
                        }
                        if (hero.getBoundsInParent().intersects(fireballs.get(0).getImage().getBoundsInParent())) {//hit the target
                            hero_Health--;
                            healthChecker();
                            fireballs.get(0).getImage().setVisible(false);
                            fireballs.remove(0);
                        }
                        if (timepassed4 == 2) { //missed the target
                            fireballs.get(0).getImage().setVisible(false);
                            fireballs.remove(0);
                        }
                    } catch (Exception e) {
                    }
                }
            }));
 */
}
