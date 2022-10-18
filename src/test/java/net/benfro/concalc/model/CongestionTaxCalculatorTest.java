package net.benfro.concalc.model;

import net.benfro.concalc.model.CongestionTaxCalculator;
import net.benfro.concalc.model.DefaultVehicle;
import net.benfro.concalc.model.Vehicle;
import net.benfro.concalc.service.TaxDateService;
import net.benfro.concalc.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
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
    TaxDateService taxDateService;

    @Autowired
    List<Vehicle> taxFreeVehicles;

    VehicleService vehicleService;

    CongestionTaxCalculator instance;

    @BeforeEach
    void setUp() {
        instance = new CongestionTaxCalculator(new VehicleService(taxDateService, taxFreeVehicles));
    }

    @Test
    void testCalculateTax() {
        assertEquals(4, taxFreeVehicles.size());
    }


    @ParameterizedTest
    @CsvSource(value = {
            "Bil, 2013-01-01 22:03:27, 2013-01-01 10:03:27, 0",
            "Bil, 2013-01-02 06:03:27, 2013-01-02 18:03:27, 16",
            "Bil, 2013-01-02 06:03:27, 2013-01-02 06:33:27, 13",
            "Bil, 2013-01-02 06:03:27, 2013-01-02 15:33:27, 26",
    })
    void testIt(String vehicle, String date1, String date2, int expected) {
        List<String> dates = new ArrayList<>();
        dates.add(date1);
        dates.add(date2);
        assertEquals(expected, instance.getTax(DefaultVehicle.get("Bil"), dates));
    }


    void testTwo() {
        List<String> dates = new ArrayList<>();
        dates.add("2013-01-02 06:03:27");
        dates.add("2013-01-02 06:33:27");
        dates.add("2013-01-02 07:02:00");
        assertEquals(18, instance.getTax(DefaultVehicle.get("Bil"), dates));
    }

    void testThree() {
        List<String> dates = new ArrayList<>();
        dates.add("2013-01-02 06:03:27");
        dates.add("2013-01-02 06:33:27");
        dates.add("2013-01-02 07:02:00");
        dates.add("2013-01-02 07:05:00");
        assertEquals(18*2, instance.getTax(DefaultVehicle.get("Bil"), dates));
    }
}