package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.Exception.ResourceNotFoundException;
import com.projects.Neighbrly.Neighbrly.dto.HotelPriceDto;
import com.projects.Neighbrly.Neighbrly.dto.HotelSearchRequest;
import com.projects.Neighbrly.Neighbrly.dto.InventoryDto;
import com.projects.Neighbrly.Neighbrly.dto.UpdateInventoryRequestDto;
import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import com.projects.Neighbrly.Neighbrly.entity.Room;
import com.projects.Neighbrly.Neighbrly.entity.User;
import com.projects.Neighbrly.Neighbrly.repository.HotelMinPriceRepository;
import com.projects.Neighbrly.Neighbrly.repository.InventoryRepository;
import com.projects.Neighbrly.Neighbrly.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.projects.Neighbrly.Neighbrly.utils.userUtil.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImp  implements  InventoryService{

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final HotelMinPriceRepository hotelMinPrice;
    private final RoomRepository roomRepository;
    @Override
    public void initializeInventory(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate afterOneYear = today.plusYears(1);

        for(;!today.isAfter(afterOneYear);today=today.plusDays(1)){
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .city(room.getHotel().getCity())
                    .closed(false)
                    .surgeFactor(BigDecimal.ONE)
                    .date(today)
                    .price(room.getBasePrice())
                    .totalCount(room.getTotalCount())
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteFutureInventory(Room room) {
        LocalDate today =LocalDate.now();
        inventoryRepository.deleteByDateAfterAndRoom(today,room);
    }

    @Override
    public Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());
        long dateCount = ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;


        Page<HotelPriceDto> hotelPage = hotelMinPrice.findHotelsWithAvailableInventory(hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(),hotelSearchRequest.getRoomsCount(),dateCount,pageable);

        return hotelPage;
    }


    @Override
    public List<InventoryDto> getAllInventoryByRoom(Long roomId) {
        log.info("Getting All inventory by room for room with id: {}", roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: "+roomId));

        User user = getCurrentUser();
        if(!user.equals(room.getHotel().getOwner())) throw new AccessDeniedException("You are not the owner of room with id: "+roomId);

        return inventoryRepository.findByRoomOrderByDate(room).stream()
                .map((element) -> modelMapper.map(element,
                        InventoryDto.class))
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto) {
        log.info("Updating All inventory by room for room with id: {} between date range: {} - {}", roomId,
                updateInventoryRequestDto.getStartDate(), updateInventoryRequestDto.getEndDate());

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: "+roomId));

        User user = getCurrentUser();
        if(!user.equals(room.getHotel().getOwner())) throw new AccessDeniedException("You are not the owner of room with id: "+roomId);

        inventoryRepository.getInventoryAndLockBeforeUpdate(roomId, updateInventoryRequestDto.getStartDate(),
                updateInventoryRequestDto.getEndDate());

        inventoryRepository.updateInventory(roomId, updateInventoryRequestDto.getStartDate(),
                updateInventoryRequestDto.getEndDate(), updateInventoryRequestDto.getClosed(),
                updateInventoryRequestDto.getSurgeFactor());
    }
}
