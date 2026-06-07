package com.firstclub.membership.service;

import com.firstclub.membership.entity.MembershipPlan;
import com.firstclub.membership.repository.MembershipPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipPlanServiceImpl implements MembershipPlanService {

    @Autowired
    MembershipPlanRepository repository;

    @Override
    public List<MembershipPlan> getAllPlans() {
        return repository.findAll();
    }
}