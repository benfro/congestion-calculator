package net.benfro.concalc.service;

import lombok.RequiredArgsConstructor;
import net.benfro.concalc.model.Vehicle;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final TaxDateService taxDateService;
    private final List<Vehicle> taxFreeVehicles;

    public int getTollFee(LocalDateTime incomingTime, Vehicle vehicle) {
        if (taxDateService.isTollFreeDate(incomingTime.toLocalDate()) || isTollFreeVehicle(vehicle)) return 0;

        int hour = incomingTime.getHour();
        int minute = incomingTime.getMinute();

        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;
    }

    public boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        return taxFreeVehicles.contains(vehicle);
    }
}
