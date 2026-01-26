package main.java.generator.logic;

import main.java.generator.model.Person;
import java.time.LocalDate;
import java.util.Random;

public class DataCorruptor {

    private final Random random = new Random();

    public void corrupt(Person person, double chance) {

        if (chance <= 0.0) return;

        if (random.nextDouble() < chance) {
            person.setLastName(null);
        }

        if (random.nextDouble() < chance) {
            String pesel = person.getPesel();
            if (pesel != null && pesel.length() > 6) {
                int pos = random.nextInt(pesel.length());
                person.setPesel(pesel.substring(0, pos) + "X" + pesel.substring(pos + 1));
            }
        }

        if (random.nextDouble() < chance) {
            if (person.getContact() != null) {
                String email = person.getContact().getEmail();
                if (email != null) {
                    person.getContact().setEmail(email.replace("@", ""));
                }
            }
        }

        if (random.nextDouble() < chance) {
            if (person.getAddress() != null) {
                person.getAddress().setHouseNumber("");
            }
        }

        if (random.nextDouble() < chance) {
            String name = person.getFirstName();
            if (name != null && !name.isEmpty()) {
                person.setFirstName(name.toLowerCase());
            }
        }

        if (random.nextDouble() < chance) {
            String name = person.getFirstName();
            if (name != null) {
                if (random.nextBoolean()) {
                    person.setFirstName(" " + name);
                } else {
                    person.setFirstName(name + " ");
                }
            }
        }

        if (random.nextDouble() < chance) {
            String lastName = person.getLastName();
            if (lastName != null && lastName.length() > 2) {
                person.setLastName(swapChars(lastName));
            }
        }

        if (random.nextDouble() < chance) {
            person.setBirthDate(LocalDate.now().minusDays(random.nextInt(365)));
        }

        if (random.nextDouble() < chance) {
            String phone = person.getContact().getPhoneNumber();
            if (phone != null && phone.length() > 5) {
                person.getContact().setPhoneNumber(phone.substring(0, phone.length() - 1));
            }
        }
    }

    private String swapChars(String input) {
        char[] chars = input.toCharArray();
        int pos = random.nextInt(chars.length - 1);
        char temp = chars[pos];
        chars[pos] = chars[pos + 1];
        chars[pos + 1] = temp;
        return new String(chars);
    }
}