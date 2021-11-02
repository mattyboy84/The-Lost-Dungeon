package com.company;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class temp {
    /*
    private void attackingEnemies() {
        try {
            int currentFloorSUB = Integer.parseInt(currentFloor.substring(6));
            currentFloorSUB--;
            if (usedSword.getBoundsInParent().intersects(enemies.get(currentFloorSUB).getImage().getBoundsInParent()) && enemies.get(currentFloorSUB).getImage().isVisible() && enemies.get(currentFloorSUB).getImage().getOpacity() > 0 && timePassed > 0.5) {
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
        } catch (Exception e) {
            sound = "swordMiss";
            soundManager();
        }
    }

     */
}
