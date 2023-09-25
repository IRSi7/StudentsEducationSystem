package space.irsi7;

import space.irsi7.app.StudController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IllegalInitialDataException, IOException {
        //initTest();
        while(StudController.start());
    }
}