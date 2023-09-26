package space.irsi7;

import space.irsi7.controllers.StudController;
import space.irsi7.exceptions.IllegalInitialDataException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IllegalInitialDataException, IOException {
        //initTest();
        while(StudController.start());
    }
}