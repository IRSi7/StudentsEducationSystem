package space.irsi7.repository;

import space.irsi7.IllegalInitialDataException;
import space.irsi7.dao.YamlDAO;
import space.irsi7.models.EduPlan;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.io.IOException;
import java.util.ArrayList;

public class Repository {

    ArrayList<Student> students;
    ArrayList<EduPlan> eduPlans;
    ArrayList<Theme> themes;
    YamlDAO yamlDAO;
    public Repository() throws IllegalInitialDataException {
        this.yamlDAO = new YamlDAO();
        try{
            students = yamlDAO.readYAML(Paths.STUDENTS.path);
            eduPlans = yamlDAO.readYAML(Paths.EDUPLANS.path);
            themes = yamlDAO.readYAML(Paths.THEMES.path);
            System.out.println("Успешно");
        } catch (IOException e) {
            throw new IllegalInitialDataException("Ошибка при чтении из YAML");
        }
    }

    public Boolean addStudent(Student student){
        if(student.eduPlanId > 0 && student.eduPlanId <= eduPlans.size()){
            this.students.add(student);
            notifyChanges();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeStudent(int id){
        if(id > 0 && id < students.size()){
            this.students.remove(id);
            notifyChanges();
            return true;
        } else {
            return false;
        }

    }

    public void rateStudent(int studentId, int mark){
        this.students.get(studentId).eduMarks.add(mark);
        notifyChanges();
    }

    private void notifyChanges(){
        try {
            yamlDAO.writeYAML(students, Paths.STUDENTS.path);
            System.out.println("Запись прошла успешно");
        } catch (IOException e) {
            System.out.println("Ошибка при записи");
        }
    }

    enum Paths{
        STUDENTS("C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\students.yaml"),
        EDUPLANS("C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\eduplans.yaml"),
        THEMES("C:\\Users\\rsivanov\\IdeaProjects\\StudentsEducationSystem\\src\\main\\java\\space\\irsi7\\data\\themes.yaml");

        private final String path;

        Paths(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

    }
}
