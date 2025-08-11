package com.prodabel.atendimentos.service;

import com.prodabel.atendimentos.dto.CitizenRequestDTO;
import com.prodabel.atendimentos.dto.CitizenResponseDTO;

import java.util.List;

public interface CitizenService {
    CitizenResponseDTO createCitizen(CitizenRequestDTO citizenDTO);
    CitizenResponseDTO findCitizenById(Long id);
    List<CitizenResponseDTO> findAllCitizens();
    CitizenResponseDTO updateCitizen(Long id, CitizenRequestDTO citizenDTO);
    void deleteCitizen(Long id);
}
