package space.irsi7.app;

import space.irsi7.IllegalInitialDataException;
import space.irsi7.dao.YamlDAO;
import space.irsi7.models.Student;
import space.irsi7.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IllegalInitialDataException {
        //initTest();
        Repository repository = new Repository();
        Scanner in = new Scanner(System.in);
        MultipleChoiceMenu multipleChoiceMenu = new MultipleChoiceMenu(MenuS.MAIN.elements);
        switch (multipleChoiceMenu.chooseOne()){
            case(1):{
                String name;
                int eduId;
                System.out.println("Введите Имя и Фамилию студента");
                name = in.nextLine();
                System.out.println("Введите порядковый номер учебной программы");
                eduId = in.nextInt();
                if(repository.addStudent(new Student(name, eduId))){
                    System.out.println("Операция прошла успешно");
                } else {
                    System.out.println("Неправильный номер образовательной программы");
                }
                break;
            }
        }
    }

    enum MenuS{
        MAIN(List.of(
                "Добавить студента",
                "Отчислить студента",
                "Проставить оценку",
                "Рассчитать время до выпуска",
                "Получить отчёт об успеваемости",
                "Рассчитать возможность отчисления",
                "Вывести список студентов"));

        private final List elements;

        MenuS(List elements) {
            this.elements = elements;
        }

        public List getPath() {
            return elements;
        }

    }

    private static String readStringChoice(){
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public static void initTest() throws IOException {
        String path = "C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\students.yaml";
        YamlDAO yamlDAO = new YamlDAO();
        enum Paths{
            STUDENTS("C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\students.yaml"),
            EDUPLANS("\"C:\\\\Users\\\\rsivanov\\\\IdeaProjects\\\\StudentsEducationSystem\\\\src\\\\main\\\\java\\\\space\\\\irsi7\\\\data\\\\eduplans.yaml\""),
            THEMES("\"C:\\\\Users\\\\rsivanov\\\\IdeaProjects\\\\StudentsEducationSystem\\\\src\\\\main\\\\java\\\\space\\\\irsi7\\\\data\\\\themes.yaml\"");

            private final String path;

            Paths(String path) {
                this.path = path;
            }

            public String getPath() {
                return path;
            }

        }
        ArrayList<Integer> marks = new ArrayList<Integer>(){
            {
                add(4);
                add(5);
                add(5);
            }
        };
        ArrayList<Student> studentArrayList = new ArrayList<>() {
            {
                add(new Student("Roman Ivanov", 1, marks,1));
                add(new Student("Sasha Zaycev", 2, marks,2));
            }
        };
        yamlDAO.writeYAML(studentArrayList, Paths.STUDENTS.path);
    }
}