package com.cts.ecommerce.ruleengine.rules;

import com.cts.ecommerce.ruleengine.Rule;
import com.cts.ecommerce.ruleengine.RuleContext;
import org.springframework.stereotype.Component;

@Component
public class CouponDiscountRule implements Rule {

    @Override
    public boolean isApplicable(RuleContext context) {

        // ✅ Apply only if coupon exists
        return context.getCart().getCouponCode() != null;
    }

    @Override
    public void apply(RuleContext context) {

        double total = context.getTotal();

        // ✅ Example: 10% discount
        total = total * 0.90;

        context.setTotal(total);
    }
}