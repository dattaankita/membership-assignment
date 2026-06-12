package com.firstclub.membership.controller;

import com.firstclub.membership.dto.requests.TierEvaluationRequest;
import com.firstclub.membership.dto.response.MembershipTierResponse;
import com.firstclub.membership.dto.responses.TierEvaluationResponse;
import com.firstclub.membership.service.MembershipTierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tiers")
public class MembershipTierController {

    private final MembershipTierService tierService;

    public MembershipTierController(MembershipTierService tierService) {
        this.tierService = tierService;
    }

    @GetMapping
    //Get all tiers with benefits
    public List<MembershipTierResponse> getTiers() {

        return tierService.getAllTiers();
    }

    @PostMapping("/evaluate")
    //Evaluate user eligibility tier
    public TierEvaluationResponse evaluateTier(@Valid @RequestBody TierEvaluationRequest request) {

        return tierService.evaluateTier(request);
    }
}