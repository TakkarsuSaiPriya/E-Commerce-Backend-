package com.cts.ecommerce.ruleengine;

public interface Rule {

    // ✅ check if rule should apply
    boolean isApplicable(RuleContext context);

    // ✅ apply rule logic
    void apply(RuleContext context);
}