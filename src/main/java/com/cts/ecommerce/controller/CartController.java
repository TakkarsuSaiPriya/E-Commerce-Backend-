package com.cts.ecommerce.controller;

import com.cts.ecommerce.entity.Cart;
import com.cts.ecommerce.entity.CartItem;
import com.cts.ecommerce.repository.CartRepository;
import com.cts.ecommerce.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartService cartService;

    //  Create cart
    @PostMapping("/create")
    public Cart createCart() {
        return cartRepo.save(new Cart());
    }

    //  Get cart
    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Long id) {
        return cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
    }

    // Add item
    @PostMapping("/{id}/add")
    public Cart addItem(@PathVariable Long id,
                        @RequestBody CartItem item) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cart.getItems().add(item);
        return cartRepo.save(cart);
    }

    // Remove item
    @DeleteMapping("/{id}/remove/{itemId}")
    public Cart removeItem(@PathVariable Long id,
                           @PathVariable Long itemId) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        return cartRepo.save(cart);
    }

    // Apply coupon
    @PostMapping("/{id}/applyCoupon/{code}")
    public Cart applyCoupon(@PathVariable Long id,
                            @PathVariable String code) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cart.setCouponCode(code);
        return cartRepo.save(cart);
    }

    //  Get total
    @GetMapping("/{id}/total")
    public double getTotal(@PathVariable Long id) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        return cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }

    // Checkout — accepts username from frontend as query param
    @GetMapping("/{id}/checkout")
    public double checkout(@PathVariable Long id,
                           @RequestParam(required = false, defaultValue = "guest") String username) {
        return cartService.checkout(id, username);
    }
}