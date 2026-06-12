package com.firstclub.membership.dto.responses;

import lombok.Data;

@Data
public class TierEvaluationResponse {

    private String tierName;


    public TierEvaluationResponse(String name) {
        this.tierName=name;
    }

    public String getTierName() {
        return tierName;
    }
}