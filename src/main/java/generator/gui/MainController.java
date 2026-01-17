package main.java.generator.gui;

import main.java.generator.export.CsvExporter;
import main.java.generator.export.JsonExporter;
import main.java.generator.export.SqlExporter;
import main.java.generator.logic.DataCorruptor;
import main.java.generator.logic.PersonGenerator;
import main.java.generator.model.Person;
import main.java.generator.model.Gender;

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainController {

    private final MainView view;
    private final PersonGenerator personGenerator;
    private final List<Person> generatedPeople;
    private final DataCorruptor dataCorruptor;

    public MainController(MainView view) {
        this.view = view;
        this.personGenerator = new PersonGenerator();
        this.generatedPeople = new ArrayList<>();
        this.dataCorruptor = new DataCorruptor();

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
        SwingUtilities.invokeLater(() -> {
            try {
                int count = Integer.parseInt(view.getCountField().getText());
                int minAge = Integer.parseInt(view.getMinAgeField().getText());
                int maxAge = Integer.parseInt(view.getMaxAgeField().getText());
                String selectedGenderStr = (String) view.getGenderBox().getSelectedItem();

                if (count <= 0) { view.setStatus("Błąd: Liczba <= 0"); return; }
                if (minAge > maxAge) { view.setStatus("Błąd: Wiek min > max"); return; }

                Gender targetGender = null;
                if ("Kobieta".equals(selectedGenderStr)) targetGender = Gender.FEMALE;
                else if ("Mężczyzna".equals(selectedGenderStr)) targetGender = Gender.MALE;

                List<String> activeColumns = new ArrayList<>();
                Map<String, JCheckBox> checkboxes = view.getColumnCheckboxes();

                for (Map.Entry<String, JCheckBox> entry : checkboxes.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        activeColumns.add(entry.getKey());
                    }
                }

                if (activeColumns.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Musisz wybrać przynajmniej jedną kolumnę!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                view.clearTable();
                view.setTableColumns(activeColumns.toArray());

                generatedPeople.clear();
                view.setStatus("Generowanie...");

                int attempts = 0;
                int maxAttempts = count * 5000;

                while (generatedPeople.size() < count && attempts < maxAttempts) {
                    Person p = personGenerator.generate();
                    attempts++;

                    boolean ageOk = isAgeInRange(p, minAge, maxAge);
                    boolean genderOk = (targetGender == null) || (p.getGender() == targetGender);

                    if (ageOk && genderOk) {

                        int noiseValue = view.getNoiseSlider().getValue();

                        if (noiseValue > 0) {
                            double chance = noiseValue / 100.0;
                            dataCorruptor.corrupt(p, chance);
                        }

                        Object[] rowData = new Object[activeColumns.size()];

                        for (int i = 0; i < activeColumns.size(); i++) {
                            String colName = activeColumns.get(i);
                            rowData[i] = getPersonValue(p, colName);
                        }

                        generatedPeople.add(p);

                        view.addRowToTable(rowData);
                    }
                }

                view.setStatus("Wygenerowano " + generatedPeople.size() + " rekordów.");
                view.enableExportButtons(!generatedPeople.isEmpty());

            } catch (Exception ex) {
                ex.printStackTrace();
                view.setStatus("Błąd: " + ex.getMessage());
            }
        });
    }

    private Object getPersonValue(Person p, String columnName) {
        switch (columnName) {
            case "Imię": return p.getFirstName();
            case "Nazwisko": return p.getLastName();
            case "Płeć": return p.getGender();
            case "Wiek": return Period.between(p.getBirthDate(), LocalDate.now()).getYears();
            case "Data Urodzenia": return p.getBirthDate();
            case "PESEL": return p.getPesel();
            case "NIP": return p.getNip();
            case "Nr Dowodu": return p.getIdCardNumber();
            case "Miasto": return p.getAddress().getCity();
            case "Województwo": return p.getAddress().getVoivodeship();
            case "Ulica": return p.getAddress().getStreet() + " " + p.getAddress().getHouseNumber();
            case "Kod Pocztowy": return p.getAddress().getZipCode();
            case "Telefon": return p.getContact().getPhoneNumber();
            case "Email": return p.getContact().getEmail();
            default: return "";
        }
    }

    private boolean isAgeInRange(Person person, int minAge, int maxAge) {
        if (person.getBirthDate() == null) return false;
        int age = Period.between(person.getBirthDate(), LocalDate.now()).getYears();
        return age >= minAge && age <= maxAge;
    }

    private void exportData(String format) {
        if (generatedPeople.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Najpierw wygeneruj dane!", "Błąd", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String> activeColumns = new ArrayList<>();
        Map<String, JCheckBox> checkboxes = view.getColumnCheckboxes();
        for (Map.Entry<String, JCheckBox> entry : checkboxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                activeColumns.add(entry.getKey());
            }
        }

        if (activeColumns.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nie wybrano żadnych kolumn do eksportu.", "Błąd", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Zapisz plik " + format);
        fileChooser.setSelectedFile(new File("dane." + format.toLowerCase()));

        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if(!path.toLowerCase().endsWith("." + format.toLowerCase())) {
                path += "." + format.toLowerCase();
            }

            try {
                switch (format) {
                    case "CSV":
                        new CsvExporter().export(generatedPeople, path, activeColumns);
                        break;
                    case "JSON":
                        new JsonExporter().export(generatedPeople, path, activeColumns);
                        break;
                    case "SQL":
                        new SqlExporter().export(generatedPeople, path, activeColumns);
                        break;
                }
                view.setStatus("Zapisano plik: " + path);
                JOptionPane.showMessageDialog(view, "Eksport zakończony sukcesem!");
            } catch (Exception ex) {
                view.setStatus("Błąd zapisu!");
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Błąd zapisu: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}