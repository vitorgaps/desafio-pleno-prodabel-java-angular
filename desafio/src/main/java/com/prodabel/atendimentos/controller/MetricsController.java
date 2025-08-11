package com.prodabel.atendimentos.controller;

import com.prodabel.atendimentos.dto.NeighborhoodMetricDTO;
import com.prodabel.atendimentos.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
    private final ServiceRequestService serviceRequestService;

    @Autowired
    public MetricsController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping("/services-by-neighborhood")
    public ResponseEntity<List<NeighborhoodMetricDTO>> getServicesByNeighborhood() {
        List<NeighborhoodMetricDTO> metrics = serviceRequestService.getNeighborhoodMetrics();
        return ResponseEntity.ok(metrics);
    }
}
