package main.java.generator.logic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DictionaryLoader {

    /**
     * Wczytuje plik CSV z wagami (Format: IMIĘ, PŁEĆ, LICZBA).
     * Zwraca inteligentny słownik WeightedDictionary.
     */
    public WeightedDictionary loadWeightedNames(String fileName) {
        WeightedDictionary dictionary = new WeightedDictionary();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Plik nie znaleziony: " + fileName);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                br.readLine();

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");

                    if (values.length < 3) continue;

                    String name = values[0].trim();
                    long count = Long.parseLong(values[2].trim());
                    dictionary.addEntry(name, count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Błąd podczas wczytywania pliku: " + fileName);
        }

        return dictionary;
    }

    /**
     * Wczytuje plik CSV z miastami i ich populacją oraz plik CSV z nazwiskami i ich popularnością.
     * Format: MIASTO,POPULACJA
     */
    public WeightedDictionary loadWeightedData(String fileName) {
        WeightedDictionary dictionary = new WeightedDictionary();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) throw new IllegalArgumentException("Plik nie znaleziony: " + fileName);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                br.readLine(); // Pomijamy nagłówek

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");

                    // Zabezpieczenie na wypadek pustych linii
                    if (values.length < 2) continue;

                    String city = values[0].trim();
                    // Parsujemy drugą kolumnę jako wagę (populację)
                    long population = Long.parseLong(values[1].trim());

                    dictionary.addEntry(city, population);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    /**
     * Metoda dla zwykłych list (np. ulice, adres), które nie mają wag w CSV.
     * Zwraca zwykłą List<String>.
     */
    public List<String> loadSimpleList(String fileName) {
        List<String> list = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) throw new IllegalArgumentException("Plik nie znaleziony: " + fileName);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        list.add(line.trim());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}