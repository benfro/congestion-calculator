package net.benfro.concalc.config.gothenburg;

import lombok.RequiredArgsConstructor;
import net.benfro.concalc.model.Vehicle;
import net.benfro.concalc.ruleapi.TollFeeLookup;
import net.benfro.concalc.ruleapi.TollFreeDateLookup;
import net.benfro.concalc.ruleapi.TollFreeVehicleLookup;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TollFeeGbg implements TollFeeLookup {
    @Override
    public int getTollFee(LocalDateTime incomingTime, Vehicle vehicle) {

        int hour = incomingTime.getHour();
        int minute = incomingTime.getMinute();

        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;
    }
}
