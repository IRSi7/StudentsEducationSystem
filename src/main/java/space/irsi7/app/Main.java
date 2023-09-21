package space.irsi7.app;

import space.irsi7.app.NumericMenu;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> mainMenu = new  ArrayList<> (List.of(
                "Добавить студента",
                "Отчислить студента",
                "Проставить оценку",
                "Рассчитать время до выпуска",
                "Получить отчёт об успеваемости",
                "Рассчитать возможность отчисления",
                "Вывести список студентов"));
        NumericMenu numericMenu = new NumericMenu(mainMenu);
        numericMenu.printMenu();
    }
}