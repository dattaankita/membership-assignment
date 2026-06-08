package com.firstclub.membership.service;

import com.firstclub.membership.dto.TierEvaluationRequest;
import com.firstclub.membership.entity.MembershipTier;
import com.firstclub.membership.repository.MembershipTierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipTierServiceImpl implements MembershipTierService {

    @Autowired
    MembershipTierRepository repository;
    @Autowired
    MembershipTierRepository tierRepository;

    @Override
    public List<MembershipTier> getAllTiers() {
        return repository.findAll();
    }

    @Override
    public MembershipTier evaluateTier(TierEvaluationRequest request) {

        List<MembershipTier> tiers = tierRepository.findAll();

        return tiers.stream()
                .filter(tier ->
                        request.getOrderCount() >= tier.getMinOrderCount()
                                && request.getOrderValue() >= tier.getMinOrderValue()
                                && (tier.getCohort() == null
                                || tier.getCohort().equals(request.getCohort())))
                .max(Comparator.comparing(MembershipTier::getPriority))
                .orElseThrow();
    }

}