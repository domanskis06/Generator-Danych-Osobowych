package main.java.generator.logic;

import java.util.Random;

public class IdentityCardGenerator {

    private final Random random = new Random();
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generate() {
        int[] components = new int[9];

        for (int i = 0; i < 3; i++) {
            components[i] = 10 + random.nextInt(26);
        }

        for (int i = 4; i < 9; i++) {
            components[i] = random.nextInt(10);
        }

        int checksum = calculateChecksum(components);
        components[3] = checksum;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(LETTERS.charAt(components[i] - 10));
        }
        for (int i = 3; i < 9; i++) {
            sb.append(components[i]);
        }

        return sb.toString();
    }

    private int calculateChecksum(int[] components) {
        int[] weights = {7, 3, 1, 0, 7, 3, 1, 7, 3};
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            if (i == 3) continue;
            sum += components[i] * weights[i];
        }
        return sum % 10;
    }
}
