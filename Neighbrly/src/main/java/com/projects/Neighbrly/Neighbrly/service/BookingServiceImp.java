package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.Exception.resourceNotFound;
import com.projects.Neighbrly.Neighbrly.dto.BookingDto;
import com.projects.Neighbrly.Neighbrly.dto.BookingRequestDto;
import com.projects.Neighbrly.Neighbrly.dto.GuestDto;
import com.projects.Neighbrly.Neighbrly.entity.*;
import com.projects.Neighbrly.Neighbrly.entity.enums.BookingStatus;
import com.projects.Neighbrly.Neighbrly.repository.*;
import com.projects.Neighbrly.Neighbrly.security.JWTService;
import com.projects.Neighbrly.Neighbrly.security.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BookingServiceImp implements BookingService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private  final JWTService jwtService;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;


    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequestDto bookingRequestDto) {

        log.info("Initialising booking  for hotel :{} , room:{}, date{}-{}",bookingRequestDto.getHotelId(),bookingRequestDto.getRoomId(),bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate());
        Hotel hotel = hotelRepository.findById(bookingRequestDto.getHotelId()).orElseThrow(()->new resourceNotFound("Hotel with this Id is not found"+bookingRequestDto.getHotelId()));
        Room room = roomRepository.findById(bookingRequestDto.getRoomId()).orElseThrow(()->new resourceNotFound("Room with this Id is not found"+bookingRequestDto.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate(),bookingRequestDto.getRoomsCount());

        long daysCount = ChronoUnit.DAYS.between(bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate())+1;

        if(inventoryList.size()!= daysCount){
            throw  new IllegalStateException("room is not available anymore");
        }
        //reserve the room/update the bookedCount
        for(Inventory inventory:inventoryList){
            inventory.setReservedCount(inventory.getReservedCount()+bookingRequestDto.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);



        //TODO: CALCULATE DYNAMIC PRICING

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequestDto.getCheckInDate())
                .checkOutDate(bookingRequestDto.getCheckOutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequestDto.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guests) {

        log.info("Adding guests to the booking id {}",bookingId);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->new resourceNotFound("cannot find the booking with id "+bookingId));

        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has expired please try again");
        }

        if(booking.getBookingStatus()!=BookingStatus.RESERVED){
            throw  new IllegalStateException("booking is not in the reserved state");
        }

        for(GuestDto guestDto :guests ){
            Guest guest = modelMapper.map(guestDto,Guest.class);
            guestDto.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking  = bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);



    }

    public User getCurrentUser(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr == null) {
            throw new IllegalStateException("No current HTTP request");
        }

        HttpServletRequest request = attr.getRequest();

        // Extract token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        Long userId = jwtService.getUserIdFromToken(token);

        // TODO: Replace with real DB call
        return userRepository.findById(userId).orElseThrow(()->new resourceNotFound("User with this Id is not found"+userId));
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10)
                .isBefore(LocalDateTime.now());
    }
}
