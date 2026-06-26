package com.cts.ecommerce.service;

import com.cts.ecommerce.entity.AuditLog;
import com.cts.ecommerce.repository.AuditLogRepository;
import com.cts.ecommerce.util.AuditUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository repo;

    public void log(String username,
                    String method,
                    String uri,
                    Object request,
                    Object response,
                    String status) {

        try {

            AuditLog log = new AuditLog();

            log.setUsername(username);
            log.setMethod(method);
            log.setUri(uri);

            // ✅ convert → clean JSON
            String req = AuditUtil.toJson(request);
            String res = AuditUtil.toJson(response);

            // ✅ apply mask and limit
            log.setRequestBody(AuditUtil.limit(AuditUtil.mask(req)));
            log.setResponseBody(AuditUtil.limit(AuditUtil.mask(res)));

            // ✅ FIX: NO NULL STATUS
            log.setStatus(status != null ? status : "SUCCESS");

            repo.save(log);

        } catch (Exception e) {
            System.out.println("Audit log failed: " + e.getMessage());
        }
    }
}