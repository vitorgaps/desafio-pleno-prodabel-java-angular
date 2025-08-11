package com.prodabel.atendimentos.service;

import com.prodabel.atendimentos.dto.CitizenRequestDTO;
import com.prodabel.atendimentos.dto.CitizenResponseDTO;
import com.prodabel.atendimentos.entity.Citizen;
import com.prodabel.atendimentos.exception.ResourceAlreadyExistsException;
import com.prodabel.atendimentos.repository.CitizenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CitizenServiceImplTest {
    @Mock
    private CitizenRepository citizenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CitizenServiceImpl citizenService;

    @Test
    void whenCreateCitizen_withValidData_shouldReturnCitizenResponseDTO() {
        // Arrange
        CitizenRequestDTO requestDTO = new CitizenRequestDTO();
        requestDTO.setName("Novo Cidadão");
        requestDTO.setCpf("12345678901");
        requestDTO.setEmail("novo@email.com");
        requestDTO.setPassword("senha123");

        when(citizenRepository.findByCpf(requestDTO.getCpf())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("senhaCriptografada");
        when(citizenRepository.save(any(Citizen.class))).thenAnswer(invocation -> {
            Citizen citizenToSave = invocation.getArgument(0);
            citizenToSave.setId(1L);
            return citizenToSave;
        });

        // Act
        CitizenResponseDTO responseDTO = citizenService.createCitizen(requestDTO);

        // Assert
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(1L);
        assertThat(responseDTO.getName()).isEqualTo("Novo Cidadão");
    }

    @Test
    void whenCreateCitizen_withExistingCpf_shouldThrowResourceAlreadyExistsException() {
        // Arrange
        CitizenRequestDTO requestDTO = new CitizenRequestDTO();
        requestDTO.setCpf("12345678901");
        when(citizenRepository.findByCpf(requestDTO.getCpf())).thenReturn(Optional.of(new Citizen()));

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            citizenService.createCitizen(requestDTO);
        });
    }
}
