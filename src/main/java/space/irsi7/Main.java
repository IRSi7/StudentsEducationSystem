package space.irsi7;

import space.irsi7.controllers.StudController;

public class Main {
    public static void main(String[] args) {
        boolean isStarted = true;
        while(isStarted){
            isStarted = StudController.start();
        }
    }
}