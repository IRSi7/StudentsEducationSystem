package space.irsi7.models;

import java.util.ArrayList;

public class EduPlan {

    public ArrayList<Integer> themesId;

    public EduPlan(ArrayList<Integer> themes) {
        this.themesId = themes;
    }

    public EduPlan(){
        this.themesId = new ArrayList<>();
    }

    public ArrayList<Integer> getThemesId() {
        return themesId;
    }

    public void setThemesId(ArrayList<Integer> themesId) {
        this.themesId = themesId;
    }
}
