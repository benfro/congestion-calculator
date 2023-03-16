package net.benfro.concalc.service;

import lombok.RequiredArgsConstructor;
import net.benfro.concalc.model.Vehicle;
import net.benfro.concalc.ruleapi.TollFeeLookup;
import net.benfro.concalc.ruleapi.TollFreeDateLookup;
import net.benfro.concalc.ruleapi.TollFreeVehicleLookup;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TollFeeService {
    private final TollFreeDateLookup tollFreeDateLookup;

    private final TollFreeVehicleLookup tollFreeVehicleLookup;

    private final TollFeeLookup tollFeeLookup;

    public int getTollFee(LocalDateTime incomingTime, Vehicle vehicle) {
        if (tollFreeDateLookup.isTollFreeDate(incomingTime.toLocalDate()) ||
                tollFreeVehicleLookup.isTollFreeVehicle(vehicle)) return 0;

        return tollFeeLookup.getTollFee(incomingTime, vehicle);
    }

}
