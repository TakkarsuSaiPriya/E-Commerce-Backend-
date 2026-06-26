package com.cts.ecommerce.ruleengine.rules;

import com.cts.ecommerce.ruleengine.Rule;
import com.cts.ecommerce.ruleengine.RuleContext;
import org.springframework.stereotype.Component;

@Component
public class MinimumAmountRule implements Rule {

    private static final double MIN_AMOUNT = 500; // ✅ threshold

    @Override
    public boolean isApplicable(RuleContext context) {

        // ✅ Apply rule only if total is less than minimum
        return context.getTotal() < MIN_AMOUNT;
    }

    @Override
    public void apply(RuleContext context) {

        double total = context.getTotal();

        // ✅ Example logic:
        // ensure minimum payable amount
        total = MIN_AMOUNT;

        context.setTotal(total);
    }
}