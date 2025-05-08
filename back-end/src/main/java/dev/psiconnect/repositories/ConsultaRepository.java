package dev.psiconnect.repositories;

import dev.psiconnect.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository <Consulta, Long> {
    List<Consulta> findByPsicologoId(Long idPsicologo);
    List<Consulta> findByPsicologoIdAndStatus(Long id, Consulta.Status status);
}



