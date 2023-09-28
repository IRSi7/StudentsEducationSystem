package space.irsi7.models;

import space.irsi7.exceptions.IllegalInitialDataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Course {

    private int id;
    private ArrayList<Integer> themeIds;

    public Course(int id, ArrayList<Integer> themeIds){
        this.id = id;
        this.themeIds = themeIds;
    }

    public Course(){
        this.id = 0;
        this.themeIds = new ArrayList<>();
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

    public static Course constructCourse (LinkedHashMap course){
        Course answer = new Course();
        Arrays.stream(CourseSettersEnum.values()).forEach(setter -> {
            if(course.containsKey(setter.name())){
                setter.setValue(answer, course.get(setter.name()));
            } else {
                //TODO: Спросить про исключения в потоках. Не получается бросить checked exception
                throw new IllegalInitialDataException("Ошибка при чтении переменной " + setter.name() + " из класса " + Course.class.getName());
            }
        });
        return answer;
    }

    enum CourseSettersEnum {
        id {
            @Override
            void setValue(Course course, Object value) {
                course.setId((Integer) value);
            }
        },
        themeIds {
            @Override
            void setValue(Course course, Object value) {
                course.setThemeIds((ArrayList<Integer>) value);
            }
        };

        abstract void setValue(Course course, Object value);
    }
}
