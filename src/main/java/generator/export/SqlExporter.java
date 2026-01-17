package main.java.generator.export;

import main.java.generator.model.Person;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SqlExporter implements DataExporter {

    @Override
    public void export(List<Person> people, String filePath, List<String> columns) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            List<String> sqlColumns = new ArrayList<>();
            for (String col : columns) {
                sqlColumns.add(mapToSqlColumn(col));
            }

            writer.write("CREATE TABLE IF NOT EXISTS exported_people (");
            writer.newLine();
            writer.write("    id INT AUTO_INCREMENT PRIMARY KEY");

            for (String sqlCol : sqlColumns) {
                writer.write(",");
                writer.newLine();
                writer.write("    " + sqlCol + " VARCHAR(255)");
            }
            writer.write("\n);\n\n");

            for (Person p : people) {
                StringBuilder sql = new StringBuilder("INSERT INTO exported_people (");

                StringJoiner colJoiner = new StringJoiner(", ");
                for (String sqlCol : sqlColumns) {
                    colJoiner.add(sqlCol);
                }
                sql.append(colJoiner.toString());

                sql.append(") VALUES (");

                StringJoiner valJoiner = new StringJoiner(", ");
                for (String col : columns) {
                    String value = getValueForColumn(p, col);
                    valJoiner.add("'" + escapeSql(value) + "'");
                }
                sql.append(valJoiner.toString());
                sql.append(");");

                writer.write(sql.toString());
                writer.newLine();
            }
        }
    }

    private String mapToSqlColumn(String guiColumn) {
        switch (guiColumn) {
            case "Imię": return "first_name";
            case "Nazwisko": return "last_name";
            case "Płeć": return "gender";
            case "Wiek": return "age";
            case "Data Urodzenia": return "birth_date";
            case "PESEL": return "pesel";
            case "NIP": return "nip";
            case "Nr Dowodu": return "id_card";
            case "Miasto": return "city";
            case "Województwo": return "voivodeship";
            case "Ulica": return "street_address";
            case "Kod Pocztowy": return "zip_code";
            case "Telefon": return "phone";
            case "Email": return "email";
            default: return "unknown_col";
        }
    }

    private String getValueForColumn(Person p, String colName) {
        switch (colName) {
            case "Imię": return p.getFirstName();
            case "Nazwisko": return p.getLastName();
            case "Płeć": return p.getGender().toString();
            case "Wiek": return p.getBirthDate() != null ? String.valueOf(Period.between(p.getBirthDate(), LocalDate.now()).getYears()) : "0";
            case "Data Urodzenia": return p.getBirthDate() != null ? p.getBirthDate().toString() : "";
            case "PESEL": return p.getPesel();
            case "NIP": return p.getNip() != null ? p.getNip() : "";
            case "Nr Dowodu": return p.getIdCardNumber() != null ? p.getIdCardNumber() : "";
            case "Miasto": return p.getAddress().getCity();
            case "Województwo": return p.getAddress().getVoivodeship();
            case "Ulica": return p.getAddress().getStreet() + " " + p.getAddress().getHouseNumber();
            case "Kod Pocztowy": return p.getAddress().getZipCode();
            case "Telefon": return p.getContact().getPhoneNumber();
            case "Email": return p.getContact().getEmail();
            default: return "";
        }
    }

    private String escapeSql(String input) {
        if (input == null) return "";
        return input.replace("'", "''");
    }
}