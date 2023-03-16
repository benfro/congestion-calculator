package net.benfro.concalc.ruleapi;

import net.benfro.concalc.model.Vehicle;

import java.time.LocalDateTime;

public interface TollFeeLookup {
    int getTollFee(LocalDateTime incomingTime, Vehicle vehicle);
}
