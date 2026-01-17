package main.java.generator.export;

import main.java.generator.model.Person;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class JsonExporter implements DataExporter {

    @Override
    public void export(List<Person> people, String filePath, List<String> columns) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("[\n");

            for (int i = 0; i < people.size(); i++) {
                Person p = people.get(i);
                writer.write("  {\n");

                boolean first = true;

                if (columns.contains("Imię")) first = writeField(writer, "firstName", p.getFirstName(), first);
                if (columns.contains("Nazwisko")) first = writeField(writer, "lastName", p.getLastName(), first);
                if (columns.contains("Płeć")) first = writeField(writer, "gender", p.getGender().toString(), first);
                if (columns.contains("Wiek") && p.getBirthDate() != null) {
                    int age = Period.between(p.getBirthDate(), LocalDate.now()).getYears();
                    first = writeField(writer, "age", String.valueOf(age), first);
                }
                if (columns.contains("Data Urodzenia")) first = writeField(writer, "birthDate", p.getBirthDate().toString(), first);
                if (columns.contains("PESEL")) first = writeField(writer, "pesel", p.getPesel(), first);
                if (columns.contains("NIP")) first = writeField(writer, "nip", p.getNip(), first);
                if (columns.contains("Nr Dowodu")) first = writeField(writer, "idCardNumber", p.getIdCardNumber(), first);

                if (columns.contains("Miasto")) first = writeField(writer, "city", p.getAddress().getCity(), first);
                if (columns.contains("Województwo")) first = writeField(writer, "voivodeship", p.getAddress().getVoivodeship(), first);
                if (columns.contains("Ulica")) first = writeField(writer, "street", p.getAddress().getStreet() + " " + p.getAddress().getHouseNumber(), first);
                if (columns.contains("Kod Pocztowy")) first = writeField(writer, "zipCode", p.getAddress().getZipCode(), first);

                if (columns.contains("Telefon")) first = writeField(writer, "phone", p.getContact().getPhoneNumber(), first);
                if (columns.contains("Email")) first = writeField(writer, "email", p.getContact().getEmail(), first);

                writer.write("\n  }");
                if (i < people.size() - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("]");
        }
    }

    private boolean writeField(BufferedWriter writer, String key, String value, boolean isFirst) throws IOException {
        if (value == null) value = "";
        if (!isFirst) {
            writer.write(",\n");
        }
        writer.write("    \"" + key + "\": \"" + value + "\"");
        return false;
    }
}