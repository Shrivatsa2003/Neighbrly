package com.projects.Neighbrly.Neighbrly.repository;

import com.projects.Neighbrly.Neighbrly.entity.Booking;
import com.projects.Neighbrly.Neighbrly.entity.Hotel;
import com.projects.Neighbrly.Neighbrly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    Optional<Booking> findByPaymentSessionId(String sessionId);
    List<Booking> findByHotelAndCreatedAtBetween(Hotel hotel, LocalDateTime startDate, LocalDateTime endDate);
    List<Booking> findByHotel(Hotel hotel);
    Collection<Object> findByUser(User user);
}
