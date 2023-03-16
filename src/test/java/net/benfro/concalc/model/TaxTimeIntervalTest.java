package net.benfro.concalc.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TaxTimeIntervalTest {

    @Test
    void testStringConstructor() {
        final TaxTimeInterval taxTimeInterval = new TaxTimeInterval("01:00", "12:27", 7);
        assertAll(
                () -> assertEquals(LocalTime.of(1, 0), taxTimeInterval.getStart()),
                () -> assertEquals(LocalTime.of(12, 27), taxTimeInterval.getEnd()),
                () -> assertEquals(7, taxTimeInterval.getTaxRate())
        );
    }


    @ParameterizedTest
    @CsvSource({
            "01:05, true",
            "01:00, true",
            "12:27, true",
            "12:28, false",
    })
    void testIsInInterval(String time, boolean expected) {
        final TaxTimeInterval taxTimeInterval = new TaxTimeInterval("01:00", "12:27", 7);
        assertEquals(expected,taxTimeInterval.isInInterval(time));
    }

    @ParameterizedTest
    @CsvSource({
            "01:05, 7",
            "12:28, 0",
    })
    void testYield(String time, int expected) {
        final TaxTimeInterval taxTimeInterval = new TaxTimeInterval("01:00", "12:27", 7);
        assertEquals(expected, taxTimeInterval.yield(time));
    }
}