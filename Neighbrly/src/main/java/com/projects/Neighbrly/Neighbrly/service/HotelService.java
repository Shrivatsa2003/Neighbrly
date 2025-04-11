package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.dto.HotelDto;
import com.projects.Neighbrly.Neighbrly.entity.Hotel;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long Id);
    HotelDto updateHotelById(Long Id, HotelDto hotelDto);
    void deleteHotelById(Long Id);
    void activateHotel(Long Id);
}
