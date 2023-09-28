package space.irsi7.interfaces;

import space.irsi7.exceptions.IllegalInitialDataException;
import space.irsi7.models.Course;

import java.util.Arrays;
import java.util.LinkedHashMap;

public abstract class ReadableWithException {

    enum SettersEnum{}

    ReadableWithException(){};

//    public static Object constructCourse (LinkedHashMap course){
//        ReadableWithException answer = new ReadableWithException();
//        Arrays.stream(Course.CourseSettersInterface.values()).forEach(setter -> {
//            if(course.containsKey(setter.name())){
//                setter.setter.set(answer, course.get(setter.name()));
//            } else {
//                //TODO: Спросить про исключения в потоках. Не получается бросить checked exception
//                throw new IllegalInitialDataException("Ошибка при чтении переменной " + setter.name() + " из класса " + Course.class.getName());
//            }
//        });
//        return answer;
//    }

}
