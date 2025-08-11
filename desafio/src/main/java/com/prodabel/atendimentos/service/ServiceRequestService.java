package com.prodabel.atendimentos.service;

import com.prodabel.atendimentos.dto.NeighborhoodMetricDTO;
import com.prodabel.atendimentos.dto.ServiceRequestCreateDTO;
import com.prodabel.atendimentos.dto.ServiceRequestResponseDTO;

import java.util.List;

public interface ServiceRequestService {
    ServiceRequestResponseDTO createServiceRequest(ServiceRequestCreateDTO requestDTO);
    ServiceRequestResponseDTO findServiceRequestById(Long id);
    List<ServiceRequestResponseDTO> findAllServiceRequests();
    ServiceRequestResponseDTO assignEmployeeToRequest(Long requestId, Long employeeId);
    ServiceRequestResponseDTO updateRequestStatus(Long requestId, String newStatus);
    List<NeighborhoodMetricDTO> getNeighborhoodMetrics();
}
