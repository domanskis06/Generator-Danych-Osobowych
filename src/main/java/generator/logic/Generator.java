package main.java.generator.logic;

public interface Generator<T> {
    /**
     * Główna metoda generująca obiekt typu T.
     * @return Wygenerowany obiekt.
     */
    T generate();
}