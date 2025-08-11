package com.prodabel.atendimentos.repository;

import com.prodabel.atendimentos.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByRegistrationNumber(String registrationNumber);
}
