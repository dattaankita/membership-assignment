package com.firstclub.membership.strategy;

import org.springframework.stereotype.Component;

@Component
public class OrderValueRule  implements TierEligibilityRule {

    @Override
    public boolean isEligible(UserMetrics metrics) {
        return metrics.getTotalOrderValue() >= 5000;
    }
}
