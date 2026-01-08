package main.java.generator.export;

import main.java.generator.model.Person;
import main.java.generator.model.Address;
import main.java.generator.model.Contact;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvExporter implements DataExporter {


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void exportData(List<Person> data, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {


            writer.write("Imię;Nazwisko;Data Urodzenia;Płeć;PESEL;NIP;Nr Dowodu;Miasto;Ulica;Kod Pocztowy;Telefon;Email");
            writer.newLine();


            for (Person person : data) {

                String city = (person.getAddress() != null) ? person.getAddress().getCity() : "";
                String street = (person.getAddress() != null) ? person.getAddress().getStreet() : "";
                String postalCode = (person.getAddress() != null) ? person.getAddress().getPostalCode() : "";

                String phone = (person.getContact() != null) ? person.getContact().getPhoneNumber() : "";
                String email = (person.getContact() != null) ? person.getContact().getEmail() : "";

                String birthDateStr = (person.getBirthDate() != null) ? person.getBirthDate().format(DATE_FORMATTER) : "";


                String line = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s",
                        person.getFirstName(),
                        person.getLastName(),
                        birthDateStr,
                        person.getGender(),
                        person.getPesel(),
                        person.getNip(),
                        person.getIdCardNumber(),
                        city,
                        street,
                        postalCode,
                        phone,
                        email
                );

                writer.write(line);
                writer.newLine();
            }
        }
    }
}