package com.firstclub.membership.strategy;

public interface TierEligibilityRule {

    boolean isEligible(UserMetrics metrics);
}
