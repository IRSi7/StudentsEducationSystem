package space.irsi7.controllers;

import space.irsi7.enums.MenuEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultipleChoiceMenu {


    private final MenuEnum[] listChoice;
    MultipleChoiceMenu(MenuEnum[] list){
        this.listChoice = list;
    }

    public int chooseOne() {
        int choosen = -1;
        while(choosen == -1){
            printMenu();
            choosen = readChoice();
        }
        return choosen;
    }

    private void printMenu(){
        for( int i = 1; i <= listChoice.length; i++){
            System.out.println(i + "). " + listChoice[i - 1].getMessage());
        }
    }

    private int readChoice() {
        printInstruction();
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        try {
            if(validateChoice(choice)){
                return  listChoice[Integer.parseInt(choice) - 1].ordinal();
            } else {
                return -1;
            }
        } catch (NumberFormatException nE){
            return -1;
        }
    }

    private boolean validateChoice(String choice) {
        int intChoice = Integer.parseInt(choice);
        return intChoice > 0 & intChoice <= listChoice.length;
    }

    private void printInstruction(){
        System.out.println("Введите число от 1 до " + listChoice.length);
    }
}
