package com.prodabel.atendimentos.repository;

import com.prodabel.atendimentos.entity.Citizen;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CitizenRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CitizenRepository citizenRepository;

    @Test
    void whenFindByCpf_withExistingCitizen_shouldReturnCitizen() {
        // Arrange
        Citizen citizen = new Citizen();
        citizen.setName("Cidadão Teste");
        citizen.setCpf("09876543211");
        citizen.setEmail("teste@email.com");
        citizen.setPassword("senhaHash");
        entityManager.persistAndFlush(citizen);

        // Act
        Optional<Citizen> found = citizenRepository.findByCpf("09876543211");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(citizen.getName());
    }

    @Test
    void whenFindByCpf_withNonExistingCitizen_shouldReturnEmpty() {
        // Act
        Optional<Citizen> found = citizenRepository.findByCpf("cpf_inexistente");

        // Assert
        assertThat(found).isNotPresent();
    }
}
