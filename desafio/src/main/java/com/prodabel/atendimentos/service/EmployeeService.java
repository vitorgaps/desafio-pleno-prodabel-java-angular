package com.prodabel.atendimentos.service;

import com.prodabel.atendimentos.dto.EmployeeRequestDTO;
import com.prodabel.atendimentos.dto.EmployeeResponseDTO;
import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeDTO);
    EmployeeResponseDTO findEmployeeById(Long id);
    List<EmployeeResponseDTO> findAllEmployees();
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeDTO);
    void deleteEmployee(Long id);
}
