package com.projects.Neighbrly.Neighbrly.dto;

import com.projects.Neighbrly.Neighbrly.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceDto {
    private  Hotel hotel;
    private  Double price;
}
