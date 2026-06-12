package com.firstclub.membership;

import com.firstclub.membership.dto.requests.TierEvaluationRequest;
import com.firstclub.membership.dto.responses.TierEvaluationResponse;
import com.firstclub.membership.entity.MembershipTier;
import com.firstclub.membership.repository.MembershipTierRepository;
import com.firstclub.membership.service.MembershipTierServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MembershipTierServiceImplTest {

    @Mock
    private MembershipTierRepository tierRepository;

    @InjectMocks
    private MembershipTierServiceImpl service;

    @Test
    void shouldReturnSilverTier() {

        MembershipTier silver = new MembershipTier();
        silver.setName("Silver");
        silver.setPriority(1);
        silver.setMinOrderCount(0);
        silver.setMinOrderValue(BigDecimal.ZERO);
        silver.setCohort("ALL");

        when(tierRepository.findAll()).thenReturn(List.of(silver));

        TierEvaluationRequest request = new TierEvaluationRequest();
        request.setOrderCount(1);
        request.setOrderValue(100d);
        request.setCohort("ALL");

        TierEvaluationResponse response = service.evaluateTier(request);
        assertEquals("Silver", response.getTierName());
    }

    @Test
    void shouldReturnGoldTier() {

        MembershipTier silver = new MembershipTier();
        silver.setName("Silver");
        silver.setPriority(1);
        silver.setMinOrderCount(0);
        silver.setMinOrderValue(BigDecimal.ZERO);
        silver.setCohort("ALL");

        MembershipTier gold = new MembershipTier();
        gold.setName("Gold");
        gold.setPriority(2);
        gold.setMinOrderCount(10);
        gold.setMinOrderValue(BigDecimal.valueOf(5000));
        gold.setCohort("PREMIUM");

        when(tierRepository.findAll()).thenReturn(List.of(silver, gold));

        TierEvaluationRequest request = new TierEvaluationRequest();
        request.setOrderCount(15);
        request.setOrderValue(7000D);
        request.setCohort("PREMIUM");

        TierEvaluationResponse response = service.evaluateTier(request);
        assertEquals("Gold", response.getTierName());
    }
}