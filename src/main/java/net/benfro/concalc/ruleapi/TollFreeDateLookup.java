package net.benfro.concalc.ruleapi;

import net.benfro.concalc.model.StandardHolidays;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implement this interface to provide lookup for localized rules concerning
 * toll-free dates and provide it to your application
 */
public interface TollFreeDateLookup {
    boolean isTollFreeDate(LocalDate date);


}
