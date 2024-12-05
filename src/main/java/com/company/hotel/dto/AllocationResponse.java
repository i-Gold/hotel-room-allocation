package com.company.hotel.dto;

public record AllocationResponse(
        Integer usagePremium,
        Double revenuePremium,
        Integer usageEconomy,
        Double revenueEconomy
) {
}
