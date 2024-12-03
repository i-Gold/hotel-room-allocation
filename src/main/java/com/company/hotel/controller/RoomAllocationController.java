package com.company.hotel.controller;

import com.company.hotel.dto.OccupancyRequest;
import com.company.hotel.dto.OccupancyResponse;
import com.company.hotel.service.RoomAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<OccupancyResponse> allocateRooms(@RequestBody OccupancyRequest request) {
        OccupancyResponse response = roomAllocationService.allocateRooms(request);
        return ResponseEntity.ok(response);
    }
}
