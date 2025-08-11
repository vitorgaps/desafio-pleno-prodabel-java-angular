package com.prodabel.atendimentos.service;

import com.prodabel.atendimentos.dto.EmployeeRequestDTO;
import com.prodabel.atendimentos.dto.EmployeeResponseDTO;
import com.prodabel.atendimentos.entity.Employee;
import com.prodabel.atendimentos.exception.ResourceAlreadyExistsException;
import com.prodabel.atendimentos.exception.ResourceNotFoundException;
import com.prodabel.atendimentos.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeDTO) {
        employeeRepository.findByRegistrationNumber(employeeDTO.getRegistrationNumber()).ifPresent(employee -> {
            throw new ResourceAlreadyExistsException("An employee with registration number " + employeeDTO.getRegistrationNumber() + " already exists.");
        });

        Employee employee = convertToEntity(employeeDTO);

        String encodedPassword = passwordEncoder.encode(employeeDTO.getPassword());
        employee.setPassword(encodedPassword);

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponseDTO(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return convertToResponseDTO(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> findAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        if (!existingEmployee.getRegistrationNumber().equals(employeeDTO.getRegistrationNumber())) {
            employeeRepository.findByRegistrationNumber(employeeDTO.getRegistrationNumber()).ifPresent(otherEmployee -> {
                throw new ResourceAlreadyExistsException("The registration number " + employeeDTO.getRegistrationNumber() + " is already in use by another employee.");
            });
        }

        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setRegistrationNumber(employeeDTO.getRegistrationNumber());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return convertToResponseDTO(updatedEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }

        try {
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot delete employee with id " + id + ". They may have assigned service requests.", e);
        }
    }

    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setRegistrationNumber(employee.getRegistrationNumber());
        return dto;
    }

    private Employee convertToEntity(EmployeeRequestDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setRegistrationNumber(dto.getRegistrationNumber());
        employee.setEmail(dto.getEmail());
        return employee;
    }
}
