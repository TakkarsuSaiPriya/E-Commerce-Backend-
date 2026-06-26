package com.cts.ecommerce.strategy;

public class PercentageStrategy implements DiscountStrategy {

    @Override
    public double apply(double amount, double value) {
        return amount - (amount * value / 100);
    }
}