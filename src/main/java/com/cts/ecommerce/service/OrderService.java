package com.cts.ecommerce.service;

import com.cts.ecommerce.entity.Order;
import com.cts.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    public Order createOrder(double total, String username) {

        Order order = new Order();
        order.setTotal(total);
        order.setUsername(username);
        order.setStatus("SUCCESS");
        order.setPaymentStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        return repo.save(order);
    }
}