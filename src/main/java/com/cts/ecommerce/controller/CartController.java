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

    @PostMapping("/create")
    public Cart createCart() {
        return cartRepo.save(new Cart());
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Long id) {
        return cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
    }

    @PostMapping("/{id}/add")
    public Cart addItem(@PathVariable Long id, @RequestBody CartItem item) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cart.getItems().add(item);
        return cartRepo.save(cart);
    }

    @DeleteMapping("/{id}/remove/{itemId}")
    public Cart removeItem(@PathVariable Long id, @PathVariable Long itemId) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        return cartRepo.save(cart);
    }

    @PostMapping("/{id}/applyCoupon/{code}")
    public Cart applyCoupon(@PathVariable Long id,
                            @PathVariable String code) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cart.setCouponCode(code);
        return cartRepo.save(cart);
    }

    @GetMapping("/{id}/total")
    public double getTotal(@PathVariable Long id) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        return cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }

    @GetMapping("/{id}/checkout")
    public double checkout(@PathVariable Long id) {
        return cartService.checkout(id);
    }
}