package space.irsi7.repository;

import space.irsi7.IllegalInitialDataException;
import space.irsi7.dao.YamlDAO;
import space.irsi7.interfaces.CustomPair;
import space.irsi7.models.EduPlan;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.round;

public class Repository {

    ArrayList<Student> students;
    ArrayList<EduPlan> eduPlans;
    ArrayList<Theme> themes;
    YamlDAO yamlDAO;
    public Repository() throws IllegalInitialDataException {
        this.yamlDAO = new YamlDAO();
        try{
            students = yamlDAO.readYamlStudents(Paths.STUDENTS.path);
            eduPlans = yamlDAO.readYamlEduPlans(Paths.EDUPLANS.path);
            themes = yamlDAO.readYamlThemes(Paths.THEMES.path);
            System.out.println("Успешно");
        } catch (IOException e) {
            throw new IllegalInitialDataException("Ошибка при чтении из YAML");
        }
    }

    public void addStudent(Student student){
        if(student.eduPlanId > 0 && student.eduPlanId <= eduPlans.size()){
            this.students.add(student);
            System.out.println("Операция прошла успешно");
            notifyChanges();
        }
        else{
            System.out.println("Ошибка в введённых данных");
        }
    }

    public void removeStudent(int id){
        if(validateStudId(id)){
            this.students.remove(id);
            notifyChanges();
            System.out.println("Операция прошла успешно");
        } else {
            System.out.println("Ошибка в введённых данных");
        }

    }

    public void rateStudent(int studentId, int mark){
        if(validateStudId(studentId) && mark > 0 && mark < 101) {
            students.get(studentId).eduMarks.add(mark);
            notifyChanges();
            System.out.println("Операция прошла успешно");
        } else {
            System.out.println("Ошибка в введённых данных");
        }
    }

    private void notifyChanges(){
        try {
            yamlDAO.writeYAML(students, Paths.STUDENTS.path);
            System.out.println("Запись прошла успешно");
        } catch (IOException e) {
            System.out.println("Ошибка при записи");
        }
    }

    public int getEduTimeLeft(int studentId){
        if(validateStudId(studentId)) {
            Student curStudent = students.get(studentId);
            int passed = curStudent.eduMarks.size();
            int all = eduPlans.get(curStudent.eduPlanId - 1).themesId.size();
            return (all - passed);
        } else {
            return -1;
        }
    }

    public String getReportStudent(int studId){
        Student curStudent = students.get(studId);
        EduPlan curEduPlan = eduPlans.get(curStudent.eduPlanId - 1);
        StringBuilder answer = new StringBuilder("---------------------------------------------\n");
        answer.append("Студент : ")
                .append(curStudent.name)
                .append("\n");
        answer.append("Тесты :\n");
        int sum = 0;
        for (int i = 0; i < curStudent.eduMarks.size(); i++) {
            sum += curStudent.eduMarks.get(i);
            answer.append("\t").append(i + 1)
                    .append(". | Тема: ")
                    .append(themes.get(curEduPlan.themesId.get(i)).getName())
                    .append(" | Оценка: ")
                    .append(curStudent.eduMarks.get(i))
                    .append(" |\n");
        }
        answer.append("Средний балл: ").append(getGPA(studId)).append("\n");
        answer.append(("---------------------------------------------"));
        return answer.toString();
    }

    public Boolean getDropChance(int gpa){
        return (gpa >= 75);
    }

    public String getInfoStudent(int studId){
        Student curStudent = students.get(studId);
        int curGPA = getGPA(studId);
        return " ID : " + (studId + 1) + " | Студент : " + curStudent.name
                + " | Кол-во сданных тестов : " + curStudent.eduMarks.size()
                + " | Дни до конца обучения : " + getEduTimeLeft(studId)
                + " | Средний балл : " + curGPA
                + " | Оценка успеваемости : "
                + ((curGPA >= 75) ? "Низкая вероятность быть отчисленным" : "Высокая вероятность быть отчисленным");
    }

    public boolean filerByGPA(int filter, int studId){
        return switch (filter) {
            case (2) -> getDropChance(getGPA(studId));
            case (3) -> !getDropChance(getGPA(studId));
            default -> true;
        };
    }

    public ArrayList<String> getAllReport(int mode, int order, int filter){
        ArrayList sortArray;
        ArrayList<String> answer = new ArrayList<>();

        switch (mode) {
            case(1): {
                for (int i = 0; i < students.size(); i++) {
                    answer.add(getInfoStudent(i + 1));
                }
                return answer;
            }
            case(2): {
                sortArray = new ArrayList<CustomPair<Integer, String>>();
                break;
            }
//            case(5): {
//                sortArray = new ArrayList<CustomPair<Integer, Boolean>>();
//                break;
//            }
            default: {
                sortArray = new ArrayList<CustomPair<Integer, Integer>>();
                break;
            }
        }

        for (int i = 0; i < students.size(); i++) {
            Student curStudent = students.get(i);
            switch (mode){
                case(2): {
                    if(filerByGPA(filter, i)) {
                        sortArray.add(new CustomPair<>(i, curStudent.name));
                    }
                    break;
                }
                case(3): {
                    if(filerByGPA(filter, i)) {
                        sortArray.add(new CustomPair<>(i, curStudent.eduMarks.size()));
                    }
                    break;
                }
                case(4): {
                    if(filerByGPA(filter, i)) {
                        sortArray.add(new CustomPair<>(i, getEduTimeLeft(i)));
                    }
                    break;
                }
                case(5): {
                    if(filerByGPA(filter, i)) {
                        sortArray.add(new CustomPair<>(i, getGPA(i)));
                    }
                    break;
                }
                default:
                    break;
            }

        }
        sortArray.stream().sorted().forEach(
                it -> answer.add(
                        getInfoStudent((int) ( (CustomPair<?, ?>) it).getFirst())));
        if(order == 2){
            Collections.reverse(answer);
        }
        return answer;
    }

    public int getGPA(int studId) {
        Student curStudent = students.get(studId);
        if(!curStudent.eduMarks.isEmpty()) {
            int sum = 0;
            for (int i = 0; i < curStudent.eduMarks.size(); i++) {
                sum += curStudent.eduMarks.get(i);
            }
            return round((float) sum / curStudent.eduMarks.size());
        } else {
            return 0;
        }
    }

    public boolean validateStudId(int studentId){
        return studentId >= 0 && studentId < students.size();
    }
    enum Paths{
        STUDENTS("C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\students.yaml"),
        EDUPLANS("C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\eduplans.yaml"),
        THEMES("C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\themes.yaml");

        private final String path;

        Paths(String path) {
            this.path = path;
        }
    }
}