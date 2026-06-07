package com.firstclub.membership.controller;

import com.firstclub.membership.dto.SubscribeRequest;
import com.firstclub.membership.dto.SubscriptionResponse;
import com.firstclub.membership.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    @Autowired
    SubscriptionService service;

    @PostMapping
    public SubscriptionResponse subscribe(@RequestBody @Valid SubscribeRequest request) {
        return service.subscribe(request);
    }

    @GetMapping("/{userId}")
    public SubscriptionResponse getMembership(
            @PathVariable Long userId,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate endDate) {

        return service.getMembership(
                userId,
                startDate,
                endDate);
    }

    @PutMapping("/{id}/upgrade")
    public SubscriptionResponse upgrade(@PathVariable Long id, @RequestParam Long tierId) {
        return service.upgradeTier(id, tierId);
    }

    @PutMapping("/{id}/downgrade")
    public SubscriptionResponse downgrade(@PathVariable Long id, @RequestParam Long tierId) {
        return service.downgradeTier(id, tierId);
    }


    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        service.cancelSubscription(id);
    }
}
