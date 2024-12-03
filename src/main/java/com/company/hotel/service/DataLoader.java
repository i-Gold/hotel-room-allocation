package com.company.hotel.service;

import com.company.hotel.enumeration.RoomCategory;
import com.company.hotel.model.Room;
import com.company.hotel.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoomRepository roomRepository;

    public DataLoader(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createRooms();
    }

    private void createRooms() {
        if (roomRepository.count() == 0) {
            // Load premium rooms
            Room room1 = new Room(RoomCategory.PREMIUM, Boolean.FALSE); // Room is free
            Room room2 = new Room(RoomCategory.PREMIUM, Boolean.FALSE);
            Room room3 = new Room(RoomCategory.PREMIUM, Boolean.TRUE);  // Room is not available
            Room room4 = new Room(RoomCategory.PREMIUM, Boolean.FALSE);
            Room room5 = new Room(RoomCategory.PREMIUM, Boolean.FALSE);
            Room room6 = new Room(RoomCategory.PREMIUM, Boolean.FALSE);
            Room room7 = new Room(RoomCategory.PREMIUM, Boolean.FALSE);
            Room room8 = new Room(RoomCategory.PREMIUM, Boolean.FALSE);

            // Load economy rooms
            Room room9 = new Room(RoomCategory.ECONOMY, Boolean.FALSE);
            Room room10 = new Room(RoomCategory.ECONOMY, Boolean.TRUE);
            Room room11 = new Room(RoomCategory.ECONOMY, Boolean.FALSE);
            Room room12 = new Room(RoomCategory.ECONOMY, Boolean.FALSE);
            Room room13 = new Room(RoomCategory.ECONOMY, Boolean.TRUE);
            Room room14 = new Room(RoomCategory.ECONOMY, Boolean.FALSE);
            Room room15 = new Room(RoomCategory.ECONOMY, Boolean.FALSE);
            Room room16 = new Room(RoomCategory.ECONOMY, Boolean.FALSE);

            roomRepository.saveAll(List.of(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10, room11, room12, room13, room14, room15, room16));

            System.out.println("Data loaded: " + roomRepository.count() + " rooms created.");
        }
    }
}

