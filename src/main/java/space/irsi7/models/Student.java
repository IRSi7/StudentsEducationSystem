package space.irsi7.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Student {

    public String name;

    public int eduPlanId;

    public ArrayList<Integer> eduMarks;

    public Student(String name, int eduPlanId, ArrayList<Integer> eduMarks) {
        this.name = name;
        this.eduPlanId = eduPlanId;
        this.eduMarks = eduMarks;
    }

    public Student(String name, int eduPlanId) {
        this.name = name;
        this.eduPlanId = eduPlanId;
        this.eduMarks = new ArrayList<>();
    }

    public Student(){
        name = null;
        eduPlanId = 0;
        eduMarks = new ArrayList<>();
    }
    public LinkedHashMap linkedHashMap(){
        LinkedHashMap answer = new LinkedHashMap<>();
        answer.put("name", name);
        answer.put("eduPlanId", eduPlanId);
        answer.put("eduMarks", eduMarks);
        return answer;
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
}
