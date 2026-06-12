package com.firstclub.membership.service;

import com.firstclub.membership.dto.requests.SubscribeRequest;
import com.firstclub.membership.dto.responses.SubscriptionResponse;
import com.firstclub.membership.entity.MembershipPlan;
import com.firstclub.membership.entity.MembershipTier;
import com.firstclub.membership.entity.Subscription;
import com.firstclub.membership.enums.PlanDuration;
import com.firstclub.membership.enums.SubscriptionStatus;
import com.firstclub.membership.exception.*;
import com.firstclub.membership.repository.MembershipPlanRepository;
import com.firstclub.membership.repository.MembershipTierRepository;
import com.firstclub.membership.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final MembershipPlanRepository planRepository;

    private final MembershipTierRepository tierRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, MembershipPlanRepository planRepository, MembershipTierRepository tierRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.tierRepository = tierRepository;
    }

    @Override
    @Transactional
    public SubscriptionResponse subscribe(SubscribeRequest request) {

        subscriptionRepository
                .findByUserIdAndStatus(request.getUserId(), SubscriptionStatus.ACTIVE)
                .ifPresent(subscription -> {
                    throw new BadRequestException("User already has an active subscription");
                });

        MembershipPlan plan = planRepository.findById(request.getPlanId()).orElseThrow(() ->
                                new ResourceNotFoundException("Plan not found"));

        MembershipTier tier = tierRepository.findById(request.getTierId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tier not found"));

        LocalDate startDate = LocalDate.now();

        LocalDate endDate = calculateEndDate(startDate, plan.getDuration());

        Subscription subscription = new Subscription(request.getUserId(),plan, tier,
                SubscriptionStatus.ACTIVE, startDate, endDate);

        Subscription saved = subscriptionRepository.save(subscription);

        return mapToResponse(saved);
    }

    @Override
    public SubscriptionResponse getMembership(Long userId) {

        Subscription subscription = subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        if (subscription.getEndDate().isBefore(LocalDate.now())) {

            subscription.setStatus(SubscriptionStatus.EXPIRED);

            subscription = subscriptionRepository.save(subscription);
        }

        return mapToResponse(subscription);
    }

    @Override
    @Transactional
    public SubscriptionResponse upgradeTier(Long subscriptionId, Long newTierId) {

        Subscription subscription = getSubscription(subscriptionId);

        MembershipTier currentTier =
                subscription.getTier();

        MembershipTier newTier =
                tierRepository.findById(newTierId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tier not found"));

        validateUpgrade(currentTier, newTier);

        subscription.setTier(newTier);

        Subscription updated = subscriptionRepository.save(subscription);

        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public SubscriptionResponse downgradeTier(Long subscriptionId, Long newTierId) {

        Subscription subscription = getSubscription(subscriptionId);

        MembershipTier currentTier =
                subscription.getTier();

        MembershipTier newTier =
                tierRepository.findById(newTierId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tier not found"));

        validateDowngrade(currentTier, newTier);

        subscription.setTier(newTier);

        Subscription updated =
                subscriptionRepository.save(subscription);

        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void cancelSubscription(
            Long subscriptionId) {

        Subscription subscription =
                getSubscription(subscriptionId);

        subscription.setStatus(
                SubscriptionStatus.CANCELLED);

        subscriptionRepository.save(subscription);
    }

    private Subscription getSubscription(
            Long subscriptionId) {

        return subscriptionRepository.findById(
                        subscriptionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subscription not found"));
    }

    private void validateUpgrade(
            MembershipTier currentTier,
            MembershipTier newTier) throws BadRequestException {

        if (newTier.getPriority() <= currentTier.getPriority()) {
            throw new BadRequestException("Upgrade must move to a higher tier");
        }
    }

    private void validateDowngrade(
            MembershipTier currentTier,
            MembershipTier newTier) throws BadRequestException {

        if (newTier.getPriority()
                >= currentTier.getPriority()) {

            throw new BadRequestException("Downgrade must move to a lower tier");
        }
    }

    private LocalDate calculateEndDate(
            LocalDate startDate,
            PlanDuration duration) {

        return switch (duration) {

            case MONTHLY ->
                    startDate.plusMonths(1);

            case QUARTERLY ->
                    startDate.plusMonths(3);

            case YEARLY ->
                    startDate.plusYears(1);
        };
    }

    private SubscriptionResponse mapToResponse(
            Subscription subscription) {

        long remainingDays =
                ChronoUnit.DAYS.between(
                        LocalDate.now(),
                        subscription.getEndDate());

        return new SubscriptionResponse(subscription.getId(),subscription.getUserId(),subscription.getPlan().getName()
               ,subscription.getTier().getName(),subscription.getStatus().name(),subscription.getStartDate(),subscription.getEndDate(),
                        Math.max(remainingDays, 0));
    }
}