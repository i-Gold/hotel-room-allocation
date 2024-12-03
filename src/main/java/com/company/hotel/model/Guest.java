package com.company.hotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double willingnessToPay;

    public Guest() {
    }

    public Guest(Long id, Double willingnessToPay) {
        this.id = id;
        this.willingnessToPay = willingnessToPay;
    }

    public Guest(Double willingnessToPay) {
        this.willingnessToPay = willingnessToPay;
    }

    public Double getWillingnessToPay() {
        return willingnessToPay;
    }

    public void setWillingnessToPay(Double willingnessToPay) {
        this.willingnessToPay = willingnessToPay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guest guest = (Guest) o;

        if (!Objects.equals(id, guest.id)) return false;
        return Objects.equals(willingnessToPay, guest.willingnessToPay);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (willingnessToPay != null ? willingnessToPay.hashCode() : 0);
        return result;
    }
}
