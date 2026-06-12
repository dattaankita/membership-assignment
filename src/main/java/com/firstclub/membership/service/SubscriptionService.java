package com.firstclub.membership.service;

import com.firstclub.membership.dto.requests.SubscribeRequest;
import com.firstclub.membership.dto.responses.SubscriptionResponse;

public interface SubscriptionService {

    SubscriptionResponse subscribe(SubscribeRequest request);

    SubscriptionResponse getMembership(Long userId);

    SubscriptionResponse upgradeTier(Long subscriptionId, Long tierId) ;

    SubscriptionResponse downgradeTier(Long subscriptionId, Long tierId);

    void cancelSubscription(Long subscriptionId);
}