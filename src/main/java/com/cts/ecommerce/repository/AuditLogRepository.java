package com.cts.ecommerce.repository;

import com.cts.ecommerce.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // find logs by username
    List<AuditLog> findByUsername(String username);

    //  find logs by status (SUCCESS / FAILED)
    List<AuditLog> findByStatus(String status);

    //  find logs by URI (endpoint)
    List<AuditLog> findByUri(String uri);

    // find logs ordered by latest first
    List<AuditLog> findAllByOrderByCreatedAtDesc();
}
