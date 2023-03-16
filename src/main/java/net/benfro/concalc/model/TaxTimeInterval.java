package net.benfro.concalc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class TaxTimeInterval {

    public static TaxTimeInterval of(String start, String end, int fee) {
        return new TaxTimeInterval(start, end, fee);
    }

    private static DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int taxRate;

    TaxTimeInterval(String startTime, String endTime, int taxRate) {
        this.startTime = parse(startTime);
        this.endTime = parse(endTime);
        this.taxRate = taxRate;
    }

    LocalTime parse(String timeStr) {
        return LocalTime.parse(timeStr, parser);
    }

    boolean isInTimeInterval(String timeStr) {
        final LocalTime localTime = parse(timeStr);
        return isInTimeInterval(localTime);
    }

    boolean isInTimeInterval(LocalTime time) {
        return time.isAfter(startTime.minusMinutes(1))
                && time.isBefore(endTime.plusMinutes(1));
    }

    public int yieldTaxRate(String time) {
        if(isInTimeInterval(time)) return taxRate;
        return 0;
    }

    public int yieldTaxRate(LocalTime time) {
        if(isInTimeInterval(time)) return taxRate;
        return 0;
    }
}
