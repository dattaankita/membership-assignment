package com.firstclub.membership.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "membership_tier")
public class MembershipTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer priority;

    private Integer minOrderCount;

    private BigDecimal minOrderValue;

    private String cohort;

    @OneToMany(mappedBy = "tier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TierBenefit> benefits;

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

    public Integer getMinOrderCount() {
        return minOrderCount;
    }

    public void setMinOrderCount(Integer minOrderCount) {
        this.minOrderCount = minOrderCount;
    }

    public BigDecimal getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(BigDecimal minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public String getCohort() {
        return cohort;
    }

    public void setCohort(String cohort) {
        this.cohort = cohort;
    }

    public List<TierBenefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<TierBenefit> benefits) {
        this.benefits = benefits;
    }
}