package net.benfro.concalc.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaxDateServiceTest {

    TaxDateService instance;


    @ParameterizedTest
    @ValueSource(strings = {
            "2013-01-01",
            "2013-03-28",
            "2013-03-29",
            "2013-04-01",
            "2013-04-30",
            "2013-05-01",
            "2013-05-08",
            "2013-05-09",
            "2013-06-05",
            "2013-06-06",
            "2013-06-21",
            "2013-07-21",
            "2013-11-01",
            "2013-12-24",
            "2013-12-25",
            "2013-12-26",
            "2013-12-31",
    })
    void shouldBeTaxFree2013(LocalDate localDate) {
        instance = new TaxDateService();
        assertTrue(instance.isTollFreeDate(localDate));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2013-01-02",
            "2013-08-21",
            "2013-12-13",
    })
    void shouldNotBeTaxFree2013(LocalDate localDate) {
        instance = new TaxDateService();
        assertFalse(instance.isTollFreeDate(localDate));
    }
}