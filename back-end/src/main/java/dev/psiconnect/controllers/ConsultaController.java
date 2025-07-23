package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.ConsultaRequestDTO;
import dev.psiconnect.dtos.responses.ConsultaResponseDTO;
import dev.psiconnect.entities.Consulta;
import dev.psiconnect.entities.Paciente;
import dev.psiconnect.entities.Psicologo;

import dev.psiconnect.services.ConsultaService;
import dev.psiconnect.services.PacienteService;
import dev.psiconnect.services.PsicologoService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador responsável pelos endpoints relacionados às consultas.
 */
@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private PsicologoService psicologoService;

    @Autowired
    private PacienteService pacienteService;

    /**
     * Endpoint para salvar uma nova consulta.
     *
     * @param data dados da consulta recebidos no corpo da requisição
     * @return ResponseEntity com status 201 se criado com sucesso ou 400 se dados inválidos
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Transactional
    public ResponseEntity<String> saveConsulta(@RequestBody ConsultaRequestDTO data) {
        Psicologo psicologo = psicologoService.buscarPorId(data.idPsicologo()).orElse(null);
        Paciente paciente = pacienteService.buscarPorId(data.idPaciente()).orElse(null);

        if (psicologo == null || paciente == null) {
            return ResponseEntity.badRequest().body("Psicólogo ou paciente não encontrados.");
        }

        Consulta consulta = new Consulta(data, psicologo, paciente);
        consultaService.salvar(consulta);

        return ResponseEntity.status(201).body("Consulta criada com sucesso.");
    }

    /**
     * Endpoint para buscar todas as consultas cadastradas.
     *
     * @return lista de ConsultaResponseDTO com status 200
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> getAllConsultas() {
        List<ConsultaResponseDTO> consultas = consultaService.buscarTodos().stream()
                .map(ConsultaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(consultas);
    }

    /**
     * Endpoint para buscar uma consulta pelo ID.
     *
     * @param id identificador da consulta
     * @return ConsultaResponseDTO se encontrada, ou 404 se não encontrada
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> getConsultaById(@PathVariable Long id) {
        return consultaService.buscarPorId(id)
                .map(consulta -> ResponseEntity.ok(new ConsultaResponseDTO(consulta)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para atualizar uma consulta existente.
     *
     * @param id   identificador da consulta a ser atualizada
     * @param data novos dados da consulta
     * @return Consulta atualizada (DTO) ou 404 se não encontrada
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateConsulta(@PathVariable Long id, @RequestBody ConsultaRequestDTO data) {
        return consultaService.buscarPorId(id)
                .map(consulta -> {
                    consulta.setDataConsulta(data.dataConsulta());
                    consulta.setStatus(data.status());

                    consultaService.salvar(consulta);
                    return ResponseEntity.ok(new ConsultaResponseDTO(consulta));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
