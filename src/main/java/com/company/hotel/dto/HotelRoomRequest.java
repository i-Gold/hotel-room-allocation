package com.company.hotel.dto;

import java.util.List;
import java.util.Objects;

public class HotelRoomRequest {
    private Integer premiumRooms;
    private Integer economyRooms;
    private List<Double> potentialGuests;

    public Integer getPremiumRooms() {
        return premiumRooms;
    }

    public void setPremiumRooms(Integer premiumRooms) {
        this.premiumRooms = premiumRooms;
    }

    public Integer getEconomyRooms() {
        return economyRooms;
    }

    public void setEconomyRooms(Integer economyRooms) {
        this.economyRooms = economyRooms;
    }

    public List<Double> getPotentialGuests() {
        return potentialGuests;
    }

    public void setPotentialGuests(List<Double> potentialGuests) {
        this.potentialGuests = potentialGuests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotelRoomRequest that = (HotelRoomRequest) o;

        if (!Objects.equals(premiumRooms, that.premiumRooms)) return false;
        if (!Objects.equals(economyRooms, that.economyRooms)) return false;
        return Objects.equals(potentialGuests, that.potentialGuests);
    }

    @Override
    public int hashCode() {
        int result = premiumRooms != null ? premiumRooms.hashCode() : 0;
        result = 31 * result + (economyRooms != null ? economyRooms.hashCode() : 0);
        result = 31 * result + (potentialGuests != null ? potentialGuests.hashCode() : 0);
        return result;
    }
}
