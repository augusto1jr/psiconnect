package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.AvaliacaoRequestDTO;
import dev.psiconnect.dtos.responses.AvaliacaoResponseDTO;
import dev.psiconnect.entities.Avaliacao;
import dev.psiconnect.entities.Consulta;
import dev.psiconnect.entities.Paciente;
import dev.psiconnect.entities.Psicologo;
import dev.psiconnect.repositories.AvaliacaoRepository;
import dev.psiconnect.repositories.ConsultaRepository;
import dev.psiconnect.repositories.PacienteRepository;
import dev.psiconnect.repositories.PsicologoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador responsável pelos endpoints relacionados às avaliações.
 */
@RestController
@RequestMapping("avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * Endpoint para salvar uma nova avaliação.
     *
     * @param data dados da avaliação enviados pelo cliente
     * @return ResponseEntity com status 201 se criado com sucesso, 400 se dados inválidos
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Transactional
    public ResponseEntity<String> saveAvaliacao(@RequestBody AvaliacaoRequestDTO data) {
        Psicologo psicologo = psicologoRepository.findById(data.idPsicologo()).orElse(null);
        Paciente paciente = pacienteRepository.findById(data.idPaciente()).orElse(null);
        Consulta consulta = consultaRepository.findById(data.idConsulta()).orElse(null);

        if (psicologo == null || paciente == null || consulta == null) {
            return ResponseEntity.badRequest().body("Psicólogo, paciente ou consulta não encontrados.");
        }

        if (!consulta.getPsicologo().getId().equals(psicologo.getId()) ||
                !consulta.getPaciente().getId().equals(paciente.getId())) {
            return ResponseEntity.badRequest().body("Psicólogo ou paciente não correspondem à consulta.");
        }

        Avaliacao avaliacao = new Avaliacao(data, consulta, psicologo, paciente);
        avaliacaoRepository.save(avaliacao);

        return ResponseEntity.status(201).body("Avaliação criada com sucesso.");
    }

    /**
     * Endpoint para buscar todas as avaliações cadastradas.
     *
     * @return lista de AvaliacaoResponseDTO e status HTTP 200
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> getAllAvaliacoes() {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoRepository.findAll().stream()
                .map(AvaliacaoResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(avaliacoes);
    }

    /**
     * Endpoint para buscar uma avaliação pelo ID.
     *
     * @param id identificador da avaliação
     * @return AvaliacaoResponseDTO se encontrada, ou status HTTP 404
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> getAvaliacaoById(@PathVariable Long id) {
        return avaliacaoRepository.findById(id)
                .map(avaliacao -> ResponseEntity.ok(new AvaliacaoResponseDTO(avaliacao)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para buscar uma avaliação a partir do ID de uma consulta.
     *
     * @param id identificador da consulta
     * @return AvaliacaoResponseDTO correspondente, ou status HTTP 404
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/consulta/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> getAvaliacaoByConsultaId(@PathVariable Long id) {
        return avaliacaoRepository.findByConsultaId(id)
                .map(avaliacao -> ResponseEntity.ok(new AvaliacaoResponseDTO(avaliacao)))
                .orElse(ResponseEntity.notFound().build());
    }
}
