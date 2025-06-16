package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.dto.BookingDto;
import com.projects.Neighbrly.Neighbrly.dto.BookingRequestDto;
import com.projects.Neighbrly.Neighbrly.dto.GuestDto;
import com.projects.Neighbrly.Neighbrly.entity.Guest;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequestDto bookingRequestDto);
    BookingDto addGuests(Long bookingId, List<GuestDto> guests);
    String initiatePayments(Long bookingId);
}
