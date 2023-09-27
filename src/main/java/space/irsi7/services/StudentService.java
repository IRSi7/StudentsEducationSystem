package space.irsi7.services;

import space.irsi7.dao.YamlDAO;
import space.irsi7.enums.MenuEnum;
import space.irsi7.enums.PathsEnum;
import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.models.Config;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;
import space.irsi7.repository.StudentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class StudentService {

    StudentRepository students;
    final Map<Integer, Course> courses;
    final Map<Integer, Theme> themes;

    public StudentService() throws IllegalInitialDataException {
        try {
            students = new StudentRepository();
        } catch (IOException e) {
            throw new IllegalInitialDataException(e);
        }
        try {
            var yamlDAO = new YamlDAO();
            Config config = yamlDAO.readYamlConfig(PathsEnum.CONFIG.getPath());
            themes = config.getThemesMap();
            courses = config.getCoursesMap();
        } catch (IOException e) {
            throw new IllegalInitialDataException(e);
        }
    }

    public void addStudent(String name, int course) {
        // TODO: Добавить индексацию
        // TODO: Добавить проверку course
        students.addStudent(name, course);
    }

    public void removeStudent(int id) {
        students.removeStudent(id);
    }

    public void rateStudent(int studentId, int mark) {
        students.rateStudent(studentId, mark);
    }

    public int getEduTimeLeft(int studentId) {
        Student curStudent = students.getStudent(studentId);
        int passed = curStudent.getEduMarks().size();
        int all = courses.get(curStudent.getEduPlanId()).getThemesId().size();
        return (all - passed);
    }

    public String getReportStudent(int studId) {
        Student curStudent = students.getStudent(studId);
        Course curCourse = courses.get(curStudent.getEduPlanId());
        StringBuilder answer = new StringBuilder("---------------------------------------------\n");
        answer.append("Студент : ")
                .append(curStudent.getName())
                .append("\n");
        answer.append("Тесты :\n");
        // TODO: Переделать на Stream API
        for (int i = 0; i < curStudent.getEduMarks().size(); i++) {
            answer.append("\t").append(i)
                    .append(". | Тема: ")
                    .append(themes.get(curCourse.getThemesId().get(i)).getName())
                    .append(" | Оценка: ")
                    .append(curStudent.getEduMarks().get(i))
                    .append(" |\n");
        }
        answer.append("Средний балл: ").append(getGPA(studId)).append("\n");
        answer.append(("---------------------------------------------"));
        return answer.toString();
    }

    public Boolean getDropChance(int studId) {
        return students.getStudent(studId).getGpa() >= 75;
    }

    public ArrayList<String> getAllReport(int sort, int order, int filter) {

        ArrayList<String> answer = new ArrayList<>();

        // TODO: Спросить про инкапсуляцию (Нужно ли сделать так, чтобы StudentService не использовал класс Student)
        students.getStudents().values().stream()
                .filter( s -> {
                    if(filter == MenuEnum.FILTER_LOW.ordinal()){
                        return s.getGpa() < 75;
                    }
                    if(filter == MenuEnum.FILTER_HIGH.ordinal()){
                        return s.getGpa() >= 75;
                    }
                    return true;
                })
                .sorted((Student s, Student s1) -> {
                    if(sort == MenuEnum.SORT_ID.ordinal()){
                        return Integer.compare(s.getId(), s1.getId());
                    }
                    if(sort == MenuEnum.SORT_NAME.ordinal()){
                        return s.getName().compareTo(s1.getName());
                    }
                    if(sort == MenuEnum.SORT_TESTS_PASSED.ordinal()){
                        return Integer.compare(s.getEduMarks().size(), s1.getEduMarks().size());
                    }
                    if(sort == MenuEnum.SORT_GPA.ordinal()){
                        return Integer.compare(s.getGpa(), s1.getGpa());
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

    public boolean validateId(int id){
        if(students.containsStudent(id)){
            return true;
        } else {
            System.out.println("Студента с таким ID не существует");
            return false;
        }
    }

    public int getGPA(int studId) {
        return students.getStudent(studId).getGpa();
    }
}
