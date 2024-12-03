package com.company.hotel.repository;

import com.company.hotel.enumeration.RoomCategory;
import com.company.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.category = :category AND r.isOccupied = :occupied")
    List<Room> findAvailableRoomsByCategoryAndIsOccupied(
            @Param("category") RoomCategory category,
            @Param("occupied") Boolean occupied,
            @Param("limit") int limit);
}
