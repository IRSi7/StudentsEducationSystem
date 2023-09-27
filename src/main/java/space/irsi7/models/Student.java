package space.irsi7.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import space.irsi7.enums.MenuEnum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

import static java.lang.Math.round;

public class Student {

    public static int count = 0;
    public int id;
    public String name;
    public int eduPlanId;
    public ArrayList<Integer> eduMarks;
    public int gpa;

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Student.count = count;
    }

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

    public Student(String name, int eduPlanId) {
        this.id = count++;
        this.name = name;
        this.eduPlanId = eduPlanId;
        this.eduMarks = new ArrayList<>();
        this.gpa = getGPA();
    }

    public Student(){
        this.id = 0;
        this.name = null;
        this.eduPlanId = 0;
        this.eduMarks = new ArrayList<>();
        this.gpa = 0;
    }

//    public LinkedHashMap linkedHashMap(){
//        LinkedHashMap answer = new LinkedHashMap<>();
//        answer.put("name", name);
//        answer.put("eduPlanId", eduPlanId);
//        answer.put("eduMarks", eduMarks);
//        return answer;
//    }


    @Override
    public String toString() {
        return " ID : " + id + " | Студент : " + name
                + " | Кол-во сданных тестов : " + eduMarks.size()
                + " | Средний балл : " + gpa
                + " | Оценка успеваемости : "
                + ((gpa >= 75) ? "Низкая вероятность быть отчисленным" : "Высокая вероятность быть отчисленным");
    }

    public int getGPA(){
        if (!this.eduMarks.isEmpty()) {
            return round((float) eduMarks.stream().mapToInt(it -> it).sum() / this.eduMarks.size());
        } else {
            return 0;
        }
    }
}
