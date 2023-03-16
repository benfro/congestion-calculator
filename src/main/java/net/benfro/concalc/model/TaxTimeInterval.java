package net.benfro.concalc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class TaxTimeInterval {

    private static DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalTime start;
    private final LocalTime end;
    private final int taxRate;

    public TaxTimeInterval(String start, String end, int taxRate) {
        this.start = parse(start);
        this.end = parse(end);
        this.taxRate = taxRate;
    }

    LocalTime parse(String time) {
        return LocalTime.parse(time, parser);
    }

    boolean isInInterval(String time) {
        final LocalTime parse = parse(time);
        return parse.isAfter(start.minusMinutes(1))
                && parse.isBefore(end.plusMinutes(1));
    }

    public int yield(String time) {
        if(isInInterval(time)) return taxRate;
        return 0;
    }
}
