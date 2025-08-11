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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceRequestServiceImplTest {

    @Mock
    private ServiceRequestRepository serviceRequestRepository;
    @Mock
    private CitizenRepository citizenRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ServiceRequestServiceImpl serviceRequestService;

    @Test
    void whenCreateServiceRequest_withValidCitizen_shouldReturnResponseDTO() {
        // Arrange
        ServiceRequestCreateDTO createDTO = new ServiceRequestCreateDTO();
        createDTO.setCitizenId(1L);
        createDTO.setTitle("Poste quebrado");
        createDTO.setNeighborhood("Centro");

        Citizen mockCitizen = new Citizen();
        mockCitizen.setId(1L);
        mockCitizen.setName("Cidadão Exemplo");

        when(citizenRepository.findById(1L)).thenReturn(Optional.of(mockCitizen));
        when(serviceRequestRepository.save(any(ServiceRequest.class))).thenAnswer(invocation -> {
            ServiceRequest sr = invocation.getArgument(0);
            sr.setId(100L);
            return sr;
        });

        // Act
        ServiceRequestResponseDTO response = serviceRequestService.createServiceRequest(createDTO);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(100L);
        assertThat(response.getStatus()).isEqualTo("OPEN");
        assertThat(response.getCitizen().getName()).isEqualTo("Cidadão Exemplo");
    }

    @Test
    void whenAssignEmployee_withValidIds_shouldSucceed() {
        // Arrange
        ServiceRequest mockRequest = new ServiceRequest();
        mockRequest.setId(100L);
        mockRequest.setCitizen(new Citizen());

        Employee mockEmployee = new Employee();
        mockEmployee.setId(20L);

        when(serviceRequestRepository.findById(100L)).thenReturn(Optional.of(mockRequest));
        when(employeeRepository.findById(20L)).thenReturn(Optional.of(mockEmployee));
        when(serviceRequestRepository.save(any(ServiceRequest.class))).thenReturn(mockRequest);

        // Act
        ServiceRequestResponseDTO response = serviceRequestService.assignEmployeeToRequest(100L, 20L);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("IN_PROGRESS");
        assertThat(response.getAssignedEmployee()).isNotNull();
        assertThat(response.getAssignedEmployee().getId()).isEqualTo(20L);
    }

    @Test
    void whenAssignEmployee_withInvalidRequestId_shouldThrowException() {
        // Arrange
        when(serviceRequestRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            serviceRequestService.assignEmployeeToRequest(999L, 20L);
        });
    }

    @Test
    void whenGetNeighborhoodMetrics_shouldReturnMetricsList() {
        // Arrange
        List<NeighborhoodMetricDTO> mockMetrics = Collections.singletonList(
                new NeighborhoodMetricDTO("Centro", 5L)
        );
        when(serviceRequestRepository.countServicesByNeighborhood()).thenReturn(mockMetrics);

        // Act
        List<NeighborhoodMetricDTO> metrics = serviceRequestService.getNeighborhoodMetrics();

        // Assert
        assertThat(metrics).isNotNull();
        assertThat(metrics.get(0).getNeighborhood()).isEqualTo("Centro");
    }
}
