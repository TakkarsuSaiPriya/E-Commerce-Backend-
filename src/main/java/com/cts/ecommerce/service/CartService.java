package com.cts.ecommerce.service;

import com.cts.ecommerce.entity.*;
import com.cts.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CouponRepository couponRepo;

    @Autowired
    private CouponValidationService couponValidationService;

    @Autowired
    private DiscountEngine discountEngine;

    @Autowired
    private OrderService orderService;

    //  username passed directly from frontend
    public double checkout(Long cartId, String username) {

        // Get cart
        Cart cart = cartRepo.findById(cartId).orElseThrow();

        //  Calculate subtotal
        double total = 0;
        for (CartItem item : cart.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        //  Apply Coupon Discount
        if (cart.getCouponCode() != null) {
            Coupon coupon = couponRepo
                    .findById(cart.getCouponCode())
                    .orElse(null);

            if (couponValidationService.isValid(coupon, total)) {
                total = total - (total * coupon.getValue() / 100);
            }
        }

        //  Apply Strategy Discounts via Discount Engine
        total = discountEngine.applyDiscounts(cart, total);

        //  Use real username from frontend or fallback to guest
        String finalUsername = (username != null && !username.trim().isEmpty())
                ? username.trim()
                : "guest";

        //  Create Order with real username
        orderService.createOrder(total, finalUsername);

        return total;
    }
}