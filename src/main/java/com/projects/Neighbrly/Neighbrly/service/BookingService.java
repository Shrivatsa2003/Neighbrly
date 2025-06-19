package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.dto.BookingDto;
import com.projects.Neighbrly.Neighbrly.dto.BookingRequestDto;
import com.projects.Neighbrly.Neighbrly.dto.GuestDto;
import com.projects.Neighbrly.Neighbrly.dto.HotelReportDto;
import com.projects.Neighbrly.Neighbrly.entity.Booking;
import com.projects.Neighbrly.Neighbrly.entity.Guest;

import com.stripe.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequestDto bookingRequestDto);
     BookingDto addGuests(Long bookingId, List<Long> guestIdList);
    String initiatePayments(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelId);
    void capturePayment(Event event);
    void cancelBooking(Long bookingId);

    HotelReportDto getHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);
    List<BookingDto> getMyBookings();
}
