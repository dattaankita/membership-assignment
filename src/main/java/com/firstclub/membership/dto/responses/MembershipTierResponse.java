package com.firstclub.membership.dto.response;

import com.firstclub.membership.dto.responses.BenefitResponse;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MembershipTierResponse {

    private Long id;
    private String name;
    private Integer priority;
    private List<BenefitResponse> benefits;

    public MembershipTierResponse(Long id, String name, Integer priority, List<BenefitResponse> benefits) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.benefits = benefits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<BenefitResponse> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<BenefitResponse> benefits) {
        this.benefits = benefits;
    }
}