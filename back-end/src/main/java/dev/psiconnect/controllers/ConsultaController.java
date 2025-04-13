package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.ConsultaRequestDTO;
import dev.psiconnect.dtos.responses.ConsultaResponseDTO;
import dev.psiconnect.entities.Consulta;
import dev.psiconnect.entities.Paciente;
import dev.psiconnect.entities.Psicologo;
import dev.psiconnect.repositories.ConsultaRepository;
import dev.psiconnect.repositories.PacienteRepository;
import dev.psiconnect.repositories.PsicologoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Transactional
    public void saveConsulta(@RequestBody ConsultaRequestDTO data) {
        Psicologo psicologo = psicologoRepository.findById(data.idPsicologo()).orElse(null);
        Paciente paciente = pacienteRepository.findById(data.idPaciente()).orElse(null);

        if (psicologo == null || paciente == null) {
            return;
        }

        Consulta consulta = new Consulta(data, psicologo, paciente);
        consultaRepository.save(consulta);
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> getAllConsultas() {
        List<ConsultaResponseDTO> consultas = consultaRepository.findAll().stream()
                .map(ConsultaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(consultas);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> getConsultaById(@PathVariable Long id) {
        return consultaRepository.findById(id)
                .map(consulta -> ResponseEntity.ok(new ConsultaResponseDTO(consulta)))
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateConsulta(@PathVariable Long id, @RequestBody ConsultaRequestDTO data) {
        return consultaRepository.findById(id)
                .map(consulta -> {

                    consulta.setDataConsulta(data.dataConsulta());
                    consulta.setStatus(data.status());

                    consultaRepository.save(consulta);
                    return ResponseEntity.ok(new ConsultaResponseDTO(consulta));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
