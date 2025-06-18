package com.projects.Neighbrly.Neighbrly.config;

import com.projects.Neighbrly.Neighbrly.dto.BookingDto;
import com.projects.Neighbrly.Neighbrly.dto.HotelDto;
import com.projects.Neighbrly.Neighbrly.entity.Booking;
import com.projects.Neighbrly.Neighbrly.entity.Hotel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        return mapper;
    }

}
