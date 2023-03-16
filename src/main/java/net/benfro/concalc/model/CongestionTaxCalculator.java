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

        final LinkedList<LocalDateTime> collect = new LinkedList<LocalDateTime>(dateTimes.stream().map(d -> getAsLocalDateTime(d)).collect(Collectors.toList()));

        // Sort it
        collect.sort(Comparator.naturalOrder());

        int totalTax = 0;

        // Get the ones within sixty minutes - return highest tax
        while (!collect.isEmpty()) {
            final LocalDateTime pop = collect.pop();
            final List<LocalDateTime> entriesWithinSixtyMinutes = findEntriesWithinSixtyMinutes(pop, collect);
            if (!entriesWithinSixtyMinutes.isEmpty()) {
                totalTax += findMaxTaxFromEntries(entriesWithinSixtyMinutes, vehicle);
                collect.removeAll(collect);
            } else {
                totalTax += tollFeeService.getTollFee(pop, vehicle);
            }
        }

        return Math.min(totalTax, 60);
    }

    private int findMaxTaxFromEntries(List<LocalDateTime> entriesWithinSixtyMinutes, Vehicle vehicle) {
        return entriesWithinSixtyMinutes.stream().mapToInt(d -> tollFeeService.getTollFee(d, vehicle)).max().orElse(0);
    }

    private List<LocalDateTime> findEntriesWithinSixtyMinutes(LocalDateTime ldt, List<LocalDateTime> collect) {
        return collect.stream().filter(t -> t.isBefore(ldt.plusMinutes(60))).collect(Collectors.toList());
    }

    private LocalDateTime getAsLocalDateTime(String in) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(in, formatter);
    }


}
