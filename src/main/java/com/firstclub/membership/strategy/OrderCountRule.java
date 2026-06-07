package com.firstclub.membership.strategy;

import org.springframework.stereotype.Component;

@Component
public class OrderCountRule
        implements TierEligibilityRule {

    @Override
    public boolean isEligible(UserMetrics metrics) {

        return metrics.getOrderCount() >= 10;
    }
}
