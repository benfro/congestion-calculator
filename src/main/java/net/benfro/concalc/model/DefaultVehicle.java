package net.benfro.concalc.model;

import java.util.Objects;

public record DefaultVehicle(String vehicleType) implements Vehicle {

    public static DefaultVehicle of(String vehicleType) {
        return new DefaultVehicle(vehicleType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultVehicle that = (DefaultVehicle) o;

        return Objects.equals(vehicleType, that.vehicleType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vehicleType);
    }
}
