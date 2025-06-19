package com.projects.Neighbrly.Neighbrly.dto;

import com.projects.Neighbrly.Neighbrly.entity.HotelContactInfo;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data

public class HotelDto {
    private Long id;
    private String name;

    private String city;

    private String[] photos;

    private String[] amenities;

    private HotelContactInfo contactInfo;

    private Boolean active;

}
