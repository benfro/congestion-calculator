package net.benfro.concalc.service;

import net.benfro.concalc.model.DefaultVehicle;
import net.benfro.concalc.model.Vehicle;
import net.benfro.concalc.ruleapi.TollFeeLookup;
import net.benfro.concalc.ruleapi.TollFreeDateLookup;
import net.benfro.concalc.ruleapi.TollFreeVehicleLookup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
class TollFeeServiceTest {

    @Autowired
    TollFreeDateLookup tollFreeDateLookup;

    @Autowired
    TollFreeVehicleLookup taxFreeVehicles;

    @Autowired
    TollFeeLookup tollFeeLookup;

    TollFeeService instance;

    @BeforeEach
    public void setUp() {
        instance = new TollFeeService(tollFreeDateLookup, taxFreeVehicles, tollFeeLookup);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Foreign",
            //"Diplomat",
            //"Bus",
            //"Military",
    })
    void testIsTaxFreeVehicle(String in) {
        //assertTrue(instance.isTollFreeVehicle(DefaultVehicle.get(in)));
    }
}