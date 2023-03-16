package net.benfro.concalc.config.gothenburg;

import lombok.RequiredArgsConstructor;
import net.benfro.concalc.model.Vehicle;
import net.benfro.concalc.ruleapi.TollFreeVehicleLookup;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class TollFreeVehiclesGbg implements TollFreeVehicleLookup {

    private final List<Vehicle> taxFreeVehicles;

    @Override
    public boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        return taxFreeVehicles.contains(vehicle);
    }
}
