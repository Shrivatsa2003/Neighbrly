package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.entity.Hotel;
import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import com.projects.Neighbrly.Neighbrly.entity.Room;
import com.projects.Neighbrly.Neighbrly.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImp  implements  InventoryService{

    private final InventoryRepository inventoryRepository;
    @Override
    public void initializeInventory(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate afterOneYear = today.plusYears(1);

        for(;!today.isAfter(afterOneYear);today=today.plusDays(1)){
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .closed(false)
                    .surgeFactor(BigDecimal.ONE)
                    .date(today)
                    .price(room.getBasePrice())
                    .totalCount(room.getTotalCount())
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteFutureInventory(Room room) {
        LocalDate today =LocalDate.now();
        inventoryRepository.deleteByDateAfterAndRoom(today,room);
    }
}
