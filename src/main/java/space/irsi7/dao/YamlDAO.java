package space.irsi7.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class YamlDAO {

    ObjectMapper mapper = new YAMLMapper().enable(SerializationFeature.INDENT_OUTPUT);;

    public void writeYAML(ArrayList arrayList, String path) throws IOException {
        mapper.writeValue(new File(path), arrayList);
    }

    public ArrayList readYAML(String path) throws IOException {
        return mapper.readValue(new File(path), ArrayList.class);
    }

}
