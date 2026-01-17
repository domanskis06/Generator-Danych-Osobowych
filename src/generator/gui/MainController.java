package generator.gui;

import main.java.generator.export.CsvExporter;
import main.java.generator.export.JsonExporter;
import main.java.generator.export.SqlExporter;
import main.java.generator.logic.PersonGenerator;
import main.java.generator.model.Person;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private final MainView view;
    private List<Person> generatedPeople;
    private final PersonGenerator personGenerator;

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
            String text = view.getCountField().getText();
            int count = Integer.parseInt(text);

            if (count <= 0) {
                view.appendLog("Błąd: Liczba osób musi być większa od 0.");
                return;
            }

            view.appendLog("Rozpoczynam generowanie " + count + " osób...");

            // Generowanie danych
            generatedPeople.clear();
            for (int i = 0; i < count; i++) {
                generatedPeople.add(personGenerator.generate());
            }

            view.appendLog("Sukces: Wygenerowano " + generatedPeople.size() + " rekordów.");
            view.enableExportButtons(true);

        } catch (NumberFormatException ex) {
            view.appendLog("Błąd: Wprowadź poprawną liczbę całkowitą.");
            JOptionPane.showMessageDialog(view, "Proszę podać poprawną liczbę.", "Błąd", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            view.appendLog("Błąd podczas generowania: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void exportData(String format) {
        if (generatedPeople.isEmpty()) {
            view.appendLog("Brak danych do eksportu.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Zapisz plik " + format);
        fileChooser.setSelectedFile(new File("people." + format.toLowerCase()));

        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String path = fileToSave.getAbsolutePath();

            try {
                view.appendLog("Eksportowanie do " + format + "...");

                switch (format) {
                    case "CSV":
                        new CsvExporter().export(generatedPeople, path);
                        break;
                    case "JSON":
                        new JsonExporter().export(generatedPeople, path);
                        break;
                    case "SQL":
                        new SqlExporter().export(generatedPeople, path);
                        break;
                }

                view.appendLog("Sukces: Zapisano plik w " + path);
                JOptionPane.showMessageDialog(view, "Dane wyeksportowane pomyślnie!", "Sukces", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                view.appendLog("Błąd eksportu: " + ex.getMessage());
                JOptionPane.showMessageDialog(view, "Błąd podczas zapisu pliku: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}