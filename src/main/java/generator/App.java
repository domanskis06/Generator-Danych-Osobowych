package main.java.generator;

import generator.gui.MainController;
import generator.gui.MainView;
import main.java.generator.export.CsvExporter;
import main.java.generator.export.JsonExporter;
import main.java.generator.export.SqlExporter;
import main.java.generator.logic.PersonGenerator;
import main.java.generator.model.Person;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Opcjonalnie: ustawienie wyglądu systemowego
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainView view = new MainView();
            new MainController(view);
        });





        System.out.println("GENERATOR DANYCH OSOBOWYCH");
        System.out.println();

        long startTime = System.currentTimeMillis();
        PersonGenerator generator = new PersonGenerator();
        long endTime = System.currentTimeMillis();

        System.out.println("--> Słowniki załadowane w czasie: " + (endTime - startTime) + " ms");
        System.out.println("--> Generowanie przykładowych rekordów:\n");

        List<Person> people = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Person p = generator.generate();
            people.add(p);

            printPersonDetails(i, p);
        }

        String outputFolder = "output";

        try {
            Files.createDirectories(Paths.get(outputFolder));
        } catch (IOException e) {
            System.err.println("Nie udało się stworzyć folderu eksportu: " + e.getMessage());
            return;
        }
        try {
            System.out.println("Eksportowanie danych do CSV");
            CsvExporter exporter = new CsvExporter();
            exporter.export(people, outputFolder + File.separator + "people.csv");
        } catch (IOException e) {
            System.err.println("Błąd eksportu: " + e.getMessage());
        }
        try {
            System.out.println("Eksportowanie danych do JSON");
            JsonExporter exporter = new JsonExporter();
            exporter.export(people, outputFolder + File.separator + "people.json");
        } catch (IOException e) {
            System.err.println("Błąd eksportu: " + e.getMessage());
        }
        try {
            System.out.println("Eksportowanie danych do sql");
            SqlExporter exporter = new SqlExporter();
            exporter.export(people, outputFolder + File.separator + "people.sql");
        } catch (IOException e) {
            System.err.println("Błąd eksportu: " + e.getMessage());
        }
    }



    private static void printPersonDetails(int index, Person p) {
        System.out.println("REKORD #" + index);

        System.out.println("Imię i Nazwisko:  " + p.getFirstName() + " " + p.getLastName());
        System.out.println("Płeć:             " + p.getGender());

        System.out.println("Data urodzenia:   " + p.getBirthDate());
        System.out.println("PESEL:            " + p.getPesel());

        System.out.println("Adres:");
        System.out.println("   Miasto:        " + p.getAddress().getCity());
        System.out.println("   Województwo:   " + p.getAddress().getVoivodeship());
        System.out.println("   Ulica:         ul. " + p.getAddress().getStreet() + " " + p.getAddress().getHouseNumber());
        System.out.println("   Kod pocztowy:  " + p.getAddress().getZipCode());

        System.out.println("Kontakt:");
        System.out.println("   Nr telefonu:   " + p.getContact().getPhoneNumber());
        System.out.println("   Email:         " + p.getContact().getEmail());

        System.out.println("\n");
    }
}