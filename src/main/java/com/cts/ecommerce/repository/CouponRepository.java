package com.cts.ecommerce.repository;

import com.cts.ecommerce.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, String> {
}
