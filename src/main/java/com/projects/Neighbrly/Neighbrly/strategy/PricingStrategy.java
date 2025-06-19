package com.projects.Neighbrly.Neighbrly.strategy;

import com.projects.Neighbrly.Neighbrly.entity.Inventory;


import java.math.BigDecimal;


public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
