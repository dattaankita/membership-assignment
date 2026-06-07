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


}