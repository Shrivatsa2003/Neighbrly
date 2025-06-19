package com.projects.Neighbrly.Neighbrly.repository;

import com.projects.Neighbrly.Neighbrly.entity.Guest;
import com.projects.Neighbrly.Neighbrly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest,Long> {

    List<Guest> findByUser(User user);
}
