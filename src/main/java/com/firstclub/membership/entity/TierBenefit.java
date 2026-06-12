package com.firstclub.membership.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tier_benefit")
public class TierBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String benefitName;

    private String benefitValue;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    private MembershipTier tier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenefitName() {
        return benefitName;
    }

    public void setBenefitName(String benefitName) {
        this.benefitName = benefitName;
    }

    public String getBenefitValue() {
        return benefitValue;
    }

    public void setBenefitValue(String benefitValue) {
        this.benefitValue = benefitValue;
    }

    public MembershipTier getTier() {
        return tier;
    }

    public void setTier(MembershipTier tier) {
        this.tier = tier;
    }
}