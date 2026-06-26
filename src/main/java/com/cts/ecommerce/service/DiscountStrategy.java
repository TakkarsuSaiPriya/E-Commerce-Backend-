package com.cts.ecommerce.service;

import com.cts.ecommerce.entity.Cart;

public interface DiscountStrategy {

    double applyDiscount(double total);

    boolean isApplicable(Cart cart);
}