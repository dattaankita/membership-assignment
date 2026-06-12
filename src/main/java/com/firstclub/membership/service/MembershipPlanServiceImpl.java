package com.firstclub.membership.service;

import com.firstclub.membership.dto.responses.MembershipPlanResponse;
import com.firstclub.membership.repository.MembershipPlanRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MembershipPlanServiceImpl implements MembershipPlanService {

    private final MembershipPlanRepository repository;

    public MembershipPlanServiceImpl(MembershipPlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MembershipPlanResponse> getAllPlans() {

        return repository.findAll().stream().map(plan ->
                        new MembershipPlanResponse(plan.getId(), plan.getName(),
                                plan.getDuration().name(), plan.getPrice())).toList();
    }
}