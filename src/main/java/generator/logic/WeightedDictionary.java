package main.java.generator.logic;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedDictionary {
    private final NavigableMap<Long, String> map = new TreeMap<>();
    private final Random random = new Random();
    private long totalWeight = 0;


    public void addEntry(String value, long weight) {
        if (weight > 0) {
            totalWeight += weight;
            map.put(totalWeight, value);
        }
    }

    public String getRandomValue() {
        if (totalWeight == 0) return "BrakDanych";

        long value = random.nextLong(totalWeight);

        return map.higherEntry(value).getValue();
    }
}