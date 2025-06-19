package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.entity.Booking;
import com.projects.Neighbrly.Neighbrly.entity.User;
import com.projects.Neighbrly.Neighbrly.repository.BookingRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckoutImp implements Checkout{
    private final BookingRepository bookingRepository;
    @Override
    public String getCheckoutSession(Booking booking, String successUrl, String failureUrl) {

        log.info("creating session for booking {}",booking.getId());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{

            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setEmail(user.getEmail())
                    .setName(user.getName())
                    .build();
            Customer customer = Customer.create(customerCreateParams);
            SessionCreateParams params =  SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setCustomer(customer.getId())
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(failureUrl)
                    .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("inr")
                                                .setUnitAmount(booking.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(booking.getHotel().getName() + ":" + booking.getRoom().getType())
                                                                .setDescription("Booking Id :"+booking.getId())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                    )
                    .build();

            Session session = Session.create(params);
            booking.setPaymentSessionId(session.getId());
            bookingRepository.save(booking);
            log.info("session created successfully for bookingId : {}",booking.getId());
            return session.getUrl();
        }
        catch (StripeException e){
            throw new RuntimeException(e);
        }

    }
}
