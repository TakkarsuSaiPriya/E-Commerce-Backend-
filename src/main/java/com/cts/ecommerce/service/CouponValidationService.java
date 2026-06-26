package com.cts.ecommerce.service;

import com.cts.ecommerce.entity.Coupon;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CouponValidationService {

    public boolean isValid(Coupon coupon, double total) {

        if (coupon == null) return false;

        if (coupon.getExpiryDate() != null &&
                coupon.getExpiryDate().isBefore(LocalDate.now())) {
            return false;
        }

        if (coupon.getMinPurchase() != null &&
                total < coupon.getMinPurchase()) {
            return false;
        }

        if (coupon.getActive() != null && !coupon.getActive()) {
            return false;
        }

        return true;
    }
}