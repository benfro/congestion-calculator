package net.benfro.concalc.model;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

public record DefaultVehicle(String vehicleType) implements Vehicle {

    public static DefaultVehicle get(String vehicleType) {
        return new DefaultVehicle(vehicleType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultVehicle that = (DefaultVehicle) o;

        return Objects.equals(vehicleType, that.vehicleType);
    }

}
