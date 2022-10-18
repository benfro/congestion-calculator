package net.benfro.concalc.model;

import org.junit.jupiter.api.Test;

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
}