package net.benfro.concalc.ruleapi;

import net.benfro.concalc.model.Vehicle;

import java.time.LocalDateTime;
/**
 * Implement this interface to provide lookup for localized rules concerning
 * toll fees and provide it to your application
 */
public interface TollFeeLookup {
    int getTollFee(LocalDateTime incomingTime, Vehicle vehicle);
}
