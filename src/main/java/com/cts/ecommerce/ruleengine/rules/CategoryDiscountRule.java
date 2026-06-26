package com.cts.ecommerce.ruleengine.rules;

import com.cts.ecommerce.ruleengine.Rule;
import com.cts.ecommerce.ruleengine.RuleContext;
import org.springframework.stereotype.Component;

@Component
public class CategoryDiscountRule implements Rule {

    @Override
    public boolean isApplicable(RuleContext context) {

        // ✅ Example condition:
        // apply if more than 3 items in cart
        return context.getCart().getItems() != null &&
                context.getCart().getItems().size() >= 3;
    }

    @Override
    public void apply(RuleContext context) {

        double total = context.getTotal();

        // ✅ Example discount: 5% off
        total = total * 0.95;

        context.setTotal(total);
    }
}
