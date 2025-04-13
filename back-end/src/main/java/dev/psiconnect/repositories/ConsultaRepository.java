package dev.psiconnect.repositories;

import dev.psiconnect.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository <Consulta, Long> {
}



