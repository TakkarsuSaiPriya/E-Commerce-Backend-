package com.cts.ecommerce.controller;

import com.cts.ecommerce.entity.Coupon;
import com.cts.ecommerce.repository.CouponRepository;
import com.cts.ecommerce.service.AuditService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@CrossOrigin(origins = "http://localhost:3000")
public class CouponController {

    @Autowired
    private CouponRepository repo;

    @Autowired
    private AuditService auditService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Coupon coupon,
                                    HttpServletRequest request) {
        try {
            //  Remove spaces from code
            if (coupon.getCode() != null) {
                coupon.setCode(coupon.getCode().toUpperCase().trim().replace(" ", ""));
            }

            //  Validate required fields
            if (coupon.getCode() == null || coupon.getCode().isEmpty()) {
                return ResponseEntity.status(400).body("Coupon code is required");
            }

            if (coupon.getValue() == null) {
                return ResponseEntity.status(400).body("Discount value is required");
            }

            // Check if coupon code already exists
            if (repo.findById(coupon.getCode()).isPresent()) {
                return ResponseEntity.status(400).body("Coupon code already exists");
            }

            Coupon saved = repo.save(coupon);

            auditService.log(
                    "admin", "POST",
                    request.getRequestURI(),
                    coupon,
                    "Coupon created: " + saved.getCode(),
                    "SUCCESS"
            );

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            auditService.log(
                    "admin", "POST",
                    request.getRequestURI(),
                    coupon,
                    e.getMessage(),
                    "FAILED"
            );
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> get(@PathVariable String code) {
        Coupon c = repo.findById(code).orElse(null);
        if (c == null) {
            return ResponseEntity.status(404).body("Coupon not found");
        }
        return ResponseEntity.ok(c);
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> update(@PathVariable String code,
                                    @RequestBody Coupon coupon,
                                    HttpServletRequest request) {
        try {
            Coupon existing = repo.findById(code).orElse(null);
            if (existing == null) {
                return ResponseEntity.status(404).body("Coupon not found");
            }

            existing.setValue(coupon.getValue());

            Coupon updated = repo.save(existing);

            auditService.log(
                    "admin", "PUT",
                    request.getRequestURI(),
                    coupon,
                    "Coupon updated: " + updated.getCode(),
                    "SUCCESS"
            );

            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> delete(@PathVariable String code,
                                    HttpServletRequest request) {
        try {
            Coupon existing = repo.findById(code).orElse(null);
            if (existing == null) {
                return ResponseEntity.status(404).body("Coupon not found");
            }

            repo.delete(existing);

            auditService.log(
                    "admin", "DELETE",
                    request.getRequestURI(),
                    code,
                    "Coupon deleted: " + code,
                    "SUCCESS"
            );

            return ResponseEntity.ok("Deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}