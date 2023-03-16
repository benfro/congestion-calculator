package net.benfro.concalc.service;

import net.benfro.concalc.model.DefaultVehicle;
import net.benfro.concalc.ruleapi.TollFeeLookup;
import net.benfro.concalc.ruleapi.TollFreeDateLookup;
import net.benfro.concalc.ruleapi.TollFreeVehicleLookup;
import net.benfro.concalc.service.CongestionTaxCalculator;
import net.benfro.concalc.service.TollFeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
class CongestionTaxCalculatorTest {

    @Autowired
    TollFreeDateLookup tollFreeDateLookup;

    @Autowired
    TollFreeVehicleLookup taxFreeVehicles;

    @Autowired
    TollFeeLookup tollFeeLookup;

    CongestionTaxCalculator instance;

    @BeforeEach
    void setUp() {
        instance = new CongestionTaxCalculator(new TollFeeService(tollFreeDateLookup, taxFreeVehicles, tollFeeLookup));
    }


    @ParameterizedTest
    @CsvSource(value = {
            "Bil, 2013-01-01 22:03:27, 2013-01-01 10:03:27, 0",  // Two passages, both free
            "Bil, 2013-01-01 22:03:27, 2013-01-02 06:00:27, 8",  // Two passages, one free
            "Bil, 2013-01-02 06:03:27, 2013-01-02 18:03:27, 16", // Two passages during one day
            "Bil, 2013-01-02 06:03:27, 2013-01-02 15:33:27, 26", // Two passages during one day
            "Bil, 2013-01-02 06:33:27, 2013-01-03 07:01:27, 31", // Two consecutive dates
            "Bil, 2013-01-03 07:01:27, 2013-01-02 06:33:27, 31", // Two consecutive dates - Unsorted
    })
    void testAdditiveFee(String vehicle, String date1, String date2, int expected) {
        List<String> dates = new ArrayList<>();
        dates.add(date1);
        dates.add(date2);
        assertEquals(expected, instance.getTax(DefaultVehicle.of(vehicle), dates));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Bil, 2013-01-02 06:03:27, 2013-01-02 06:33:27, 13", // Highest rate - two passages in 60 min
            "Bil, 2013-01-02 06:33:27, 2013-01-02 07:01:27, 18", // Highest rate - two passages in 60 min

    })
    void testOnlyOneFeeWithinOneHourRule(String vehicle, String date1, String date2, int expected) {
        List<String> dates = List.of(date1, date2);
        assertEquals(expected, instance.getTax(DefaultVehicle.of(vehicle), dates));
    }


    @Test
    @DisplayName("Three passages, all within 60 minutes - the greatest fee returned")
    void testBiggestFeeReturnedWithinSixtyMinutes() {
        List<String> dates = new ArrayList<>();
        dates.add("2013-01-02 06:03:27"); // 8
        dates.add("2013-01-02 06:33:27"); // 13
        dates.add("2013-01-02 07:02:00"); // 18
        assertEquals(18, instance.getTax(DefaultVehicle.of("Bil"), dates));
    }
    @Test
    @DisplayName("Four passages, first three within 60 minutes = 18 and one more 18")
    void testThree() {
        List<String> dates = new ArrayList<>();
        dates.add("2013-01-02 06:03:27");
        dates.add("2013-01-02 06:33:27");
        dates.add("2013-01-02 07:02:00");
        dates.add("2013-01-02 07:05:00");
        assertEquals(18*2, instance.getTax(DefaultVehicle.of("Bil"), dates));
    }
}