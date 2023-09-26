package space.irsi7.controllers;

import space.irsi7.enums.MenuEnum;
import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.dao.YamlDAO;
import space.irsi7.models.Student;
import space.irsi7.repository.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudController {

    static Repository repository;

    static {
        try {
            repository = new Repository();
        } catch (IllegalInitialDataException e) {
            throw new RuntimeException(e);
        }
    }

    static Scanner in = new Scanner(System.in);

    public static boolean start() {
        MultipleChoiceMenu multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.MAIN_ADD, MenuEnum.MAIN_REMOVE, MenuEnum.MAIN_RATE, MenuEnum.MAIN_TIME_LEFT,
                MenuEnum.MAIN_DROP_CHANCE, MenuEnum.MAIN_REPORT_ONE, MenuEnum.MAIN_REPORT_ALL
        });

        int choice = multipleChoiceMenu.chooseOne();
        System.out.println("Успешно");
        if(choice == MenuEnum.MAIN_ADD.ordinal()){
            add();
        }
        if(choice == MenuEnum.MAIN_REMOVE.ordinal()){
            remove();
        }
        if(choice == MenuEnum.MAIN_RATE.ordinal()){
            rate();
        }
        if(choice == MenuEnum.MAIN_TIME_LEFT.ordinal()){
            time();
        }
        if(choice == MenuEnum.MAIN_DROP_CHANCE.ordinal()){
            possibility();
        }
        if(choice == MenuEnum.MAIN_REPORT_ONE.ordinal()){
            report();
        }
        if(choice == MenuEnum.MAIN_REPORT_ALL.ordinal()){
            fullReport();
        }
        return restart();
    }

    public static void add(){
        String name;
        int eduId;
        System.out.println("Введите Имя и Фамилию студента");
        name = in.nextLine();
        System.out.println("Введите порядковый номер учебной программы");
        eduId = in.nextInt();

        repository.addStudent(new Student(name, eduId));
    }

    public static void remove(){
        int studentId;
        System.out.println("Введите порядковый номер студента");
        studentId = in.nextInt();
        repository.removeStudent(studentId - 1);
    }

    public static void rate(){
        int studentId;
        int mark;
        System.out.println("Введите порядковый номер студента");
        studentId = in.nextInt();
        System.out.println("Введите оценку за последний тест");
        mark = in.nextInt();

        repository.rateStudent(studentId - 1, mark);
    }

    public static void time(){
        int studentId;
        int answer;
        System.out.println("Введите порядковый номер студента");
        studentId = in.nextInt();
        answer = repository.getEduTimeLeft(studentId - 1);
        if (answer != -1) {
            System.out.println("Выбранному студенту осталось учиться " + answer + " дней");
        } else {
            System.out.println("Error in data");
        }
    }

    public static void report(){
        int studentId;
        System.out.println("Введите порядковый номер студента");
        studentId = in.nextInt();
        System.out.println(repository.getReportStudent(studentId - 1));
    }

    public static void possibility(){
        int studentId;
        System.out.println("Введите ID студента");
        studentId = in.nextInt();
        if (repository.getDropChance(repository.getGPA(studentId - 1))) {
            System.out.println("Низкая вероятность быть отчисленным");
        } else {
            System.out.println("Высокая вероятность быть отчисленным");
        }
    }

    public static void fullReport(){
        MultipleChoiceMenu multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.SORT_ID, MenuEnum.SORT_NAME, MenuEnum.SORT_TESTS_PASSED,
                MenuEnum.SORT_DAYS_LEFT, MenuEnum.SORT_GPA
        });

        int sort = multipleChoiceMenu.chooseOne();

        multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.ORDER_DIRECT, MenuEnum.ORDER_REVERSED
        });
        int order = multipleChoiceMenu.chooseOne();

        multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.FILTER_NO, MenuEnum.FILTER_LOW, MenuEnum.FILTER_HIGH
        });
        int filter = multipleChoiceMenu.chooseOne();

        multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.WRITE_CONSOLE, MenuEnum.WRITE_TXT
        });
        int write = multipleChoiceMenu.chooseOne();

        if(write == MenuEnum.WRITE_CONSOLE.ordinal()) {
            ArrayList<String> test =   repository.getAllReport(sort, order, filter);
            repository.getAllReport(sort, order, filter).forEach(System.out::println);
        }
        if(write == MenuEnum.WRITE_TXT.ordinal()) {
            writeToTxt(repository.getAllReport(sort, order, filter));
        }
    }

    public static boolean restart() {
        System.out.println("Хотите продолжить?");
        MultipleChoiceMenu yesNo = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.YES, MenuEnum.NO
        });
        return yesNo.chooseOne() == MenuEnum.YES.ordinal();
    }

    public static void writeToTxt(ArrayList<String> strings){
        try {
            File file = new File("output.txt");
            PrintStream stream = new PrintStream(file);
            System.setOut(stream);
            strings.forEach(System.out::println);
            PrintStream consoleStream = new PrintStream(
                    new FileOutputStream(FileDescriptor.out));
            System.setOut(consoleStream);
            stream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл для записи не найден");
            throw new RuntimeException(e);
        }
    }

    public static void initTest () throws IOException {
//        String path = "C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\students.yaml";
//        YamlDAO yamlDAO = new YamlDAO();
//        enum Paths {
//            STUDENTS("C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\students.yaml"),
//            EDUPLANS("\"C:\\\\Users\\\\rsivanov\\\\IdeaProjects\\\\StudentsEducationSystem\\\\src\\\\main\\\\java\\\\space\\\\irsi7\\\\data\\\\eduplans.yaml\""),
//            THEMES("\"C:\\\\Users\\\\rsivanov\\\\IdeaProjects\\\\StudentsEducationSystem\\\\src\\\\main\\\\java\\\\space\\\\irsi7\\\\data\\\\themes.yaml\"");
//
//            private final String path;
//
//            Paths(String path) {
//                this.path = path;
//            }
//
//            public String getPath() {
//                return path;
//            }
//
//        }
//        ArrayList<Integer> marks = new ArrayList<Integer>() {
//            {
//                add(4);
//                add(5);
//                add(5);
//            }
//        };
//        ArrayList<Student> studentArrayList = new ArrayList<>() {
//            {
//                add(new Student("Roman Ivanov", 1, marks));
//                add(new Student("Sasha Zaycev", 2, marks));
//            }
//        };
//        yamlDAO.writeYAML(studentArrayList, Paths.STUDENTS.path);
    }
}
