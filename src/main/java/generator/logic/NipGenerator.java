package main.java.generator.logic;

import java.util.Random;

public class NipGenerator {

    private final Random random = new Random();

    public String generate() {
        int[] nip = new int[10];
        int checksum;

        do {

            nip[0] = random.nextInt(9) + 1;
            for (int i = 1; i < 9; i++) {
                nip[i] = random.nextInt(10);
            }
            checksum = calculateChecksum(nip);
        } while (checksum == 10);

        nip[9] = checksum;

        StringBuilder sb = new StringBuilder();
        for (int digit : nip) {
            sb.append(digit);
        }
        return sb.toString();
    }

    private int calculateChecksum(int[] digits) {
        int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += digits[i] * weights[i];
        }
        return sum % 11;
    }
}
