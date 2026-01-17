package main.java.generator.gui;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private JTextField countField;
    private JTextField minAgeField;
    private JTextField maxAgeField;
    private JComboBox<String> genderBox;
    private JButton generateButton;
    private JTextArea logArea;
    private JButton exportCsvButton;
    private JButton exportJsonButton;
    private JButton exportSqlButton;

    public MainView() {
        setTitle("Generator Danych Osobowych");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));


        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        controlPanel.add(new JLabel("Liczba osób:"));
        countField = new JTextField("10", 5);
        controlPanel.add(countField);

        controlPanel.add(new JLabel("Wiek min:"));
        minAgeField = new JTextField("0", 3);
        controlPanel.add(minAgeField);

        controlPanel.add(new JLabel("Wiek max:"));
        maxAgeField = new JTextField("100", 3);
        controlPanel.add(maxAgeField);

        controlPanel.add(new JLabel("Płeć:"));
        String[] genders = {"Wszystkie", "Kobieta", "Mężczyzna"};
        genderBox = new JComboBox<>(genders);
        controlPanel.add(genderBox);

        generateButton = new JButton("Generuj");
        controlPanel.add(generateButton);

        add(controlPanel, BorderLayout.NORTH);


        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Logi aplikacji"));

        add(scrollPane, BorderLayout.CENTER);


        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        exportPanel.setBorder(BorderFactory.createTitledBorder("Eksport danych"));

        exportCsvButton = new JButton("Zapisz do CSV");
        exportJsonButton = new JButton("Zapisz do JSON");
        exportSqlButton = new JButton("Zapisz do SQL");

        enableExportButtons(false);

        exportPanel.add(exportCsvButton);
        exportPanel.add(exportJsonButton);
        exportPanel.add(exportSqlButton);

        add(exportPanel, BorderLayout.SOUTH);
    }

    public void enableExportButtons(boolean enabled) {
        exportCsvButton.setEnabled(enabled);
        exportJsonButton.setEnabled(enabled);
        exportSqlButton.setEnabled(enabled);
    }

    public void appendLog(String text) {
        logArea.append(text + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }


    public JTextField getCountField() { return countField; }
    public JTextField getMinAgeField() { return minAgeField; }
    public JTextField getMaxAgeField() { return maxAgeField; }
    public JComboBox<String> getGenderBox() { return genderBox; }
    public JButton getGenerateButton() { return generateButton; }
    public JButton getExportCsvButton() { return exportCsvButton; }
    public JButton getExportJsonButton() { return exportJsonButton; }
    public JButton getExportSqlButton() { return exportSqlButton; }
}