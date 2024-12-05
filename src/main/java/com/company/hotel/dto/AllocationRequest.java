package com.company.hotel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AllocationRequest(
        @NotNull(message = "Number of premium rooms cannot be null")
        @Min(value = 0, message = "Number of premium rooms cannot be negative")
        Integer premiumRooms,

        @NotNull(message = "Number of economy rooms cannot be null")
        @Min(value = 0, message = "Number of economy rooms cannot be negative")
        Integer economyRooms,

        @NotNull(message = "Guests list cannot be null")
        @Size(min = 1, message = "Guests list cannot be empty")
        List<Double> potentialGuests
) {
}
