package com.cts.ecommerce.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuditUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    // ✅ Convert object → single-line JSON
    public static String toJson(Object obj) {
        try {
            if (obj == null) return "{}";

            // ✅ remove extra spaces & new lines
            return mapper.writeValueAsString(obj)
                    .replaceAll("\\s+", " ");

        } catch (Exception e) {
            return "{}";
        }
    }

    // ✅ Mask sensitive data (FIXED ✅)
    public static String mask(String json) {

        if (json == null) return null;

        // ✅ mask password (handles spaces also)
        json = json.replaceAll("\"password\"\\s*:\\s*\".*?\"", "\"password\":\"***\"");

        // ✅ mask token field
        json = json.replaceAll("\"token\"\\s*:\\s*\".*?\"", "\"token\":\"***\"");

        // ✅ mask JWT tokens ANYWHERE
        json = json.replaceAll("eyJ[A-Za-z0-9._-]+", "***");

        return json;
    }

    // ✅ Limit size
    public static String limit(String data) {

        if (data != null && data.length() > 1000) {
            return data.substring(0, 1000) + "...";
        }

        return data;
    }
}
