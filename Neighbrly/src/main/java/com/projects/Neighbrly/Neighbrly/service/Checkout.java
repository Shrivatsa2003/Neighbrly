package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.entity.Booking;

public interface Checkout {
    String getCheckoutSession(Booking  booking,String successUrl,String failureUrl);
}
