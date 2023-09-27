package space.irsi7.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class Config {
    private ArrayList<Course> courses;

    private ArrayList<Theme> themes;

    public Config(ArrayList<Course> courses, ArrayList<Theme> themes) {
        this.courses = courses;
        this.themes = themes;
    }

    public Config(){
        this.courses = new ArrayList<>();
        this.themes = new ArrayList<>();
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void setThemes(ArrayList<Theme> themes) {
        this.themes = themes;
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }

    public Map<Integer, Theme> getThemesMap(){
        return themes.stream().collect(Collectors.toMap(Theme::getId, t -> t));
    }

    public Map<Integer, Course> getCoursesMap(){
        return courses.stream().collect(Collectors.toMap(Course::getId, c -> c));
    }
}
