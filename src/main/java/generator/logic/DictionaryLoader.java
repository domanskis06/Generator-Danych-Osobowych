package main.java.generator.logic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryLoader {

    /**
     * Wczytuje plik csv z wagami (Format: IMIĘ, PŁEĆ, LICZBA).
     * Separator: przecinek
     */
    public WeightedDictionary loadWeightedNames(String fileName) {
        WeightedDictionary dictionary = new WeightedDictionary();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Plik nie znaleziony: " + fileName);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;

                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] values = line.split(",");

                    if (values.length < 3) continue;

                    String name = values[0].trim();

                    try {
                        String countStr = values[2].trim().replace(" ", "").replace("\u00A0", "");
                        long count = Long.parseLong(countStr);
                        dictionary.addEntry(name, count);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    /**
     * Wczytuje plik CSV z miastami i ich populacją oraz plik CSV z nazwiskami.
     * Format: NAZWA, WAGA
     * Separator: przecinek
     */
    public WeightedDictionary loadWeightedData(String fileName) {
        WeightedDictionary dictionary = new WeightedDictionary();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) throw new IllegalArgumentException("Plik nie znaleziony: " + fileName);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;

                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] values = line.split(",");

                    if (values.length < 2) continue;

                    String key = values[0].trim();

                    try {
                        String weightStr = values[1].trim().replace(" ", "").replace("\u00A0", "");
                        long weight = Long.parseLong(weightStr);
                        dictionary.addEntry(key, weight);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    /**
     * Format: MIASTO; WOJEWÓDZTWO; LICZBA
     * Separator: średnik
     */
    public WeightedDictionary loadWeightedCities(String fileName) {
        WeightedDictionary dictionary = new WeightedDictionary();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Plik nie znaleziony: " + fileName);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;

                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] values = line.split(";");

                    if (values.length >= 3) {
                        String cityName = values[0].trim();

                        try {
                            String populationStr = values[2].trim().replace(" ", "").replace("\u00A0", "");
                            long population = Long.parseLong(populationStr);

                            dictionary.addEntry(cityName, population);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    /**
     * Metoda dla zwykłych list (ulice.txt, imiona_zenskie.txt).
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

    /**
     * Mapuje Miasto -> Województwo
     */
    public Map<String, String> loadCityVoivodeshipMap(String fileName) {
        Map<String, String> map = new HashMap<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) throw new IllegalArgumentException("Plik nie znaleziony: " + fileName);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] values = line.split(";"); // Średnik!

                    if (values.length >= 2) {
                        String city = values[0].trim();
                        String voivodeship = values[1].trim();

                        map.put(city, voivodeship);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}