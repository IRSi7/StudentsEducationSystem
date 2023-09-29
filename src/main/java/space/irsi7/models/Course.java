package space.irsi7.models;

import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.interfaces.Readable;

import java.util.*;

public class Course extends Readable {

    public int id;
    public ArrayList<Integer> themeIds;

    public Course(int id, ArrayList<Integer> themeIds){
        this.id = id;
        this.themeIds = themeIds;
    }

    public Course(){
        super();
    }

    public Course(Map<?, ?> course){
        super(course);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getThemeIds() {
        return themeIds;
    }

    public void setThemeIds(ArrayList<Integer> themeIds) {
        this.themeIds = themeIds;
    }

    //TODO:Спросить про возможность выноса этого метода в абстрактный класс
//    public Course(Map<String, Object> theme){
//        Arrays.stream(this.getClass().getFields()).forEach( field -> {
//            System.out.println(field.getName());
//            if(theme.containsKey(field.getName())){
//                try {
//                    field.set(this, theme.get(field.getName()));
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                throw new IllegalInitialDataException("Ошибка при чтении переменной " + field.getName() + " из класса " + this.getClass().getName());
//            }
//        });
//    }
}
