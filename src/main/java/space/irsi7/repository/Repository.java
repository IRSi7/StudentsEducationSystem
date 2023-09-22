package space.irsi7.repository;

import space.irsi7.IllegalInitialDataException;
import space.irsi7.dao.YamlDAO;
import space.irsi7.models.EduPlan;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            this.students.remove(id-1);
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
            int passed = students.get(studentId).eduMarks.size();
            int all = eduPlans.get(students.get(studentId).eduPlanId).themesId.size();
            return (all - passed);
        } else {
            return -1;
        }
    }

    public String getReportStudent(int studId){
        Student curStudent = students.get(studId - 1);
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

    public String getDropChance(int gpa){
        if(gpa >= 75){
            return "Низкая вероятность быть отчисленным";
        } else {
            return "Высокая вероятность быть отчисленным";
        }
    }

    public String getInfoStudent(int studId){
        Student curStudent = students.get(studId - 1);
        int curGPA = getGPA(studId);
        String answer = studId + "). | Студент : " + curStudent.name
                + " | Кол-во сданных тестов : " + curStudent.eduMarks.size()
                + " | Дни до конца обучения : " + getEduTimeLeft(studId)
                + " | Средний балл : " + curGPA
                + " | Оценка успеваемости : " + getDropChance(curGPA);
        return answer;
    }

//    public ArrayList<String> getAllReport(int mode){
//        Map sortMap;
//        switch (mode) {
//            case(1): {
//                sortMap = new HashMap<Integer, String >();
//                break;
//            }
//            case(5): {
//                sortMap = new HashMap<Integer, Boolean>();
//                break;
//            }
//            default: {
//                sortMap = new HashMap<Integer, Integer>();
//                break;
//            }
//        }
//
//        for (int i = 0; i < students.size(); i++) {
//            Student curStudent = students.get(i);
//            String string = getInfoStudent(i + 1);
//            switch (mode){
//                case(1): {
//                    sortMap.put(curStudent.name, string);
//                    break;
//                }
//                case(2): {
//                    sortMap.put(curStudent.eduMarks.size(), string);
//                    break;
//                }
//                case(3): {
//                    sortMap.put(getEduTimeLeft(i + 1), string);
//                    break;
//                }
//                case(4): {
//                    sortMap.put(getGPA(i + 1), string);
//                    break;
//                }
//                case(5): {
//                    sortMap.put(getGPA(i + 1) >= 75, string);
//                    break;
//                }
//            }
//        }
//
//        sortMap.keySet();
//    }

    public int getGPA(int studId) {
        Student curStudent = students.get(studId - 1);
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
        return studentId > 0 && studentId <= students.size();
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