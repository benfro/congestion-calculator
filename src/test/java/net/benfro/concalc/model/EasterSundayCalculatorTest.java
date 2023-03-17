package net.benfro.concalc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EasterSundayCalculatorTest {

    EasterSundayCalculator instance;

    @BeforeEach
    void setUp() {
        instance = new EasterSundayCalculator();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2021, 4, 4",
            "2022, 4, 17",
            "2023, 4, 9",
            "2024, 3, 31",
            "2025, 4, 20",
            "2026, 4, 5",
            "2027, 3, 28",
            "2028, 4, 16",
            "2029, 4, 1",
            "2030, 4, 21",
            "2031, 4, 13",
            "2032, 3, 28",
            "2033, 4, 17",
            "2034, 4, 9",
            "2035, 3, 25",
            "2036, 4, 13",
            "2037, 4, 5",
            "2038, 4, 25",
            "2039, 4, 10",
            "2040, 4, 1",
            "2041, 4, 21",
            "2042, 4, 6",
            "2043, 3, 29",
            "2044, 4, 17",
            "2045, 4, 9",
    })
    public void testCorrectEasterSunday(int year, int month, int day) {
        assertEquals(LocalDate.of(year, month, day), instance.calculateEasterSunday(year));
    }

}