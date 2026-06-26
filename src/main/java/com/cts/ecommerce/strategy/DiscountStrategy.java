package com.cts.ecommerce.strategy;

public interface DiscountStrategy {
    double apply(double amount, double value);
}