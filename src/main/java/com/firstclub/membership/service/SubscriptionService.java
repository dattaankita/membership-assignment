package com.firstclub.membership.service;

import com.firstclub.membership.dto.SubscribeRequest;
import com.firstclub.membership.dto.SubscriptionResponse;

import java.time.LocalDate;

public interface SubscriptionService {

    SubscriptionResponse subscribe(SubscribeRequest request);

    SubscriptionResponse getMembership(Long userId, LocalDate startDate, LocalDate endDate);

    SubscriptionResponse upgradeTier(Long subscriptionId, Long tierId);

    SubscriptionResponse downgradeTier(Long subscriptionId, Long tierId);

    void cancelSubscription(Long subscriptionId);
}