package com.cts.ecommerce.ruleengine;

public class RuleResult {

    private boolean stop;
    private String message;

    public RuleResult(boolean stop, String message) {
        this.stop = stop;
        this.message = message;
    }

    public boolean isStop() {
        return stop;
    }

    public String getMessage() {
        return message;
    }
}