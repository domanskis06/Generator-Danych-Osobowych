package main.java.generator.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainView extends JFrame {

    private JTextField countField;
    private JTextField minAgeField;
    private JTextField maxAgeField;
    private JComboBox<String> genderBox;
    private JButton generateButton;

    private Map<String, JCheckBox> columnCheckboxes;

    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    private JButton exportCsvButton;
    private JButton exportJsonButton;
    private JButton exportSqlButton;

    private JCheckBox noiseCheckbox;
    private JSlider noiseSlider;

    public MainView() {
        setTitle("Generator Danych Osobowych 2025");
        setSize(1300, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        sidePanel.setBackground(new Color(240, 240, 245));

        JScrollPane sideScroll = new JScrollPane(sidePanel);
        sideScroll.setBorder(null);
        sideScroll.setPreferredSize(new Dimension(280, 0));
        sideScroll.getVerticalScrollBar().setUnitIncrement(16);

        sidePanel.add(createTitle("Konfiguracja"));
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        sidePanel.add(createLabel("Liczba osób:"));
        countField = new JTextField("10");
        styleTextField(countField);
        sidePanel.add(countField);

        sidePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidePanel.add(createLabel("Wiek (min - max):"));
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        agePanel.setOpaque(false);
        minAgeField = new JTextField("18", 3);
        maxAgeField = new JTextField("80", 3);
        styleTextField(minAgeField);
        styleTextField(maxAgeField);
        agePanel.add(minAgeField);
        agePanel.add(new JLabel(" - "));
        agePanel.add(maxAgeField);
        agePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(agePanel);

        sidePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidePanel.add(createLabel("Płeć:"));
        String[] genders = {"Wszystkie", "Kobieta", "Mężczyzna"};
        genderBox = new JComboBox<>(genders);
        genderBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        genderBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(genderBox);

        sidePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        sidePanel.add(new JSeparator());
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        sidePanel.add(createTitle("Wybierz kolumny"));

        columnCheckboxes = new LinkedHashMap<>();
        addCheckbox(sidePanel, "Imię", true);
        addCheckbox(sidePanel, "Nazwisko", true);
        addCheckbox(sidePanel, "Płeć", true);
        addCheckbox(sidePanel, "Wiek", true);
        addCheckbox(sidePanel, "Data Urodzenia", false);
        addCheckbox(sidePanel, "PESEL", true);
        addCheckbox(sidePanel, "NIP", false);
        addCheckbox(sidePanel, "Nr Dowodu", false);
        addCheckbox(sidePanel, "Miasto", true);
        addCheckbox(sidePanel, "Województwo", false);
        addCheckbox(sidePanel, "Ulica", false);
        addCheckbox(sidePanel, "Kod Pocztowy", false);
        addCheckbox(sidePanel, "Telefon", true);
        addCheckbox(sidePanel, "Email", true);

        JLabel noiseLabel = new JLabel("Poziom błędów: 0%");
        noiseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        noiseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(noiseLabel);

        noiseSlider = new JSlider(0, 50, 0);
        noiseSlider.setBackground(new Color(240, 240, 245));
        noiseSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        noiseSlider.setPreferredSize(new Dimension(200, 40));

        noiseSlider.setMajorTickSpacing(25);
        noiseSlider.setMinorTickSpacing(5);
        noiseSlider.setPaintTicks(true);

        noiseSlider.addChangeListener(e -> {
            noiseLabel.setText("Poziom błędów: " + noiseSlider.getValue() + "%");
        });

        sidePanel.add(noiseSlider);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        generateButton = new JButton("GENERUJ DANE");
        generateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        generateButton.setBackground(new Color(70, 130, 180));
        generateButton.setForeground(Color.WHITE);
        generateButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        generateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(generateButton);

        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(createTitle("Eksport"));

        exportCsvButton = new JButton("CSV");
        exportJsonButton = new JButton("JSON");
        exportSqlButton = new JButton("SQL");

        styleExportButton(exportCsvButton);
        styleExportButton(exportJsonButton);
        styleExportButton(exportSqlButton);

        sidePanel.add(exportCsvButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidePanel.add(exportJsonButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidePanel.add(exportSqlButton);

        enableExportButtons(false);

        add(sideScroll, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        centerPanel.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusLabel = new JLabel("Gotowy.");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void addCheckbox(JPanel panel, String name, boolean checked) {
        JCheckBox cb = new JCheckBox(name);
        cb.setSelected(checked);
        cb.setOpaque(false);
        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        columnCheckboxes.put(name, cb);
        panel.add(cb);
    }

    private JLabel createTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 16));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private void styleTextField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleExportButton(JButton btn) {
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBackground(Color.WHITE);
    }

    public Map<String, JCheckBox> getColumnCheckboxes() {
        return columnCheckboxes;
    }

    public void enableExportButtons(boolean enabled) {
        exportCsvButton.setEnabled(enabled);
        exportJsonButton.setEnabled(enabled);
        exportSqlButton.setEnabled(enabled);
    }
    public void setStatus(String text) { statusLabel.setText(text); }
    public void clearTable() { tableModel.setRowCount(0); tableModel.setColumnCount(0); }

    public void setTableColumns(Object[] columns) {
        tableModel.setColumnIdentifiers(columns);
    }

    public void addRowToTable(Object[] rowData) { tableModel.addRow(rowData); }

    public JTextField getCountField() { return countField; }
    public JTextField getMinAgeField() { return minAgeField; }
    public JTextField getMaxAgeField() { return maxAgeField; }
    public JComboBox<String> getGenderBox() { return genderBox; }
    public JButton getGenerateButton() { return generateButton; }
    public JButton getExportCsvButton() { return exportCsvButton; }
    public JButton getExportJsonButton() { return exportJsonButton; }
    public JButton getExportSqlButton() { return exportSqlButton; }
    public JSlider getNoiseSlider() { return noiseSlider; }

    public void showStatisticsReport(String reportText) {
        JTextArea textArea = new JTextArea(reportText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 500));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Raport Statystyczny", JOptionPane.INFORMATION_MESSAGE);
    }
}