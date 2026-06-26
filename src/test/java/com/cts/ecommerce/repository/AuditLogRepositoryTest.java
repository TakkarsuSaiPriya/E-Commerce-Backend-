package com.cts.ecommerce.repository;

import com.cts.ecommerce.entity.AuditLog;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AuditLogRepositoryTest {

    @Autowired
    private AuditLogRepository repo;

    @Test
    void saveAuditLog() {

        AuditLog log = new AuditLog();
        log.setUsername("user");
        log.setMethod("POST");
        log.setUri("/test");
        log.setStatus("SUCCESS");

        AuditLog saved = repo.save(log);

        assertThat(saved.getId()).isNotNull();
    }
}