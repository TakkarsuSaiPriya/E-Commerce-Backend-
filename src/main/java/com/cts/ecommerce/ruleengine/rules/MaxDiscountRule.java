package com.cts.ecommerce.ruleengine.rules;

import com.cts.ecommerce.ruleengine.Rule;
import com.cts.ecommerce.ruleengine.RuleContext;
import org.springframework.stereotype.Component;

@Component
public class MaxDiscountRule implements Rule {

    private static final double MAX_DISCOUNT = 500; // ✅ max cap

    @Override
    public boolean isApplicable(RuleContext context) {

        // ✅ Always check to cap discount
        return true;
    }

    @Override
    public void apply(RuleContext context) {

        double total = context.getTotal();
        double original = context.getCart().getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        double discount = original - total;

        // ✅ If discount exceeds max limit → cap it
        if (discount > MAX_DISCOUNT) {

            double newTotal = original - MAX_DISCOUNT;
            context.setTotal(newTotal);
        }
    }
}