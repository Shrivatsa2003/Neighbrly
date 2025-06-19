package com.projects.Neighbrly.Neighbrly.strategy;

import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class HolidayPricing implements PricingStrategy{

    private final PricingStrategy wrapped;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        boolean isHoliday = true;
        if(isHoliday){
            price = price.multiply(BigDecimal.valueOf(1.25));

        }
        return price;

    }
}
