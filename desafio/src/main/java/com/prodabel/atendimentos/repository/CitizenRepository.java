package com.prodabel.atendimentos.repository;

import com.prodabel.atendimentos.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findByCpf(String cpf);
}
