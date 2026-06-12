package com.firstclub.membership.dto.responses;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class MembershipPlanResponse {

    private Long id;
    private String name;
    private String duration;
    private BigDecimal price;

    public MembershipPlanResponse(Long id, String name, String duration, BigDecimal price) {
        this.duration=duration;
        this.id = id;
        this.name = name;
        this.price = price;
    }
}