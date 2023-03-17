package net.benfro.concalc.service;

import lombok.RequiredArgsConstructor;
import net.benfro.concalc.model.Vehicle;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class CongestionTaxCalculator {

    private final TollFeeService tollFeeService;

    private final int maxTaxPerDay;

    public int calculate(Vehicle vehicle, List<String> dateTimesStrings) {

        Map<LocalDate, LinkedList<LocalDateTime>> groupedByDate =
                dateTimesStrings
                        .stream()
                        .map(this::getAsLocalDateTime)
                        .sorted(Comparator.naturalOrder())
                        .collect(groupingBy(LocalDateTime::toLocalDate, Collectors.toCollection(LinkedList::new)));

        return groupedByDate.values()
                .stream()
                .mapToInt(list -> calculateForDay(vehicle, list))
                .sum();
    }

    private int calculateForDay(Vehicle vehicle, LinkedList<LocalDateTime> sortedDateTimes) {
        int totalTax = 0;
        while (!sortedDateTimes.isEmpty()) {
            final LocalDateTime firstDate = sortedDateTimes.pop();
            final List<LocalDateTime> entriesWithinSixtyMinutes = findEntriesWithinSixtyMinutes(firstDate, sortedDateTimes);
            if (!entriesWithinSixtyMinutes.isEmpty()) {
                totalTax += findMaxTaxFromEntries(entriesWithinSixtyMinutes, vehicle);
                sortedDateTimes.removeAll(entriesWithinSixtyMinutes);
            } else {
                totalTax += tollFeeService.getTollFee(firstDate, vehicle);
            }
        }

        return Math.min(totalTax, maxTaxPerDay);
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
