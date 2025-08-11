package com.prodabel.atendimentos.controller;

import com.prodabel.atendimentos.dto.AssignEmployeeDTO;
import com.prodabel.atendimentos.dto.ServiceRequestCreateDTO;
import com.prodabel.atendimentos.dto.ServiceRequestResponseDTO;
import com.prodabel.atendimentos.dto.UpdateStatusDTO;
import com.prodabel.atendimentos.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {
    private final ServiceRequestService serviceRequestService;

    @Autowired
    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @PostMapping
    public ResponseEntity<ServiceRequestResponseDTO> create(@Valid @RequestBody ServiceRequestCreateDTO createDTO) {
        ServiceRequestResponseDTO response = serviceRequestService.createServiceRequest(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequestResponseDTO>> getAll() {
        return ResponseEntity.ok(serviceRequestService.findAllServiceRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceRequestService.findServiceRequestById(id));
    }

    @PatchMapping("/{id}/assignment")
    public ResponseEntity<ServiceRequestResponseDTO> assignEmployee(
            @PathVariable Long id,
            @Valid @RequestBody AssignEmployeeDTO assignDTO) {
        ServiceRequestResponseDTO response = serviceRequestService.assignEmployeeToRequest(id, assignDTO.getEmployeeId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ServiceRequestResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusDTO statusDTO) {
        ServiceRequestResponseDTO response = serviceRequestService.updateRequestStatus(id, statusDTO.getStatus());
        return ResponseEntity.ok(response);
    }
}
