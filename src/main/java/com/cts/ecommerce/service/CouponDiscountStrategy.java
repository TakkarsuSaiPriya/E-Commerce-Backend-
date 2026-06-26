package com.cts.ecommerce.service;

import com.cts.ecommerce.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public class CouponDiscountStrategy implements DiscountStrategy {

    @Override
    public double applyDiscount(double total) {
        return total * 0.9; // 10% discount
    }

    @Override
    public boolean isApplicable(Cart cart) {
        return cart.getCouponCode() != null;
    }
}