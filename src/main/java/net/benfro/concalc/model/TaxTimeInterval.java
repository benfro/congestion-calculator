package net.benfro.concalc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class TaxTimeInterval {

    private static DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm");

    private LocalTime start;
    private LocalTime end;
    private int taxRate;

    public TaxTimeInterval(String start, String end, int taxRate) {
        this.start = LocalTime.parse(start, parser);
        this.end = LocalTime.parse(end, parser);
        this.taxRate = taxRate;
    }

}
