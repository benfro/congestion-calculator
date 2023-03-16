package net.benfro.concalc.ruleapi;

import net.benfro.concalc.model.Vehicle;

public interface TollFreeVehicleLookup {
    boolean isTollFreeVehicle(Vehicle vehicle);
}
