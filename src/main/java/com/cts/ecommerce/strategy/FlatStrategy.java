package com.cts.ecommerce.strategy;

public class FlatStrategy implements DiscountStrategy {

    @Override
    public double apply(double amount, double value) {
        return amount - value;
    }
}