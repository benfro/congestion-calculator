package net.benfro.concalc.config.gothenburg;

import lombok.RequiredArgsConstructor;
import net.benfro.concalc.model.StandardHolidays;
import net.benfro.concalc.ruleapi.util.TollFreeDateCache;
import net.benfro.concalc.ruleapi.TollFreeDateLookup;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class TollFreeDatesGbg implements TollFreeDateLookup {

    private final TollFreeDateCache tollFreeDateCache;

    @Override
    public boolean isTollFreeDate(LocalDate date) {
        final DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) return true;

        final Month month = date.getMonth();
        if (month == Month.JULY) {
            return true;
        }

        final int year = date.getYear();
        Optional<List<LocalDateTime>> localDateTimes;
        try {
            localDateTimes = tollFreeDateCache.get(year);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        if (localDateTimes.isPresent()) {
            return localDateTimes.get().stream().map(LocalDateTime::toLocalDate).toList().contains(date);
        }
        return false;

    }


}
