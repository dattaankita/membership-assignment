package com.firstclub.membership.service;

import com.firstclub.membership.dto.SubscribeRequest;
import com.firstclub.membership.dto.SubscriptionResponse;
import com.firstclub.membership.entity.MembershipPlan;
import com.firstclub.membership.entity.MembershipTier;
import com.firstclub.membership.entity.Subscription;
import com.firstclub.membership.enums.PlanDuration;
import com.firstclub.membership.enums.SubscriptionStatus;
import com.firstclub.membership.exception.ResourceNotFoundException;
import com.firstclub.membership.repository.MembershipPlanRepository;
import com.firstclub.membership.repository.MembershipTierRepository;
import com.firstclub.membership.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    MembershipPlanRepository planRepository;
    @Autowired
    MembershipTierRepository tierRepository;

    @Override
    public SubscriptionResponse subscribe(SubscribeRequest request) {

        MembershipPlan plan = planRepository.findById(request.getPlanId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Plan not found"));

        MembershipTier tier = tierRepository.findById(request.getTierId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tier not found"));

        Subscription subscription =
                Subscription.builder()
                        .userId(request.getUserId())
                        .planId(plan.getId())
                        .tierId(tier.getId())
                        .status(SubscriptionStatus.ACTIVE)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now()
                                .plusMonths(getMonths(plan)))
                        .build();

        subscriptionRepository.save(subscription);

        return buildResponse(subscription, plan, tier);
    }

    @Override
    public SubscriptionResponse getMembership(
            Long userId,
            LocalDate startDate,
            LocalDate endDate) {

        Subscription subscription = subscriptionRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subscription not found"));

        MembershipPlan plan = planRepository.findById(subscription.getPlanId()).orElseThrow();

        MembershipTier tier = tierRepository.findById(subscription.getTierId()).orElseThrow();

        return buildResponse(subscription, plan, tier);
    }
    
    @Override
    public SubscriptionResponse upgradeTier(
            Long subscriptionId,
            Long tierId) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subscription not found"));

        MembershipTier tier =
                tierRepository.findById(tierId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tier not found"));

        subscription.setTierId(tier.getId());

        subscriptionRepository.save(subscription);

        MembershipPlan plan =
                planRepository.findById(subscription.getPlanId())
                        .orElseThrow();

        return buildResponse(subscription, plan, tier);
    }

    @Override
    public SubscriptionResponse downgradeTier(Long subscriptionId, Long tierId) {
        return null;
    }

    @Override
    public void cancelSubscription(
            Long subscriptionId) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subscription not found"));

        subscription.setStatus(SubscriptionStatus.CANCELLED);

        subscriptionRepository.save(subscription);
    }

    private Integer getMonths(MembershipPlan plan) {

        return switch (plan.getDuration()) {
            case PlanDuration.MONTHLY -> 1;
            case PlanDuration.QUARTERLY -> 3;
            case PlanDuration.YEARLY -> 12;
        };
    }

    private SubscriptionResponse buildResponse(
            Subscription subscription,
            MembershipPlan plan,
            MembershipTier tier) {

        long remainingDays = Math.max(
                0,
                ChronoUnit.DAYS.between(
                        LocalDate.now(),
                        subscription.getEndDate()));

        return new SubscriptionResponse(subscription.getId(), subscription.getUserId(),plan.getName(),tier.getName(),subscription.getStatus()
                , subscription.getStartDate(),subscription.getEndDate(),remainingDays);
    }
}
