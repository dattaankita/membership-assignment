package com.firstclub.membership.service;

import com.firstclub.membership.dto.TierEvaluationRequest;
import com.firstclub.membership.entity.MembershipTier;

import java.util.List;

public interface MembershipTierService {

    List<MembershipTier> getAllTiers();

    MembershipTier evaluateTier(TierEvaluationRequest request);
}