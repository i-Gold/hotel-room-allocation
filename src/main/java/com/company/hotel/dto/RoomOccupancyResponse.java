package com.company.hotel.dto;

public class RoomOccupancyResponse {
    private Integer usagePremium;
    private Double revenuePremium;
    private Integer usageEconomy;
    private Double revenueEconomy;

    public RoomOccupancyResponse() {
    }

    public RoomOccupancyResponse(Integer usagePremium, Double revenuePremium, Integer usageEconomy, Double revenueEconomy) {
        this.usagePremium = usagePremium;
        this.revenuePremium = revenuePremium;
        this.usageEconomy = usageEconomy;
        this.revenueEconomy = revenueEconomy;
    }

    public Integer getUsagePremium() {
        return usagePremium;
    }

    public void setUsagePremium(Integer usagePremium) {
        this.usagePremium = usagePremium;
    }

    public Double getRevenuePremium() {
        return revenuePremium;
    }

    public void setRevenuePremium(Double revenuePremium) {
        this.revenuePremium = revenuePremium;
    }

    public Integer getUsageEconomy() {
        return usageEconomy;
    }

    public void setUsageEconomy(Integer usageEconomy) {
        this.usageEconomy = usageEconomy;
    }

    public Double getRevenueEconomy() {
        return revenueEconomy;
    }

    public void setRevenueEconomy(Double revenueEconomy) {
        this.revenueEconomy = revenueEconomy;
    }
}
