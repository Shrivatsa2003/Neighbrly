package com.projects.Neighbrly.Neighbrly.controller;

import com.projects.Neighbrly.Neighbrly.dto.BookingDto;
import com.projects.Neighbrly.Neighbrly.dto.BookingRequestDto;
import com.projects.Neighbrly.Neighbrly.dto.GuestDto;
import com.projects.Neighbrly.Neighbrly.service.BookingService;
import com.projects.Neighbrly.Neighbrly.service.BookingServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor

public class BookingController {

    private final BookingServiceImp bookingServiceImp;

    @PostMapping("/init")
    @Operation(summary = "Initiate the booking", tags = {"Booking Flow"})
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequestDto bookingRequestDto){
        return ResponseEntity.ok(bookingServiceImp.initialiseBooking(bookingRequestDto));
    }

    @PostMapping("/{bookingId}/addGuests")
    @Operation(summary = "Add guest Ids to the booking", tags = {"Booking Flow"})
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
                                                @RequestBody List<Long> guestIdList) {
        return ResponseEntity.ok(bookingServiceImp.addGuests(bookingId, guestIdList));
    }

    @PostMapping("/{bookingId}/payment")
    @Operation(summary = "Initiate payments flow for the booking", tags = {"Booking Flow"})
    public ResponseEntity<Map<String,String>> initiatePayment(@PathVariable Long bookingId ){
        String sessionUrl =  bookingServiceImp.initiatePayments(bookingId);
        return  ResponseEntity.ok(Map.of("Session_Url",sessionUrl));
    }

    @PostMapping("/{bookingId}/cancel")
    @Operation(summary = "Cancel the booking", tags = {"Booking Flow"})
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId ){
            bookingServiceImp.cancelBooking(bookingId);
          return ResponseEntity.noContent().build();
    }



}
