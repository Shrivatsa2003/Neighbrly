package com.projects.Neighbrly.Neighbrly.repository;


import com.projects.Neighbrly.Neighbrly.entity.Hotel;
import com.projects.Neighbrly.Neighbrly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Long> {

    List<Hotel> findByOwner(User user);

}
