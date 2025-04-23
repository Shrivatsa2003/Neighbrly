package com.projects.Neighbrly.Neighbrly.service;
import com.projects.Neighbrly.Neighbrly.Exception.resourceNotFound;
import com.projects.Neighbrly.Neighbrly.dto.HotelDto;
import com.projects.Neighbrly.Neighbrly.dto.HotelInfoDto;
import com.projects.Neighbrly.Neighbrly.dto.RoomDto;
import com.projects.Neighbrly.Neighbrly.entity.Hotel;
import com.projects.Neighbrly.Neighbrly.entity.Room;
import com.projects.Neighbrly.Neighbrly.repository.HotelRepository;
import com.projects.Neighbrly.Neighbrly.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class HotelServiceImp implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;
    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("creating a new hotel with name {}",hotelDto.getName());
        Hotel hotelEntity = modelMapper.map(hotelDto,Hotel.class);
        hotelEntity.setActive(false);
         hotelEntity = hotelRepository.save(hotelEntity);
        log.info("creating a new hotel with Id "+hotelDto.getId());
         return  modelMapper.map(hotelEntity,HotelDto.class);

    }

    @Override
    public HotelDto getHotelById(Long Id) {
        log.info("getting hotel by id{}", Id);
        Hotel hotelEntity = hotelRepository
                .findById(Id)
                .orElseThrow(()->new resourceNotFound("Could not find any hotel with the Id "+Id));
        return modelMapper.map(hotelEntity,HotelDto.class);

    }

    @Override
    public HotelDto updateHotelById(Long Id, HotelDto hotelDto) {
        log.info("getting hotel by id{}", Id);
        Hotel hotel = hotelRepository
                .findById(Id)
                .orElseThrow(()->new resourceNotFound("Could not find any hotel with the Id "+Id));
        modelMapper.map(hotelDto, hotel);
        return modelMapper.map(hotelRepository.save(hotel),HotelDto.class);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        return hotelRepository
                .findAll()
                .stream()
                .map((element)->modelMapper.map(element,HotelDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteHotelById(Long Id) {
        Hotel hotel = hotelRepository
                .findById(Id)
                .orElseThrow(() -> new resourceNotFound("Could not find any hotel with the Id " + Id));

        log.info("deleting the inventory for this hotel");
        for (Room room : hotel.getRooms()) {
            inventoryService.deleteFutureInventory(room);
        }

        log.info("deleting rooms first");
        roomRepository.deleteAll(hotel.getRooms());

        log.info("deleting the hotel");
        hotelRepository.delete(hotel);
    }


    @Transactional
    @Override
    public void activateHotel(Long Id) {
        log.info("activating the hotel with hotelId "+Id);
        Hotel hotel = hotelRepository
                .findById(Id)
                .orElseThrow(()->new resourceNotFound("Could not find any hotel with the Id "+Id));

        log.info("creating inventories for the rooms of this hotel");
        hotel.setActive(true);
        for(Room room:hotel.getRooms()){
            inventoryService.initializeInventory(room);
        }


    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new resourceNotFound("Could not find any hotel with the Id " + hotelId));

        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }
}
