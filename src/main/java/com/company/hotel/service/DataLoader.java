package com.company.hotel.service;

import com.company.enumeration.RoomCategory;
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
            Room room1 = new Room(RoomCategory.PREMIUM, Boolean.FALSE); // Room is not occupied
            Room room2 = new Room(RoomCategory.PREMIUM, Boolean.FALSE);
            Room room3 = new Room(RoomCategory.PREMIUM, Boolean.TRUE);  // Room is free
            Room room4 = new Room(RoomCategory.PREMIUM, Boolean.FALSE);

            // Load economy rooms
            Room room5 = new Room(RoomCategory.ECONOMY, Boolean.FALSE);
            Room room6 = new Room(RoomCategory.ECONOMY, Boolean.TRUE);
            Room room7 = new Room(RoomCategory.ECONOMY, Boolean.FALSE);
            Room room8 = new Room(RoomCategory.ECONOMY, Boolean.FALSE);
            Room room9 = new Room(RoomCategory.ECONOMY, Boolean.TRUE);

            roomRepository.saveAll(List.of(room1, room2, room3, room4, room5, room6, room7, room8, room9));

            System.out.println("Data loaded: " + roomRepository.count() + " rooms created.");
        }
    }
}

