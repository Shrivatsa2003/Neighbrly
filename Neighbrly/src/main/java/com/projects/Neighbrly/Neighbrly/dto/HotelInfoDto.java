package com.projects.Neighbrly.Neighbrly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.RoundingMode;
import java.util.List;

@Data
@AllArgsConstructor
public class HotelInfoDto {
    private HotelDto hotel;
    private List<RoomDto> room;
}
