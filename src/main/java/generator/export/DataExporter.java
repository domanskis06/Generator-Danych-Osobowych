package main.java.generator.export;


import main.java.generator.model.Person;
import java.util.List;
import java.io.IOException;

public interface DataExporter {
    void export(List<Person> people, String filePath) throws IOException;
}
