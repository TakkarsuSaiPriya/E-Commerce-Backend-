package com.cts.ecommerce.repository;

import com.cts.ecommerce.entity.Coupon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository repo;

    @Test
    void saveCoupon() {

        Coupon c = new Coupon();
        c.setCode("SAVE10");


        c.setValue(10.0);

        Coupon saved = repo.save(c);

        assertThat(saved).isNotNull();
    }
}