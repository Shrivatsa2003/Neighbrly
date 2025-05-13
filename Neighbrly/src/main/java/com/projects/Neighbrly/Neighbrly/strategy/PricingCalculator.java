package com.projects.Neighbrly.Neighbrly.strategy;

import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@Component
@RequiredArgsConstructor
public class PricingCalculator {

    public BigDecimal calculateDynamicPricing(Inventory inventory){

        PricingStrategy pricingStrategy = new BasePricing();
        pricingStrategy = new SurgeFactorPricing(pricingStrategy);
        pricingStrategy = new OccupancyPricing(pricingStrategy);
        pricingStrategy = new UrgencyPricing(pricingStrategy);
        pricingStrategy = new HolidayPricing(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);

    }

}
