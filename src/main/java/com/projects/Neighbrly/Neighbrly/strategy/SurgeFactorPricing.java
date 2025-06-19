package com.projects.Neighbrly.Neighbrly.strategy;

import com.projects.Neighbrly.Neighbrly.entity.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class SurgeFactorPricing implements  PricingStrategy {
    private final PricingStrategy wrapped;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {

        return wrapped.calculatePrice(inventory).multiply(inventory.getSurgeFactor());
    }
}
