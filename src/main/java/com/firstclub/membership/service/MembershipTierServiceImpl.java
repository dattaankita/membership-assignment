package com.firstclub.membership.service;

import com.firstclub.membership.dto.requests.TierEvaluationRequest;
import com.firstclub.membership.dto.responses.BenefitResponse;
import com.firstclub.membership.dto.response.MembershipTierResponse;
import com.firstclub.membership.dto.responses.TierEvaluationResponse;
import com.firstclub.membership.entity.MembershipTier;
import com.firstclub.membership.exception.ResourceNotFoundException;
import com.firstclub.membership.repository.BenefitRepository;
import com.firstclub.membership.repository.MembershipTierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MembershipTierServiceImpl implements MembershipTierService {

    private final MembershipTierRepository tierRepository;

    private final BenefitRepository benefitRepository;

    public MembershipTierServiceImpl(MembershipTierRepository tierRepository, BenefitRepository benefitRepository) {
        this.tierRepository = tierRepository;
        this.benefitRepository = benefitRepository;
    }


    @Override
    public List<MembershipTierResponse> getAllTiers() {

        return tierRepository.findAll()
                .stream()
                .map(tier ->
                        new MembershipTierResponse(tier.getId(), tier.getName(),tier.getPriority(),
                                benefitRepository.findByTierId(tier.getId())
                                                .stream()
                                                .map(benefit -> new BenefitResponse(benefit.getBenefitName(),
                                                                benefit.getBenefitValue())).toList())).toList();
    }

    @Override
    public TierEvaluationResponse evaluateTier(TierEvaluationRequest request) {

        MembershipTier eligibleTier = tierRepository.findAll()
                .stream()
                .filter(tier -> (tier.getCohort().equalsIgnoreCase("ALL")
                        || tier.getCohort().equalsIgnoreCase(request.getCohort())) &&
                        request.getOrderCount() >= tier.getMinOrderCount()
                                && BigDecimal.valueOf(request.getOrderValue()).compareTo(tier.getMinOrderValue()) >= 0)
                .max((t1, t2) ->
                        Integer.compare(
                                t1.getPriority(),
                                t2.getPriority()))
                .orElseThrow(() -> new ResourceNotFoundException("No eligible tier found"));
        return new TierEvaluationResponse(eligibleTier.getName());
    }
}