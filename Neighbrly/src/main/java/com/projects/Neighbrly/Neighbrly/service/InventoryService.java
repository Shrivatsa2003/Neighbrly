package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.dto.HotelDto;
import com.projects.Neighbrly.Neighbrly.dto.HotelPriceDto;
import com.projects.Neighbrly.Neighbrly.dto.HotelSearchRequest;
import com.projects.Neighbrly.Neighbrly.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeInventory(Room room);
    void deleteFutureInventory(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
