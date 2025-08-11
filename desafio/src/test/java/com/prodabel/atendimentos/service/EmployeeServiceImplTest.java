package com.prodabel.atendimentos.service;

import com.prodabel.atendimentos.dto.EmployeeRequestDTO;
import com.prodabel.atendimentos.dto.EmployeeResponseDTO;
import com.prodabel.atendimentos.entity.Employee;
import com.prodabel.atendimentos.exception.ResourceAlreadyExistsException;
import com.prodabel.atendimentos.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void whenCreateEmployee_withValidData_shouldReturnEmployeeResponseDTO() {
        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setName("Novo Funcionário");
        requestDTO.setRegistrationNumber("12345");
        requestDTO.setPassword("senhaSegura");

        when(employeeRepository.findByRegistrationNumber(requestDTO.getRegistrationNumber())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("senhaHasheada");
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            emp.setId(1L);
            return emp;
        });

        // Act
        EmployeeResponseDTO responseDTO = employeeService.createEmployee(requestDTO);

        // Assert
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(1L);
        assertThat(responseDTO.getRegistrationNumber()).isEqualTo("12345");
    }

    @Test
    void whenCreateEmployee_withExistingRegistrationNumber_shouldThrowException() {
        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setRegistrationNumber("12345");

        when(employeeRepository.findByRegistrationNumber(requestDTO.getRegistrationNumber())).thenReturn(Optional.of(new Employee()));

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            employeeService.createEmployee(requestDTO);
        });
    }
}
