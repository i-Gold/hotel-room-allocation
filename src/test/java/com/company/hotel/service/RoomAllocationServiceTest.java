package com.company.hotel.service;

import com.company.enumeration.RoomCategory;
import com.company.hotel.dto.OccupancyRequest;
import com.company.hotel.dto.OccupancyResponse;
import com.company.hotel.model.Guest;
import com.company.hotel.model.Room;
import com.company.hotel.repository.GuestRepository;
import com.company.hotel.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoomAllocationServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomAllocationService roomAllocationService;

    private OccupancyRequest occupancyRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        occupancyRequest = new OccupancyRequest();
        occupancyRequest.setPremiumRooms(3);
        occupancyRequest.setEconomyRooms(2);
        occupancyRequest.setPotentialGuests(Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0));
    }

    @Test
    void testAllocateRoomsSuccessfully() {
        List<Room> premiumRooms = Arrays.asList(new Room(RoomCategory.PREMIUM, false), new Room(RoomCategory.PREMIUM, false), new Room(RoomCategory.PREMIUM, false));
        List<Room> economyRooms = Arrays.asList(new Room(RoomCategory.ECONOMY, false), new Room(RoomCategory.ECONOMY, false));

        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.PREMIUM, Boolean.FALSE, 3)).thenReturn(premiumRooms);
        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.ECONOMY, Boolean.FALSE, 2)).thenReturn(economyRooms);

        when(guestRepository.saveAll(anyList())).thenReturn(Arrays.asList(new Guest(155.0), new Guest(374.0), new Guest(99.99), new Guest(100.0)));

        OccupancyResponse response = roomAllocationService.allocateRooms(occupancyRequest);

        assertEquals(3, response.getUsagePremium());
        assertEquals(2, response.getUsageEconomy());
        assertEquals(738.0, response.getRevenuePremium());
        assertEquals(144.99, response.getRevenueEconomy());

        verify(guestRepository, times(2)).saveAll(anyList());
        verify(roomRepository, times(2)).saveAll(anyList());
    }

    @Test
    void testNotEnoughPremiumRooms() {
        List<Room> premiumRooms = Arrays.asList(new Room(RoomCategory.PREMIUM, false), new Room(RoomCategory.PREMIUM, false));
        List<Room> economyRooms = Arrays.asList(new Room(RoomCategory.ECONOMY, false), new Room(RoomCategory.ECONOMY, false));

        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.PREMIUM, Boolean.FALSE, 3)).thenReturn(premiumRooms);
        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.ECONOMY, Boolean.FALSE, 2)).thenReturn(economyRooms);

        when(guestRepository.saveAll(anyList())).thenReturn(Arrays.asList(new Guest(155.0), new Guest(374.0), new Guest(99.99), new Guest(100.0)));

        OccupancyResponse response = roomAllocationService.allocateRooms(occupancyRequest);

        assertEquals(2, response.getUsagePremium());
        assertEquals(2, response.getUsageEconomy());
        assertEquals(583.0, response.getRevenuePremium());
        assertEquals(144.99, response.getRevenueEconomy());
    }

    @Test
    void testNoAvailableRooms() {
        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.PREMIUM, Boolean.FALSE, 3)).thenReturn(List.of());
        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.ECONOMY, Boolean.FALSE, 2)).thenReturn(List.of());

        OccupancyResponse response = roomAllocationService.allocateRooms(occupancyRequest);

        assertEquals(0, response.getUsagePremium());
        assertEquals(0, response.getUsageEconomy());
        assertEquals(0, response.getRevenuePremium());
        assertEquals(0, response.getRevenueEconomy());
    }

    @Test
    void testAllGuestsArePremium() {
        occupancyRequest.setPotentialGuests(Arrays.asList(200.0, 250.0, 300.0));

        List<Room> premiumRooms = Arrays.asList(new Room(RoomCategory.PREMIUM, false), new Room(RoomCategory.PREMIUM, false), new Room(RoomCategory.PREMIUM, false));
        List<Room> economyRooms = Arrays.asList(new Room(RoomCategory.ECONOMY, false), new Room(RoomCategory.ECONOMY, false));

        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.PREMIUM, Boolean.FALSE, 3)).thenReturn(premiumRooms);
        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.ECONOMY, Boolean.FALSE, 2)).thenReturn(economyRooms);

        when(guestRepository.saveAll(anyList())).thenReturn(Arrays.asList(new Guest(250.0), new Guest(300.0), new Guest(200.0)));

        OccupancyResponse response = roomAllocationService.allocateRooms(occupancyRequest);

        assertEquals(3, response.getUsagePremium());
        assertEquals(0, response.getUsageEconomy());
        assertEquals(750.0, response.getRevenuePremium());
        assertEquals(0, response.getRevenueEconomy());
    }

    @Test
    void testAllGuestsUnder100() {
        occupancyRequest.setPremiumRooms(2);
        occupancyRequest.setEconomyRooms(3);
        occupancyRequest.setPotentialGuests(Arrays.asList(23.0, 45.0, 30.0, 60.0, 99.0));

        List<Room> premiumRooms = Arrays.asList(new Room(RoomCategory.PREMIUM, false), new Room(RoomCategory.PREMIUM, false));
        List<Room> economyRooms = Arrays.asList(new Room(RoomCategory.ECONOMY, false), new Room(RoomCategory.ECONOMY, false), new Room(RoomCategory.ECONOMY, false));

        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.PREMIUM, Boolean.FALSE, 2)).thenReturn(premiumRooms);
        when(roomRepository.findAvailableRoomsByCategoryAndIsOccupied(RoomCategory.ECONOMY, Boolean.FALSE, 3)).thenReturn(economyRooms);

        when(guestRepository.saveAll(anyList())).thenReturn(Arrays.asList(new Guest(23.0), new Guest(45.0), new Guest(30.0), new Guest(60.0), new Guest(99.0)));

        OccupancyResponse response = roomAllocationService.allocateRooms(occupancyRequest);

        assertEquals(0, response.getUsagePremium());
        assertEquals(3, response.getUsageEconomy());
        assertEquals(0, response.getRevenuePremium());
        assertEquals(204.0, response.getRevenueEconomy());
    }
}
