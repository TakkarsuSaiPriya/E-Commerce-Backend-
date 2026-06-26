package com.cts.ecommerce.repository;

import com.cts.ecommerce.entity.Product;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repo;

    @Test
    void saveProduct() {

        Product p = new Product();
        p.setName("Laptop");
        p.setCategory("Electronics");
        p.setPrice(50000);

        Product saved = repo.save(p);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void findProductById() {

        Product p = new Product();
        p.setName("Laptop");

        Product saved = repo.save(p);

        Optional<Product> found = repo.findById(saved.getId());

        assertThat(found).isPresent();
    }

    @Test
    void findAllProducts() {

        repo.save(new Product());
        repo.save(new Product());

        List<Product> list = repo.findAll();

        assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    void deleteProduct() {

        Product p = repo.save(new Product());

        repo.delete(p);

        assertThat(repo.findById(p.getId())).isEmpty();
    }
}