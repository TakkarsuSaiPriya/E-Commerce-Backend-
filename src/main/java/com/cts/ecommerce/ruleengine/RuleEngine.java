package com.cts.ecommerce.ruleengine;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleEngine {

    private final List<Rule> rules;

    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
    }

    public double applyRules(RuleContext context) {

        for (Rule rule : rules) {

            if (rule.isApplicable(context)) {
                rule.apply(context);
            }
        }

        // ✅ FIXED: use getTotal()
        return context.getTotal();
    }
}
