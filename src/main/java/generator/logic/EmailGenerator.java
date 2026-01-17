package main.java.generator.logic;

import main.java.generator.model.Person;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EmailGenerator {

    private final Random random = new Random();

    private final List<String> domains = Arrays.asList(
            "gmail.com", "wp.pl", "onet.pl", "interia.pl",
            "o2.pl", "outlook.com", "yahoo.com"
    );

    private final List<String> separators = Arrays.asList(".", "_", "");

    /**
     * Główna metoda generująca e-mail na podstawie danych osoby.
     */
    public String generate(Person person) {
        List<String> parts = new ArrayList<>();

        // 1. Decydujemy jakie dane bierzemy (1-3 elementy)
        // Pula dostępnych danych
        boolean useFirstName = true; // Imię dajemy prawie zawsze
        boolean useLastName = random.nextBoolean() || !useFirstName;
        boolean useBirthDate = random.nextBoolean();

        // 2. Przygotowujemy części (z losową długością dla tekstów)
        if (useFirstName) {
            parts.add(prepareString(person.getFirstName()));
        }
        if (useLastName) {
            parts.add(prepareString(person.getLastName()));
        }

        // Tasujemy imię i nazwisko
        Collections.shuffle(parts);

        // 3. Obsługa daty (nie może być pierwsza, jeśli są inne elementy)
        if (useBirthDate) {
            String datePart = prepareDate(person.getBirthDate());
            if (parts.isEmpty()) {
                parts.add(datePart);
            } else {
                // Wstawiamy datę na losową pozycję, ale nie na indeks 0
                int position = 1 + random.nextInt(parts.size());
                parts.add(position, datePart);
            }
        }

        // 4. Składanie w całość z losowymi przerywnikami
        StringBuilder emailName = new StringBuilder();
        String currentSeparator = separators.get(random.nextInt(separators.size()));

        for (int i = 0; i < parts.size(); i++) {
            emailName.append(parts.get(i));
            if (i < parts.size() - 1) {
                emailName.append(currentSeparator);
            }
        }

        // 5. Opcjonalne cyfry na końcu (jeśli na końcu nie ma daty)
        String lastElement = parts.get(parts.size() - 1);
        boolean isDateAtEnd = lastElement.matches("\\d+");

        if (!isDateAtEnd && random.nextInt(100) < 30) { // 30% szans
            emailName.append(random.nextInt(1000));
        }

        // 6. Dodanie domeny
        String domain = domains.get(random.nextInt(domains.size()));

        return emailName.toString().toLowerCase() + "@" + domain;
    }

    /**
     * Usuwa polskie znaki, bierze losową długość ciągu.
     */
    private String prepareString(String input) {
        if (input == null || input.isEmpty()) return "user";

        // Usuwanie polskich znaków
        String normalized = removeAccents(input.toLowerCase());

        // Losowa długość (całość lub np. pierwsze 3-4 litery)
        if (random.nextBoolean() && normalized.length() > 3) {
            int len = 3 + random.nextInt(normalized.length() - 2);
            return normalized.substring(0, Math.min(len, normalized.length()));
        }

        return normalized;
    }

    private String prepareDate(LocalDate date) {
        if (date == null) return String.valueOf(random.nextInt(99));
        // Losujemy czy pełny rok (1995) czy końcówka (95)
        return random.nextBoolean() ?
                String.valueOf(date.getYear()) :
                String.valueOf(date.getYear()).substring(2);
    }

    private String removeAccents(String input) {
        return input.replace("ą", "a")
                .replace("ć", "c")
                .replace("ę", "e")
                .replace("ł", "l")
                .replace("ń", "n")
                .replace("ó", "o")
                .replace("ś", "s")
                .replace("ź", "z")
                .replace("ż", "z");
    }
}