package main.java.generator.export;

import main.java.generator.model.Person;
import main.java.generator.model.Address;
import main.java.generator.model.Contact;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonExporter implements DataExporter {

    @Override
    public void export(List<Person> people, String filePath) throws IOException {
        if (people == null || filePath == null) {
            throw new IllegalArgumentException("Lista osób oraz ścieżka nie mogą być puste.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("[\n");

            for (int i = 0; i < people.size(); i++) {
                Person p = people.get(i);
                writer.write("  {\n");

                writer.write(formatField("firstName", p.getFirstName(), true));
                writer.write(formatField("lastName", p.getLastName(), true));
                writer.write(formatField("birthDate", p.getBirthDate() != null ? p.getBirthDate().toString() : "", true));
                writer.write(formatField("gender", p.getGender() != null ? p.getGender().toString() : "", true));
                writer.write(formatField("pesel", p.getPesel(), true));
                writer.write(formatField("nip", p.getNip(), true));
                writer.write(formatField("idCardNumber", p.getIdCardNumber(), true));

                Address addr = p.getAddress();
                writer.write("    \"address\": {\n");
                if (addr != null) {
                    writer.write(formatField("street", addr.getStreet(), true, 6));
                    writer.write(formatField("houseNumber", addr.getHouseNumber(), true, 6));
                    writer.write(formatField("city", addr.getCity(), true, 6));
                    writer.write(formatField("zipCode", addr.getZipCode(), true, 6));
                    writer.write(formatField("voivodeship", addr.getVoivodeship(), false, 6));
                }
                writer.write("    },\n");

                Contact cont = p.getContact();
                writer.write("    \"contact\": {\n");
                if (cont != null) {
                    writer.write(formatField("email", cont.getEmail(), true, 6));
                    writer.write(formatField("phoneNumber", cont.getPhoneNumber(), false, 6));
                }
                writer.write("    }\n");

                writer.write("  }");

                if (i < people.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("]");
        }
    }

    private String formatField(String key, String value, boolean hasNext) {
        return formatField(key, value, hasNext, 4);
    }

    private String formatField(String key, String value, boolean hasNext, int indentSize) {
        String safeValue = (value == null) ? "" : value;
        String indent = " ".repeat(indentSize);
        String line = indent + "\"" + key + "\": \"" + safeValue + "\"";
        return hasNext ? line + ",\n" : line + "\n";
    }
}
