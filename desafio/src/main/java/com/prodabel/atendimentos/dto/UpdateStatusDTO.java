package com.prodabel.atendimentos.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateStatusDTO {
    @NotBlank(message = "Status cannot be blank.")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
