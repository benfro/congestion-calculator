package net.benfro.concalc.service;

import net.benfro.concalc.model.DefaultVehicle;
import net.benfro.concalc.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
class VehicleServiceTest {

    @Autowired
    TaxDateService taxDateService;

    @Autowired
    List<Vehicle> taxFreeVehicles;

    VehicleService instance;

    @BeforeEach
    public void setUp() {
        instance = new VehicleService(taxDateService, taxFreeVehicles);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Foreign",
            //"Diplomat",
            //"Bus",
            //"Military",
    })
    void testIsTaxFreeVehicle(String in) {
        assertTrue(instance.isTollFreeVehicle(DefaultVehicle.get(in)));
    }
}