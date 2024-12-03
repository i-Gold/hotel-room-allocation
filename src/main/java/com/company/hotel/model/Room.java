package com.company.hotel.model;

import com.company.hotel.enumeration.RoomCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomCategory category;

    private Boolean isOccupied;

    public Room() {
    }

    public Room(RoomCategory roomCategory, boolean isOccupied) {
        this.category = roomCategory;
        this.isOccupied = isOccupied;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public void setCategory(RoomCategory category) {
        this.category = category;
    }

    public Boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(Boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (!Objects.equals(id, room.id)) return false;
        if (category != room.category) return false;
        return Objects.equals(isOccupied, room.isOccupied);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (isOccupied != null ? isOccupied.hashCode() : 0);
        return result;
    }
}
