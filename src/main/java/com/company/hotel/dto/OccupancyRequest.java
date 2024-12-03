package com.company.hotel.dto;

import java.util.List;


public class OccupancyRequest {
    private Integer premiumRooms;
    private Integer economyRooms;
    private List<Double> potentialGuests;

    public OccupancyRequest() {
    }

    public OccupancyRequest(Integer premiumRooms, Integer economyRooms, List<Double> potentialGuests) {
        this.premiumRooms = premiumRooms;
        this.economyRooms = economyRooms;
        this.potentialGuests = potentialGuests;
    }

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
}
