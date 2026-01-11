package main.java.generator.export;

import main.java.generator.model.Person;
import main.java.generator.model.Address;
import main.java.generator.model.Contact;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SqlExporter implements DataExporter {

    @Override
    public void export(List<Person> people, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("CREATE TABLE IF NOT EXISTS people (");
            writer.newLine();
            writer.write("    id INT AUTO_INCREMENT PRIMARY KEY,");
            writer.newLine();
            writer.write("    first_name VARCHAR(50),");
            writer.newLine();
            writer.write("    last_name VARCHAR(50),");
            writer.newLine();
            writer.write("    birth_date DATE,");
            writer.newLine();
            writer.write("    gender VARCHAR(10),");
            writer.newLine();
            writer.write("    pesel VARCHAR(11),");
            writer.newLine();
            writer.write("    city VARCHAR(100),");
            writer.newLine();
            writer.write("    street VARCHAR(100),");
            writer.newLine();
            writer.write("    email VARCHAR(100),");
            writer.newLine();
            writer.write("    phone VARCHAR(20)");
            writer.newLine();
            writer.write(");");
            writer.newLine();
            writer.newLine();

            for (Person person : people) {
                Address address = person.getAddress();
                Contact contact = person.getContact();

                String birthDate = (person.getBirthDate() != null) ? "'" + person.getBirthDate() + "'" : "NULL";
                String city = (address != null && address.getCity() != null) ? "'" + escapeSql(address.getCity()) + "'" : "NULL";
                String street = (address != null && address.getStreet() != null) ? "'" + escapeSql(address.getStreet()) + "'" : "NULL";
                String email = (contact != null && contact.getEmail() != null) ? "'" + escapeSql(contact.getEmail()) + "'" : "NULL";
                String phone = (contact != null && contact.getPhoneNumber() != null) ? "'" + escapeSql(contact.getPhoneNumber()) + "'" : "NULL";

                String sql = String.format(
                        "INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('%s', '%s', %s, '%s', '%s', %s, %s, %s, %s);",
                        escapeSql(person.getFirstName()),
                        escapeSql(person.getLastName()),
                        birthDate,
                        person.getGender(),
                        person.getPesel(),
                        city,
                        street,
                        email,
                        phone
                );

                writer.write(sql);
                writer.newLine();
            }
        }
    }


    private String escapeSql(String input) {
        if (input == null) return "";
        return input.replace("'", "''");
    }
}
