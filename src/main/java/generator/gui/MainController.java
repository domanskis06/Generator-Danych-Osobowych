package main.java.generator.gui;

import main.java.generator.export.CsvExporter;
import main.java.generator.export.JsonExporter;
import main.java.generator.export.SqlExporter;
import main.java.generator.logic.PersonGenerator;
import main.java.generator.model.Person;
import main.java.generator.model.Gender; // Import enum Gender

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private final MainView view;
    private final PersonGenerator personGenerator;
    private final List<Person> generatedPeople;

    public MainController(MainView view) {
        this.view = view;
        this.personGenerator = new PersonGenerator();
        this.generatedPeople = new ArrayList<>();

        initListeners();
        view.setVisible(true);
    }

    private void initListeners() {
        view.getGenerateButton().addActionListener(e -> generateData());
        view.getExportCsvButton().addActionListener(e -> exportData("CSV"));
        view.getExportJsonButton().addActionListener(e -> exportData("JSON"));
        view.getExportSqlButton().addActionListener(e -> exportData("SQL"));
    }

    private void generateData() {
        try {

            String countText = view.getCountField().getText();
            String minAgeText = view.getMinAgeField().getText();
            String maxAgeText = view.getMaxAgeField().getText();
            String selectedGenderStr = (String) view.getGenderBox().getSelectedItem();

            int count = Integer.parseInt(countText);
            int minAge = Integer.parseInt(minAgeText);
            int maxAge = Integer.parseInt(maxAgeText);


            if (count <= 0) {
                view.appendLog("Błąd: Liczba osób musi być większa od 0.");
                return;
            }
            if (minAge < 0 || maxAge < 0 || minAge > maxAge) {
                view.appendLog("Błąd: Nieprawidłowy zakres wieku.");
                return;
            }
            int ABSOLUTE_MAX_AGE = 110;
            if (maxAge > ABSOLUTE_MAX_AGE) {
                String errorMsg = "Błąd: Maksymalny wiek (" + maxAge + ") jest zbyt wysoki. Limit to " + ABSOLUTE_MAX_AGE + " lat.";
                view.appendLog(errorMsg);
                JOptionPane.showMessageDialog(view, errorMsg, "Nieprawidłowy wiek", JOptionPane.ERROR_MESSAGE);
                return;
            }


            Gender targetGender = null;
            if ("Kobieta".equals(selectedGenderStr)) targetGender = Gender.FEMALE;
            else if ("Mężczyzna".equals(selectedGenderStr)) targetGender = Gender.MALE;

            view.appendLog("Generowanie " + count + " osób...");
            view.appendLog("-> Parametry: Wiek=" + minAge + "-" + maxAge + ", Płeć=" + selectedGenderStr);

            generatedPeople.clear();
            int attempts = 0;
            int maxAttempts = count * 2000;


            while (generatedPeople.size() < count && attempts < maxAttempts) {
                Person p = personGenerator.generate();
                attempts++;


                boolean ageOk = isAgeInRange(p, minAge, maxAge);
                boolean genderOk = (targetGender == null) || (p.getGender() == targetGender);

                if (ageOk && genderOk) {
                    generatedPeople.add(p);
                }
            }

            if (generatedPeople.size() < count) {
                view.appendLog("Ostrzeżenie: Wygenerowano tylko " + generatedPeople.size() + " osób (przekroczono limit prób).");
                view.appendLog("Możliwe, że kryteria są zbyt restrykcyjne dla losowego generatora.");
            } else {
                view.appendLog("Sukces: Wygenerowano " + generatedPeople.size() + " rekordów.");
            }

            view.enableExportButtons(!generatedPeople.isEmpty());

        } catch (NumberFormatException ex) {
            view.appendLog("Błąd: Wprowadź poprawne liczby.");
            JOptionPane.showMessageDialog(view, "Błędne dane wejściowe.", "Błąd", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            view.appendLog("Błąd krytyczny: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private boolean isAgeInRange(Person person, int minAge, int maxAge) {
        if (person.getBirthDate() == null) return false;
        int age = Period.between(person.getBirthDate(), LocalDate.now()).getYears();
        return age >= minAge && age <= maxAge;
    }

    private void exportData(String format) {
        if (generatedPeople.isEmpty()) return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Zapisz plik " + format);
        fileChooser.setSelectedFile(new File("people." + format.toLowerCase()));

        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                switch (format) {
                    case "CSV": new CsvExporter().export(generatedPeople, path); break;
                    case "JSON": new JsonExporter().export(generatedPeople, path); break;
                    case "SQL": new SqlExporter().export(generatedPeople, path); break;
                }
                view.appendLog("Zapisano: " + path);
                JOptionPane.showMessageDialog(view, "Eksport zakończony sukcesem!");
            } catch (Exception ex) {
                view.appendLog("Błąd zapisu: " + ex.getMessage());
            }
        }
    }
}