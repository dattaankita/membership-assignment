package com.firstclub.membership.service;

import com.firstclub.membership.factory.RuleFactory;
import com.firstclub.membership.strategy.UserMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TierEvaluationService {

    @Autowired
    RuleFactory ruleFactory;

    public String determineTier(UserMetrics metrics) {

        if(ruleFactory.getRule("COHORT").isEligible(metrics)) {

            return "PLATINUM";
        }

        if(ruleFactory.getRule("ORDER_VALUE").isEligible(metrics)) {

            return "GOLD";
        }

        if(ruleFactory.getRule("ORDER_COUNT").isEligible(metrics)) {

            return "SILVER";
        }

        return "SILVER";
    }
}
