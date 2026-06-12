package com.firstclub.membership.service;

import com.firstclub.membership.dto.responses.MembershipPlanResponse;
import java.util.List;

public interface MembershipPlanService {

    List<MembershipPlanResponse> getAllPlans();
}