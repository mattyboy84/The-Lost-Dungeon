package com.company;

public class CoolThreads  extends Thread {
    public void run() {
        System.out.println("Thread is running");
        CoolThreads Thread1 = new CoolThreads();
        Thread1.start();
    }

}
