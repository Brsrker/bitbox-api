package com.jarvi.bitboxapi.model;

import javax.validation.constraints.NotNull;

public class DeactivateItemDTO {
    @NotNull
    public String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
