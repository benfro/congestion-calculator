package net.benfro.concalc.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of https://en.wikipedia.org/wiki/Computus
 */
public class EasterSundayCalculator {

    static class DayMonth {
        public final int day;
        public final int month;

        public DayMonth(int day, int month) {
            this.day = day;
            this.month = month;
        }
    }

    static final Map<Integer, DayMonth> firstFullMoon;

    static {
        Map<Integer, DayMonth> temp = new HashMap<>();
        temp.put(1, new DayMonth(14,4));
        temp.put(2, new DayMonth(3,4));
        temp.put(3, new DayMonth(23,3));
        temp.put(4, new DayMonth(11,4));
        temp.put(5, new DayMonth(31,3));
        temp.put(6, new DayMonth(18,4));
        temp.put(7, new DayMonth(8,4));
        temp.put(8, new DayMonth(28,3));
        temp.put(9, new DayMonth(16,4));
        temp.put(10, new DayMonth(5,4));
        temp.put(11, new DayMonth(25,3));
        temp.put(12, new DayMonth(13,4));
        temp.put(13, new DayMonth(2,4));
        temp.put(14, new DayMonth(22,3));
        temp.put(15, new DayMonth(10,4));
        temp.put(16, new DayMonth(30,3));
        temp.put(17, new DayMonth(17,4));
        temp.put(18, new DayMonth(7,4));
        temp.put(19, new DayMonth(27,3));
        firstFullMoon = Collections.unmodifiableMap(temp);
    }

    int magicNumberForYear(int year) {
        return (year % 19) + 1;
    }

    LocalDate firstFullMoonInMarchDate(int year) {
        final DayMonth dayMonth = firstFullMoon.get(magicNumberForYear(year));
        return LocalDate.of(year, dayMonth.month, dayMonth.day);
    }

    public LocalDate calculateEasterSunday(int year) {
        final LocalDate firstFullMoonInMarch = firstFullMoonInMarchDate(year);
        final DayOfWeek dayOfWeek = firstFullMoonInMarch.getDayOfWeek();
        return firstFullMoonInMarch.plusDays(dayOfWeek == DayOfWeek.SUNDAY ? 7 : 7 - dayOfWeek.getValue());
    }
}
