package net.benfro.concalc.config.gothenburg;

import net.benfro.concalc.model.DefaultVehicle;
import net.benfro.concalc.ruleapi.TollFeeLookup;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TollFeeLookupTest {

    @Autowired
    TollFeeLookup tollFeeLookup;

    @ParameterizedTest
    @CsvSource ({
            "2017-03-14T06:25:00.0, 8",
            "2017-03-14T06:31:00.0, 13",
            "2017-03-14T07:59:00.0, 18",
            "2017-03-14T08:28:00.0, 13",
            "2017-03-14T08:30:00.0, 8",
            "2017-03-14T14:59:00.0, 8",
            "2017-03-14T15:15:00.0, 13",
            "2017-03-14T15:30:00.0, 18",
            "2017-03-14T16:59:00.0, 18",
            "2017-03-14T17:00:00.0, 13",
            "2017-03-14T17:59:00.0, 13",
            "2017-03-14T18:00:00.0, 8",
            "2017-03-14T18:29:00.0, 8",
            "2017-03-14T18:30:00.0, 0",
})
    void testFee(LocalDateTime dt, int expectedFee) {
        assertEquals(expectedFee, tollFeeLookup.getTollFee(dt, new DefaultVehicle("Bil")));
    }
}
