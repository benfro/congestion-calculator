package net.benfro.concalc.ruleapi;

import net.benfro.concalc.model.Vehicle;
/**
 * Implement this interface to provide lookup for localized rules concerning
 * toll-free vehicles and provide it to your application
 */
public interface TollFreeVehicleLookup {
    boolean isTollFreeVehicle(Vehicle vehicle);
}
