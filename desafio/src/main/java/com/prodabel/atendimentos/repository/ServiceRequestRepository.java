package com.prodabel.atendimentos.repository;

import com.prodabel.atendimentos.dto.NeighborhoodMetricDTO;
import com.prodabel.atendimentos.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    @Query("SELECT new com.prodabel.atendimentos.dto.NeighborhoodMetricDTO(s.neighborhood, COUNT(s.id)) " +
            "FROM ServiceRequest s GROUP BY s.neighborhood")
    List<NeighborhoodMetricDTO> countServicesByNeighborhood();
}
