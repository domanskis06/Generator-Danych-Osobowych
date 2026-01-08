package main.java.generator.logic;

import java.time.LocalDate;
import java.util.Random;

public class PeselGenerator {

    private final Random random = new Random();

    public String generate(LocalDate birthDate, boolean isMale) {
        int year = birthDate.getYear();
        int month = birthDate.getMonthValue();
        int day = birthDate.getDayOfMonth();

        if (year >= 1800 && year <= 1899) month += 80;
        else if (year >= 2000 && year <= 2099) month += 20;
        else if (year >= 2100 && year <= 2199) month += 40;
        else if (year >= 2200 && year <= 2299) month += 60;

        StringBuilder pesel = new StringBuilder();

        pesel.append(String.format("%02d", year % 100));
        pesel.append(String.format("%02d", month));
        pesel.append(String.format("%02d", day));
        pesel.append(String.format("%03d", random.nextInt(1000)));

        int genderDigit = random.nextInt(5) * 2 + (isMale ? 1 : 0);
        pesel.append(genderDigit);

        pesel.append(calculateChecksum(pesel.toString()));

        return pesel.toString();
    }
    private int calculateChecksum(String pesel10) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(pesel10.charAt(i)) * weights[i];
        }
        int lastDigit = sum % 10;
        return (10 - lastDigit) % 10;
    }
}