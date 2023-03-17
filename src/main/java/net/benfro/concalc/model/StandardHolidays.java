package net.benfro.concalc.model;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

public class StandardHolidays {

    private static EasterSundayCalculator easterSundayCalculator = new EasterSundayCalculator();

    public enum Holiday {
        NEW_YEARS_DAY("Nyårdsdagen", Month.JANUARY, 1, Constants.NOT_APPLICABLE),
        EPIPHANY("Trettondedag Jul", Month.JANUARY, 6, Constants.NOT_APPLICABLE),
        GOOD_FRIDAY("Långfredag", Month.MARCH, Constants.NOT_APPLICABLE, -2),
        HOLY_SATURDAY("Påskafton", Month.MARCH, Constants.NOT_APPLICABLE, -1),
        EASTER_DAY("Påskdagen", Month.MARCH, Constants.NOT_APPLICABLE, 0),
        EASTER_MONDAY("Annandag Påsk", Month.MARCH, Constants.NOT_APPLICABLE, 1),
        FIRST_OF_MAY("Första Maj", Month.MAY, 1, Constants.NOT_APPLICABLE),
        ASCENSION_DAY("Kristi Himmelsfärdsdag", Month.APRIL, Constants.NOT_APPLICABLE, 39),
        SWEDISH_NATIONAL_HOLIDAY("Sveriges Nationaldag", Month.JUNE, 6, Constants.NOT_APPLICABLE),
        MIDSUMMER_EVE("Midsommarafton", Month.JUNE, 19, 7),
        MIDSUMMER_DAY("Midsommardagen", Month.JUNE, 20, 7),
        ALL_SAINTS_DAY("Alla helgons dag", Month.OCTOBER, 31, 7),
        CHRISTMAS_EVE("Julafton", Month.DECEMBER, 24, Constants.NOT_APPLICABLE),
        CHRISTMAS_DAY("Juldagen", Month.DECEMBER, 25, Constants.NOT_APPLICABLE),
        SECOND_DAY_OF_CHRISTMAS("Annandag Jul", Month.DECEMBER, 26, Constants.NOT_APPLICABLE),
        NEW_YEARS_EVE("Nyårsafton", Month.DECEMBER, 31, Constants.NOT_APPLICABLE);

        public final String name;
        public final Month month;
        public final int date;
        public final int calculated;

        Holiday(String name, Month month, int date, int calculated) {
            this.name = name;
            this.month = month;
            this.date = date;
            this.calculated = calculated;
        }

        private static class Constants {
            public static final int NOT_APPLICABLE = -100;
        }

        public LocalDateTime asLocalDateTime(int year) {
            return LocalDateTime.of(year, month, date, 0, 0);
        }
    }

    static class HolidayAtYear {
        public final Holiday holiday;
        public final int year;

        public HolidayAtYear(Holiday holiday, int year) {
            this.holiday = holiday;
            this.year = year;
        }

        public LocalDateTime asLocalDateTime() {
            return calculateNext(holiday, year);
        }
    }


    public static LocalDateTime calculateNext(Holiday holiday, int forYear) {

        switch (holiday) {
            case MIDSUMMER_EVE:
                return getDateOnWeekDayInInterval(holiday, DayOfWeek.FRIDAY, forYear);
            case MIDSUMMER_DAY:
            case ALL_SAINTS_DAY:
                return getDateOnWeekDayInInterval(holiday, DayOfWeek.SATURDAY, forYear);
            case GOOD_FRIDAY:
            case HOLY_SATURDAY:
            case EASTER_DAY:
            case EASTER_MONDAY:
            case ASCENSION_DAY:
                return easterSundayCalculator.calculateEasterSunday(forYear).atStartOfDay().plusDays(holiday.calculated);
            default:
                if (holiday.calculated != -100) {
                    //log.error("{} is a calculated day", holiday.name);
                    throw new RuntimeException("This is a calculated day!");
                }
                return holiday.asLocalDateTime(forYear);

        }

    }

    static LocalDateTime getDateOnWeekDayInInterval(Holiday holiday, DayOfWeek dayOfWeek, int forYear) {
        LocalDateTime dayCursor = holiday.asLocalDateTime(forYear);
        LocalDateTime endDate = dayCursor.plusDays(holiday.calculated);
        while (dayCursor.getDayOfWeek() != dayOfWeek && dayCursor.isBefore(endDate)) {
            dayCursor = dayCursor.plusDays(1);
        }
        return dayCursor;
    }


}
