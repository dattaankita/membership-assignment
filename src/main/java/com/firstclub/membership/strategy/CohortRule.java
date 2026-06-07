package com.firstclub.membership.strategy;

import org.springframework.stereotype.Component;

@Component
public class CohortRule implements TierEligibilityRule {

    @Override
    public boolean isEligible(UserMetrics metrics) {
        return "PREMIUM".equals(metrics.getCohort());
    }
}
