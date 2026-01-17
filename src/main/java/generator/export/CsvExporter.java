package main.java.generator.export;

import main.java.generator.model.Person;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.StringJoiner;

public class CsvExporter implements DataExporter {

    @Override
    public void export(List<Person> data, String filePath, List<String> columns) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {

            StringJoiner headerJoiner = new StringJoiner(";");
            for (String col : columns) {
                headerJoiner.add(col);
            }
            writer.write(headerJoiner.toString());
            writer.newLine();

            for (Person person : data) {
                StringJoiner lineJoiner = new StringJoiner(";");

                for (String col : columns) {
                    String value = getValueForColumn(person, col);
                    lineJoiner.add(value);
                }

                writer.write(lineJoiner.toString());
                writer.newLine();
            }
        }
    }

    private String getValueForColumn(Person p, String colName) {
        switch (colName) {
            case "Imię": return p.getFirstName();
            case "Nazwisko": return p.getLastName();
            case "Płeć": return p.getGender().toString();
            case "Wiek":
                return p.getBirthDate() != null ?
                        String.valueOf(Period.between(p.getBirthDate(), LocalDate.now()).getYears()) : "";
            case "Data Urodzenia": return p.getBirthDate() != null ? p.getBirthDate().toString() : "";
            case "PESEL": return p.getPesel();
            case "NIP": return p.getNip() != null ? p.getNip() : "";
            case "Nr Dowodu": return p.getIdCardNumber() != null ? p.getIdCardNumber() : "";
            case "Miasto": return p.getAddress().getCity();
            case "Województwo": return p.getAddress().getVoivodeship();
            case "Ulica": return p.getAddress().getStreet() + " " + p.getAddress().getHouseNumber();
            case "Kod Pocztowy": return p.getAddress().getZipCode();
            case "Telefon": return p.getContact().getPhoneNumber();
            case "Email": return p.getContact().getEmail();
            default: return "";
        }
    }
}