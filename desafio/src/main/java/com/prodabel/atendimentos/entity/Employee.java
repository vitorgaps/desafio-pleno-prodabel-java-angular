package com.prodabel.atendimentos.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "employees")
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends User{
    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @OneToMany(mappedBy = "assignedEmployee")
    private List<ServiceRequest> assignedRequests;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<ServiceRequest> getAssignedRequests() {
        return assignedRequests;
    }

    public void setAssignedRequests(List<ServiceRequest> assignedRequests) {
        this.assignedRequests = assignedRequests;
    }
}
