package net.benfro.concalc.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TaxDateService {

    public boolean isTollFreeDate(LocalDate date) {

        final int year = date.getYear();
        final DayOfWeek day = date.getDayOfWeek();
        final Month month = date.getMonth();
        final int dayOfMonth = date.getDayOfMonth();

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) return true;

        if (year == 2013) {
            if ((month == Month.JANUARY && dayOfMonth == 1) ||
                    (month == Month.MARCH && (dayOfMonth == 28 || dayOfMonth == 29)) ||
                    (month == Month.APRIL && (dayOfMonth == 1 || dayOfMonth == 30)) ||
                    (month == Month.MAY && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
                    (month == Month.JUNE && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
                    (month == Month.JULY) ||
                    (month == Month.NOVEMBER && dayOfMonth == 1) ||
                    (month == Month.DECEMBER && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31))) {
                return true;
            }
        }
        return false;
    }

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
