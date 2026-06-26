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

    public double checkout(Long cartId) {

        Cart cart = cartRepo.findById(cartId).orElseThrow();

        double total = 0;

        for (CartItem item : cart.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        // ✅ Apply Coupon Validation
        if (cart.getCouponCode() != null) {

            Coupon coupon = couponRepo.findById(cart.getCouponCode()).orElse(null);

            if (couponValidationService.isValid(coupon, total)) {

                total = total - (total * coupon.getValue() / 100);
            }
        }

        // ✅ Apply Strategy Discounts
        total = discountEngine.applyDiscounts(cart, total);

        // ✅ Create Order
        orderService.createOrder(total, "testUser");

        return total;
    }
}