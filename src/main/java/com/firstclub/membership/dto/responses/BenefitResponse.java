package com.firstclub.membership.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BenefitResponse {

    private String benefitName;
    private String benefitValue;

    public BenefitResponse(String benefitName, String benefitValue) {
        this.benefitName = benefitName;
        this.benefitValue = benefitValue;
    }
}