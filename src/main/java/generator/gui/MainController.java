package main.java.generator.gui;

import main.java.generator.export.CsvExporter;
import main.java.generator.export.JsonExporter;
import main.java.generator.export.SqlExporter;
import main.java.generator.logic.DataCorruptor;
import main.java.generator.logic.PersonGenerator;
import main.java.generator.model.Person;
import main.java.generator.model.Gender;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

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

                // --- DODANY KOD STATYSTYK ---
                long total = generatedPeople.size();
                long males = generatedPeople.stream().filter(p -> p.getGender() == Gender.MALE).count();
                long females = generatedPeople.stream().filter(p -> p.getGender() == Gender.FEMALE).count();

                StringBuilder statsMessage = new StringBuilder();
                statsMessage.append("Zakończono generowanie danych.\n\n");
                statsMessage.append("Łącznie rekordów: ").append(total).append("\n");
                statsMessage.append("Mężczyzn: ").append(males).append("\n");
                statsMessage.append("Kobiet: ").append(females).append("\n");
        
                view.showStatistics(statsMessage.toString());
                // -----------------------------
        // List<Person> people = generator.generate(config);  <-- w miejscu gdzie masz listę osób
        
        // Generowanie i wyświetlanie raportu
        String report = createDistributionReport(generatedPeople);
        view.showStatisticsReport(report);
        
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

    private String createDistributionReport(List<Person> people) {
        StringBuilder sb = new StringBuilder();
        sb.append("RAPORT ROZKŁADU DANYCH (N=").append(people.size()).append(")\n");
        sb.append("========================================\n\n");

        // 1. Rozkład Płci
        long males = people.stream().filter(p -> p.getGender() == Gender.MALE).count();
        long females = people.stream().filter(p -> p.getGender() == Gender.FEMALE).count();
        sb.append("1. ROZKŁAD PŁCI:\n");
        sb.append(drawBar("Mężczyźni", (int) males, people.size()));
        sb.append(drawBar("Kobiety  ", (int) females, people.size()));
        sb.append("\n");

        // 2. Rozkład Wieku
        Map<String, Integer> ageGroups = new HashMap<>();
        // Inicjalizacja grup
        String[] groups = {"0-18", "19-30", "31-50", "51-65", "65+"};
        for(String g : groups) ageGroups.put(g, 0);

        LocalDate now = LocalDate.now();
        for (Person p : people) {
            // Zakładam, że Person ma getBirthDate(). Jeśli nie, trzeba pobrać z PESEL.
            int age = Period.between(p.getBirthDate(), now).getYears();
            if (age <= 18) ageGroups.put("0-18", ageGroups.get("0-18") + 1);
            else if (age <= 30) ageGroups.put("19-30", ageGroups.get("19-30") + 1);
            else if (age <= 50) ageGroups.put("31-50", ageGroups.get("31-50") + 1);
            else if (age <= 65) ageGroups.put("51-65", ageGroups.get("51-65") + 1);
            else ageGroups.put("65+", ageGroups.get("65+") + 1);
        }

        sb.append("2. ROZKŁAD WIEKU:\n");
        for (String group : groups) {
            sb.append(drawBar(String.format("%-5s", group), ageGroups.get(group), people.size()));
        }
        sb.append("\n");

        // 3. Top 5 Miast (jeśli Person ma adres)
        sb.append("3. TOP 5 MIAST:\n");
        Map<String, Long> cityCounts = people.stream()
            .collect(Collectors.groupingBy(p -> p.getAddress().getCity(), Collectors.counting()));
        
        cityCounts.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> {
                sb.append(drawBar(String.format("%-15s", entry.getKey()), entry.getValue().intValue(), people.size()));
            });

        return sb.toString();
    }

    // Metoda pomocnicza do rysowania pasków ASCII
    private String drawBar(String label, int value, int total) {
        if (total == 0) return label + ": 0\n";
        int barMaxLength = 30; // maksymalna długość paska
        int barLength = (int) (((double) value / total) * barMaxLength);
        
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < barLength; i++) bar.append("█");
        
        double percentage = ((double) value / total) * 100;
        return String.format("%s | %-30s | %d (%.1f%%)\n", label, bar.toString(), value, percentage);
    }
}