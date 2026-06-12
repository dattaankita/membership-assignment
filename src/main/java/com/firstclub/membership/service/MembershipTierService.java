package com.firstclub.membership.service;

import com.firstclub.membership.dto.requests.TierEvaluationRequest;
import com.firstclub.membership.dto.response.MembershipTierResponse;
import com.firstclub.membership.dto.responses.TierEvaluationResponse;

import java.util.List;

public interface MembershipTierService {

    List<MembershipTierResponse> getAllTiers();

    TierEvaluationResponse evaluateTier(TierEvaluationRequest request);
}