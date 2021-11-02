package com.company;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.TimerTask;

public class Timer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public static class Reminder {
        Timer timer;

        public Reminder(int seconds) {
            timer = new Timer();
            timer.schedule(new RemindTask(), seconds * 1000);
        }

        class RemindTask extends TimerTask {
            public void run() {
                System.out.println("Time's up!");
                try {
                    timer.stop(); //Terminate the timer thread
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String args[]) {
            new Reminder(5);
            System.out.println("Task scheduled.");
        }
    }

    private void schedule(Reminder.RemindTask remindTask, int i) {
    }
}
