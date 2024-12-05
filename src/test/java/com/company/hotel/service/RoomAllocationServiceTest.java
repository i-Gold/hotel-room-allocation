package com.company.hotel.service;

import com.company.hotel.dto.AllocationRequest;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomAllocationServiceTest {

    private final RoomAllocationService service = new RoomAllocationService();

    @Test
    void shouldAllocateRoomsSuccessfully() {
        var request = new AllocationRequest(
                7,
                5,
                List.of(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0)
        );

        var response = service.allocateRooms(request);

        assertEquals(6, response.usagePremium());
        assertEquals(1054.0, response.revenuePremium()); // 374 + 209 + 155 + 115 + 101 + 100
        assertEquals(4, response.usageEconomy());
        assertEquals(189.99, response.revenueEconomy()); // 22 + 23 + 45 + 99.99
    }

    @Test
    void shouldUpgradeEconomyToPremiumIfSpaceAvailable() {
        var request = new AllocationRequest(
                3,
                3,
                List.of(150.0, 80.0, 60.0, 30.0, 40.0, 90.0)
        );

        var response = service.allocateRooms(request);

        assertEquals(3, response.usagePremium());
        assertEquals(320.0, response.revenuePremium());  // 150 + 90 + 80 (including upgraded the highest paying guests willing to pay < 100)
        assertEquals(3, response.usageEconomy());
        assertEquals(130.0, response.revenueEconomy());  // others with low willingness to pay (30 + 40 + 60)
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughRooms() {
        var request = new AllocationRequest(
                3,
                3,
                List.of(150.0, 80.0, 60.0, 30.0, 40.0, 90.0, 200.0)  // 7 potential guests but only 6 rooms
        );

        assertThrows(ValidationException.class, () -> service.allocateRooms(request),
                "Not enough rooms for the potential guests");
    }

    @Test
    void shouldThrowExceptionWhenRoomsAreNegative() {
        var request = new AllocationRequest(
                -1,  // negative premium rooms
                3,
                List.of(100.0, 50.0)
        );

        assertThrows(IllegalArgumentException.class, () -> service.allocateRooms(request),
                "Number of rooms can't be negative");
    }

    @Test
    void shouldThrowExceptionWhenGuestsListIsEmpty() {
        var request = new AllocationRequest(
                3,
                3,
                List.of()  // no guests
        );

        assertThrows(ValidationException.class, () -> service.allocateRooms(request),
                "Guests list can't be empty");
    }

    @Test
    void shouldUpgradeToPremiumIfEconomyIsOccupied() {
        var request = new AllocationRequest(
                3,
                3,
                List.of(80.0, 90.0, 50.0, 40.0, 30.0)  // All guests willing to pay less than 100
        );

        var response = service.allocateRooms(request);

        assertEquals(2, response.usagePremium());  // upgraded guests with the highest willing to pay
        assertEquals(170.0, response.revenuePremium());
        assertEquals(3, response.usageEconomy());  // All guests in economy
        assertEquals(120.0, response.revenueEconomy());  // 30 + 40 + 50
    }
}
