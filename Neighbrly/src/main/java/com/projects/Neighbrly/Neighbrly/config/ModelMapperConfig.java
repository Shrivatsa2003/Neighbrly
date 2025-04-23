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

        // Skip mapping the ID from DTO to Entity to avoid identifier mutation
        mapper.typeMap(HotelDto.class, Hotel.class)
                .addMappings(m -> m.skip(Hotel::setId));

        mapper.typeMap(Booking.class, BookingDto.class)
                .addMappings(m -> {
            m.map(src -> src.getHotel().getId(), BookingDto::setHotelId);
            m.map(src -> src.getHotel().getName(), BookingDto::setHotelName);
            m.map(src -> src.getRoom().getId(), BookingDto::setRoomId);
            m.map(src -> src.getRoom().getType(), BookingDto::setRoomName);
            m.map(src -> src.getUser().getId(), BookingDto::setUserId);
        });

        return mapper;
    }

}
