package com.projects.Neighbrly.Neighbrly.dto;

import com.projects.Neighbrly.Neighbrly.entity.Hotel;
import lombok.Data;

@Data
public class HotelPriceDto {
    private  Hotel hotel;
    private  Double price;
}
