package net.benfro.concalc.ruleapi;

import java.time.LocalDate;

/**
 * Implement this interface to provide lookup for localized rules concerning
 * toll-free dates and provide it to your application
 */
public interface TollFreeDateLookup {
    boolean isTollFreeDate(LocalDate date);
}
