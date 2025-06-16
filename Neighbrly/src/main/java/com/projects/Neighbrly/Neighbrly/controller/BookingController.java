package com.projects.Neighbrly.Neighbrly.controller;

import com.projects.Neighbrly.Neighbrly.dto.BookingDto;
import com.projects.Neighbrly.Neighbrly.dto.BookingRequestDto;
import com.projects.Neighbrly.Neighbrly.dto.GuestDto;
import com.projects.Neighbrly.Neighbrly.service.BookingService;
import com.projects.Neighbrly.Neighbrly.service.BookingServiceImp;
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
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequestDto bookingRequestDto){
        return ResponseEntity.ok(bookingServiceImp.initialiseBooking(bookingRequestDto));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId ,@RequestBody List<GuestDto> guests){
        System.out.println("Received POST /booking/" + bookingId + "/addGuests");
        return  ResponseEntity.ok(bookingServiceImp.addGuests(bookingId,guests));
    }

    @PostMapping("/{bookingId}/payment")
    public ResponseEntity<Map<String,String>> initiatePayment(@PathVariable Long bookingId ){
        String sessionUrl =  bookingServiceImp.initiatePayments(bookingId);
        return  ResponseEntity.ok(Map.of("Session_Url",sessionUrl));
    }

}
