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
        boolean useFirstName = random.nextBoolean();
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
            int position = 1 + random.nextInt(parts.size());
            parts.add(position, datePart);
        }

        String lastElement = parts.get(parts.size() - 1);
        boolean isDateAtEnd = lastElement.matches("\\d+");

        if (!isDateAtEnd && random.nextInt(100) < 50) {
            parts.add(String.valueOf(random.nextInt(1000)));
        }

        StringBuilder emailName = new StringBuilder();
        int separatorPosition = parts.size() > 1 ? random.nextInt(parts.size() - 1) : -1;
        String currentSeparator = separators.get(random.nextInt(separators.size()));

        for (int i = 0; i < parts.size(); i++) {
            emailName.append(parts.get(i));
            if (i == separatorPosition) {
                emailName.append(currentSeparator);
            }
        }

        String domain = domains.get(random.nextInt(domains.size()));
        return emailName.toString().toLowerCase() + "@" + domain;
    }

    private String prepareString(String input) {
        String normalized = removeAccents(input.toLowerCase());

        if (random.nextBoolean()) {
            int randomLen = 1 + random.nextInt(normalized.length());
            return normalized.substring(0, randomLen);
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