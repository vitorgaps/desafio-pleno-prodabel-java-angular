package com.prodabel.atendimentos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServiceRequestCreateDTO {
    @NotBlank(message = "Title is required.")
    @Size(max = 100)
    private String title;

    @NotBlank(message = "Description is required.")
    @Size(max = 1000)
    private String description;

    @NotBlank(message = "Neighborhood is required.")
    private String neighborhood;

    @NotNull(message = "Citizen ID is required.")
    private Long citizenId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }
}
