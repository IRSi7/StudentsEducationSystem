package space.irsi7.repository;

import space.irsi7.enums.MenuEnum;
import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.dao.YamlDAO;
import space.irsi7.enums.LogsEnum;
import space.irsi7.enums.PathsEnum;
import space.irsi7.models.Config;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.io.IOException;
import java.util.*;

import static java.lang.Math.round;

//Только инициализирует
public class Repository {

    Config config;
    Map<Integer, Student> students;
    Map<Integer, Course> courses;
    Map<Integer, Theme> themes;
    YamlDAO yamlDAO;

    public Repository() throws IllegalInitialDataException {
        this.yamlDAO = new YamlDAO();
        //            students = yamlDAO.readYamlStudents(PathsEnum.STUDENTS.getPath());
//            courses = yamlDAO.readYamlEduPlans(PathsEnum.EDUPLANS.getPath());
//            themes = yamlDAO.readYamlThemes(PathsEnum.THEMES.getPath());
        try {
            students = yamlDAO.readYamlStudents(PathsEnum.STUDENTS.getPath());
            config = yamlDAO.readYamlConfig(PathsEnum.CONFIG.getPath());
            themes = config.getThemesMap();
            courses = config.getCoursesMap();
            System.out.println("Успешно");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addStudent(Student student) {
        if (student.eduPlanId > 0 && student.eduPlanId <= courses.size()) {
            // TODO: Добавить индексацию
            this.students.put(0 ,student);
            notifyChanges();
        } else {
            System.out.println(LogsEnum.FAIL.getMessage());
        }
    }

    public void removeStudent(int id) {
        if (validateStudId(id)) {
            this.students.remove(id);
            notifyChanges();
        } else {
            System.out.println(LogsEnum.FAIL.getMessage());
        }
    }

    public void rateStudent(int studentId, int mark) {
        if (validateStudId(studentId) && mark > 0 && mark < 101) {
            students.get(studentId).eduMarks.add(mark);
            notifyChanges();
        } else {
            System.out.println(LogsEnum.FAIL.getMessage());
        }
    }

    private void notifyChanges() {
        try {
            yamlDAO.writeYAML(students.values(), PathsEnum.STUDENTS.getPath());
            System.out.println(LogsEnum.SUCCESS.getMessage());
        } catch (IOException e) {
            System.out.println(LogsEnum.FAIL.getMessage());
        }
    }

    public int getEduTimeLeft(int studentId) {
        if (validateStudId(studentId)) {
            Student curStudent = students.get(studentId);
            int passed = curStudent.eduMarks.size();
            int all = courses.get(curStudent.eduPlanId - 1).getThemesId().size();
            return (all - passed);
        } else {
            return -1;
        }
    }

    public int getEduTimeLeft(Student student) {
        int passed = student.eduMarks.size();
        int all = courses.get(student.eduPlanId - 1).getThemesId().size();
        return (all - passed);
    }

    public String getReportStudent(int studId) {
        Student curStudent = students.get(studId);
        Course curCourse = courses.get(curStudent.eduPlanId - 1);
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
                    //.append(themes.get(curCourse.themesId.get(i)).getName())
                    .append(" | Оценка: ")
                    .append(curStudent.eduMarks.get(i))
                    .append(" |\n");
        }
        answer.append("Средний балл: ").append(getGPA(studId)).append("\n");
        answer.append(("---------------------------------------------"));
        return answer.toString();
    }

    public Boolean getDropChance(int gpa) {
        return (gpa >= 75);
    }

    public String getInfoStudent(int studId) {
        Student curStudent = students.get(studId);
        int curGPA = getGPA(studId);
        return " ID : " + (studId + 1) + " | Студент : " + curStudent.name
                + " | Кол-во сданных тестов : " + curStudent.eduMarks.size()
                + " | Дни до конца обучения : " + getEduTimeLeft(studId)
                + " | Средний балл : " + curGPA
                + " | Оценка успеваемости : "
                + ((curGPA >= 75) ? "Низкая вероятность быть отчисленным" : "Высокая вероятность быть отчисленным");
    }

    public boolean filterByGPA(int filter, Student student) {
        return switch (filter) {
            case (2) -> getDropChance(student.getGPA());
            case (3) -> !getDropChance(student.getGPA());
            default -> true;
        };
    }

    public Object getFiltredObject(Student student, int sort){
        if(sort == MenuEnum.SORT_ID.ordinal()){
            return student.id;
        }
        if(sort == MenuEnum.SORT_NAME.ordinal()){
            return student.name;
        }
        if(sort == MenuEnum.SORT_TESTS_PASSED.ordinal()){
            return student.eduMarks.size();
        }
        if(sort == MenuEnum.SORT_DAYS_LEFT.ordinal()){
            return getEduTimeLeft(student);
        }
        if(sort == MenuEnum.SORT_GPA.ordinal()){
            return student.getGPA();
        }
        return null;
    }

    public ArrayList<String> getAllReport(int sort, int order, int filter) {

        ArrayList<String> answer = new ArrayList<>();

        students.values().stream()
                .filter( s -> filterByGPA(filter, s))
                .sorted((Student s, Student s1) -> {
                    if(sort == MenuEnum.SORT_ID.ordinal()){
                        return Integer.compare(s.id, s1.id);
                    }
                    if(sort == MenuEnum.SORT_NAME.ordinal()){
                        return s.name.compareTo(s1.name);
                    }
                    if(sort == MenuEnum.SORT_TESTS_PASSED.ordinal()){
                        return s.name.compareTo(s1.name);
                    }
                    if(sort == MenuEnum.SORT_GPA.ordinal()){
                        return Integer.compare(s.eduMarks.size(), s1.eduMarks.size());
                    }
                    return 0;
                })
                .toList()
                .forEach( s -> answer.add(s.toString()));

        if (order == MenuEnum.ORDER_REVERSED.ordinal()) {
            Collections.reverse(answer);
        }
        return answer;
    }

    public int getGPA(int studId) {
        Student curStudent = students.get(studId);
        if (!curStudent.eduMarks.isEmpty()) {
            int sum = 0;
            //stream
            for (int i = 0; i < curStudent.eduMarks.size(); i++) {
                sum += curStudent.eduMarks.get(i);
            }
            return round((float) sum / curStudent.eduMarks.size());
        } else {
            return 0;
        }
    }

    public boolean validateStudId(int studentId) {
        return studentId >= 0 && studentId < students.size();
    }

}