package com.firstclub.membership.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpgradeTierRequest {

    @NotNull
    private Long tierId;
}