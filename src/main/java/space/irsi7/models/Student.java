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
