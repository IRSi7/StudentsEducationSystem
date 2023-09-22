package space.irsi7.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultipleChoiceMenu {
    private final ArrayList<String> listChoice;
    MultipleChoiceMenu(List<String> list){
        this.listChoice = new ArrayList<>(list);
    }

    public int chooseOne() {
        int choosen = 0;
        while(choosen == 0){
            printMenu();
            choosen = readChoice();
        }
        return choosen;
    }

    private void printMenu(){
        for( int i = 1; i <= listChoice.size(); i++){
            System.out.println(i + "). " + listChoice.get(i - 1));
        }
    }

    private int readChoice() {
        printInstruction();
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        try {
            if(validateChoice(choice)){
                return  Integer.parseInt(choice);
            } else {
                return -0;
            }
        } catch (NumberFormatException nE){
            return 0;
        }
    }

    private boolean validateChoice(String choice) {
        int intChoice = Integer.parseInt(choice);
        return intChoice > 0 & intChoice <= listChoice.size();
    }

    private void printInstruction(){
        System.out.println("Введите число от 1 до " + listChoice.size());
    }
}
