package space.irsi7.models;

import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.interfaces.Readable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Theme extends Readable {
    public int id;
    public String name;
    public int hours;

    public Theme(int id, String name, int hours) {
        this.id = id;
        this.name = name;
        this.hours = hours;
    }

    public Theme(Map<?, ?> theme){
        super(theme);
    }
}
