package com.company.hotel.controller;

import com.company.hotel.dto.AllocationRequest;
import com.company.hotel.dto.AllocationResponse;
import com.company.hotel.service.RoomAllocationService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<AllocationResponse> allocateRooms(@RequestBody @Valid AllocationRequest request) {
        try {
            AllocationResponse response = roomAllocationService.allocateRooms(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ValidationException | IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
