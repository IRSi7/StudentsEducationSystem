package space.irsi7.models;

import java.util.ArrayList;

public class Student {

    String Name;
    EduProgram eduProgram;
    ArrayList<Integer> eduMarks;
    int programProgress;

    public Student(String name, EduProgram eduProgram, ArrayList<Integer> eduMarks, int programProgress) {
        Name = name;
        this.eduProgram = eduProgram;
        this.eduMarks = eduMarks;
        this.programProgress = programProgress;
    }
}
