package net.benfro.concalc.config.gothenburg;

import lombok.RequiredArgsConstructor;
import net.benfro.concalc.model.TaxTimeIntervals;
import net.benfro.concalc.model.Vehicle;
import net.benfro.concalc.ruleapi.TollFeeLookup;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TollFeeGbg implements TollFeeLookup {

    private final TaxTimeIntervals intervals;

    @Override
    public int getTollFee(LocalDateTime incomingTime, Vehicle vehicle) {
        return intervals.getIntervals().stream().mapToInt(i -> i.yieldTaxRate(incomingTime.toLocalTime())).sum();
    }
}
