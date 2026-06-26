package com.cts.ecommerce.aspect;

import com.cts.ecommerce.service.AuditService;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditService auditService;

    @Autowired
    private HttpServletRequest request;

    // ✅ Capture ALL controller methods
    @Around("execution(* com.cts.ecommerce.controller..*(..))")
    public Object logAudit(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = null;
        String status = "SUCCESS";

        try {

            // ✅ Run actual API
            result = joinPoint.proceed();

            return result;

        } catch (Exception ex) {

            status = "FAILED";
            throw ex;

        } finally {

            try {
                // ✅ Extract request details
                String uri = request.getRequestURI();
                String method = request.getMethod();

                Object[] args = joinPoint.getArgs();
                Object requestBody = (args != null && args.length > 0) ? args : null;

                // ✅ username (temporary)
                String username = "user";

                // ✅ Save log
                auditService.log(
                        username,
                        method,
                        uri,
                        requestBody,
                        result,
                        status
                );

            } catch (Exception e) {
                System.out.println("Audit failed: " + e.getMessage());
            }
        }
    }
}