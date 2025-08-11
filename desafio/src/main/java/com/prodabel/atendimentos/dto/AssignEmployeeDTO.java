package com.prodabel.atendimentos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignEmployeeDTO {
    @NotNull(message = "Employee ID is required.")
    private Long employeeId;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
