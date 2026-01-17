package main.java.generator.logic;

import main.java.generator.model.Address;
import main.java.generator.model.Contact;
import main.java.generator.model.Gender;
import main.java.generator.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PersonGenerator implements Generator<Person> {

    private final PeselGenerator peselGenerator;
    private final EmailGenerator emailGenerator;
    private final Random random;

    private WeightedDictionary maleNames;
    private WeightedDictionary femaleNames;

    private WeightedDictionary maleSurnames;
    private WeightedDictionary femaleSurnames;

    private WeightedDictionary cities;
    private Map<String, String> cityToVoivodeship;
    private List<String> streets;
    private Map<String, List<String>> voivodeshipToZipCodes;


    public PersonGenerator() {
        this.peselGenerator = new PeselGenerator();
        this.emailGenerator = new EmailGenerator();
        this.random = new Random();
        loadDictionaries();
    }


    private void loadDictionaries() {
        DictionaryLoader loader = new DictionaryLoader();

        this.maleNames = loader.loadWeightedData3cols("imiona_meskie.csv");
        this.femaleNames = loader.loadWeightedData3cols("imiona_zenskie.csv");

        this.maleSurnames = loader.loadWeightedData("nazwiska_meskie.csv");
        this.femaleSurnames = loader.loadWeightedData("nazwiska_zenskie.csv");

        this.cities = loader.loadWeightedData3cols("miasta.csv");
        this.cityToVoivodeship = loader.loadCityVoivodeshipMap("miasta.csv");

        this.streets = loader.loadSimpleList("ulice.csv");
        this.voivodeshipToZipCodes = loader.loadZipCodesMap("kody_pocztowe.csv");
    }

    @Override
    public Person generate() {
        Person person = new Person();

        Gender gender = random.nextBoolean() ? Gender.FEMALE : Gender.MALE;
        person.setGender(gender);

        String firstName;
        if (gender == Gender.MALE) {
            firstName = maleNames.getRandomValue();
            if (firstName == null) firstName = "Jan";
        } else {
            firstName = femaleNames.getRandomValue();
            if (firstName == null) firstName = "Anna";
        }
        person.setFirstName(firstName);

        String lastName;
        if (gender == Gender.MALE) {
            lastName = maleSurnames.getRandomValue();
            if (lastName == null) lastName = "Kowalski";
        } else {
            lastName = femaleSurnames.getRandomValue();
            if (lastName == null) lastName = "Kowalska";
        }
        person.setLastName(lastName);

        int year = 1950 + random.nextInt(56);
        int dayOfYear = 1 + random.nextInt(365);
        LocalDate birthDate = LocalDate.ofYearDay(year, dayOfYear);
        person.setBirthDate(birthDate);

        String pesel = peselGenerator.generate(birthDate, gender);
        person.setPesel(pesel);

        Address address = new Address();

        String city = cities.getRandomValue();
        if (city == null) city = "Warszawa";
        address.setCity(city);

        String voivodeship = cityToVoivodeship.getOrDefault(city, "mazowieckie");
        address.setVoivodeship(voivodeship);

        if (!streets.isEmpty()) {
            address.setStreet(streets.get(random.nextInt(streets.size())));
        } else {
            address.setStreet("Polna");
        }

        address.setHouseNumber(String.valueOf(random.nextInt(150) + 1));

        List<String> validCodes = voivodeshipToZipCodes.get(voivodeship);

        if (validCodes != null && !validCodes.isEmpty()) {
            String randomCode = validCodes.get(random.nextInt(validCodes.size()));
            address.setZipCode(randomCode);
        } else {
            address.setZipCode(String.format("%02d-%03d", random.nextInt(100), random.nextInt(1000)));
        }
        person.setAddress(address);

        Contact contact = new Contact();
        String generatedEmail = emailGenerator.generate(person);
        contact.setEmail(generatedEmail);
        contact.setPhoneNumber("+48" + String.valueOf(100000000 + random.nextInt(900000000)));
        person.setContact(contact);

        return person;
    }
}