package space.irsi7.models;

import java.util.ArrayList;

public class Course {

    private int id;
    private ArrayList<Integer> themesId;

    public Course(int id, ArrayList<Integer> themesId){
        this.id = id;
        this.themesId = themesId;
    }

    public Course(){
        this.id = 0;
        this.themesId = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getThemesId() {
        return themesId;
    }

    public void setThemesId(ArrayList<Integer> themesId) {
        this.themesId = themesId;
    }
}
