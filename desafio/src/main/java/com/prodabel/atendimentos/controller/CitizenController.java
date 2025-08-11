package com.prodabel.atendimentos.controller;

import com.prodabel.atendimentos.dto.CitizenRequestDTO;
import com.prodabel.atendimentos.dto.CitizenResponseDTO;
import com.prodabel.atendimentos.service.CitizenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {
    private final CitizenService citizenService;

    @Autowired
    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PostMapping
    public ResponseEntity<CitizenResponseDTO> createCitizen(@Valid @RequestBody CitizenRequestDTO citizenDTO) {
        CitizenResponseDTO createdCitizen = citizenService.createCitizen(citizenDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCitizen);
    }

    @GetMapping
    public ResponseEntity<List<CitizenResponseDTO>> getAllCitizens() {
        List<CitizenResponseDTO> citizens = citizenService.findAllCitizens();
        return ResponseEntity.ok(citizens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenResponseDTO> getCitizenById(@PathVariable Long id) {
        CitizenResponseDTO citizen = citizenService.findCitizenById(id);
        return ResponseEntity.ok(citizen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitizenResponseDTO> updateCitizen(@PathVariable Long id, @Valid @RequestBody CitizenRequestDTO citizenDTO) {
        CitizenResponseDTO updatedCitizen = citizenService.updateCitizen(id, citizenDTO);
        return ResponseEntity.ok(updatedCitizen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCitizen(@PathVariable Long id) {
        citizenService.deleteCitizen(id);
        return ResponseEntity.noContent().build();
    }
}
