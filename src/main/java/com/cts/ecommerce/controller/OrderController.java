package com.cts.ecommerce.controller;

import com.cts.ecommerce.entity.Order;
import com.cts.ecommerce.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderRepository repo;

    @GetMapping
    public List<Order> getOrders() {
        return repo.findAll();
    }
}