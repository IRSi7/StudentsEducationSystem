package space.irsi7.models;

public class Theme {

    public String name;
    public int hours;

    public Theme(int hours, String name) {
        this.name = name;
        this.hours = hours;
    }

    public Theme(){
        this.name = null;
        this.hours = 0;
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
