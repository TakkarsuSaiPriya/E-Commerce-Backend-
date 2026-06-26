package com.cts.ecommerce.ruleengine;

import com.cts.ecommerce.entity.Cart;

public class RuleContext {

    private Cart cart;
    private double total;

    public RuleContext(Cart cart) {
        this.cart = cart;

        // ✅ calculate dynamically
        this.total = cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }

    public Cart getCart() {
        return cart;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}