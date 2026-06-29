package com.cts.ecommerce.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

@Entity
public class Coupon {

    @Id
    private String code;

    @Column(name = "discount_value")
    private Double value;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expiryDate;

    @Column(nullable = true)
    private Double minPurchase;

    @Column(nullable = true)
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