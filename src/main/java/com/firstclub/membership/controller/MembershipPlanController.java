package com.firstclub.membership.controller;

import com.firstclub.membership.entity.MembershipPlan;
import com.firstclub.membership.service.MembershipPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class MembershipPlanController {

    @Autowired
    MembershipPlanService service;

    @GetMapping
    public List<MembershipPlan> getPlans() {
        return service.getAllPlans();
    }
}