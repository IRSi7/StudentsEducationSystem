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
    public static void main(String[] args) throws IllegalInitialDataException, IOException {
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

                repository.addStudent(new Student(name, eduId));
            }
            case(2):{
                int studentId;
                System.out.println("Введите порядковый номер студента");
                studentId = in.nextInt();

                repository.removeStudent(studentId);

                break;
            }
            case(3):{
                int studentId;
                int mark;
                System.out.println("Введите порядковый номер студента");
                studentId = in.nextInt();
                System.out.println("Введите оценку за последний тест");
                mark = in.nextInt();

                repository.rateStudent(studentId, mark);

                break;
            }
            case(4):{
                int studentId;
                int answer;
                System.out.println("Введите порядковый номер студента");
                studentId = in.nextInt();
                answer = repository.getEduTimeLeft(studentId);
                if(answer != -1){
                    System.out.println("Выбранному студенту осталось учиться " + answer + " дней");
                } else {

                }
                break;
            }
            case(5):{
                int studentId;
                System.out.println("Введите порядковый номер студента");
                studentId = in.nextInt();
                System.out.println(repository.getReportStudent(studentId));
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

        private final List<String> elements;

        MenuS(List<String> elements) {
            this.elements = elements;
        }

        public List<String> getPath() {
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
                add(new Student("Roman Ivanov", 1, marks));
                add(new Student("Sasha Zaycev", 2, marks));
            }
        };
        yamlDAO.writeYAML(studentArrayList, Paths.STUDENTS.path);
    }
}