package com.projects.Neighbrly.Neighbrly.repository;

import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import com.projects.Neighbrly.Neighbrly.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
