package com.company.hotel.service;

import com.company.hotel.dto.AllocationRequest;
import com.company.hotel.dto.AllocationResponse;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RoomAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(RoomAllocationService.class);

    public AllocationResponse allocateRooms(AllocationRequest request) {
        validateRequest(request);

        var potentialGuests = request.potentialGuests();
        var premiumRooms = request.premiumRooms();
        var economyRooms = request.economyRooms();

        if (potentialGuests.size() > (premiumRooms + economyRooms)) {
            logger.error("Invalid room count: premiumRooms={} economyRooms={}", request.premiumRooms(), request.economyRooms());
            throw new ValidationException("Not enough rooms for the potential guests");
        }

        // Sort guests by their willingness to pay (descending order) for allocation by the highest cost firstly
        var guests = potentialGuests.stream()
                .sorted(Comparator.reverseOrder())
                .toList();

        // Allocate Premium guests (willing to pay 100 or more)
        var premiumResult = allocate(
                guests.stream().filter(amount -> amount >= 100).toList(),
                premiumRooms
        );

        // Separate economy guests (willing to pay less than 100)
        var economyGuests = guests.stream()
                .filter(amount -> amount < 100)
                .sorted() // sorting by ascending order, to handle upgrade option correctly if premium rooms are left
                .toList();

        // Allocate Economy guests
        var economyResult = allocate(
                economyGuests,
                economyRooms
        );

        // Upgrade remaining Economy guests to Premium (if Premium rooms are available)
        var remainingEconomyGuests = economyGuests.stream()
                .filter(amount -> amount < 100)
                .skip(economyResult.usage())
                .toList();

        // Allocate remaining Economy guests to Premium, highest paying guests first
        var upgradeResult = allocate(
                remainingEconomyGuests,
                premiumRooms - premiumResult.usage()
        );

        return new AllocationResponse(
                premiumResult.usage() + upgradeResult.usage(),
                premiumResult.revenue() + upgradeResult.revenue(),
                economyResult.usage(),
                economyResult.revenue()
        );
    }

    private AllocationResult allocate(List<Double> guests, int availableRooms) {
        if (guests.isEmpty() || availableRooms <= 0) {
            return new AllocationResult(0, 0.0);
        }

        var allocatedGuests = guests.stream()
                .limit(availableRooms)
                .toList();

        var revenue = allocatedGuests.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return new AllocationResult(allocatedGuests.size(), revenue);
    }

    private void validateRequest(AllocationRequest request) {
        logger.info("Starting room allocation with request: {}", request);
        if (request.premiumRooms() < 0 || request.economyRooms() < 0) {
            logger.error("Invalid room count: premiumRooms={} economyRooms={}", request.premiumRooms(), request.economyRooms());
            throw new IllegalArgumentException("Number of rooms can't be negative");
        }

        if (request.potentialGuests().isEmpty()) {
            logger.error("Guests list is empty");
            throw new ValidationException("Guests list can't be empty");
        }
    }

    // Use record to store allocation results
    private record AllocationResult(int usage, double revenue) {
    }
}
