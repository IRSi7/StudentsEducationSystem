package space.irsi7.models;

import java.util.LinkedHashMap;
import java.util.Objects;

public class Theme {
    private int id;
    private String name;
    private int hours;

    public Theme() {
        this.id = 0;
        this.name = null;
        this.hours = 0;
    }

    public Theme(int id, String name, int hours) {
        this.id = id;
        this.name = name;
        this.hours = hours;
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
