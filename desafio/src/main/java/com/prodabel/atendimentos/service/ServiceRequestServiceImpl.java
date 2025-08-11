package com.prodabel.atendimentos.service;

import com.prodabel.atendimentos.dto.NeighborhoodMetricDTO;
import com.prodabel.atendimentos.dto.ServiceRequestCreateDTO;
import com.prodabel.atendimentos.dto.ServiceRequestResponseDTO;
import com.prodabel.atendimentos.entity.Citizen;
import com.prodabel.atendimentos.entity.Employee;
import com.prodabel.atendimentos.entity.ServiceRequest;
import com.prodabel.atendimentos.exception.ResourceNotFoundException;
import com.prodabel.atendimentos.repository.CitizenRepository;
import com.prodabel.atendimentos.repository.EmployeeRepository;
import com.prodabel.atendimentos.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRequestServiceImpl implements ServiceRequestService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final CitizenRepository citizenRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ServiceRequestServiceImpl(
            ServiceRequestRepository serviceRequestRepository,
            CitizenRepository citizenRepository,
            EmployeeRepository employeeRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.citizenRepository = citizenRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public ServiceRequestResponseDTO createServiceRequest(ServiceRequestCreateDTO requestDTO) {
        Citizen citizen = citizenRepository.findById(requestDTO.getCitizenId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot create service request. Citizen not found with id: " + requestDTO.getCitizenId()));

        ServiceRequest newRequest = new ServiceRequest();
        newRequest.setTitle(requestDTO.getTitle());
        newRequest.setDescription(requestDTO.getDescription());
        newRequest.setNeighborhood(requestDTO.getNeighborhood());
        newRequest.setCitizen(citizen);
        newRequest.setStatus("OPEN");

        ServiceRequest savedRequest = serviceRequestRepository.save(newRequest);

        return convertToResponseDTO(savedRequest);
    }

    @Override
    @Transactional
    public ServiceRequestResponseDTO assignEmployeeToRequest(Long requestId, Long employeeId) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request not found with id: " + requestId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        request.setAssignedEmployee(employee);
        request.setStatus("IN_PROGRESS");

        ServiceRequest updatedRequest = serviceRequestRepository.save(request);

        return convertToResponseDTO(updatedRequest);
    }

    @Override
    @Transactional
    public ServiceRequestResponseDTO updateRequestStatus(Long requestId, String newStatus) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request not found with id: " + requestId));

        request.setStatus(newStatus.toUpperCase());
        ServiceRequest updatedRequest = serviceRequestRepository.save(request);

        return convertToResponseDTO(updatedRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NeighborhoodMetricDTO> getNeighborhoodMetrics() {
        return serviceRequestRepository.countServicesByNeighborhood();
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceRequestResponseDTO findServiceRequestById(Long id) {
        ServiceRequest request = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request not found with id: " + id));
        return convertToResponseDTO(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestResponseDTO> findAllServiceRequests() {
        return serviceRequestRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private ServiceRequestResponseDTO convertToResponseDTO(ServiceRequest request) {
        ServiceRequestResponseDTO dto = new ServiceRequestResponseDTO();
        dto.setId(request.getId());
        dto.setTitle(request.getTitle());
        dto.setDescription(request.getDescription());
        dto.setNeighborhood(request.getNeighborhood());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());

        ServiceRequestResponseDTO.CitizenInfo citizenInfo = new ServiceRequestResponseDTO.CitizenInfo();
        citizenInfo.setId(request.getCitizen().getId());
        citizenInfo.setName(request.getCitizen().getName());
        dto.setCitizen(citizenInfo);

        if (request.getAssignedEmployee() != null) {
            ServiceRequestResponseDTO.EmployeeInfo employeeInfo = new ServiceRequestResponseDTO.EmployeeInfo();
            employeeInfo.setId(request.getAssignedEmployee().getId());
            employeeInfo.setName(request.getAssignedEmployee().getName());
            dto.setAssignedEmployee(employeeInfo);
        }

        return dto;
    }
}
