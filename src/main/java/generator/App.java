package main.java.generator;

import main.java.generator.logic.PersonGenerator;
import main.java.generator.model.Person;

public class App {

    public static void main(String[] args) {
        System.out.println("GENERATOR DANYCH OSOBOWYCH");
        System.out.println();

        long startTime = System.currentTimeMillis();
        PersonGenerator generator = new PersonGenerator();
        long endTime = System.currentTimeMillis();

        System.out.println("--> Słowniki załadowane w czasie: " + (endTime - startTime) + " ms");
        System.out.println("--> Generowanie przykładowych rekordów:\n");

        for (int i = 1; i <= 10; i++) {
            Person p = generator.generate();

            printPersonDetails(i, p);
        }
    }

    
    private static void printPersonDetails(int index, Person p) {
        System.out.println("REKORD #" + index);
        System.out.println("------------------------------------------");

        System.out.println("Imię i Nazwisko:  " + p.getFirstName() + " " + p.getLastName());
        System.out.println("Płeć:             " + p.getGender());

        System.out.println("Data urodzenia:   " + p.getBirthDate());
        System.out.println("PESEL:            " + p.getPesel());

        System.out.println("Adres:");
        System.out.println("   Miasto:        " + p.getAddress().getCity());
        System.out.println("   Województwo:   " + p.getAddress().getVoivodeship());
        System.out.println("   Ulica:         ul. " + p.getAddress().getStreet() + " " + p.getAddress().getHouseNumber());
        System.out.println("   Kod pocztowy:  " + p.getAddress().getZipCode());

        System.out.println("------------------------------------------\n");
    }
}