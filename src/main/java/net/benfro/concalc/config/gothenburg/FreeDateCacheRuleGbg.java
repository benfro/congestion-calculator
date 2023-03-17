package net.benfro.concalc.config.gothenburg;

import net.benfro.concalc.model.StandardHolidays;
import net.benfro.concalc.ruleapi.util.TollFreeDateCacheLoadingRule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FreeDateCacheRuleGbg implements TollFreeDateCacheLoadingRule {
    @Override
    public List<LocalDateTime> loadDatesToCacheRule(int year) {
        List<StandardHolidays.Holiday> remove = List.of(
                StandardHolidays.Holiday.NEW_YEARS_EVE,
                StandardHolidays.Holiday.MIDSUMMER_EVE,
                StandardHolidays.Holiday.EASTER_DAY,
                StandardHolidays.Holiday.EASTER_MONDAY);
        EnumSet<StandardHolidays.Holiday> previousDayIsAlsoFree = EnumSet.allOf(StandardHolidays.Holiday.class);
        previousDayIsAlsoFree.removeAll(remove);
        final List<LocalDateTime> holidaysAsLocalTimes =
                Arrays.stream(StandardHolidays.Holiday.values())
                        .map(h -> StandardHolidays.calculateNext(h, year))
                        .collect(Collectors.toList());
        final List<LocalDateTime> daysBefore =
                Arrays.stream(previousDayIsAlsoFree.toArray(new StandardHolidays.Holiday[0]))
                        .map(h -> StandardHolidays.calculateNext(h, year).minusDays(1))
                        .toList();
        holidaysAsLocalTimes.addAll(daysBefore);
        return holidaysAsLocalTimes;
    }
}
