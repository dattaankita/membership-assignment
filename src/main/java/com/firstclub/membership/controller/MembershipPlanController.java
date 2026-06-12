package com.firstclub.membership.controller;

import com.firstclub.membership.dto.responses.MembershipPlanResponse;
import com.firstclub.membership.service.MembershipPlanService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
public class MembershipPlanController {

    private final MembershipPlanService planService;

    public MembershipPlanController(MembershipPlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    //"Get all membership plans"
    public List<MembershipPlanResponse> getPlans() {

        return planService.getAllPlans();
    }
}