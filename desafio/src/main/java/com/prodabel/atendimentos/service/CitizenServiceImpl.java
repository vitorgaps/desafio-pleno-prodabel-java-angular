package com.prodabel.atendimentos.service;

import com.prodabel.atendimentos.dto.CitizenRequestDTO;
import com.prodabel.atendimentos.dto.CitizenResponseDTO;
import com.prodabel.atendimentos.entity.Citizen;
import com.prodabel.atendimentos.exception.ResourceAlreadyExistsException;
import com.prodabel.atendimentos.exception.ResourceNotFoundException;
import com.prodabel.atendimentos.repository.CitizenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitizenServiceImpl  implements CitizenService{
    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CitizenResponseDTO createCitizen(CitizenRequestDTO citizenDTO) {
        citizenRepository.findByCpf(citizenDTO.getCpf()).ifPresent(citizen -> {
            throw new ResourceAlreadyExistsException("A citizen with CPF " + citizenDTO.getCpf() + " already exists.");
        });

        Citizen citizen = convertToEntity(citizenDTO);

        String encodedPassword = passwordEncoder.encode(citizenDTO.getPassword());
        citizen.setPassword(encodedPassword);

        Citizen savedCitizen = citizenRepository.save(citizen);

        return convertToResponseDTO(savedCitizen);
    }

    @Override
    @Transactional(readOnly = true)
    public CitizenResponseDTO findCitizenById(Long id) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found with id: " + id));
        return convertToResponseDTO(citizen);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitizenResponseDTO> findAllCitizens() {
        return citizenRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CitizenResponseDTO updateCitizen(Long id, CitizenRequestDTO citizenDTO) {
        Citizen existingCitizen = citizenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found with id: " + id));

        if (!existingCitizen.getCpf().equals(citizenDTO.getCpf())) {
            citizenRepository.findByCpf(citizenDTO.getCpf()).ifPresent(otherCitizen -> {
                throw new ResourceAlreadyExistsException("The CPF " + citizenDTO.getCpf() + " is already in use by another citizen.");
            });
        }

        existingCitizen.setName(citizenDTO.getName());
        existingCitizen.setCpf(citizenDTO.getCpf());

        Citizen updatedCitizen = citizenRepository.save(existingCitizen);

        return convertToResponseDTO(updatedCitizen);
    }

    @Override
    @Transactional
    public void deleteCitizen(Long id) {
        if (!citizenRepository.existsById(id)) {
            throw new ResourceNotFoundException("Citizen not found with id: " + id);
        }

        citizenRepository.deleteById(id);
    }

    private CitizenResponseDTO convertToResponseDTO(Citizen citizen) {
        CitizenResponseDTO dto = new CitizenResponseDTO();
        dto.setId(citizen.getId());
        dto.setName(citizen.getName());
        dto.setCpf(citizen.getCpf());
        return dto;
    }

    private Citizen convertToEntity(CitizenRequestDTO dto) {
        Citizen citizen = new Citizen();
        citizen.setName(dto.getName());
        citizen.setCpf(dto.getCpf());
        citizen.setEmail(dto.getEmail());
        return citizen;
    }
}
