package com.cts.ecommerce.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Coupon {

    @Id
    private String code;

    @Column(name = "discount_value")
    private Double value;

    private LocalDate expiryDate;

    private Double minPurchase;

    private Boolean active;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public Double getValue() { return value; }

    public void setValue(Double value) { this.value = value; }

    public LocalDate getExpiryDate() { return expiryDate; }

    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public Double getMinPurchase() { return minPurchase; }

    public void setMinPurchase(Double minPurchase) { this.minPurchase = minPurchase; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }
}
