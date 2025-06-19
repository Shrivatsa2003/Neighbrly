package com.projects.Neighbrly.Neighbrly.strategy;

import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class OccupancyPricing implements PricingStrategy {

    private final PricingStrategy wrapped;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {

        BigDecimal price = wrapped.calculatePrice(inventory);

        double occupancyRate = (double) inventory.getBookedCount()/inventory.getTotalCount();
        if(occupancyRate>0.8){
            price = price.multiply(BigDecimal.valueOf(1.2));
        }
        return price;
    }
}
