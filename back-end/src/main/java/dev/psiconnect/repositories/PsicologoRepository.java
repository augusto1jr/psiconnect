package dev.psiconnect.repositories;

import dev.psiconnect.entities.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório Spring Data JPA para a entidade {@link Psicologo}.
 *
 * Fornece operações básicas de CRUD para psicólogos,
 * além de métodos para busca por e-mail e para recuperar
 * os psicólogos mais recomendados com base em avaliações e consultas concluídas.
 */
@Repository
public interface PsicologoRepository extends JpaRepository<Psicologo, Long> {

    /**
     * Busca um psicólogo pelo seu e-mail.
     *
     * @param email o e-mail do psicólogo
     * @return o psicólogo encontrado, ou null se nenhum psicólogo possuir o e-mail informado
     */
    Psicologo findByEmail(String email);

    /**
     * Recupera os top 10 psicólogos recomendados, ordenados pela nota média das avaliações (descendente),
     * pela quantidade total de avaliações (descendente) e pela quantidade total de consultas concluídas (descendente).
     *
     * A consulta utiliza JOINs para agregar dados de avaliações e consultas relacionadas.
     *
     * @return lista dos 10 psicólogos mais recomendados
     */
    @Query(value = """
    SELECT p.*
    FROM psicologos p
    LEFT JOIN (
        SELECT a.id_psicologo, COUNT(*) AS total_avaliacoes, AVG(a.nota) AS nota_media
        FROM avaliacoes a
        GROUP BY a.id_psicologo
    ) res_avaliacoes ON p.id = res_avaliacoes.id_psicologo
    LEFT JOIN (
        SELECT c.id_psicologo, COUNT(*) AS total_concluidas
        FROM consultas c
        WHERE c.status = 'CONCLUIDA'
        GROUP BY c.id_psicologo
    ) res_consultas ON p.id = res_consultas.id_psicologo
    ORDER BY 
        COALESCE(res_avaliacoes.nota_media, 0) DESC,
        COALESCE(res_avaliacoes.total_avaliacoes, 0) DESC,
        COALESCE(res_consultas.total_concluidas, 0) DESC
    LIMIT 10
    """, nativeQuery = true)
    List<Psicologo> findTopRecomendados();

}
