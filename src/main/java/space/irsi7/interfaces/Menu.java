package space.irsi7.interfaces;

public interface Menu {
    void printMenu();
    int readChoice();
    boolean validateChoice(String choice);
}
