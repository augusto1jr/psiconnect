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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Transactional
    public void saveAvaliacao(@RequestBody AvaliacaoRequestDTO data) {
        Psicologo psicologo = psicologoRepository.findById(data.idPsicologo()).orElse(null);
        Paciente paciente = pacienteRepository.findById(data.idPaciente()).orElse(null);
        Consulta consulta = consultaRepository.findById(data.idConsulta()).orElse(null);

        if (psicologo == null || paciente == null || consulta == null) {
            return;
        }

        if (!consulta.getPsicologo().getId().equals(psicologo.getId()) ||
                !consulta.getPaciente().getId().equals(paciente.getId())) {
            return;
        }

        Avaliacao avaliacao = new Avaliacao(data, consulta, psicologo, paciente);
        avaliacaoRepository.save(avaliacao);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> getAllAvaliacoes() {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoRepository.findAll().stream()
                .map(AvaliacaoResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(avaliacoes);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> getAvaliacaoById(@PathVariable Long id) {
        return avaliacaoRepository.findById(id)
                .map(avaliacao -> ResponseEntity.ok(new AvaliacaoResponseDTO(avaliacao)))
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/consulta/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> getAvaliacaoByConsultaId(@PathVariable Long id) {
        return avaliacaoRepository.findByConsultaId(id)
                .map(avaliacao -> ResponseEntity.ok(new AvaliacaoResponseDTO(avaliacao)))
                .orElse(ResponseEntity.notFound().build());
    }

}
