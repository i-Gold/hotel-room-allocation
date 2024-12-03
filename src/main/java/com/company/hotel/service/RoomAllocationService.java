package com.company.hotel.service;

import com.company.hotel.enumeration.RoomCategory;
import com.company.hotel.dto.OccupancyRequest;
import com.company.hotel.dto.OccupancyResponse;
import com.company.hotel.model.Guest;
import com.company.hotel.model.Room;
import com.company.hotel.repository.GuestRepository;
import com.company.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
public class RoomAllocationService {

    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomAllocationService(GuestRepository guestRepository, RoomRepository roomRepository) {
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }

    public OccupancyResponse allocateRooms(OccupancyRequest request) {
        // Potential guests mapping
        List<Guest> guests = request.getPotentialGuests().stream()
                .map(Guest::new)
                .sorted((g1, g2) -> Double.compare(g2.getWillingnessToPay(), g1.getWillingnessToPay())) // Sort willingToPay by Descending
                .toList();

        // Get free rooms based on category and limit
        List<Room> premiumRooms = roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.PREMIUM, Boolean.FALSE, request.getPremiumRooms());
        List<Room> economyRooms = roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.ECONOMY, Boolean.FALSE, request.getEconomyRooms());

        // Premium guests distribution
        List<Guest> premiumGuests = guests.stream()
                .filter(guest -> guest.getWillingnessToPay() >= 100)
                .limit(premiumRooms.size())
                .toList();
        guestRepository.saveAll(premiumGuests);

        IntStream.range(0, premiumGuests.size()).forEach(i -> premiumRooms.get(i).setIsOccupied(Boolean.TRUE));
        roomRepository.saveAll(premiumRooms);

        Double premiumRevenue = premiumGuests.stream()
                .mapToDouble(Guest::getWillingnessToPay)
                .sum();

        // Filtering remaining guests for Economy
        List<Guest> economyGuests = guests.stream()
                .filter(guest -> guest.getWillingnessToPay() < 100)
                .limit(economyRooms.size())
                .toList();
        guestRepository.saveAll(economyGuests);

        IntStream.range(0, economyGuests.size()).forEach(i -> economyRooms.get(i).setIsOccupied(Boolean.TRUE));
        roomRepository.saveAll(economyRooms);

        Double economyRevenue = economyGuests.stream()
                .mapToDouble(Guest::getWillingnessToPay)
                .sum();

        return new OccupancyResponse(
                premiumGuests.size(),
                premiumRevenue,
                economyGuests.size(),
                economyRevenue
        );
    }
}
