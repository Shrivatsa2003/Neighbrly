package com.projects.Neighbrly.Neighbrly.repository;

import com.projects.Neighbrly.Neighbrly.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.InterfaceAddress;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}
