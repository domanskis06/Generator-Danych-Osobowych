# Generator Danych Osobowych

Aplikacja desktopowa napisana w języku Java, służąca do generowania realistycznych danych testowych dla obywateli Polski. Narzędzie dedykowane jest programistom i testerom oprogramowania, umożliwiając tworzenie dużych zbiorów danych do testów wydajnościowych, integracyjnych oraz weryfikacji walidatorów.

Projekt implementuje algorytmy generowania polskich numerów identyfikacyjnych takich jak: PESEL, NIP oraz numeru dowodu osobistego i posiada unikalny moduł zaszumiania danych do testowania odporności systemów.

## Spis treści

1. [Opis funkcjonalności](#opis-funkcjonalności)
2. [Technologie](#technologie)
3. [Struktura projektu](#struktura-projektu)
4. [Wymagania i instalacja](#wymagania-i-instalacja)
5. [Instrukcja obsługi](#instrukcja-obsługi)
6. [Licencja](#licencja)

---

## Opis funkcjonalności

### 1. Generowanie realistycznych danych
Aplikacja korzysta z ważonych słowników, co zapewnia statystyczne prawdopodobieństwo występowania imion i nazwisk zgodne z rzeczywistością.
* **Dane osobowe:** Imiona i nazwiska dobierane na podstawie płci.
* **Adresy:** Zachowana spójność geograficzna.
* **Kontakt:** Adresy e-mail generowane na podstawie imienia i nazwiska; numery telefonów z prefiksami polskich operatorów.

### 2. Algorytmy i walidacja
Wszystkie numery identyfikacyjne są generowane zgodnie z oficjalnymi algorytmami:
* **PESEL:** Zgodny z datą urodzenia i płcią, zawiera poprawną cyfrę kontrolną.
* **NIP:** Generowany z poprawną sumą kontrolną.
* **Numer Dowodu Osobistego:** Format (3 litery + 6 cyfr) z poprawną sumą kontrolną.

### 3. Eksport danych
Możliwość zapisu wygenerowanych rekordów do formatów:
* **CSV:** format tekstowy oddzielony średnikami.
* **JSON:** format obiektowy.
* **SQL:** baza danych.

Użytkownik ma możliwość dynamicznego wyboru kolumn, które mają znaleźć się w pliku wynikowym.

### 4. Moduł Zaszumiania Danych (Data Corruption)
Funkcja umożliwiająca celowe wprowadzanie błędów do generowanych danych w celu testowania scenariuszy negatywnych. Poziom zaszumienia regulowany jest suwakiem (0-50%).

Typy generowanych błędów:
* **Podróżnik w czasie:** Ustawienie daty urodzenia na dzień jutrzejszy lub dalszą przyszłość (symulacja błędu logicznego w systemach, np. ujemny wiek).
* **Czeski błąd:** Losowa zamiana miejscami dwóch sąsiadujących liter w nazwisku (np. *Kowalsik* zamiast *Kowalski*).
* **Białe znaki:** Doklejenie spacji na początku lub na końcu imienia - błąd często niewidoczny wizualnie, a powodujący problemy przy porównywaniu ciągów znaków w bazach danych.
* **Uszkodzenie PESEL:** Wstrzyknięcie litery 'X' w losowe miejsce ciągu cyfr, co narusza format numeryczny i sumę kontrolną.
* **Błędy formatu:** Usunięcie znaku `@` z adresu e-mail oraz celowe skracanie numerów telefonów.
* **Błędy wielkości liter:** Zamiana liter w imieniu na małe.
* **Brak danych:** Celowe usuwanie wartości (ustawianie `null`) dla pól opcjonalnych, takich jak numer domu czy nazwisko.

---

## Technologie

* **Język:** Java 8+
* **GUI:** Java Swing

---

## Struktura projektu

Projekt podzielony jest na pakiety zgodnie ze wzorcem MVC:

* `src/main/java/generator/App.java` - Główna klasa uruchomieniowa.
* `src/main/java/generator/model` - Klasy reprezentujące dane (Person, Address, Contact).
* `src/main/java/generator/logic` - Logika biznesowa (generatory PESEL/NIP, wczytywanie słowników, logika zaszumiania).
* `src/main/java/generator/gui` - Warstwa prezentacji (MainView) i sterowania (MainController).
* `src/main/java/generator/export` - Klasy odpowiedzialne za zapis do plików (CSV, JSON, SQL).
* `src/main/resources` - Pliki słownikowe CSV i TXT (imiona, nazwiska, miasta, ulice).

---

## Wymagania i instalacja

### Wymagania systemowe
* Java Development Kit w wersji 8 lub nowszej.
* Środowisko programistyczne np. IntelliJ IDEA, Eclipse lub narzędzie budowania.

### Konfiguracja w IntelliJ IDEA
1. Pobierz kod źródłowy projektu.
2. Otwórz projekt w środowisku programistycznym.
3. Uruchom klasę `App.java`.

---

## Instrukcja obsługi

1. **Konfiguracja:** W panelu bocznym wprowadź liczbę osób do wygenerowania oraz zakres wieku. Możesz również filtrować wyniki według płci.
2. **Wybór danych:** Zaznacz checkboxy przy polach, które chcesz wygenerować (np. PESEL, NIP, Miasto). Niezaznaczone pola zostaną pominięte w tabeli oraz w plikach eksportu.
3. **Zaszumianie (Opcjonalne):** Ustaw suwak "Poziom błędów" na wartość wyższą niż 0%, aby część rekordów zawierała celowe błędy.
4. **Generowanie:** Kliknij przycisk "GENERUJ DANE". Wyniki zostaną wyświetlone w tabeli po prawej stronie.
5. **Eksport:** Wybierz format docelowy (CSV, JSON lub SQL), aby zapisać dane na dysku.
