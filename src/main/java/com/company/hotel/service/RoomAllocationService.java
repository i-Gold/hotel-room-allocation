package com.company.hotel.service;

import com.company.hotel.dto.AllocationRequest;
import com.company.hotel.dto.AllocationResponse;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(RoomAllocationService.class);

    public AllocationResponse allocateRooms(AllocationRequest request) {
        validateRequest(request);

        List<Double> potentialGuests = request.potentialGuests();
        int premiumRooms = request.premiumRooms();
        int economyRooms = request.economyRooms();

        if (potentialGuests.size() > (premiumRooms + economyRooms)) {
            logger.error("Invalid room count: premiumRooms={} economyRooms={}", request.premiumRooms(), request.economyRooms());
            throw new ValidationException("Not enough rooms for the potential guests");
        }

        // Sort guests by their willingness to pay (descending order) for allocation by the highest cost firstly
        var guests = potentialGuests.stream()
                .sorted((a, b) -> Double.compare(b, a))
                .toList();

        // Separate premium guests (willing to pay 100 or more)
        var premiumGuests = guests.stream()
                .filter(willingToPay -> willingToPay >= 100)
                .toList();

        // Fill Premium rooms with guests willing to pay >= 100
        var filledPremium = premiumGuests.stream()
                .limit(premiumRooms)  // Limit by available premium rooms
                .toList();

        int usagePremium = filledPremium.size();
        double revenuePremium = filledPremium.stream().mapToDouble(Double::doubleValue).sum();

        // Separate economy guests (willing to pay less than 100)
        var economyGuests = guests.stream()
                .filter(willingToPay -> willingToPay < 100)
                .sorted()  // sorting by ascending order, to handle upgrade option correctly if premium rooms are left
                .toList();

        // Fill Economy rooms
        var filledEconomy = economyGuests.stream()
                .limit(economyRooms)  // Limit by available economy rooms
                .toList();

        int usageEconomy = filledEconomy.size();
        double revenueEconomy = filledEconomy.stream().mapToDouble(Double::doubleValue).sum();

        // If Premium rooms are still available, upgrade Economy guests with the highest payment to Premium
        var remainingEconomyGuests = economyGuests.stream()
                .skip(filledEconomy.size())  // Skip those already placed in Economy rooms
                .toList();

        var upgradedEconomyToPremium = remainingEconomyGuests.stream()
                .limit(premiumRooms - usagePremium)  // Upgrade to Premium rooms if there's space
                .toList();

        usagePremium += upgradedEconomyToPremium.size();
        revenuePremium += upgradedEconomyToPremium.stream().mapToDouble(Double::doubleValue).sum();

        return new AllocationResponse(usagePremium, revenuePremium, usageEconomy, revenueEconomy);
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
}
