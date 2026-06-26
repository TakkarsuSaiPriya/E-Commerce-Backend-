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
        Coupon saved = repo.save(coupon);
        auditService.log("admin", "POST", request.getRequestURI(),
                coupon, "Coupon created: " + saved.getCode(), "SUCCESS");
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> get(@PathVariable String code) {
        Coupon c = repo.findById(code).orElse(null);
        if (c == null) return ResponseEntity.status(404).body("Coupon not found");
        return ResponseEntity.ok(c);
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> update(@PathVariable String code,
                                    @RequestBody Coupon coupon,
                                    HttpServletRequest request) {
        Coupon existing = repo.findById(code).orElse(null);
        if (existing == null) return ResponseEntity.status(404).body("Coupon not found");
        existing.setValue(coupon.getValue());
        Coupon updated = repo.save(existing);
        auditService.log("admin", "PUT", request.getRequestURI(),
                coupon, "Coupon updated: " + updated.getCode(), "SUCCESS");
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> delete(@PathVariable String code,
                                    HttpServletRequest request) {
        Coupon existing = repo.findById(code).orElse(null);
        if (existing == null) return ResponseEntity.status(404).body("Coupon not found");
        repo.delete(existing);
        auditService.log("admin", "DELETE", request.getRequestURI(),
                code, "Coupon deleted: " + code, "SUCCESS");
        return ResponseEntity.ok("Deleted successfully");
    }
}