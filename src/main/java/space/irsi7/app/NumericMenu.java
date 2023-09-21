package space.irsi7.app;

import space.irsi7.interfaces.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class NumericMenu implements Menu {
    private final ArrayList<String> listChoice;
    NumericMenu(ArrayList<String> list){
        this.listChoice = list;
    }
    @Override
    public void printMenu() {
        for( int i = 1; i <= listChoice.size(); i++){
            System.out.println(i + "). " + listChoice.get(i - 1));
        }
        while(readChoice() == 0);
    }

    @Override
    public int readChoice() {
        printInstruction();
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        try {
            if(validateChoice(choice)){
                return  Integer.parseInt(choice);
            } else {
                return 0;
            }
        } catch (NumberFormatException nE){
            return 0;
        }
    }

    @Override
    public boolean validateChoice(String choice) {
        int intChoice = Integer.parseInt(choice);
        return intChoice > 0 & intChoice <= listChoice.size();
    }

    private void printInstruction(){
        System.out.println("Введите число от 1 до " + listChoice.size());
    }
}
