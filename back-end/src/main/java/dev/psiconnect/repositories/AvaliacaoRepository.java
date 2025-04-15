package dev.psiconnect.repositories;

import dev.psiconnect.entities.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository <Avaliacao, Long> {
}
