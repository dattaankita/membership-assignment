package com.firstclub.membership;

import com.firstclub.membership.dto.requests.SubscribeRequest;
import com.firstclub.membership.dto.requests.TierEvaluationRequest;
import com.firstclub.membership.entity.MembershipPlan;
import com.firstclub.membership.entity.MembershipTier;
import com.firstclub.membership.entity.Subscription;
import com.firstclub.membership.enums.PlanDuration;
import com.firstclub.membership.enums.SubscriptionStatus;
import com.firstclub.membership.exception.BadRequestException;
import com.firstclub.membership.exception.ResourceNotFoundException;
import com.firstclub.membership.repository.MembershipPlanRepository;
import com.firstclub.membership.repository.MembershipTierRepository;
import com.firstclub.membership.repository.SubscriptionRepository;
import com.firstclub.membership.service.MembershipTierServiceImpl;
import com.firstclub.membership.service.SubscriptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private MembershipPlanRepository planRepository;

    @Mock
    private MembershipTierRepository tierRepository;

    @InjectMocks
    private SubscriptionServiceImpl service;

    @InjectMocks
    private MembershipTierServiceImpl tierService;

    @Test
    void shouldCreateSubscriptionSuccessfully() {

        SubscribeRequest request = new SubscribeRequest();
        request.setUserId(101L);
        request.setPlanId(1L);
        request.setTierId(1L);

        MembershipPlan plan = new MembershipPlan();
        plan.setId(1L);
        plan.setDuration(PlanDuration.MONTHLY);
        plan.setPrice(BigDecimal.valueOf(199));

        MembershipTier tier = new MembershipTier();
        tier.setId(1L);
        tier.setName("Silver");

        when(subscriptionRepository.findByUserIdAndStatus(
                101L,
                SubscriptionStatus.ACTIVE))
                .thenReturn(Optional.empty());

        when(planRepository.findById(1L))
                .thenReturn(Optional.of(plan));

        when(tierRepository.findById(1L))
                .thenReturn(Optional.of(tier));

        when(subscriptionRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(
                () -> service.subscribe(request));

        verify(subscriptionRepository)
                .save(any(Subscription.class));
    }

    @Test
    void shouldThrowWhenActiveSubscriptionExists() {

        SubscribeRequest request = new SubscribeRequest();
        request.setUserId(101L);
        request.setPlanId(1L);
        request.setTierId(1L);

        Subscription activeSubscription =
                new Subscription();

        activeSubscription.setStatus(
                SubscriptionStatus.ACTIVE);

        when(subscriptionRepository.findByUserIdAndStatus(
                101L,
                SubscriptionStatus.ACTIVE))
                .thenReturn(
                        Optional.of(activeSubscription));

        assertThrows(
                BadRequestException.class,
                () -> service.subscribe(request));
    }

    @Test
    void shouldThrowWhenNoTierEligible() {

        when(tierRepository.findAll()).thenReturn(List.of());

        TierEvaluationRequest request =
                new TierEvaluationRequest();

        request.setOrderCount(1);
        request.setOrderValue(10D);
        request.setCohort("ALL");

        assertThrows(ResourceNotFoundException.class, () -> tierService.evaluateTier(request));
    }
}