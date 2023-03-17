package net.benfro.concalc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.*;

import static java.time.Month.JUNE;
import static net.benfro.concalc.model.StandardHolidays.Holiday.ASCENSION_DAY;
import static org.junit.jupiter.api.Assertions.*;

class StandardHolidaysTest {

    StandardHolidays instance;

    @BeforeEach
    void setUp() {
        instance = new StandardHolidays();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2021, 25",
            "2022, 24",
            "2023, 23",
            "2024, 21",
            "2025, 20",
            "2026, 19",
            "2027, 25",
            "2028, 23",
            "2029, 22",
            "2030, 21",
            "2031, 20",
    })
    void testMidsummerEve(int year, int date) {
        final LocalDateTime result = instance.getDateOnWeekDayInInterval(StandardHolidays.Holiday.MIDSUMMER_EVE, DayOfWeek.FRIDAY, year);
        assertEquals(LocalDateTime.of(year, JUNE, date, 0, 0), result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NEW_YEARS_DAY, 2022, JANUARY, 1",
            "EPIPHANY, 2022,  JANUARY, 6",
            "GOOD_FRIDAY, 2022,  APRIL, 15",
            "HOLY_SATURDAY, 2022,  APRIL, 16",
            "EASTER_DAY, 2022,  APRIL, 17",
            "EASTER_MONDAY, 2022,  APRIL, 18",
            "FIRST_OF_MAY, 2022,  MAY, 1",
            "ASCENSION_DAY, 2022,  MAY, 26",
            "SWEDISH_NATIONAL_HOLIDAY, 2022,  JUNE, 6",
            "MIDSUMMER_EVE, 2022,  JUNE, 24",
            "MIDSUMMER_DAY, 2022,  JUNE, 25",
            "CHRISTMAS_EVE, 2022,  DECEMBER, 24",
            "CHRISTMAS_DAY, 2022,  DECEMBER, 25",
            "SECOND_DAY_OF_CHRISTMAS, 2022,  DECEMBER, 26",
            "NEW_YEARS_EVE, 2022,  DECEMBER, 31",
            "ALL_SAINTS_DAY, 2013,  NOVEMBER, 2",
            "ALL_SAINTS_DAY, 2014,  NOVEMBER, 1",
    })
    void testNextHoliday(StandardHolidays.Holiday holiday, int year, Month month, int date) {
        final LocalDateTime result = StandardHolidays.calculateNext(holiday, year);
        assertEquals(LocalDateTime.of(year, month, date, 0, 0), result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2021",
            "2023",
            "2024",
            "2026",
            "2027",
            "2029",
            "2031",
            "2499",
    })
    void testAscensionDayIsAlwaysThursday(int year) {
        final LocalDateTime result = StandardHolidays.calculateNext(ASCENSION_DAY, year);
        assertEquals(DayOfWeek.THURSDAY, result.getDayOfWeek());
    }

    int daysBetween(LocalDate start, LocalDate end) {
       return (int) Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays();
    }
}