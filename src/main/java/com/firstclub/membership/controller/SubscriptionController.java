package com.firstclub.membership.controller;

import com.firstclub.membership.dto.requests.SubscribeRequest;
import com.firstclub.membership.dto.requests.UpgradeTierRequest;
import com.firstclub.membership.dto.responses.SubscriptionResponse;
import com.firstclub.membership.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    //Create new membership subscription
    public SubscriptionResponse subscribe(@Valid @RequestBody SubscribeRequest request) {
        return subscriptionService.subscribe(request);
    }

    @GetMapping("/{userId}")
    //Get current membership details
    public SubscriptionResponse getMembership(@PathVariable Long userId) {

        return subscriptionService.getMembership(userId);
    }

    @PutMapping("/{subscriptionId}/upgrade")
    //Upgrade membership tier
    public SubscriptionResponse upgradeTier(@PathVariable Long subscriptionId, @RequestBody UpgradeTierRequest request) {

        return subscriptionService.upgradeTier(subscriptionId, request.getTierId());
    }

    @PutMapping("/{subscriptionId}/downgrade")
    //Downgrade membership tier
    public SubscriptionResponse downgradeTier(@PathVariable Long subscriptionId, @RequestBody UpgradeTierRequest request) {

        return subscriptionService.downgradeTier(subscriptionId, request.getTierId());
    }

    @DeleteMapping("/{subscriptionId}")
   //Cancel membership subscription
    public ResponseEntity<Void> cancelSubscription(@PathVariable Long subscriptionId) {

        subscriptionService.cancelSubscription(subscriptionId);

        return ResponseEntity.noContent().build();
    }
}