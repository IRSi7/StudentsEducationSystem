package space.irsi7.models;

import java.util.ArrayList;
import static java.lang.Math.round;

public class Student {

    private int id;
    private String name;
    private int eduPlanId;
    private ArrayList<Integer> eduMarks;
    private int gpa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEduPlanId() {
        return eduPlanId;
    }

    public void setEduPlanId(int eduPlanId) {
        this.eduPlanId = eduPlanId;
    }

    public ArrayList<Integer> getEduMarks() {
        return eduMarks;
    }

    public void setEduMarks(ArrayList<Integer> eduMarks) {
        this.eduMarks = eduMarks;
    }

    public int getGpa() {
        return gpa;
    }

    public void setGpa(int gpa) {
        this.gpa = gpa;
    }

    public Student(String name, int eduPlanId, ArrayList<Integer> eduMarks, int gpa) {

        this.name = name;
        this.eduPlanId = eduPlanId;
        this.eduMarks = eduMarks;
        this.gpa = gpa;
    }

    public Student(int id, String name, int eduPlanId) {
        this.id = id;
        this.name = name;
        this.eduPlanId = eduPlanId;
        this.eduMarks = new ArrayList<>();
        recountGPA();
    }

    public Student(){
        this.id = 0;
        this.name = null;
        this.eduPlanId = 0;
        this.eduMarks = new ArrayList<>();
        this.gpa = 0;
    }

    @Override
    public String toString() {
        return " ID : " + id + " | Студент : " + name
                + " | Кол-во сданных тестов : " + eduMarks.size()
                + " | Средний балл : " + gpa
                + " | Оценка успеваемости : "
                + ((gpa >= 75) ? "Низкая вероятность быть отчисленным" : "Высокая вероятность быть отчисленным");
    }

    public void recountGPA(){
        if (!this.eduMarks.isEmpty()) {
            this.gpa = round((float) eduMarks.stream().mapToInt(it -> it).sum() / this.eduMarks.size());
        } else {
            this.gpa = 0;
        }
    }
}
