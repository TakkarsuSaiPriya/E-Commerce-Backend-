package com.cts.ecommerce.repository;

import com.cts.ecommerce.entity.Cart;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository repo;

    // ✅ CREATE
    @Test
    void createCart() {

        Cart cart = new Cart();

        Cart saved = repo.save(cart);

        assertThat(saved.getId()).isNotNull();
    }

    // ✅ FIND
    @Test
    void findCart() {

        Cart saved = repo.save(new Cart());

        Optional<Cart> found = repo.findById(saved.getId());

        assertThat(found).isPresent();
    }

    // ✅ DELETE
    @Test
    void deleteCart() {

        Cart cart = repo.save(new Cart());

        repo.delete(cart);

        Optional<Cart> result = repo.findById(cart.getId());

        assertThat(result).isEmpty();
    }

    // ✅ FIND ALL
    @Test
    void findAllCarts() {

        repo.save(new Cart());
        repo.save(new Cart());

        assertThat(repo.findAll().size()).isGreaterThan(0);
    }
}