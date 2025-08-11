package com.prodabel.atendimentos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServiceRequestUpdateDTO {
    @NotNull(message = "Employee ID is required for assignment.")
    private Long employeeId;

    @NotBlank
    private String status;
}
