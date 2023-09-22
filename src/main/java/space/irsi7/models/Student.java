package space.irsi7.models;

import java.util.ArrayList;

public class Student {

    public String name;
    public int eduPlanId;
    public ArrayList<Integer> eduMarks;
    public int programProgress;

    public Student(String name, int eduPlanId, ArrayList<Integer> eduMarks, int programProgress) {
        this.name = name;
        this.eduPlanId = eduPlanId;
        this.eduMarks = eduMarks;
        this.programProgress = programProgress;
    }

    public Student(String name, int eduPlanId) {
        this.name = name;
        this.eduPlanId = eduPlanId;
        this.eduMarks = new ArrayList<>();
        this.programProgress = 0;
    }
}
