package space.irsi7.dao;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.yaml.snakeyaml.Yaml;
import space.irsi7.models.Config;
import space.irsi7.models.Course;
import space.irsi7.models.Student;
import space.irsi7.models.Theme;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class YamlDAO {

    ObjectMapper mapper = new YAMLMapper().enable(SerializationFeature.INDENT_OUTPUT);
    Yaml yaml = new Yaml();

    public void writeYAML(Collection arrayList, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        yaml.dump(arrayList, writer);
    }

    public ArrayList<Student> readYamlStudentsArray(String path) throws IOException {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(path);
        ArrayList<Student> answer = yaml.load(inputStream, Student.class);
        return yaml.load(inputStream);
    }


    public Map<Integer, Student> readYamlStudents(String path) throws IOException {
        var test = readYamlStudentsArray(path);
        return readYamlStudentsArray(path).stream().collect(Collectors.toMap(Student::getId, s -> s));
    }

    public ArrayList<Course> readYamlEduPlans(String path) throws IOException {
        return mapper.readValue(new File(path), new TypeReference<>() {
        });
    }

    public ArrayList<Theme> readYamlThemes(String path) throws IOException {
        return mapper.readValue(new File(path), new TypeReference<>() {
        });
    }

    public Config readYamlConfig(String path) throws IOException {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(path);
        return yaml.loadAs(inputStream, Config.class);
    }

}
