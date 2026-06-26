package com.cts.ecommerce.service;

import com.cts.ecommerce.repository.AuditLogRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private AuditLogRepository repo;

    @InjectMocks
    private AuditService service;

    @Test
    void log_success() {

        service.log("user", "POST", "/test", "req", "res", "SUCCESS");

        verify(repo).save(any());
    }
}