package com.prodabel.atendimentos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ServiceRequestResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String neighborhood;
    private String status;
    private LocalDateTime createdAt;

    private CitizenInfo citizen;

    private EmployeeInfo assignedEmployee;

    @Data
    public static class CitizenInfo {
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Data
    public static class EmployeeInfo {
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CitizenInfo getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenInfo citizen) {
        this.citizen = citizen;
    }

    public EmployeeInfo getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(EmployeeInfo assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }
}
