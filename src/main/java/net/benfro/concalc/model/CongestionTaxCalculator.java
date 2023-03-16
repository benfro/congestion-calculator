package net.benfro.concalc.model;

import lombok.RequiredArgsConstructor;
import net.benfro.concalc.service.TollFeeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CongestionTaxCalculator {

    private final TollFeeService tollFeeService;

    public int getTax(Vehicle vehicle, List<String> dateTimes) {

        final LinkedList<LocalDateTime> sortedDateTimes = dateTimes
                .stream()
                .map(this::getAsLocalDateTime)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toCollection(LinkedList::new));

        int totalTax = 0;

        // Get the ones within sixty minutes - return highest tax
        while (!sortedDateTimes.isEmpty()) {
            final LocalDateTime latestDate = sortedDateTimes.pop();
            final List<LocalDateTime> entriesWithinSixtyMinutes = findEntriesWithinSixtyMinutes(latestDate, sortedDateTimes);
            if (!entriesWithinSixtyMinutes.isEmpty()) {
                totalTax += findMaxTaxFromEntries(entriesWithinSixtyMinutes, vehicle);
                sortedDateTimes.clear();
            } else {
                totalTax += tollFeeService.getTollFee(latestDate, vehicle);
            }
        }

        return Math.min(totalTax, 60);
    }

    private int findMaxTaxFromEntries(List<LocalDateTime> entriesWithinSixtyMinutes, Vehicle vehicle) {
        return entriesWithinSixtyMinutes.stream().mapToInt(d -> tollFeeService.getTollFee(d, vehicle)).max().orElse(0);
    }

    private List<LocalDateTime> findEntriesWithinSixtyMinutes(LocalDateTime baseTime, List<LocalDateTime> collect) {
        return collect.stream().filter(t -> t.isBefore(baseTime.plusMinutes(60))).collect(Collectors.toList());
    }

    private LocalDateTime getAsLocalDateTime(String in) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(in, formatter);
    }


}
