package space.irsi7.models;

import space.irsi7.exceptions.IllegalInitialDataException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Theme {
    public int id;
    public String name;
    public int hours;

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

    public Theme(LinkedHashMap theme){
        var test = this.getClass().getFields();
        Arrays.stream(this.getClass().getFields()).forEach( field -> {
            System.out.println(field.getName());
            if(theme.containsKey(field.getName())){
                //setter.setValue(answer, theme.get(setter.name()));
                try {
                    field.set(this, theme.get(field.getName()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalInitialDataException("Ошибка при чтении переменной " + field.getName() + " из класса " + Theme.class.getName());
            }
        });
//        Arrays.stream(ThemeSettersEnum.values()).forEach(setter -> {
//            if(theme.containsKey(setter.name())){
//                //setter.setValue(answer, theme.get(setter.name()));
//                Method method = this.getClass().getField();
//            } else {
//                throw new IllegalInitialDataException("Ошибка при чтении переменной " + setter.name() + " из класса " + Theme.class.getName());
//            }
//        });
    }

    // Содержит названия переменных класса, а также
    enum ThemeSettersEnum {
        id {
            @Override
            void setValue(Theme theme, Object value) {
                theme.setId((Integer) value);
            }
        },
        name {
            @Override
            void setValue(Theme theme, Object value) {
                theme.setName((String) value);
            }
        },
        hours {
            @Override
            void setValue(Theme theme, Object value) {
                theme.setHours((Integer) value);
            }
        };

        abstract void setValue(Theme theme, Object value);
    }
}
