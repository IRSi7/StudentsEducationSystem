package space.irsi7.app;

import space.irsi7.IllegalInitialDataException;
import space.irsi7.dao.YamlDAO;
import space.irsi7.models.Student;
import space.irsi7.repository.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
        MultipleChoiceMenu multipleChoiceMenu = new MultipleChoiceMenu(MenuS.MAIN.elements);
        switch (multipleChoiceMenu.chooseOne()) {
            case (1): {
                String name;
                int eduId;
                System.out.println("Введите Имя и Фамилию студента");
                name = in.nextLine();
                System.out.println("Введите порядковый номер учебной программы");
                eduId = in.nextInt();

                repository.addStudent(new Student(name, eduId));
                break;
            }
            case (2): {
                int studentId;
                System.out.println("Введите порядковый номер студента");
                studentId = in.nextInt();

                repository.removeStudent(studentId - 1);
                break;
            }
            case (3): {
                int studentId;
                int mark;
                System.out.println("Введите порядковый номер студента");
                studentId = in.nextInt();
                System.out.println("Введите оценку за последний тест");
                mark = in.nextInt();

                repository.rateStudent(studentId - 1, mark);
                break;
            }
            case (4): {
                int studentId;
                int answer;
                System.out.println("Введите порядковый номер студента");
                studentId = in.nextInt();
                answer = repository.getEduTimeLeft(studentId - 1);
                if (answer != -1) {
                    System.out.println("Выбранному студенту осталось учиться " + answer + " дней");
                } else {

                }
                break;
            }
            case (5): {
                int studentId;
                System.out.println("Введите порядковый номер студента");
                studentId = in.nextInt();
                System.out.println(repository.getReportStudent(studentId - 1));
                break;
            }
            case (6): {
                int studentId;
                System.out.println("Введите порядковый номер студента");
                studentId = in.nextInt();
                if (repository.getDropChance(repository.getGPA(studentId - 1))) {
                    System.out.println("Низкая вероятность быть отчисленным");
                } else {
                    System.out.println("Высокая вероятность быть отчисленным");
                }
                break;
            }
            case (7): {

                multipleChoiceMenu = new MultipleChoiceMenu(MenuS.SORT.elements);
                int sortParameters = multipleChoiceMenu.chooseOne();

                multipleChoiceMenu = new MultipleChoiceMenu(MenuS.ORDERBY.elements);
                int sortOrder = multipleChoiceMenu.chooseOne();

                multipleChoiceMenu = new MultipleChoiceMenu(MenuS.FILTERBY.elements);
                int filter = multipleChoiceMenu.chooseOne();

                multipleChoiceMenu = new MultipleChoiceMenu(MenuS.WRITETO.elements);
                int yesno = multipleChoiceMenu.chooseOne();
                switch (yesno) {
                    case (1): {
                        repository.getAllReport(sortParameters, sortOrder, filter).forEach(System.out::println);
                        break;
                    }
                    case (2): {
                        writeToTxt(repository.getAllReport(sortParameters, sortOrder, filter));
                        break;
                    }
                    default:
                        break;
                }
                break;
            }
        }
        return restart();
    }

    public static boolean restart() {
        System.out.println("Хотите продолжить?");
        MultipleChoiceMenu yesNo = new MultipleChoiceMenu(MenuS.YESNO.elements);
        return yesNo.chooseOne() == 1;
    }

    public static void writeToTxt(ArrayList<String> strings){
        try {
            File file = new File("C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\resources\\output.txt");
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
        String path = "C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\students.yaml";
        YamlDAO yamlDAO = new YamlDAO();
        enum Paths {
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
        ArrayList<Integer> marks = new ArrayList<Integer>() {
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

    private static String readStringChoice () {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
    enum MenuS {
            MAIN(List.of(
                    "Добавить студента",
                    "Отчислить студента",
                    "Проставить оценку",
                    "Рассчитать время до выпуска",
                    "Получить отчёт об успеваемости",
                    "Рассчитать возможность отчисления",
                    "Вывести список студентов")),
            SORT(List.of(
                    "Сортировать по порядку",
                    "Сортировать по Фамилии и Имени",
                    "Сортировать по кол-ву сданных тестов",
                    "Сортировать по кол-ву дней до конца обучения",
                    "Сортировать по среднему баллу"
            )),
            ORDERBY(List.of(
                    "По возрастанию",
                    "По убыванию")),
            FILTERBY(List.of(
                    "Не фильтровать",
                    "Показать только c низкой вероятностью быть отчисленными",
                    "Показать только с высокой вероятностью быть отчисленными"
            )),
            WRITETO(List.of(
                    "Вывести в консоль",
                    "Вывести в output.txt")),
            YESNO(List.of(
                    "Да",
                    "Нет"
            ));

            private final List<String> elements;

            MenuS(List<String> elements) {
                this.elements = elements;
            }

            public List<String> getPath() {
                return elements;
            }

        }

    }
