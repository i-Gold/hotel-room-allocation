package com.company.hotel.controller;

import com.company.hotel.service.RoomAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/occupancy")
public class RoomAllocationController {

    private final RoomAllocationService roomAllocationService;

    @Autowired
    public RoomAllocationController(RoomAllocationService roomAllocationService) {
        this.roomAllocationService = roomAllocationService;
    }
}
