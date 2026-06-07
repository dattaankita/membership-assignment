package com.firstclub.membership.factory;

import com.firstclub.membership.strategy.TierEligibilityRule;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RuleFactory {

    private final Map<String, TierEligibilityRule> rules;

    public RuleFactory(Map<String, TierEligibilityRule> rules) {
        this.rules = rules;
    }

    public TierEligibilityRule getRule(
            String ruleType) {

        return rules.get(ruleType);
    }
}
