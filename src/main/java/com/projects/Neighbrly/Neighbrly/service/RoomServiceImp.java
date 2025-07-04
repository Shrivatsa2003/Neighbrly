package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.Exception.ResourceNotFoundException;
import com.projects.Neighbrly.Neighbrly.Exception.UnAuthorisedException;
import com.projects.Neighbrly.Neighbrly.dto.RoomDto;
import com.projects.Neighbrly.Neighbrly.entity.Hotel;
import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import com.projects.Neighbrly.Neighbrly.entity.Room;
import com.projects.Neighbrly.Neighbrly.entity.User;
import com.projects.Neighbrly.Neighbrly.repository.HotelRepository;
import com.projects.Neighbrly.Neighbrly.repository.InventoryRepository;
import com.projects.Neighbrly.Neighbrly.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.projects.Neighbrly.Neighbrly.utils.userUtil.getCurrentUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImp implements  RoomService{
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("creating a new room in the hotel{}",hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->  new ResourceNotFoundException("could not find the hotel with id"+hotelId));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);
        if(hotel.getActive()){
            inventoryService.initializeInventory(room);
        }

        return modelMapper.map(room,RoomDto.class);


    }

    @Override
    public List<RoomDto> getAllRooms(Long hotelId) {
        log.info("getting all rooms in hotel id : "+hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->  new ResourceNotFoundException("could not find the hotel with id"+hotelId));

        return hotel.getRooms()
                .stream()
                .map((element)->modelMapper.map(element,RoomDto.class))
                .collect(Collectors.toList());
    }



    @Override
    public RoomDto getRoomById(Long roomId, Long hotelId) {
        log.info("getting room with id{} ",roomId);

        // check if the user  is providing valid hotel
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->  new ResourceNotFoundException("could not find the hotel with id"+hotelId));

        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("could not find a room with id"+roomId));

        //check if room provided in dto exists in that hotel itself
        if(!Objects.equals(room.getHotel().getId(), hotelId)) throw  new ResourceNotFoundException("could not find room Id "+roomId+ " in hotel with hotel Id "+hotelId);
        return modelMapper.map(room,RoomDto.class);
    }

    @Transactional
    @Override
    public void deleteRoomById(Long roomId) {
        log.info("deleting room with id{}",roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("could not find a room with id"+roomId));
        inventoryService.deleteFutureInventory(room);

        roomRepository.delete(room);
    }

    @Override
    @Transactional
    public RoomDto updateRoomById(Long hotelId, Long roomId, RoomDto roomDto) {
        log.info("Updating the room with ID: {}", roomId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        User user = getCurrentUser();
        if(!user.getId().equals(hotel.getOwner().getId())) {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotelId);
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));

       if(!Objects.equals(room.getTotalCount(), roomDto.getTotalCount())){
           List<Inventory> inventoryList = inventoryRepository.findByRoom(room);
           inventoryList.forEach(inventory -> inventory.setTotalCount(roomDto.getTotalCount()));
           inventoryRepository.saveAll(inventoryList);
       }
        modelMapper.map(roomDto, room);

        room.setId(roomId);

        room = roomRepository.save(room);

        return modelMapper.map(room, RoomDto.class);
    }
}
