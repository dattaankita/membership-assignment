package com.firstclub.membership.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpgradeTierRequest {

    @NotNull
    private Long tierId;

    public Long getTierId() {
        return tierId;
    }
}