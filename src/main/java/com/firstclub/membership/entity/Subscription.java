package com.firstclub.membership.entity;

import com.firstclub.membership.enums.SubscriptionStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_subscription",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_active_subscription",
                        columnNames = {
                                "user_id",
                                "status"
                        }
                )
        }
)
public class Subscription {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Long userId;

        @ManyToOne
        @JoinColumn(name = "plan_id")
        private MembershipPlan plan;

        @ManyToOne
        @JoinColumn(name = "tier_id")
        private MembershipTier tier;

        @Enumerated(EnumType.STRING)
        private SubscriptionStatus status;

        private LocalDate startDate;

        private LocalDate endDate;

        @Version
        private Long version;

    public Subscription(Long userId, MembershipPlan plan, MembershipTier tier,
                        SubscriptionStatus subscriptionStatus, LocalDate startDate,
                        LocalDate endDate) {
        this.userId = userId;
        this.plan = plan;
        this.tier = tier;
        this.status = subscriptionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Subscription() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public MembershipPlan getPlan() {
        return plan;
    }

    public void setPlan(MembershipPlan plan) {
        this.plan = plan;
    }

    public MembershipTier getTier() {
        return tier;
    }

    public void setTier(MembershipTier tier) {
        this.tier = tier;
    }
}