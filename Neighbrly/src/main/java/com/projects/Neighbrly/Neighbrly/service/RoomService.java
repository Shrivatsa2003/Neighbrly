package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.dto.RoomDto;

import java.util.List;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId,RoomDto roomDto);
    List<RoomDto> getAllRooms(Long hotelId);

    RoomDto getRoomById(Long roomId, Long hotelId);

    void deleteRoomById(Long roomId);
}
