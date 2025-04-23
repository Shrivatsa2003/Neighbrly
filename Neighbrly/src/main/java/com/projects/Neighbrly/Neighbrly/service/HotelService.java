package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.dto.HotelDto;
import com.projects.Neighbrly.Neighbrly.dto.HotelInfoDto;
import com.projects.Neighbrly.Neighbrly.entity.Hotel;

import java.util.List;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long Id);
    HotelDto updateHotelById(Long Id, HotelDto hotelDto);
    List<HotelDto> getAllHotels();
    void deleteHotelById(Long Id);
    void activateHotel(Long Id);

    HotelInfoDto getHotelInfoById(Long hotelId);
}
