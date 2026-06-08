package com.firstclub.membership.dto;

import com.firstclub.membership.enums.SubscriptionStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class SubscriptionResponse {

    private Long subscriptionId;
    private Long userId;
    private String planName;
    private String tierName;
    private SubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long remainingDays;


    public SubscriptionResponse(Long id, Long userId, String planName, String tierName,
                                SubscriptionStatus status, LocalDate startDate, LocalDate endDate,
                                long remainingDays) {
        this.subscriptionId = id;
        this.userId = userId;
        this.planName = planName;
        this.tierName = tierName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingDays = remainingDays;
    }
}