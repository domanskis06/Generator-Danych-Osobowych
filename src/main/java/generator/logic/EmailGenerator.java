package main.java.generator.logic;

import main.java.generator.model.Person;
import java.time.LocalDate;
import java.util.*;

public class EmailGenerator {
    private final Random random = new Random();
    private final List<String> domains = Arrays.asList(
            "gmail.com", "wp.pl", "onet.pl", "interia.pl",
            "o2.pl", "outlook.com", "yahoo.com"
    );
    private final List<String> separators = Arrays.asList(".", "_", "");

    public String generate(Person person) {
        List<String> parts = new ArrayList<>();
        boolean useFirstName = true;
        boolean useLastName = random.nextBoolean() || !useFirstName;
        boolean useBirthDate = random.nextBoolean();
        if (useFirstName) {
            parts.add(prepareString(person.getFirstName()));
        }
        if (useLastName) {
            parts.add(prepareString(person.getLastName()));
        }
        Collections.shuffle(parts);

        if (useBirthDate) {
            String datePart = prepareDate(person.getBirthDate());
            if (parts.isEmpty()) {
                parts.add(datePart);
            } else {
                int position = 1 + random.nextInt(parts.size());
                parts.add(position, datePart);
            }
        }
        StringBuilder emailName = new StringBuilder();
        String currentSeparator = separators.get(random.nextInt(separators.size()));

        for (int i = 0; i < parts.size(); i++) {
            emailName.append(parts.get(i));
            if (i < parts.size() - 1) {
                emailName.append(currentSeparator);
            }
        }

        String lastElement = parts.get(parts.size() - 1);
        boolean isDateAtEnd = lastElement.matches("\\d+");

        if (!isDateAtEnd && random.nextInt(100) < 30) {
            emailName.append(random.nextInt(1000));
        }

        String domain = domains.get(random.nextInt(domains.size()));

        return emailName.toString().toLowerCase() + "@" + domain;
    }

    private String prepareString(String input) {
        if (input == null || input.isEmpty()) return "user";

        String normalized = removeAccents(input.toLowerCase());

        if (random.nextBoolean() && normalized.length() > 3) {
            int len = 3 + random.nextInt(normalized.length() - 2);
            return normalized.substring(0, Math.min(len, normalized.length()));
        }

        return normalized;
    }

    private String prepareDate(LocalDate date) {
        if (date == null) return String.valueOf(random.nextInt(99));
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