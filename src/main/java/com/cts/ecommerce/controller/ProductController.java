package com.cts.ecommerce.controller;

import com.cts.ecommerce.entity.Product;
import com.cts.ecommerce.repository.ProductRepository;
import com.cts.ecommerce.service.AuditService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private AuditService auditService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product,
                                    HttpServletRequest request) {
        try {
            Product saved = repo.save(product);
            auditService.log(
                    "admin", "POST",
                    request.getRequestURI(),
                    product,
                    "Product created with id: " + saved.getId(),
                    "SUCCESS"
            );
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            auditService.log(
                    "admin", "POST",
                    request.getRequestURI(),
                    product, e.getMessage(), "FAILED"
            );
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Product p = repo.findById(id).orElse(null);
        if (p == null) {
            return ResponseEntity.status(404).body("Product not found");
        }
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Product product,
                                    HttpServletRequest request) {
        try {
            Product existing = repo.findById(id).orElse(null);
            if (existing == null) {
                return ResponseEntity.status(404).body("Product not found");
            }

            existing.setName(product.getName());
            existing.setCategory(product.getCategory());
            existing.setPrice(product.getPrice());
            existing.setImageUrl(product.getImageUrl()); // ✅ SAVE IMAGE URL

            Product updated = repo.save(existing);
            auditService.log(
                    "admin", "PUT",
                    request.getRequestURI(),
                    product,
                    "Product updated with id: " + updated.getId(),
                    "SUCCESS"
            );
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            auditService.log(
                    "admin", "PUT",
                    request.getRequestURI(),
                    product, e.getMessage(), "FAILED"
            );
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    HttpServletRequest request) {
        try {
            Product existing = repo.findById(id).orElse(null);
            if (existing == null) {
                return ResponseEntity.status(404).body("Product not found");
            }
            repo.delete(existing);
            auditService.log(
                    "admin", "DELETE",
                    request.getRequestURI(),
                    id,
                    "Product deleted with id: " + id,
                    "SUCCESS"
            );
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            auditService.log(
                    "admin", "DELETE",
                    request.getRequestURI(),
                    id, e.getMessage(), "FAILED"
            );
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}