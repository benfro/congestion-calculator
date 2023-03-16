package net.benfro.concalc.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaxTimeIntervals {
    private final List<TaxTimeInterval> intervals = new ArrayList<>();

    public TaxTimeIntervals() throws IOException {

        final List<String> lines = Files.readAllLines(Paths.get("src/main/resources","tax-interval-fees-gbg.csv").toAbsolutePath());
        lines.forEach( line -> {
            final String[] splitLine = line.split(";");
            if(!lineIsComment(splitLine)) {
                intervals.add(createTaxTimeIntervalFromTextualData(splitLine));
            }
        });
    }

    private static boolean lineIsComment(String[] splitLine) {
        return splitLine[0].startsWith("#");
    }

    private static TaxTimeInterval createTaxTimeIntervalFromTextualData(String[] split) {
        final int taxFee = Integer.parseInt(split[2].substring(split[2].indexOf(' ') + 1));
        return TaxTimeInterval.of(split[0], split[1], taxFee);
    }

    public List<TaxTimeInterval> getIntervals() {
        return intervals;
    }
}
