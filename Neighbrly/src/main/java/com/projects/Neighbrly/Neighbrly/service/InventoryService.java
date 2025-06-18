package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.dto.*;
import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import com.projects.Neighbrly.Neighbrly.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {
    void initializeInventory(Room room);
    void deleteFutureInventory(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);

    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto);
}
