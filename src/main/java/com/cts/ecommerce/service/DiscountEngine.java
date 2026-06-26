package com.cts.ecommerce.service;

import com.cts.ecommerce.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountEngine {

    @Autowired
    private List<DiscountStrategy> strategies;

    public double applyDiscounts(Cart cart, double total) {

        if (strategies == null) return total;

        for (DiscountStrategy strategy : strategies) {

            if (strategy.isApplicable(cart)) {
                total = strategy.applyDiscount(total);
            }
        }

        return total;
    }
}
