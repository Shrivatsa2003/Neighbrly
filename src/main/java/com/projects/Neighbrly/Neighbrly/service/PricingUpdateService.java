package com.projects.Neighbrly.Neighbrly.service;


import com.projects.Neighbrly.Neighbrly.entity.Hotel;
import com.projects.Neighbrly.Neighbrly.entity.HotelMinPrice;
import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import com.projects.Neighbrly.Neighbrly.repository.HotelMinPriceRepository;
import com.projects.Neighbrly.Neighbrly.repository.HotelRepository;
import com.projects.Neighbrly.Neighbrly.repository.InventoryRepository;
import com.projects.Neighbrly.Neighbrly.strategy.PricingCalculator;
import com.projects.Neighbrly.Neighbrly.strategy.PricingStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional

public class PricingUpdateService {
    int page =0;
    int batchSize = 100;
    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingCalculator pricingCalculator;
    @Scheduled(cron = "0 0 * * * *")
    public void updatePrices(){

        while(true){
            Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(page,batchSize));

            if(hotelPage.isEmpty()){
                break;
            }
            hotelPage.getContent().forEach(hotel1 -> updateHotelPrice(hotel1));
            page++;
        }
    }

    private void updateHotelPrice(Hotel hotel){
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusYears(1);

        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel,startDate,endDate);
        log.info("updating hotel prices with hote Id"+hotel.getId());
        updateInventoryPrices(inventoryList);

        updateHotelMinPrice(hotel,inventoryList,startDate,endDate);

    }

    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate, LocalDate endDate) {
        log.info("updating min price of the rooms");
        Map<LocalDate,BigDecimal> dailyMinPrices = inventoryList.stream()
                .collect(Collectors.groupingBy(
                        Inventory::getDate,
                        Collectors.mapping(Inventory::getPrice,Collectors.minBy(Comparator.naturalOrder()))
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,e->e.getValue().orElse(BigDecimal.ZERO)));

        List<HotelMinPrice> hotelMinPrices = new ArrayList<>();

        dailyMinPrices.forEach((date,price)->{
            HotelMinPrice hotelMinPrice = hotelMinPriceRepository.findByHotelAndDate(hotel,date)
                    .orElse(new HotelMinPrice(hotel,date));
            hotelMinPrice.setPrice(price);
            hotelMinPrices.add(hotelMinPrice);
        });
        hotelMinPriceRepository.saveAll(hotelMinPrices);
    }

    private void updateInventoryPrices(List<Inventory> inventoryList) {
        inventoryList.forEach(
                inventory -> {
                    BigDecimal dynamicPrice = pricingCalculator.calculateDynamicPricing(inventory);
                    inventory.setPrice(dynamicPrice);
                }
        );
        inventoryRepository.saveAll(inventoryList);
    }

}
