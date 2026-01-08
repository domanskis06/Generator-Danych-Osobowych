package main.java.generator.export;

import model.Person;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvExporter extends DataExporter {

    public void exportData(List<Person> data, String filePath) throws IOException {
        // Używamy kodowania UTF-8, żeby polskie znaki (ą, ę, ś) działały poprawnie
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {

            // 1. Zapisujemy nagłówek kolumn
            writer.write("Imię;Nazwisko;Płeć;PESEL;Miasto;Województwo;Ulica");
            writer.newLine();

            // 2. Pętla po wszystkich osobach z listy
            for (Person person : data) {

                // Składamy linię tekstu oddzieloną średnikami
                String line = String.format("%s;%s;%s;%s;%s;%s;%s",
                        person.getFirstName(),
                        person.getLastName(),
                        person.getGender(),
                        person.getPesel(),
                        person.getCity(),
                        person.getStreet()
                );

                // Zapisujemy linię do pliku
                writer.write(line);
                writer.newLine();
            }
        }
    }
}